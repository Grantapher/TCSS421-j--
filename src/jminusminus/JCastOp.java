// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.Hashtable;

import static jminusminus.CLConstants.*;

/**
 * The AST for an cast expression, which has both a cast (a type) and the
 * expression to be cast.
 */

class JCastOp extends JExpression {

    /**
     * The cast.
     */
    private Type cast;

    /**
     * The expression we're casting.
     */
    private JExpression expr;

    /**
     * The conversions table.
     */
    private static Conversions conversions;

    /**
     * The converter to use for this cast.
     */
    private Converter converter;

    /**
     * Construct an AST node for a cast operation from its line number, cast,
     * and expression.
     *
     * @param line the line in which the operation occurs in the source file.
     * @param cast the type we're casting our expression as.
     * @param expr the expression we're casting.
     */

    public JCastOp(int line, Type cast, JExpression expr) {
        super(line);
        this.cast = cast;
        this.expr = expr;
        conversions = new Conversions();
    }

    /**
     * Analyzing a cast expression means, resolving the type (to which we are
     * casting), checking the legality of the cast, and computing a (possibly
     * null) conversion for use in code generation.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        expr = (JExpression) expr.analyze(context);
        type = cast = cast.resolve(context);
        if (cast.equals(expr.type())) {
            converter = Converter.Identity;
        } else if (cast.isJavaAssignableFrom(expr.type())) {
            converter = Converter.WidenReference;
        } else if (expr.type().isJavaAssignableFrom(cast)) {
            converter = new NarrowReference(cast);
        } else if ((converter = conversions.get(expr.type(), cast)) != null) {
        } else {
            JAST.compilationUnit.reportSemanticError(line, "Cannot cast a " +
                                                             expr.type().toString() +
                                                             " to a " + cast.toString()
            );
        }
        return this;
    }

    /**
     * Generating code for a cast expression involves generating code for the
     * original expr, and then for any necessary conversion.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        expr.codegen(output);
        converter.codegen(output);
    }

    @Override
    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        codegen(output);
        output.addBranchInstruction(onTrue ? IFNE : IFEQ, targetLabel);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JCastOp line=\"%d\" type=\"%s\"/>\n", line(),
                 ((cast == null) ? "" : cast.toString())
        );
        p.indentRight();
        if (expr != null) {
            p.println("<Expression>");
            p.indentRight();
            expr.writeToStdOut(p);
            p.indentLeft();
            p.println("</Expression>");
        }
        p.indentLeft();
        p.println("</JCastOp>");
    }

}

/**
 * A 2-D table of conversions, from one type to another.
 */

class Conversions {

    /**
     * Table of conversions; maps a source and target type pair to its
     * converter.
     */
    private Hashtable<String, Converter> table;

    /**
     * Construct a table of conversions and populate it.
     */

    public Conversions() {
        table = new Hashtable<String, Converter>();

        // Populate the table

        put(Type.CHAR, Type.INT, Converter.Identity);
        //TODO more char casts?


        put(Type.DOUBLE, Type.FLOAT, new D2F());
        put(Type.DOUBLE, Type.INT, new D2I());
        put(Type.DOUBLE, Type.LONG, new D2L());

        put(Type.FLOAT, Type.DOUBLE, new F2D());
        put(Type.FLOAT, Type.INT, new F2I());
        put(Type.FLOAT, Type.LONG, new F2L());

        put(Type.INT, Type.CHAR, new I2C());
        put(Type.INT, Type.FLOAT, new I2F());
        put(Type.INT, Type.DOUBLE, new I2D());
        put(Type.INT, Type.LONG, new I2L());

        put(Type.LONG, Type.DOUBLE, new L2D());
        put(Type.LONG, Type.FLOAT, new L2F());
        put(Type.LONG, Type.INT, new L2I());

        // Boxing
        put(Type.CHAR, Type.BOXED_CHAR, new Boxing(Type.CHAR, Type.BOXED_CHAR));
        put(Type.INT, Type.BOXED_INT, new Boxing(Type.INT, Type.BOXED_INT));
        put(Type.LONG, Type.BOXED_LONG, new Boxing(Type.LONG, Type.BOXED_LONG));
        put(Type.FLOAT, Type.BOXED_FLOAT, new Boxing(Type.FLOAT, Type.BOXED_FLOAT));
        put(Type.DOUBLE, Type.BOXED_DOUBLE, new Boxing(Type.DOUBLE, Type.BOXED_DOUBLE));
        put(Type.LONG, Type.BOXED_LONG, new Boxing(Type.LONG, Type.BOXED_LONG));
        put(Type.BOOLEAN, Type.BOXED_BOOLEAN, new Boxing(Type.BOOLEAN, Type.BOXED_BOOLEAN)
        );

        // Un-boxing
        put(Type.BOXED_CHAR, Type.CHAR,
            new UnBoxing(Type.BOXED_CHAR, Type.CHAR, "charValue")
        );
        put(Type.BOXED_INT, Type.INT, new UnBoxing(Type.BOXED_INT, Type.INT, "intValue"));
        put(Type.BOXED_LONG, Type.LONG,
            new UnBoxing(Type.BOXED_LONG, Type.LONG, "longValue")
        );
        put(Type.BOXED_FLOAT, Type.FLOAT,
            new UnBoxing(Type.BOXED_FLOAT, Type.FLOAT, "floatValue")
        );
        put(Type.BOXED_DOUBLE, Type.DOUBLE,
            new UnBoxing(Type.BOXED_DOUBLE, Type.DOUBLE, "doubleValue")
        );
        put(Type.BOXED_BOOLEAN, Type.BOOLEAN,
            new UnBoxing(Type.BOXED_BOOLEAN, Type.BOOLEAN, "booleanValue")
        );
    }

    /**
     * Define a conversion. This is used locally, for populating the table.
     *
     * @param source the original type.
     * @param target the target type.
     * @param c      the converter necessary.
     */

    private void put(Type source, Type target, Converter c) {
        table.put(source.toDescriptor() + "2" + target.toDescriptor(), c);
    }

    /**
     * Retrieve a converter for converting from some original type to a target
     * type; the converter may be empty (requiring no code for run-time
     * execution).
     *
     * @param source the original type.
     * @param target the target type.
     * @return the converter.
     */

    public Converter get(Type source, Type target) {
        return table.get(source.toDescriptor() + "2" + target.toDescriptor());
    }

}

/**
 * A Converter encapusates any (possibly none) code necessary to perform a cast
 * operation.
 */

interface Converter {

    /**
     * For identity conversion (no run-time code needed).
     */
    public static Converter Identity = new Identity();

    /**
     * For widening conversion (no run-time code needed).
     */
    public static Converter WidenReference = Identity;

    /**
     * Emit code necessary to convert (cast) a source type to a target type.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output);

}

/**
 * The identity conversion requires no run-time code.
 */

class Identity implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        // Nothing
    }

}

/**
 * A narrowing conversion on a reference type requires a run-time check on the
 * type.
 */

class NarrowReference implements Converter {

    /**
     * The target type.
     */
    private Type target;

    /**
     * Construct a narrowing converter.
     *
     * @param target the target type.
     */

    public NarrowReference(Type target) {
        this.target = target;
    }

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addReferenceInstruction(CHECKCAST, target.jvmName());
    }

}

/**
 * Boxing requires invoking the appropriate conversion method from the (Java)
 * API.
 */

class Boxing implements Converter {

    /**
     * The source type.
     */
    private Type source;

    /**
     * The target type.
     */
    private Type target;

    /**
     * Construct a Boxing converter.
     *
     * @param source the source type.
     * @param target the target type.
     */

    public Boxing(Type source, Type target) {
        this.source = source;
        this.target = target;
    }

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addMemberAccessInstruction(INVOKESTATIC, target.jvmName(), "valueOf",
                                          "(" + source.toDescriptor() + ")" +
                                                  target.toDescriptor()
        );
    }

}

/**
 * Unboxing requires invoking the appropriate conversion method from the (Java)
 * API.
 */

class UnBoxing implements Converter {

    /**
     * The source type.
     */
    private Type source;

    /**
     * The target type.
     */
    private Type target;

    /**
     * The (Java) method to invoke for the conversion.
     */
    private String methodName;

    /**
     * Construct an UnBoxing converter.
     *
     * @param source     the source type.
     * @param target     the target type.
     * @param methodName the (Java) method to invoke for the conversion.
     */

    public UnBoxing(Type source, Type target, String methodName) {
        this.source = source;
        this.target = target;
        this.methodName = methodName;
    }

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addMemberAccessInstruction(INVOKEVIRTUAL, source.jvmName(), methodName,
                                          "()" + target.toDescriptor()
        );
    }

}

/**
 * Converting from a double to a float requires an D2F instruction.
 */

class D2F implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(D2F);
    }

}

/**
 * Converting from a double to an int requires an D2I instruction.
 */

class D2I implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(D2I);
    }

}

/**
 * Converting from a double to a long requires an D2L instruction.
 */

class D2L implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(D2L);
    }

}

/**
 * Converting from a float to a double requires an F2D instruction.
 */

class F2D implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(F2D);
    }

}

/**
 * Converting from a float to an int requires an F2I instruction.
 */

class F2I implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(F2I);
    }

}

/**
 * Converting from a float to a long requires an F2L instruction.
 */

class F2L implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(F2L);
    }

}

/**
 * Converting from an int to a char requires an I2C instruction.
 */

class I2C implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(I2C);
    }

}

/**
 * Converting from an int to a double requires an I2D instruction.
 */

class I2D implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(I2D);
    }

}

/**
 * Converting from an int to a float requires an I2F instruction.
 */

class I2F implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(I2F);
    }

}

/**
 * Converting from an int to a long requires an I2L instruction.
 */

class I2L implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(I2L);
    }

}

/**
 * Converting from a long to a double requires an L2D instruction.
 */

class L2D implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(L2D);
    }

}

/**
 * Converting from a long to a float requires an L2F instruction.
 */

class L2F implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(L2F);
    }

}

/**
 * Converting from a long to an int requires an L2I instruction.
 */

class L2I implements Converter {

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(L2I);
    }

}
