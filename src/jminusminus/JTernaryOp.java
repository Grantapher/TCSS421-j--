package jminusminus;

import static jminusminus.CLConstants.*;

public class JTernaryOp extends JExpression {

    /**
     * Left hand side of the ternary.
     */
    private JExpression condition;

    /**
     * First option of the ternary.
     */
    private JExpression lhs;

    /**
     * Second option of the ternary.
     */
    private JExpression rhs;

    /**
     * Initialize the ternary.
     *
     * @param line      line number the ternary is on
     * @param condition Left hand side of the ternary
     * @param lhs       First option of the ternary
     * @param rhs       Second option of the ternary
     */
    public JTernaryOp(int line, JExpression condition, JExpression lhs, JExpression rhs) {
        super(line);
        this.condition = condition;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public JExpression analyze(Context context) {
        condition = (JExpression) condition.analyze(context);
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);

        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        lhs.type().mustMatchExpected(line(), rhs.type());
        this.type = lhs.type();
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        String falseLabel = output.createLabel();
        String endTernaryLabel = output.createLabel();

        condition.codegen(output, falseLabel, false);
        lhs.codegen(output);
        output.addBranchInstruction(GOTO, endTernaryLabel);
        output.addLabel(falseLabel);
        rhs.codegen(output);
        output.addLabel(endTernaryLabel);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTernaryOp line=\"%d\" type=\"%s\">\n",
                line(), ((type == null) ? "" : type.toString())
        );
        p.indentRight();
        p.printf("<Condition>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Condition>\n");
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
        p.printf("</JTernaryOp>\n");
    }
}
