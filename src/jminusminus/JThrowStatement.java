package jminusminus;

public class JThrowStatement extends JStatement {

    public JExpression expression;

    public Type type;

    public JThrowStatement(int line, JExpression expression) {
        super(line);
        this.expression = expression;
    }

    @Override
    public JAST analyze(Context context) {
        expression = expression.analyze(context);
        type = expression.type();
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        //todo
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowStatement line=\"%d\" type=\"%s\">\n", line(), ((type == null) ? "" : type.toString()));
        p.indentRight();
        p.printf("<expression>\n");
        p.indentRight();
        expression.writeToStdOut(p);
        p.indentLeft();
        p.printf("</expression>\n");
        p.indentLeft();
        p.printf("</JThrowStatement>\n");
    }
}
