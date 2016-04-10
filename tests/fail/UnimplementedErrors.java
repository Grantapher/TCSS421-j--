// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package fail;

import java.lang.Integer;
import java.lang.System;

// This program has unimplemented operators and
// reserved words and shouldn't compile.

public class UnimplementedErrors {

    public static int test(int n) {
        int result = true ? 1 : 0;
        result = ~result;
        result = 8 / 4;
        result /= 2;
        result = 4 + 5;
        result += 12;
        result++;
        result = 10 - 4;
        result -= 4;
        result--;
        result = 4 * 5;
        result *= 4;
        result = 10 % 4;
        result %= 2;
        result = 1 >> 2;
        result >>= 1;
        result = -1 >>> 2;
        result >>>= 1;
        result = 16 << 1;
        result <<= 1;
        result = 3 ^ 1;
        result ^= 1;
        result = 2 | 1;
        result |= 4;
        result = 4 & 2;
        result &= 1;

        boolean bool = true == false;
        bool = !false;
        bool = true != false;
        bool = 3 >= 2;
        bool = 3 > 3;
        bool = 3 <= 3;
        bool = 3 < 3;
        bool = true || false;
        bool = true && false;

        int intLiteral = 0;
        intLiteral = 0x1234567890ABCDEF;
        intLiteral = 0b01;
        intLiteral = 01234567;
        intLiteral = 0L;

        float floatLiteral = 0.;
        floatLiteral = .0e0;
        floatLiteral = 0d;
        floatLiteral = 0e0;
        floatLiteral = 0x.0p0;
        floatLiteral = 0x0.0p0;
        floatLiteral = 0.f;
        floatLiteral = .0e0f;
        floatLiteral = 0e0f;
        floatLiteral = 0x.0p0f;
        floatLiteral = 0x0.0p0f;

        return result;
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        System.out.println(x + "! = " + test(x));
    }

}
