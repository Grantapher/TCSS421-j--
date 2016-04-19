package junit;

import junit.framework.TestCase;
import pass.Long;

public class LongTest extends TestCase {

    public void testLong() {
        assertEquals(1L, Long.smallLong());
        assertEquals(64L, Long.mediumLong());
        assertEquals(16000L, Long.largeLong());
        assertEquals(64000L, Long.xLargeLong());
        assertEquals(2147483648L, Long.xxLargeLong());
    }
}
