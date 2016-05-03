package junit;

import junit.framework.TestCase;
import pass.Ternary;

public class TernaryTest extends TestCase {

    public void testTernaryTrue() {
        assertEquals(5, Ternary.ternary(true, 5, 10));
    }

    public void testTernaryFalse() {
        assertEquals(10, Ternary.ternary(false, 5, 10));
    }

}
