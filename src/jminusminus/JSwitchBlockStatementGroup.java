package jminusminus;

import java.util.ArrayList;

//todo
public class JSwitchBlockStatementGroup extends JAST {
    public JSwitchBlockStatementGroup(int line, ArrayList<JSwitchLabel> switchLabels, JStatement jStatement) {
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
