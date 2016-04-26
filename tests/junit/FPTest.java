package junit;

import junit.framework.TestCase;
import pass.FP;

public class FPTest extends TestCase {

    public void testDouble() {
        assertEquals(0d, FP.dZero1());
        assertEquals(0d, FP.dZero2());
        assertEquals(0d, FP.dZero3());
        assertEquals(0d, FP.dZero4());
        assertEquals(0d, FP.dZero5());
        assertEquals(0d, FP.dZero6());
        assertEquals(.0001d, FP.xxSmallDouble());
        assertEquals(.5d, FP.xSmallDouble());
        assertEquals(1d, FP.smallDouble());
        assertEquals(2d, FP.otherSmallDouble());
        assertEquals(64d, FP.mediumDouble());
        assertEquals(16000d, FP.largeDouble());
        assertEquals(64000d, FP.xLargeDouble());
        assertEquals(2147483648d, FP.xxLargeDouble());
    }

    public void testFloat() {
        assertEquals(0f, FP.fZero1());
        assertEquals(0f, FP.fZero2());
        assertEquals(0f, FP.fZero3());
        assertEquals(0f, FP.fZero4());
        assertEquals(0f, FP.fZero5());
        assertEquals(0f, FP.fZero6());
        assertEquals(.0001f, FP.xxSmallFloat());
        assertEquals(.5f, FP.xSmallFloat());
        assertEquals(1f, FP.smallFloat());
        assertEquals(2f, FP.otherSmallFloat());
        assertEquals(64f, FP.mediumFloat());
        assertEquals(16000f, FP.largeFloat());
        assertEquals(64000f, FP.xLargeFloat());
        assertEquals(2147483648f, FP.xxLargeFloat());
    }
}
