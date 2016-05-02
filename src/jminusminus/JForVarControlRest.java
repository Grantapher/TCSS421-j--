package jminusminus;

import java.util.ArrayList;

//todo
public class JForVarControlRest extends JAST {
    public JForVarControlRest(int line, JExpression expression) {
        super(line);
    }

    public JForVarControlRest(int line, JExpression variableInitializer, ArrayList<JVariableDeclarator> variableDeclarators, JExpression expression, JForInitUpdate forUpdate) {
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
