package pass;

public class Assignment {

    public static int plusEqual(int start, int add) {
        int local = start;
        local += add;
        return local;
    }

    public static int minusEqual(int start, int sub) {
        int local = start;
        local -= sub;
        return local;
    }

    public static int timesEqual(int start, int mul) {
        int local = start;
        local *= mul;
        return local;
    }

    public static int divideEqual(int start, int div) {
        int local = start;
        local /= div;
        return local;
    }

    public static int modEqual(int start, int mod) {
        int local = start;
        local %= mod;
        return local;
    }

    public static int andEqual(int start, int and) {
        int local = start;
        local &= and;
        return local;
    }

    public static int xorEqual(int start, int xor) {
        int local = start;
        local ^= xor;
        return local;
    }

    public static int orEqual(int start, int or) {
        int local = start;
        local |= or;
        return local;
    }

    public static int leftShiftEqual(int start, int shl) {
        int local = start;
        local <<= shl;
        return local;
    }

    public static int rightShiftEqual(int start, int shr) {
        int local = start;
        local >>= shr;
        return local;
    }

    public static int unsignedRightShiftEqual(int start, int ushr) {
        int local = start;
        local >>>= ushr;
        return local;
    }

}
