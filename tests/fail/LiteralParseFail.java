package fail;

import java.lang.Float;
import java.lang.Integer;

//fail cases for literal scannings
public class LiteralScanFail {

    public static void test() {
        int i = 2147483648;
        long l = 9223372036854775808L;
        float f = 3.4028235E39f;
        double d = 1.7976931348623157E309;
    }
}