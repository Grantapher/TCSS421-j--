package jminusminus;

//todo
public class JForStatement extends JStatement {
    public JForStatement(int line, JForControl control, JStatement statement) {
        super(line);
    }

    @Override
    public JAST analyze(Context context) {
        return null;
    }

    @Override
    public void codegen(CLEmitter output) {

    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {

    }
}
