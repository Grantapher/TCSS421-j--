package jminusminus;

import java.util.ArrayList;

public class JForInit extends JAST {
    private ArrayList<JStatement> statementExpressions;
    private JVariableDeclaration variableDeclaration;

    public JForInit(int line, ArrayList<JStatement> statementExpressions, int erasureBreaker) {
        super(line);
        this.statementExpressions = statementExpressions;
        variableDeclaration = null;
    }

    public JForInit(int line, ArrayList<JVariableDeclarator> variableDeclarators) {
        super(line);
        this.statementExpressions = null;
        variableDeclaration = new JVariableDeclaration(line(), new ArrayList<>(),
                variableDeclarators);
    }

    @Override
    public JAST analyze(Context context) {
        if (statementExpressions != null) {
            for (int i = 0; i < statementExpressions.size(); i++) {
                JStatement statement = statementExpressions.get(i);
                statement = (JStatement) statement.analyze(context);
                statementExpressions.set(i, statement);
            }
        } else {
            variableDeclaration = (JVariableDeclaration) variableDeclaration.analyze(context);
        }
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        if (statementExpressions != null) {
            for (JStatement statement : statementExpressions) {
                statement.codegen(output);
            }
        } else {
            variableDeclaration.codegen(output);
        }
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        if (statementExpressions != null) {
            p.printf("<StatementExpressions>\n");
            p.indentRight();
            for (JStatement statement : statementExpressions) {
                statement.writeToStdOut(p);
            }
            p.indentLeft();
            p.printf("</StatementExpressions>\n");
        } else {
            variableDeclaration.writeToStdOut(p);
        }
    }
}
