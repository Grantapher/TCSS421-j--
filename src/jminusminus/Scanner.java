// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;

import static jminusminus.TokenKind.*;

/**
 * A lexical analyzer for j--, that has no backtracking mechanism.
 * <p>
 * When you add a new token to the scanner, you must also add an entry in the
 * TokenKind enum in TokenInfo.java specifying the kind and image of the new
 * token.
 */

class Scanner {

    /**
     * End of file character.
     */
    public final static char EOFCH = CharReader.EOFCH;

    /**
     * Keywords in j--.
     */
    private Hashtable<String, TokenKind> reserved;

    /**
     * Unimplemented keywords in java.
     */
    private Hashtable<String, TokenKind> unimplemented;

    /**
     * Source characters.
     */
    private CharReader input;

    /**
     * Next unscanned character.
     */
    private char ch;

    /**
     * Whether a scanner error has been found.
     */
    private boolean isInError;

    /**
     * Source file name.
     */
    private String fileName;

    /**
     * Line number of current token.
     */
    private int line;

    /**
     * Construct a Scanner object.
     *
     * @param fileName the name of the file containing the source.
     * @throws FileNotFoundException when the named file cannot be found.
     */

    public Scanner(String fileName) throws FileNotFoundException {
        this.input = new CharReader(fileName);
        this.fileName = fileName;
        isInError = false;

        // Keywords in j--
        reserved = new Hashtable<String, TokenKind>();
        unimplemented = new Hashtable<String, TokenKind>();

        putReserved(ABSTRACT);
        putReserved(BOOLEAN);
        putReserved(BREAK);
        putReserved(BYTE);
        putReserved(CASE);
        putReserved(CATCH);
        putReserved(CHAR);
        putReserved(CLASS);
        putReserved(CONST);
        putReserved(CONTINUE);
        putReserved(DEFAULT);
        putReserved(DO);
        putReserved(DOUBLE);
        putReserved(ELSE);
        putReserved(EXTENDS);
        putReserved(FALSE);
        putReserved(FINAL);
        putReserved(FINALLY);
        putReserved(FLOAT);
        putReserved(FOR);
        putReserved(GOTO);
        putReserved(IF);
        putReserved(IMPLEMENTS);
        putReserved(IMPORT);
        putReserved(INSTANCEOF);
        putReserved(INT);
        putReserved(INTERFACE);
        putReserved(LONG);
        putReserved(NATIVE);
        putReserved(NEW);
        putReserved(NULL);
        putReserved(PACKAGE);
        putReserved(PRIVATE);
        putReserved(PROTECTED);
        putReserved(PUBLIC);
        putReserved(RETURN);
        putReserved(SHORT);
        putReserved(STATIC);
        putReserved(STRICTFP);
        putReserved(SUPER);
        putReserved(SWITCH);
        putReserved(SYNCHRONIZED);
        putReserved(THIS);
        putReserved(THROW);
        putReserved(THROWS);
        putReserved(TRANSIENT);
        putReserved(TRUE);
        putReserved(TRY);
        putReserved(VOID);
        putReserved(VOLATILE);
        putReserved(WHILE);

        //unimplemented
        putUnimplemented(BREAK);
        putUnimplemented(BYTE);
        putUnimplemented(CASE);
        putUnimplemented(CATCH);
        putUnimplemented(CONST);
        putUnimplemented(CONTINUE);
        putUnimplemented(DEFAULT);
        putUnimplemented(DO);
        putUnimplemented(FINAL);
        putUnimplemented(FINALLY);
        putUnimplemented(FOR);
        putUnimplemented(GOTO);
        putUnimplemented(IMPLEMENTS);
        putUnimplemented(INTERFACE);
        putUnimplemented(NATIVE);
        putUnimplemented(SHORT);
        putUnimplemented(STRICTFP);
        putUnimplemented(SWITCH);
        putUnimplemented(SYNCHRONIZED);
        putUnimplemented(THROW);
        putUnimplemented(THROWS);
        putUnimplemented(TRANSIENT);
        putUnimplemented(TRY);
        putUnimplemented(VOLATILE);

        // Prime the pump.
        nextCh();
    }

    /**
     * Puts the TokenKind into the reserved HashTable.
     */
    private void putReserved(TokenKind tokenKind) {
        reserved.put(tokenKind.image(), tokenKind);
    }

    /**
     * Puts the TokenKind into the unimplemented HashTable.
     */
    private void putUnimplemented(TokenKind tokenKind) {
        unimplemented.put(tokenKind.image(), tokenKind);
    }

    /**
     * Scan the next token from input.
     *
     * @return the the next scanned token.
     */

    public TokenInfo getNextToken() {
        StringBuffer buffer;
        boolean moreWhiteSpace = true;
        while (moreWhiteSpace) {
            while (isWhitespace(ch)) {
                nextCh();
            }
            if (ch == '/') {
                nextCh();
                if (ch == '/') {
                    // CharReader maps all new lines to '\n'
                    while (ch != '\n' && ch != EOFCH) {
                        nextCh();
                    }
                } else if (ch == '*') {
                    nextCh();
                    while (ch != EOFCH) {
                        if (ch == '*') {
                            nextCh();
                            if (ch == '/') {
                                nextCh();
                                break;
                            }
                        } else {
                            nextCh();
                        }
                    }
                } else if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(DIV_ASSIGN);
                    return new TokenInfo(DIV_ASSIGN, line);
                } else {
                    return new TokenInfo(DIV, line);
                }
            } else {
                moreWhiteSpace = false;
            }
        }
        line = input.line();
        switch (ch) {
            case '?':
                nextCh();
                reportUnimplementedError(TERNARY_START);
                return new TokenInfo(TERNARY_START, line);
            case ':':
                nextCh();
                reportUnimplementedError(TERNARY_END);
                return new TokenInfo(TERNARY_END, line);
            case '~':
                nextCh();
                reportUnimplementedError(BCOMP);
                return new TokenInfo(BCOMP, line);
            case '^':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(XOR_ASSIGN);
                    return new TokenInfo(XOR_ASSIGN, line);
                } else {
                    reportUnimplementedError(XOR);
                    return new TokenInfo(XOR, line);
                }
            case '|':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(BOR_ASSIGN);
                    return new TokenInfo(BOR_ASSIGN, line);
                } else if (ch == '|') {
                    nextCh();
                    reportUnimplementedError(LOR);
                    return new TokenInfo(LOR, line);
                } else {
                    reportUnimplementedError(BOR);
                    return new TokenInfo(BOR, line);
                }
            case '(':
                nextCh();
                return new TokenInfo(LPAREN, line);
            case ')':
                nextCh();
                return new TokenInfo(RPAREN, line);
            case '{':
                nextCh();
                return new TokenInfo(LCURLY, line);
            case '}':
                nextCh();
                return new TokenInfo(RCURLY, line);
            case '[':
                nextCh();
                return new TokenInfo(LBRACK, line);
            case ']':
                nextCh();
                return new TokenInfo(RBRACK, line);
            case ';':
                nextCh();
                return new TokenInfo(SEMI, line);
            case ',':
                nextCh();
                return new TokenInfo(COMMA, line);
            case '=':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(EQUAL, line);
                } else {
                    return new TokenInfo(ASSIGN, line);
                }
            case '!':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(NEQUAL, line);
                } else {
                    return new TokenInfo(LNOT, line);
                }
            case '*':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(MUL_ASSIGN);
                    return new TokenInfo(MUL_ASSIGN, line);
                } else {
                    return new TokenInfo(MUL, line);
                }
            case '%':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(MOD_ASSIGN);
                    return new TokenInfo(MOD_ASSIGN, line);
                } else {
                    return new TokenInfo(MOD, line);
                }
            case '+':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(PLUS_ASSIGN, line);
                } else if (ch == '+') {
                    nextCh();
                    return new TokenInfo(INC, line);
                } else {
                    return new TokenInfo(PLUS, line);
                }
            case '-':
                nextCh();
                if (ch == '-') {
                    nextCh();
                    return new TokenInfo(DEC, line);
                } else if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(SUB_ASSIGN);
                    return new TokenInfo(SUB_ASSIGN, line);
                } else {
                    return new TokenInfo(SUB, line);
                }
            case '&':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    reportUnimplementedError(BAND_ASSIGN);
                    return new TokenInfo(BAND_ASSIGN, line);
                } else if (ch == '&') {
                    nextCh();
                    return new TokenInfo(LAND, line);
                } else {
                    reportUnimplementedError(BAND);
                    return new TokenInfo(BAND, line);
                }
            case '>':
                nextCh();
                if (ch == '>') {
                    nextCh();
                    if (ch == '=') {
                        nextCh();
                        reportUnimplementedError(RSHIFT_ASSIGN);
                        return new TokenInfo(RSHIFT_ASSIGN, line);
                    } else if (ch == '>') {
                        nextCh();
                        if (ch == '=') {
                            nextCh();
                            reportUnimplementedError(URSHIFT_ASSIGN);
                            return new TokenInfo(URSHIFT_ASSIGN, line);
                        } else {
                            reportUnimplementedError(URSHIFT);
                            return new TokenInfo(URSHIFT, line);
                        }
                    } else {
                        reportUnimplementedError(RSHIFT);
                        return new TokenInfo(RSHIFT, line);
                    }
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(GEQ, line);
                } else {
                    return new TokenInfo(GT, line);
                }
            case '<':
                nextCh();
                if (ch == '<') {
                    nextCh();
                    if (ch == '=') {
                        nextCh();
                        reportUnimplementedError(LSHIFT_ASSIGN);
                        return new TokenInfo(LSHIFT_ASSIGN, line);
                    } else {
                        reportUnimplementedError(LSHIFT);
                        return new TokenInfo(LSHIFT, line);
                    }
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(LEQ, line);
                } else {
                    return new TokenInfo(LT, line);
                }
            case '\'':
                buffer = new StringBuffer();
                buffer.append('\'');
                nextCh();
                if (ch == '\\') {
                    nextCh();
                    buffer.append(escape());
                } else {
                    buffer.append(ch);
                    nextCh();
                }
                if (ch == '\'') {
                    buffer.append('\'');
                    nextCh();
                    return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
                } else {
                    // Expected a ' ; report error and try to
                    // recover.
                    reportScannerError(
                            ch + " found by scanner where closing ' was expected."
                    );
                    while (ch != '\'' && ch != ';' && ch != '\n') {
                        nextCh();
                    }
                    return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
                }
            case '"':
                buffer = new StringBuffer();
                buffer.append("\"");
                nextCh();
                while (ch != '"' && ch != '\n' && ch != EOFCH) {
                    if (ch == '\\') {
                        nextCh();
                        buffer.append(escape());
                    } else {
                        buffer.append(ch);
                        nextCh();
                    }
                }
                if (ch == '\n') {
                    reportScannerError("Unexpected end of line found in String");
                } else if (ch == EOFCH) {
                    reportScannerError("Unexpected end of file found in String");
                } else {
                    // Scan the closing "
                    nextCh();
                    buffer.append("\"");
                }
                return new TokenInfo(STRING_LITERAL, buffer.toString(), line);
            case '.':
                nextCh();
                // floating point literal
                if (isDigit(ch)) {
                    buffer = new StringBuffer();
                    // dot
                    buffer.append('.');
                    // digits
                    if (isDigit(ch)) {
                        while (isDigit(ch)) {
                            buffer.append(ch);
                            nextCh();
                        }
                    } else {
                        reportScannerError(
                                "Expected at least one digit after decimal point for " +
                                        "floating point literal"
                        );
                    }
                    // optional exponent
                    if (ch == 'e' || ch == 'E') {
                        // exponent indicator
                        buffer.append(ch);
                        nextCh();
                        // optional sign
                        if (ch == '+' || ch == '-') {
                            buffer.append(ch);
                            nextCh();
                        }
                        // digits
                        while (isDigit(ch)) {
                            buffer.append(ch);
                            nextCh();
                        }
                    }
                    // optional suffix
                    TokenKind literalType;
                    if (ch == 'f' || ch == 'F') {
                        literalType = FLOAT_LITERAL;
                        buffer.append(ch);
                        nextCh();
                    } else if (ch == 'd' || ch == 'D') {
                        literalType = DOUBLE_LITERAL;
                        buffer.append(ch);
                        nextCh();
                    } else {
                        literalType = DOUBLE_LITERAL;
                    }
                    return new TokenInfo(literalType, buffer.toString(), line);
                } else {
                    return new TokenInfo(DOT, line);
                }
            case EOFCH:
                return new TokenInfo(EOF, line);
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                buffer = new StringBuffer();
                buffer.append(ch);
                if (ch == '0') {
                    nextCh();
                    if (ch == 'x' || ch == 'X') {
                        //hex number
                        // the x
                        buffer.append(ch);
                        nextCh();
                        TokenKind literalType = INT_LITERAL;
                        //digits
                        while (isHexDigit(ch)) {
                            buffer.append(ch);
                            nextCh();
                        }
                        //optional dot
                        if (ch == '.') {
                            literalType = DOUBLE_LITERAL;
                            buffer.append(ch);
                            nextCh();
                            // optional hex digits following dot (not optional if no
                            // digits preceding dot)
                            if (buffer.length() == 3 && !isHexDigit(ch)) {
                                reportScannerError("Malformed floating point literal");
                                return getNextToken();
                            }
                            while (isHexDigit(ch)) {
                                buffer.append(ch);
                                nextCh();
                            }
                        }
                        // required p if floating point
                        if (ch == 'p' || ch == 'P') {
                            literalType = DOUBLE_LITERAL;
                            buffer.append(ch);
                            nextCh();
                            //optional sign
                            if (ch == '+' || ch == '-') {
                                buffer.append(ch);
                                nextCh();
                            }
                            // digits
                            if (!isDigit(ch)) {
                                reportScannerError("Malformed floating point literal");
                                return getNextToken();
                            }
                            while (isDigit(ch)) {
                                buffer.append(ch);
                                nextCh();
                            }
                        } else if (literalType == DOUBLE_LITERAL) {
                            reportScannerError("Malformed floating point literal");
                            return getNextToken();
                        }

                        // optional suffixes
                        if (ch == 'f' || ch == 'F') {
                            if (literalType == INT_LITERAL) {
                                reportScannerError("malformed floating point literal");
                                return getNextToken();
                            }
                            literalType = FLOAT_LITERAL;
                            buffer.append(ch);
                            nextCh();
                        } else if (ch == 'd' || ch == 'D') {
                            if (literalType == INT_LITERAL) {
                                reportScannerError("malformed floating point literal");
                                return getNextToken();
                            }
                            literalType = DOUBLE_LITERAL;
                            buffer.append(ch);
                            nextCh();
                        } else if (ch == 'l' || ch == 'L') {
                            if (literalType == DOUBLE_LITERAL) {
                                reportScannerError("malformed integer literal");
                                return getNextToken();
                            }
                            literalType = LONG_LITERAL;
                            buffer.append(ch);
                            nextCh();
                        }
                        return new TokenInfo(literalType, buffer.toString(), line);
                    } else if (ch == 'b' || ch == 'B') {
                        //bin int
                        reportScannerError("Binary ints are not yet implemented in j--.");
                        // the b
                        buffer.append(ch);
                        nextCh();
                        //digits
                        while (ch == '0' || ch == '1') {
                            buffer.append(ch);
                            nextCh();
                        }

                        //optional suffix
                        TokenKind literalType = INT_LITERAL;
                        if (ch == 'l' || ch == 'L') {
                            literalType = LONG_LITERAL;
                            buffer.append(ch);
                            nextCh();
                        }
                        return new TokenInfo(literalType, buffer.toString(), line);
                    } else if (isDigit(ch)) {
                        // octal
                        reportScannerError("Octal ints are not yet implemented in j--.");
                        boolean isAboveOctal = false;
                        boolean isFloatingPoint = false;
                        while (isDigit(ch)) {
                            if (ch == '8' || ch == '9') {
                                isAboveOctal = true;
                            } else if (ch == 'e' || ch == 'E' || ch == '.') {
                                isFloatingPoint = true;
                                break;
                            }
                            buffer.append(ch);
                            nextCh();
                        }

                        TokenKind literalType = INT_LITERAL;
                        if (ch == 'l' || ch == 'L') {
                            literalType = LONG_LITERAL;
                            buffer.append(ch);
                            nextCh();
                        }

                        if (isFloatingPoint) {
                            //just fall through, it's a floating point literal, the
                            // below will take care of it.
                        } else if (isAboveOctal) {
                            if (ch == 'l' || ch == 'L') {
                                buffer.append(ch);
                                nextCh();
                            }
                            reportScannerError(
                                    "Integer number too large: " + buffer.toString()
                            );
                            return getNextToken();
                        } else {
                            return new TokenInfo(literalType, buffer.toString(), line);
                        }
                    } else if (ch == 'f' || ch == 'F') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                    } else if (ch == 'd' || ch == 'D') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                    } else if (ch == 'l' || ch == 'L') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(LONG_LITERAL, buffer.toString(), line);
                    } else if (ch == 'e' || ch == 'E' || ch == '.') {
                        //just fall through, it's a floating point literal, the below
                        // will take care of it.
                    } else {
                        //just 0
                        return new TokenInfo(INT_LITERAL, buffer.toString(), line);
                    }
                } else {
                    nextCh();
                }
                TokenKind literalType = null;
                // digits
                while (isDigit(ch)) {
                    buffer.append(ch);
                    nextCh();
                }
                // optional dot
                if (ch == '.') {
                    literalType = DOUBLE_LITERAL;
                    buffer.append(ch);
                    nextCh();
                    // optional digits
                    while (isDigit(ch)) {
                        buffer.append(ch);
                        nextCh();
                    }
                }
                // optional exponent
                if (ch == 'e' || ch == 'E') {
                    literalType = DOUBLE_LITERAL;
                    // exponent indicator
                    buffer.append(ch);
                    nextCh();
                    // optional sign
                    if (ch == '+' || ch == '-') {
                        buffer.append(ch);
                        nextCh();
                    }
                    // digits
                    while (isDigit(ch)) {
                        buffer.append(ch);
                        nextCh();
                    }
                }

                // optional suffixes
                if (ch == 'f' || ch == 'F') {
                    literalType = FLOAT_LITERAL;
                    buffer.append(ch);
                    nextCh();
                } else if (ch == 'd' || ch == 'D') {
                    literalType = DOUBLE_LITERAL;
                    buffer.append(ch);
                    nextCh();
                } else if (ch == 'l' || ch == 'L') {
                    literalType = LONG_LITERAL;
                    buffer.append(ch);
                    nextCh();
                }
                return new TokenInfo(null == literalType ? INT_LITERAL : literalType,
                                     buffer.toString(), line
                );
            default:
                if (isIdentifierStart(ch)) {
                    buffer = new StringBuffer();
                    while (isIdentifierPart(ch)) {
                        buffer.append(ch);
                        nextCh();
                    }
                    String identifier = buffer.toString();
                    if (reserved.containsKey(identifier)) {
                        TokenKind token = reserved.get(identifier);
                        if (unimplemented.containsKey(identifier))
                            reportUnimplementedError(token);
                        return new TokenInfo(token, line);
                    } else {
                        return new TokenInfo(IDENTIFIER, identifier, line);
                    }
                } else {
                    reportScannerError("Unidentified input token: '%c'", ch);
                    nextCh();
                    return getNextToken();
                }
        }
    }

    /**
     * Scan and return an escaped character.
     *
     * @return escaped character.
     */

    private String escape() {
        switch (ch) {
            case 'b':
                nextCh();
                return "\\b";
            case 't':
                nextCh();
                return "\\t";
            case 'n':
                nextCh();
                return "\\n";
            case 'f':
                nextCh();
                return "\\f";
            case 'r':
                nextCh();
                return "\\r";
            case '"':
                nextCh();
                return "\"";
            case '\'':
                nextCh();
                return "\\'";
            case '\\':
                nextCh();
                return "\\\\";
            default:
                reportScannerError("Badly formed escape: \\%c", ch);
                nextCh();
                return "";
        }
    }

    /**
     * Advance ch to the next character from input, and update the line number.
     */

    private void nextCh() {
        line = input.line();
        try {
            ch = input.nextChar();
        } catch (Exception e) {
            reportScannerError("Unable to read characters from input");
        }
    }

    private void reportUnimplementedError(TokenKind identifier) {
        isInError = true;
        System.err.printf("%s:%d: ", fileName, line);
        System.err.println(identifier.image() + " is not yet implemented in j--.");
    }

    /**
     * Report a lexcial error and record the fact that an error has occured.
     * This fact can be ascertained from the Scanner by sending it an
     * errorHasOccurred() message.
     *
     * @param message message identifying the error.
     * @param args    related values.
     */

    private void reportScannerError(String message, Object... args) {
        isInError = true;
        System.err.printf("%s:%d: ", fileName, line);
        System.err.printf(message, args);
        System.err.println();
    }

    /**
     * Return true if the specified character is a digit (0-9); false otherwise.
     *
     * @param c character.
     * @return true or false.
     */

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private boolean isHexDigit(char c) {
        return isDigit(c) || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    /**
     * Return true if the specified character is a whitespace; false otherwise.
     *
     * @param c character.
     * @return true or false.
     */

    private boolean isWhitespace(char c) {
        switch (c) {
            case ' ':
            case '\t':
            case '\n': // CharReader maps all new lines to '\n'
            case '\f':
                return true;
        }
        return false;
    }

    /**
     * Return true if the specified character can start an identifier name;
     * false otherwise.
     *
     * @param c character.
     * @return true or false.
     */

    private boolean isIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$');
    }

    /**
     * Return true if the specified character can be part of an identifier name;
     * false otherwise.
     *
     * @param c character.
     * @return true or false.
     */

    private boolean isIdentifierPart(char c) {
        return (isIdentifierStart(c) || isDigit(c));
    }

    /**
     * Has an error occurred up to now in lexical analysis?
     *
     * @return true or false.
     */

    public boolean errorHasOccurred() {
        return isInError;
    }

    /**
     * The name of the source file.
     *
     * @return name of the source file.
     */

    public String fileName() {
        return fileName;
    }

}

/**
 * A buffered character reader. Abstracts out differences between platforms,
 * mapping all new lines to '\n'. Also, keeps track of line numbers where the
 * first line is numbered 1.
 */

class CharReader {

    /**
     * A representation of the end of file as a character.
     */
    public final static char EOFCH = (char) -1;

    /**
     * The underlying reader records line numbers.
     */
    private LineNumberReader lineNumberReader;

    /**
     * Name of the file that is being read.
     */
    private String fileName;

    /**
     * Construct a CharReader from a file name.
     *
     * @param fileName the name of the input file.
     * @throws FileNotFoundException if the file is not found.
     */

    public CharReader(String fileName) throws FileNotFoundException {
        lineNumberReader = new LineNumberReader(new FileReader(fileName));
        this.fileName = fileName;
    }

    /**
     * Scan the next character.
     *
     * @return the character scanned.
     * @throws IOException if an I/O error occurs.
     */

    public char nextChar() throws IOException {
        return (char) lineNumberReader.read();
    }

    /**
     * The current line number in the source file, starting at 1.
     *
     * @return the current line number.
     */

    public int line() {
        // LineNumberReader counts lines from 0.
        return lineNumberReader.getLineNumber() + 1;
    }

    /**
     * Return the file name.
     *
     * @return the file name.
     */

    public String fileName() {
        return fileName;
    }

    /**
     * Close the file.
     *
     * @throws IOException if an I/O error occurs.
     */

    public void close() throws IOException {
        lineNumberReader.close();
    }

}
