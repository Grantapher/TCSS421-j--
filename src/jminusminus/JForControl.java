package jminusminus;

//todo
public class JForControl extends JAST {
    public JForControl(int line, JForVarControl jForVarControl) {
        super(line);
    }

    public JForControl(int line, JForInitUpdate forInit, JExpression expression, JForInitUpdate forUpdate) {
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
