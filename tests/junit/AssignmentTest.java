package junit;

import junit.framework.TestCase;
import pass.Assignment;

public class AssignmentTest extends TestCase {

    public void testPlusEqual() {
        assertEquals(20, Assignment.plusEqual(10, 10));
    }

    public void testMinusEqual() {
        assertEquals(0, Assignment.minusEqual(10, 10));
    }

    public void testTimesEqual() {
        assertEquals(100, Assignment.timesEqual(10, 10));
    }

    public void testDivideEqual() {
        assertEquals(1, Assignment.divideEqual(10, 10));
    }

    public void testModEqual() {
        assertEquals(1, Assignment.modEqual(10, 3));
    }

    public void testAndEqual() {
        assertEquals(0x00F, Assignment.andEqual(0x00F, 0x0FF));
    }

    public void testXorEqual() {
        assertEquals(0x0F0, Assignment.xorEqual(0x00F, 0x0FF));
    }

    public void testOrEqual() {
        assertEquals(0x0FF, Assignment.orEqual(0x00F, 0x0FF));
    }

    public void testLeftShiftEqual() {
        assertEquals(0b10100, Assignment.leftShiftEqual(0b1010, 1));
    }

    public void testRightShiftEqual() {
        assertEquals(0xFFFF8000, Assignment.rightShiftEqual(0xFFFF0000, 1));
        assertEquals(0x7FFF, Assignment.rightShiftEqual(0xFFFF, 1));
    }

    public void testUnsignedRightShiftEqual() {
        assertEquals(0x7FFF8000, Assignment.unsignedRightShiftEqual(0xFFFF0000, 1));
        assertEquals(0x7FFF, Assignment.unsignedRightShiftEqual(0xFFFF, 1));
    }

}
