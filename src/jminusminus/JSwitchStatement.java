package jminusminus;

import java.util.ArrayList;

//todo
public class JSwitchStatement extends JStatement {
    public JSwitchStatement(int line, JExpression parExpression, ArrayList<JSwitchBlockStatementGroup> switchBlockStatementGroups) {
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
