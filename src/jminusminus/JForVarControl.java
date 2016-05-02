package jminusminus;

//todo
public class JForVarControl extends JAST {
    public JForVarControl(int line, boolean isfinal, Type type, String identifier, JForVarControlRest jForVarControlRest) {
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
