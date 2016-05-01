package fail;

import java.lang.Integer;

//fail cases for literal scannings
public class LiteralScanFail {

    public static void test() {
        int i;

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