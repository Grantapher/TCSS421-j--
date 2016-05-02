package jminusminus;

//todo
public class JFinallyBlock extends JAST{
    public JFinallyBlock(int line, JBlock block) {
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
