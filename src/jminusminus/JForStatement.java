package jminusminus;

public class JForStatement extends JStatement {

    private LocalContext context;
    private JForInit init;
    private JExpression expression;
    private JForUpdate update;
    private JStatement statement;


    public JForStatement(int line, JForInit forInit, JExpression expression,
                         JForUpdate forUpdate, JStatement statement) {
        super(line);
        init = forInit;
        this.expression = expression;
        update = forUpdate;
        this.statement = statement;
    }

    @Override
    public JAST analyze(Context context) {
        this.context = new LocalContext(context);

        if (init != null) {
            init = (JForInit) init.analyze(this.context);
        }
        if (expression != null) {
            expression = expression.analyze(this.context);
            expression.type().mustMatchExpected(line(), Type.BOOLEAN);
        }
        if (update != null) {
            update = (JForUpdate) update.analyze(this.context);
        }
        statement = (JStatement) statement.analyze(this.context);
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        String beginLoopLabel = output.createLabel();
        String endLoopLabel = output.createLabel();
        if (init != null)
            init.codegen(output);
        output.addLabel(beginLoopLabel);
        if (expression != null)
            expression.codegen(output, endLoopLabel, false);
        statement.codegen(output);
        if (update != null)
            update.codegen(output);
        output.addBranchInstruction(CLConstants.GOTO, beginLoopLabel);
        output.addLabel(endLoopLabel);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<ForInit>\n");
        p.indentRight();
        if (init != null)
            init.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ForInit>\n");
        p.printf("<ForExpression>\n");
        p.indentRight();
        if (expression != null)
            expression.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ForExpression>\n");
        p.printf("<ForUpdate>\n");
        p.indentRight();
        if (update != null)
            update.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ForUpdate>\n");
        p.printf("<ForLoopBlock>\n");
        p.indentRight();
        statement.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ForLoopBlock>\n");
        p.indentLeft();
        p.printf("</JForStatement>\n");
    }
}
