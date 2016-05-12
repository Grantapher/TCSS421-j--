package jminusminus;

public class JDoWhileStatement extends JStatement {
    private JStatement body;
    private JExpression condition;
    private LocalContext context;

    public JDoWhileStatement(int line, JStatement statement, JExpression expression) {
        super(line);
        body = statement;
        condition = expression;
    }

    @Override
    public JAST analyze(Context context) {
        this.context = new LocalContext(context);

        body = (JStatement) body.analyze(this.context);

        condition = condition.analyze(this.context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);

        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        String loopLabel = output.createLabel();

        output.addLabel(loopLabel);
        body.codegen(output);
        condition.codegen(output, loopLabel, true);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {

    }
}
