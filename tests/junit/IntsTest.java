package junit;

import junit.framework.TestCase;
import pass.Ints;

public class IntsTest extends TestCase {

    public void testLong() {
        assertEquals(0L, Ints.zeroLong());
        assertEquals(15L, Ints.binLong());
        assertEquals(668L, Ints.octalLong());
        assertEquals(11259375L, Ints.hexLong());
        assertEquals(1L, Ints.smallLong());
        assertEquals(64L, Ints.mediumLong());
        assertEquals(16000L, Ints.largeLong());
        assertEquals(64000L, Ints.xLargeLong());
        assertEquals(2147483648L, Ints.xxLargeLong());
    }

    public void testInteger() {
        assertEquals(0, Ints.zeroInt());
        assertEquals(15, Ints.binInt());
        assertEquals(668, Ints.octalInt());
        assertEquals(11259375, Ints.hexInt());
        assertEquals(1, Ints.smallInt());
        assertEquals(64, Ints.mediumInt());
        assertEquals(16000, Ints.largeInt());
        assertEquals(64000, Ints.xLargeInt());
    }
}
