package pass;

public class FP {

    public static double dZero1() {
        return 0.;
    }

    public static double dZero2() {
        return .0e0;
    }

    public static double dZero3() {
        return 0e0;
    }

    public static double dZero4() {
        return 0d;
    }

    public static double dZero5() {
        return 0x.0p0;
    }

    public static double dZero6() {
        return 0x0.0p0;
    }

    public static double xxSmallDouble() {
        return .0001d;
    }

    public static double xSmallDouble() {
        return .5d;
    }

    public static double smallDouble() {
        return 1d;
    }

    public static double otherSmallDouble() {
        return 2d;
    }

    public static double mediumDouble() {
        return 64d;
    }

    public static double largeDouble() {
        return 16000d;
    }

    public static double xLargeDouble() {
        return 64000d;
    }

    public static double xxLargeDouble() {
        return 2147483648d;
    }

    public static float xxSmallFloat() {
        return .0001f;
    }

    public static float xSmallFloat() {
        return .5f;
    }

    public static float smallFloat() {
        return 1f;
    }

    public static float otherSmallFloat() {
        return 2f;
    }

    public static float mediumFloat() {
        return 64f;
    }

    public static float largeFloat() {
        return 16000f;
    }

    public static float xLargeFloat() {
        return 64000f;
    }

    public static float xxLargeFloat() {
        return 2147483648f;
    }

    public static float fZero1() {
        return 0.f;
    }

    public static float fZero2() {
        return .0e0f;
    }

    public static float fZero3() {
        return 0e0f;
    }

    public static float fZero4() {
        return 0f;
    }

    public static float fZero5() {
        return 0x.0p0f;
    }

    public static float fZero6() {
        return 0x0.0p0f;
    }
}
