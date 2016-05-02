package jminusminus;

//todo
public class JDoUntilStatement extends JStatement {
    public JDoUntilStatement(int line, JStatement statement, JExpression parExpresion) {
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
