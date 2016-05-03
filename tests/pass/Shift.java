package pass;

public class Shift {

    public static int iLeftShift(int left, int right) {
        return left << right;
    }

    public static long lLeftShift(long left, int right) {
        return left << right;
    }

    public static int iRightShift(int left, int right) {
        return left >> right;
    }

    public static long lRightShift(long left, int right) {
        return left >> right;
    }

    public static int iUunsignedRightShift(int left, int right) {
        return left >>> right;
    }

    public static long lUunsignedRightShift(long left, int right) {
        return left >>> right;
    }

}
