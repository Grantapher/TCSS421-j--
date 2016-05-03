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

    public void testAnd() {
        assertFalse(Logical.and(false, false));
        assertFalse(Logical.and(false, true));
        assertFalse(Logical.and(true, false));
        assertTrue(Logical.and(true, true));
    }

    public void testOr() {
        assertFalse(Logical.or(false, false));
        assertTrue(Logical.or(false, true));
        assertTrue(Logical.or(true, false));
        assertTrue(Logical.or(true, true));
    }

    public void testShortCircuiting() {
        assertTrue("Or short circuiting failed", Logical.boxedOr(true, null));
        assertFalse("And short circuiting failed", Logical.boxedAnd(false, null));
    }
}
