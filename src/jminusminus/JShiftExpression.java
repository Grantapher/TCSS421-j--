// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a shift expression.
 */

abstract class JShiftExpression extends JExpression {

    /**
     * The shift operator.
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
     * Construct an AST node for a shift expression given its line number, the
     * shift operator, and lhs and rhs operands.
     *
     * @param line     line in which the binary expression occurs in the source file.
     * @param operator the binary operator.
     * @param lhs      the lhs operand.
     * @param rhs      the rhs operand.
     */

    protected JShiftExpression(int line, String operator, JExpression lhs,
                               JExpression rhs) {
        super(line);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.CHAR, Type.LONG);
        rhs.type().mustMatchOneOf(line(), Type.INT, Type.CHAR, Type.LONG);
        if (lhs.type() == Type.CHAR) {
            this.type = Type.INT;
        } else {
            this.type = lhs.type();
        }
        return this;
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JShiftExpression line=\"%d\" type=\"%s\" operator=\"%s\">\n",
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
        p.printf("</JShiftExpression>\n");
    }

}

/**
 * The AST node for a right shift (>>) expression.
 */

class JRightShift extends JShiftExpression {

    /**
     * Construct an AST node for a right shift expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the right shift expression occurs in the source
     *             file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JRightShift(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>", lhs, rhs);
    }

    /**
     * Generate code for a right shift operation.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LSHR : ISHR);
    }

}


/**
 * The AST node for an unsigned right shift (>>>) expression.
 */

class JUnsignedRightShift extends JShiftExpression {

    /**
     * Construct an AST node for an unsigned right shift expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the unsigned right shift expression occurs in the source
     *             file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JUnsignedRightShift(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>", lhs, rhs);
    }

    /**
     * Generate code for an unsigned right shift operation.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LUSHR : IUSHR);
    }

}


/**
 * The AST node for a left shift (<<) expression.
 */

class JLeftShift extends JShiftExpression {

    /**
     * Construct an AST node for a left shift expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line line in which the left shift expression occurs in the source
     *             file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */

    public JLeftShift(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<", lhs, rhs);
    }

    /**
     * Generate code for a left shift operation.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(this.type == Type.LONG ? LSHL : ISHL);
    }

}


