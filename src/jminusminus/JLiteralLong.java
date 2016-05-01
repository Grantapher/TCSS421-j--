// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an long literal.
 */

class JLiteralLong extends JExpression {

    /**
     * String representation of the long.
     */
    private String text;

    /**
     * Long representation of the long.
     */
    private long data;

    /**
     * Construct an AST node for a long literal given its line number and string
     * representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */

    public JLiteralLong(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Analyzing a long literal is trivial.
     *
     * @param context context in which names are resolved (ignored here).
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        try {
            //last character is an l
            this.data = parse(text.substring(0, text.length() - 1));
        } catch (NumberFormatException e) {
            JAST.compilationUnit
                    .reportSemanticError(line, "Bad long format (Likely out of long" +
                                    " bounds): " +
                                    text
                    );
        }
        type = Type.LONG;
        return this;
    }

    /**
     * Generating code for a long literal means generating code to push it onto
     * the stack.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        if (data == 0) {
            output.addNoArgInstruction(LCONST_0);
        } else if (data == 1) {
            output.addNoArgInstruction(LCONST_1);
        } else {
            output.addLDCInstruction(data);
        }
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JLiteralLong line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n", line(),
                 ((type == null) ? "" : type.toString()), text
        );
    }

    private long parse(String str) {
        if (str.charAt(0) != '0') {
            //regular ol' integer
            return Long.parseLong(str);
        } else {
            if (str.length() == 1) {
                //just 0
                return 0;
            }

            //second char determines format of int
            char secondChar = str.charAt(1);
            if (secondChar == 'x') {
                //hex
                return Long.parseLong(str.substring(2), 16);
            } else if (secondChar == 'b') {
                //binary
                return Long.parseLong(str.substring(2), 2);
            } else if (secondChar >= '0' && secondChar <= '7') {
                //octal
                return Long.parseLong(str.substring(1), 8);
            } else {
                throw new AssertionError(
                        "Scanner failed to catch an improperly formatted long"
                );
            }
        }
    }

}
