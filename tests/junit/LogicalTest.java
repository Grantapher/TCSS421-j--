package junit;

import junit.framework.TestCase;
import pass.Logical;

public class LogicalTest extends TestCase {

    public void testEquality() {
        assertTrue(Logical.equal(true, true));
        assertTrue(Logical.equal(false, false));

        assertFalse(Logical.equal(true, false));
    }

    public void testNEquality() {
        assertFalse(Logical.notEqual(true, true));
        assertFalse(Logical.notEqual(false, false));

        assertTrue(Logical.notEqual(true, false));
    }
}
