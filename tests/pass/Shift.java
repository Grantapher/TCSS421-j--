package pass;

public class Shift {

    public static int iLShift(int left, int right) {
        return left << right;
    }

    public static int cLShift(char left, int right) {
        return left << right;
    }

    public static long lLShift(long left, int right) {
        return left << right;
    }

    public static int iRShift(int left, int right) {
        return left >> right;
    }

    public static int cRShift(char left, int right) {
        return left >> right;
    }

    public static long lRShift(long left, int right) {
        return left >> right;
    }

    public static int iURShift(int left, int right) {
        return left >>> right;
    }

    public static int cURShift(char left, int right) {
        return left >>> right;
    }

    public static long lURShift(long left, int right) {
        return left >>> right;
    }
}
