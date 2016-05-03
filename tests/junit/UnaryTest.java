package junit;

import junit.framework.TestCase;
import pass.Unary;

public class UnaryTest extends TestCase {

    public void testComplement() {
        assertEquals(0x0000FFFF, Unary.iComp(0xFFFF0000));
        assertEquals(0x0000FFFF0000FFFFL, Unary.lComp(0xFFFF0000FFFF0000L));
    }

    public void testNegate() {
        assertEquals(-10, Unary.negate(10));
        assertEquals(10, Unary.negate(-10));
        assertEquals(0, Unary.negate(0));
    }

    public void testIncrement() {
        assertEquals(1, Unary.prePreInc(0));
        assertEquals(0, Unary.prePostInc(0));
        assertEquals(1, Unary.postPreInc(0));
        assertEquals(1, Unary.postPostInc(0));

    }
    
    public void testDecrement() {
        assertEquals(-1, Unary.prePreDec(0));
        assertEquals(0, Unary.prePostDec(0));
        assertEquals(-1, Unary.postPreDec(0));
        assertEquals(-1, Unary.postPostDec(0));

    }

    public void testNot() {
        assertEquals(true, Unary.not(false));
        assertEquals(false, Unary.not(true));
    }
}
