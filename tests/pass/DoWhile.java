package pass;

import java.lang.System;

public class DoWhile {
    public static int normal() {
        int i = 0;
        do {
            i++;
        } while (i < 10);
        return i; //10
    }

    public static int minimal() {
        int i = 0;
        do {
            i++;
        } while (false);
        return i; //1
    }

    public static int wReturn() {
        int i = 0;
        do {
            i++;
            if (i == 5) {
                return i; //5
            }
        } while (i < 10);
        return i; //unreachable
    }
}
