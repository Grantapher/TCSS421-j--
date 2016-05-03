package junit;

import junit.framework.TestCase;
import pass.Shift;

public class ShiftTest extends TestCase {

    public void testLeftShift() {
        assertEquals(0x0001FFFE, Shift.iLeftShift(0x0000FFFF, 1));
        assertEquals(0x0001FFFE0001FFFEL, Shift.lLeftShift(0x0000FFFF0000FFFFL, 1));

        assertEquals(0x000FFFF0, Shift.iLeftShift(0x0000FFFF, 4));
        assertEquals(0x000FFFF0000FFFF0L, Shift.lLeftShift(0x0000FFFF0000FFFFL, 4));
    }

    public void testRightShift() {
        assertEquals(0x00007FFF, Shift.iRightShift(0x0000FFFF, 1));
        assertEquals(0x00007FFF80007FFFL, Shift.lRightShift(0x0000FFFF0000FFFFL, 1));

        assertEquals(0x00000FFF, Shift.iRightShift(0x0000FFFF, 4));
        assertEquals(0x00000FFFF0000FFFL, Shift.lRightShift(0x0000FFFF0000FFFFL, 4));

        assertEquals(0xFFFF8000, Shift.iRightShift(0xFFFF0000, 1));
        assertEquals(0xFFFF80007FFF8000L, Shift.lRightShift(0xFFFF0000FFFF0000L, 1));

        assertEquals(0xFFFFF000, Shift.iRightShift(0xFFFF0000, 4));
        assertEquals(0xFFFFF0000FFFF000L, Shift.lRightShift(0xFFFF0000FFFF0000L, 4));
    }

    public void testUnsignedRightShift() {
        assertEquals(0x00007FFF, Shift.iUunsignedRightShift(0x0000FFFF, 1));
        assertEquals(0x00007FFF80007FFFL, Shift.lUunsignedRightShift(0x0000FFFF0000FFFFL, 1));

        assertEquals(0x00000FFF, Shift.iUunsignedRightShift(0x0000FFFF, 4));
        assertEquals(0x00000FFFF0000FFFL, Shift.lUunsignedRightShift(0x0000FFFF0000FFFFL, 4));

        assertEquals(0x7FFF8000, Shift.iUunsignedRightShift(0xFFFF0000, 1));
        assertEquals(0x7FFF80007FFF8000L, Shift.lUunsignedRightShift(0xFFFF0000FFFF0000L, 1));

        assertEquals(0x0FFFF000, Shift.iUunsignedRightShift(0xFFFF0000, 4));
        assertEquals(0x0FFFF0000FFFF000L, Shift.lUunsignedRightShift(0xFFFF0000FFFF0000L, 4));
    }

}
