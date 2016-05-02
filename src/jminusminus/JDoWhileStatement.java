package jminusminus;

//this
public class JDoWhileStatement extends JStatement {
    public JDoWhileStatement(int line, JStatement statement, JExpression parExpresion) {
        super(line);
    }

    @Override
    public JAST analyze(Context context) {
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {

    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {

    }
}
