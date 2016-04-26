package fail;

import java.lang.Integer;

//fail cases for literal parsings
public class LiteralFail {

    public static void test() {
        int i = 2147483648;
        long l = 9223372036854775808L;

        i = 0b2;
        i = 08;
        i = 0xG;

        i = 0b;
        i = 0x;

        float f = 0xA.B;
        f = 0x.f;
        f = 0x;
    }
}