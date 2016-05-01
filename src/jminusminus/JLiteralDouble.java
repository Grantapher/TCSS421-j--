// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an double literal.
 */

class JLiteralDouble extends JExpression {

    /**
     * String representation of the double.
     */
    private String text;

    /**
     * Float representation of the float.
     */
    private double data;

    /**
     * Construct an AST node for a double literal given its line number and string
     * representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */

    public JLiteralDouble(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Analyzing a double literal is trivial.
     *
     * @param context context in which names are resolved (ignored here).
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        try {
            this.data = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            JAST.compilationUnit.reportSemanticError(line, "Bad double format: " + text);
        }
        type = Type.DOUBLE;
        return this;
    }

    /**
     * Generating code for a double literal means generating code to push it onto
     * the stack.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        if (data == 0d) {
            output.addNoArgInstruction(DCONST_0);
        } else if (data == 1d) {
            output.addNoArgInstruction(DCONST_1);
        } else {
            output.addLDCInstruction(data);
        }
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JLiteralDouble line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n", line(),
                ((type == null) ? "" : type.toString()), text
        );
    }

}
