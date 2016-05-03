package pass;

import java.lang.Boolean;

public class Logical {
    public static boolean notEqual(boolean a, boolean b) {
        return a != b;
    }

    public static boolean equal(boolean a, boolean b) {
        return a == b;
    }

    public static boolean or(boolean a, boolean b) {
        return a || b;
    }

    public static boolean and(boolean a, boolean b) {
        return a && b;
    }

    public static boolean boxedOr(Boolean a, Boolean b) {
        return (boolean) a || (boolean) b;
    }

    public static boolean boxedAnd(Boolean a, Boolean b) {
        return (boolean) a && (boolean) b;
    }

}