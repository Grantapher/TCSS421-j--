package jminusminus;

//todo
public class JSwitchLabel extends JAST {

    private boolean isDefault;

    private JExpression expression;


    public JSwitchLabel(int line, JExpression expression) {
        super(line);
        this.isDefault = expression == null;
        if (!this.isDefault) {
            this.expression = expression;
        }
    }

    public boolean isDefault() {
        return isDefault;
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
