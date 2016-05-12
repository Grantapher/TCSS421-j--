package jminusminus;

import java.util.ArrayList;

public class JForUpdate extends JAST {
    ArrayList<JStatement> statementExpressions;

    public JForUpdate(int line, ArrayList<JStatement> statementExpressions) {
        super(line);
        this.statementExpressions = statementExpressions;
    }

    @Override
    public JAST analyze(Context context) {
        for (int i = 0; i < statementExpressions.size(); i++) {
            JStatement statement = statementExpressions.get(i);
            statement = (JStatement) statement.analyze(context);
            statementExpressions.set(i, statement);
        }
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        for (JStatement statement : statementExpressions) {
            statement.codegen(output);
        }
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<StatementExpressions>\n");
        p.indentRight();
        for (JStatement statement : statementExpressions) {
            statement.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</StatementExpressions>\n");

    }
}
