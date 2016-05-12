package jminusminus;

public class JThrowStatement extends JStatement {

    public JExpression expression;

    public Type type;

    public JThrowStatement(int line, JExpression expression) {
        super(line);
        this.expression = expression;
    }

    @Override
    public JAST analyze(Context context) {
        expression = expression.analyze(context);
        type = expression.type();
        if (!type.isSubtypeOf(Type.THROWABLE)) {
            JAST.compilationUnit.reportSemanticError(line(),
                                                     "Throw statement only works on " +
                                                             "subtypes of " +
                                                             "\"Throwable\", found type" +
                                                             " \"%s\"",
                                                     type.simpleName()
            );
        }
        return this;
    }

    @Override
    public void codegen(CLEmitter output) {
        expression.codegen(output);
        output.addNoArgInstruction(CLConstants.ATHROW);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowStatement line=\"%d\" type=\"%s\">\n", line(),
                 ((type == null) ? "" : type.toString())
        );
        p.indentRight();
        p.printf("<expression>\n");
        p.indentRight();
        expression.writeToStdOut(p);
        p.indentLeft();
        p.printf("</expression>\n");
        p.indentLeft();
        p.printf("</JThrowStatement>\n");
    }
}
