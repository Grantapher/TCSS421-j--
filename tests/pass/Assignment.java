package pass;

public class Assignment {

    public static int plusEqual(int start, int add) {
        int local = start;
        local += add;
        return local;
    }

    public static int minusEqual(int start, int add) {
        int local = start;
        local -= add;
        return local;
    }

    public static int timesEqual(int start, int add) {
        int local = start;
        local *= add;
        return local;
    }

    public static int divideEqual(int start, int add) {
        int local = start;
        local /= add;
        return local;
    }

    public static int modEqual(int start, int add) {
        int local = start;
        local %= add;
        return local;
    }

    public static int andEqual(int start, int add) {
        int local = start;
        local &= add;
        return local;
    }

    public static int xorEqual(int start, int add) {
        int local = start;
        local ^= add;
        return local;
    }

    public static int orEqual(int start, int add) {
        int local = start;
        local |= add;
        return local;
    }

    public static int leftShiftEqual(int start, int add) {
        int local = start;
        local <<= add;
        return local;
    }

    public static int rightShiftEqual(int start, int add) {
        int local = start;
        local >>= add;
        return local;
    }

    public static int unsignedRightShiftEqual(int start, int add) {
        int local = start;
        local >>>= add;
        return local;
    }

}
