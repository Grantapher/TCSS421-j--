package pass;

import java.lang.System;

public class Literals {

    public static long smallLong() {
        return 1L;
    }

    public static long mediumLong() {
        return 64L;
    }

    public static long largeLong() {
        return 16000L;
    }

    public static long xLargeLong() {
        return 64000L;
    }

    public static long xxLargeLong() {
        return 2147483648L;
    }

    public static void main(String[] args) {
        System.out.println(2147483648L);
    }
}
