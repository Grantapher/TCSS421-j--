package pass;

import java.lang.System;

public class For {
    public static int complicated() {
        int k = 0;
        for (int i = 10,
             j = 0;
             i >= j;
             i++,
                     j += 2)
            k = i;
        return k; //20
    }

    public static int normal() {
        int j = 0;
        for (int i = 0;
             i < 10;
             i++)
            j = i;
        return j; //9
    }

    public static int minimal() {
        for (; ; ) {
            return 2; //2
        }
    }

    public static int wReturn() {
        int j = 0;
        for (int i = 0;
             i < 10;
             i++) {
            j = i;
            if (i == 5) {
                return i; //5
            }
        }
        return j; //unreachable
    }
}
