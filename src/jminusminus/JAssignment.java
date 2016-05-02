// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an assignment statement. This is an abtract class into which
 * we factor behavior common to all assignment operations.
 */

abstract class JAssignment extends JBinaryExpression {

    /**
     * Construct an AST node for an assignment operation.
     *
     * @param line     line in which the assignment operation occurs in the source
     *                 file.
     * @param operator the actual assignment operator.
     * @param lhs      the lhs operand.
     * @param rhs      the rhs operand.
     */

    public JAssignment(int line, String operator, JExpression lhs, JExpression rhs) {
        super(line, operator, lhs, rhs);
    }

}

/**
 * The AST node for an assignment (=) expression. The = operator has two
 * operands: a lhs and a rhs.
 */

class JAssignOp extends JAssignment {

    /**
     * Construct the AST node for an assignment (=) expression given the lhs and
     * rhs operands.
     *
     * @param line line in which the assignment expression occurs in the source
     *             file.
     * @param lhs  lhs operand.
     * @param rhs  rhs operand.
     */

    public JAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "=", lhs, rhs);
    }

    /**
     * Analyze the lhs and rhs, checking that types match, and set the result
     * type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit
                    .reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = rhs.type();
        if (lhs instanceof JVariable) {
            IDefn defn = ((JVariable) lhs).iDefn();
            if (defn != null) {
                // Local variable; consider it to be initialized now.
                ((LocalVariableDefn) defn).initialize();
            }
        }
        return this;
    }

    /**
     * Code generation for an assignment involves, generating code for loading
     * any necessary Lvalue onto the stack, for loading the Rvalue, for (unless
     * a statement) copying the Rvalue to its proper place on the stack, and for
     * doing the store.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        rhs.codegen(output);
        if (!isStatementExpression) {
            // Generate code to leave the Rvalue atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }

}

/**
 * Abstraction for assignment expressions.
 */
abstract class JAssignmentExpression extends JAssignment {

    /**
     * Construct the AST node for an assignmentExpression given the lhs and
     * rhs operands.
     *
     * @param line line in which the assignment expression occurs in the source
     *             file.
     * @param op   the operator image
     * @param lhs  lhs operand.
     * @param rhs  rhs operand.
     */

    public JAssignmentExpression(int line, String op, JExpression lhs, JExpression rhs) {
        super(line, op, lhs, rhs);
    }

    /**
     * Code generation for assignment expressions involves, generating code for loading any
     * necessary l-value onto the stack, for loading the r-value, for (unless a statement)
     * copying the r-value to its proper place on the stack, and for doing the store.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    @Override
    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);                    //iload_2
        codegenOperation(output);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);         //istore_2
    }

    /**
     * The lhs and rhs is on the stack, the type is set, just choose the right operation.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */
    protected abstract void codegenOperation(CLEmitter output);

    /**
     * Analyze the lhs and rhs, checking that types match, and set the result
     * type.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    @Override
    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        analyzeOperation(context);
        return this;
    }

    /**
     * Type checking and setting.
     *
     * @param context context in which names are resolved.
     */
    protected abstract void analyzeOperation(Context context);

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JAssignmentExpression line=\"%d\" type=\"%s\" " + "operator=\"%s\">\n",
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
        p.printf("</JAssignmentExpression>\n");
    }
}

/**
 * The AST node for a += expression.
 */
class JPlusAssignOp extends JAssignmentExpression {

    public JPlusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+=", lhs, rhs);
    }

    /**
     * Plus assign has special circumstances involving strings, so it will override the codegen method.
     *
     * @param output the code emitter (basically an abstraction for producing the
     */
    @Override
    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);  //nothing
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else {
            ((JLhs) lhs).codegenLoadLhsRvalue(output); //iload_2
            rhs.codegen(output);                    //iload_1
            output.addNoArgInstruction(IADD);       //iadd
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);         //istore_2
    }

    @Override
    protected void analyzeOperation(Context context) {
        lhs.type().mustMatchOneOf(line(), Type.STRING, Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
        Type lType = lhs.type();
        if (lType.equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else if (lType.equals(Type.INT) || lType.equals(Type.LONG) || lType.equals(Type.FLOAT)
                || lType.equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), lType);
            type = lType;
        } else {
            JAST.compilationUnit.reportSemanticError(line(), "Invalid lhs type for +=: " +
                            lhs.type()
            );
        }
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        //unused because of codegen override
    }

}

/**
 * Abstraction for the arithmetic assignment expressions (-=, *=, /=)
 * <p>
 * Not += though because it has special circumstances involving strings
 */
abstract class JArithmeticAssignmentExpression extends JAssignmentExpression {

    public JArithmeticAssignmentExpression(int line, String op, JExpression lhs, JExpression rhs) {
        super(line, op, lhs, rhs);
    }

    @Override
    protected void analyzeOperation(Context context) {
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = lhs.type();
    }
}

/**
 * The AST node for a -= expression.
 */
class JSubAssignOp extends JArithmeticAssignmentExpression {

    public JSubAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
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
 * The AST node for a *= expression.
 */
class JMultiplyAssignOp extends JArithmeticAssignmentExpression {

    public JMultiplyAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
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

/**
 * The AST node for a /= expression.
 */
class JDivideAssignOp extends JArithmeticAssignmentExpression {

    public JDivideAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IDIV);
        } else if (type().equals(Type.LONG)) {
            output.addNoArgInstruction(LDIV);
        } else if (type().equals(Type.FLOAT)) {
            output.addNoArgInstruction(FDIV);
        } else { //double
            output.addNoArgInstruction(DDIV);
        }
    }
}

/**
 * Abstraction for the integer assignment expressions (%=, &=, ^=, |=, <<=, >>=, >>>=)
 */
abstract class JIntegerAssignmentExpression extends JAssignmentExpression {

    public JIntegerAssignmentExpression(int line, String op, JExpression lhs, JExpression rhs) {
        super(line, op, lhs, rhs);
    }

    @Override
    protected void analyzeOperation(Context context) {
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.LONG);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = lhs.type();
    }
}

/**
 * The AST node for a %= expression.
 */
class JModuloAssignOp extends JIntegerAssignmentExpression {

    public JModuloAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IREM);
        } else // Type.LONG
            output.addNoArgInstruction(LREM);
    }
}

/**
 * The AST node for a &= expression.
 */
class JAndAssignOp extends JIntegerAssignmentExpression {

    public JAndAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IAND);
        } else // Type.LONG
            output.addNoArgInstruction(LAND);
    }
}

/**
 * The AST node for a ^= expression.
 */
class JXorAssignOp extends JIntegerAssignmentExpression {

    public JXorAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IXOR);
        } else // Type.LONG
            output.addNoArgInstruction(LXOR);
    }
}

/**
 * The AST node for a |= expression.
 */
class JOrAssignOp extends JIntegerAssignmentExpression {

    public JOrAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IOR);
        } else // Type.LONG
            output.addNoArgInstruction(LOR);
    }
}

/**
 * The AST node for a <<= expression.
 */
class JLeftShiftAssignOp extends JIntegerAssignmentExpression {

    public JLeftShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(ISHL);
        } else // Type.LONG
            output.addNoArgInstruction(LSHL);
    }
}

/**
 * The AST node for a >>= expression.
 */
class JRightShiftAssignOp extends JIntegerAssignmentExpression {

    public JRightShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(ISHR);
        } else // Type.LONG
            output.addNoArgInstruction(LSHR);
    }
}

/**
 * The AST node for a >>>= expression.
 */
class JUnsignedRightShiftAssignOp extends JIntegerAssignmentExpression {

    public JUnsignedRightShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>=", lhs, rhs);
    }

    @Override
    protected void codegenOperation(CLEmitter output) {
        if (type().equals(Type.INT)) {
            output.addNoArgInstruction(IUSHR);
        } else // Type.LONG
            output.addNoArgInstruction(LUSHR);
    }
}
