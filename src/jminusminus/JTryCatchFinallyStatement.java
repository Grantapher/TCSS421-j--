package jminusminus;

import java.util.ArrayList;

//todo
public class JTryCatchFinallyStatement extends JStatement {
    public JTryCatchFinallyStatement(int line, JBlock block, ArrayList<JCatchBlock> catches, JFinallyBlock finallyBlock) {
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
