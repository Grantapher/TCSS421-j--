package jminusminus;

public class JDoUntilStatement extends JStatement {
    private JStatement body;
    private JExpression condition;
    private LocalContext context;

    public JDoUntilStatement(int line, JStatement statement, JExpression expression) {
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
        condition.codegen(output, loopLabel, false);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {

    }
}
