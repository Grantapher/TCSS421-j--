package jminusminus;

//todo
public class JCatchBlock extends JAST {
    public JCatchBlock(int line, Type type, String name, JBlock block) {
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
