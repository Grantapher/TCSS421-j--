package jminusminus;

import java.util.ArrayList;

//todo
public class JForInitUpdate extends JAST{
    public JForInitUpdate(int line, ArrayList<JStatement> statementExpressions) {
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
