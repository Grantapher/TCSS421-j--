package junit;

import junit.framework.TestCase;
import pass.Literals;

public class LiteralsTest extends TestCase {

    public void testLong() {
        assertEquals(1L, Literals.smallLong());
        assertEquals(64L, Literals.mediumLong());
        assertEquals(16000L, Literals.largeLong());
        assertEquals(64000L, Literals.xLargeLong());
        assertEquals(2147483648L, Literals.xxLargeLong());
    }
}
