// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an int literal.
 */

class JLiteralInt extends JExpression {

    /**
     * String representation of the int.
     */
    private String text;

    /**
     * Integer representation of the int.
     */
    private int data;

    /**
     * Construct an AST node for an int literal given its line number and string
     * representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */

    public JLiteralInt(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Analyzing an int literal is trivial.
     *
     * @param context context in which names are resolved (ignored here).
     * @return the analyzed (and possibly rewritten) AST subtree.
     */
    public JExpression analyze(Context context) {
        try {
            this.data = parse(text);
        } catch (NumberFormatException e) {
            JAST.compilationUnit
                    .reportSemanticError(line, "Bad int format (Likely out of int" +
                                    " bounds): " +
                                    text
                    );
        }
        type = Type.INT;
        return this;
    }

    /**
     * Generating code for an int literal means generating code to push it onto
     * the stack.
     *
     * @param output the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        switch (data) {
            case 0:
                output.addNoArgInstruction(ICONST_0);
                break;
            case 1:
                output.addNoArgInstruction(ICONST_1);
                break;
            case 2:
                output.addNoArgInstruction(ICONST_2);
                break;
            case 3:
                output.addNoArgInstruction(ICONST_3);
                break;
            case 4:
                output.addNoArgInstruction(ICONST_4);
                break;
            case 5:
                output.addNoArgInstruction(ICONST_5);
                break;
            default:
                if (data >= 6 && data <= 127) {
                    output.addOneArgInstruction(BIPUSH, data);
                } else if (data >= 128 && data <= 32767) {
                    output.addOneArgInstruction(SIPUSH, data);
                } else {
                    output.addLDCInstruction(data);
                }
        }
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JLiteralInt line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n", line(),
                 ((type == null) ? "" : type.toString()), text
        );
    }

    private int parse(String str) throws NumberFormatException {
        if (str.charAt(0) != '0') {
            //regular ol' integer
            return Integer.parseInt(str);
        } else {
            if (str.length() == 1) {
                //just 0
                return 0;
            }

            //second char determines format of int
            char secondChar = str.charAt(1);
            if (secondChar == 'x') {
                //hex
                return Integer.parseInt(str.substring(2), 16);
            } else if (secondChar == 'b') {
                //binary
                return Integer.parseInt(str.substring(2), 2);
            } else if (secondChar >= '0' && secondChar <= '7') {
                //octal 
                return Integer.parseInt(str.substring(1), 8);
            } else {
                throw new AssertionError(
                        "Scanner failed to catch an improperly formatted int"
                );
            }
        }
    }

}
