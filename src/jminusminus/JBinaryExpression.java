// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a binary expression. A binary expression has an operator and
 * two operands: a lhs and a rhs.
 */

abstract class JBinaryExpression extends JExpression {

    /**
     * The binary operator.
     */
    protected String operator;

    /**
     * The lhs operand.
     */
    protected JExpression lhs;

    /**
     * The rhs operand.
     */
    protected JExpression rhs;

    /**
     * Construct an AST node for a binary expression given its line number, the
     * binary operator, and lhs and rhs operands.
     *
     * @param line     line in which the binary expression occurs in the source file.
     * @param operator the binary operator.
     * @param lhs      the lhs operand.
     * @param rhs      the rhs operand.
     */

    protected JBinaryExpression(int line, String operator, JExpression lhs,
                                JExpression rhs) {
        super(line);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBinaryExpression line=\"%d\" type=\"%s\" " + "operator=\"%s\">\n",
                line(), ((type == null) ? "" : type.toString()),
                Util.escapeSpecialXMLChars(operator)
        );
        p.indentRight();
        p.printf("<Lhs>\n");
        p.indentRight();
        lhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Lhs>\n");
        p.printf("<Rhs>\n");
        p.indentRight();
        rhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs>\n");
        p.indentLeft();
        p.printf("</JBinaryExpression>\n");
    }

}

/**
 * The AST node for a plus (+) expression. In j--, as in Java, + is overloaded
 * to denote addition for numbers and concatenation for Strings.
 */

class JPlusOp extends JBinaryExpression {

    /**
     * Construct an AST node for an addition expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the addition expression occurs in the source
     *             file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JPlusOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+", lhs, rhs);
    }

    /**
     * Analysis involves first analyzing the operands. If this is a string
     * concatenation, we rewrite the subtree to make that explicit (and analyze
     * that). Otherwise we check the types of the addition operands and compute
     * the result type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.STRING || rhs.type() == Type.STRING) {
            return (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
        } else {
            lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
            rhs.type().mustMatchExpected(line(), lhs.type());
            this.type = lhs.type();
        }
        return this;
    }

    /**
     * Any string concatenation has been rewritten as a JStringConcatenationOp
     * (in analyze()), so code generation here involves simply generating code
     * for loading the operands onto the stack and then generating the
     * appropriate add instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IADD);
        } else if (type().equals(Type.LONG)) {
            output.addNoArgInstruction(LADD);
        } else if (type().equals(Type.FLOAT)) {
            output.addNoArgInstruction(FADD);
        } else { //double
            output.addNoArgInstruction(DADD);
        }
    }

}

/**
 * The AST node for a subtraction (-) expression.
 */

class JSubtractOp extends JBinaryExpression {

    /**
     * Construct an AST node for a subtraction expression given its line number,
     * and lhs and rhs operands.
     *
     * @param line line in which the subtraction expression occurs in the source
     *             file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JSubtractOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-", lhs, rhs);
    }

    /**
     * Analyzing the - operation involves analyzing its operands, checking
     * types, and determining the result type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
        rhs.type().mustMatchExpected(line(), lhs.type());
        this.type = lhs.type();
        return this;
    }

    /**
     * Generating code for the - operation involves generating code for the two
     * operands, and then the subtraction instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(ISUB);
        } else if (type().equals(Type.LONG)) {
            output.addNoArgInstruction(LSUB);
        } else if (type().equals(Type.FLOAT)) {
            output.addNoArgInstruction(FSUB);
        } else { //double
            output.addNoArgInstruction(DSUB);
        }
    }

}

/**
 * The AST node for a multiplication (*) expression.
 */

class JMultiplyOp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the multiplication expression occurs in the
     *             source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JMultiplyOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*", lhs, rhs);
    }

    /**
     * Analyzing the * operation involves analyzing its operands, checking
     * types, and determining the result type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = lhs.type();
        return this;
    }

    /**
     * Generating code for the * operation involves generating code for the two
     * operands, and then the multiplication instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IMUL);
        } else if (type().equals(Type.LONG)) {
            output.addNoArgInstruction(LMUL);
        } else if (type().equals(Type.FLOAT)) {
            output.addNoArgInstruction(FMUL);
        } else { //double
            output.addNoArgInstruction(DMUL);
        }
    }

}

class JDivideOp extends JBinaryExpression {
    public JDivideOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/", lhs, rhs);
    }

    @Override
    public JExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = lhs.type();
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type.equals(Type.INT)) {
            output.addNoArgInstruction(IDIV);
        } else if (type.equals(Type.LONG)) {
            output.addNoArgInstruction(LDIV);
        } else if (type.equals(Type.FLOAT)) {
            output.addNoArgInstruction(FDIV);
        } else { //double
            output.addNoArgInstruction(DDIV);
        }
    }
}

class JModuloOp extends JBinaryExpression {
    public JModuloOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%", lhs, rhs);
    }

    @Override
    public JExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = lhs.type();
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type.equals(Type.INT)) {
            output.addNoArgInstruction(IREM);
        } else // Type.LONG
            output.addNoArgInstruction(LREM);
    }
}

abstract class JBitwiseExpression extends JBinaryExpression {

    public JBitwiseExpression(int line, String op, JExpression lhs, JExpression rhs) {
        super(line, op, lhs, rhs);
    }

    /**
     * Analyzing the bitwise operation involves analyzing its operands, checking
     * types, and determining the result type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.CHAR, Type.LONG);
        rhs.type().mustMatchOneOf(line(), Type.INT, Type.CHAR, Type.LONG);
        if (lhs.type() == Type.LONG) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            this.type = Type.LONG;
        } else {
            rhs.type().mustMatchOneOf(line(), Type.INT, Type.CHAR);
            this.type = Type.INT;
        }
        return this;
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBitwiseExpression line=\"%d\" type=\"%s\" " + "operator=\"%s\">\n",
                line(), ((type == null) ? "" : type.toString()),
                Util.escapeSpecialXMLChars(operator)
        );
        p.indentRight();
        p.printf("<Lhs>\n");
        p.indentRight();
        lhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Lhs>\n");
        p.printf("<Rhs>\n");
        p.indentRight();
        rhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs>\n");
        p.indentLeft();
        p.printf("</JBitwiseExpression>\n");
    }

}

/**
 * The AST node for an inclusive or (|) expression.
 */

class JBitwiseInclusiveOrOp extends JBitwiseExpression {

    /**
     * Construct an AST for an inclusive or expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the inclusive or expression occurs in the
     *             source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JBitwiseInclusiveOrOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|", lhs, rhs);
    }

    /**
     * Generating code for the | operation involves generating code for the two
     * operands, and then the inclusive or instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LOR : IOR);
    }

}

/**
 * The AST node for an exclusive or (^) expression.
 */

class JBitwiseExclusiveOrOp extends JBitwiseExpression {

    /**
     * Construct an AST for an exclusive or expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the exclusive or expression occurs in the
     *             source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JBitwiseExclusiveOrOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^", lhs, rhs);
    }

    /**
     * Generating code for the ^ operation involves generating code for the two
     * operands, and then the exclusive or instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LXOR : IXOR);
    }

}

/**
 * The AST node for an and (&) expression.
 */

class JBitwiseAndOp extends JBitwiseExpression {

    /**
     * Construct an AST for an and expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the and expression occurs in the
     *             source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JBitwiseAndOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&", lhs, rhs);
    }

    /**
     * Generating code for the & operation involves generating code for the two
     * operands, and then the and instruction.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LAND : IAND);
    }

}