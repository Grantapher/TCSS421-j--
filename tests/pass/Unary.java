package pass;

public class Unary {

    public static int iComp(int i) {
        return ~i;
    }

    public static long lComp(long i) {
        return ~i;
    }

    public static int negate(int i) {
        return -i;
    }

    public static int prePreInc(int i) {
        return ++i;
    }

    public static int prePreDec(int i) {
        return --i;
    }

    public static int postPreInc(int i) {
        ++i;
        return i;
    }

    public static int postPreDec(int i) {
        --i;
        return i;
    }

    public static int prePostInc(int i) {
        return i++;
    }

    public static int prePostDec(int i) {
        return i--;
    }

    public static int postPostInc(int i) {
        i++;
        return i;
    }

    public static int postPostDec(int i) {
        i--;
        return i;
    }

    public static boolean not(boolean b) {
        return !b;
    }
}

