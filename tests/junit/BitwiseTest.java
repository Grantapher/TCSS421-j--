package junit;

import junit.framework.TestCase;
import pass.Bitwise;

public class BitwiseTest extends TestCase {

    public void testBitwiseAnd() {
        assertEquals(0x000000FF, Bitwise.iAnd(0x0000FFFF, 0x00FF00FF));
        assertEquals(0x00000000000000FFL, Bitwise.lAnd(0x0000000000000FFFFL, 0x0000000000FF00FFL));
    }

    public void testBitwiseXor() {
        assertEquals(0x00FFFF00, Bitwise.iXor(0x0000FFFF, 0x00FF00FF));
        assertEquals(0x0000000000FFFF00L, Bitwise.lXor(0x000000000000FFFFL, 0x0000000000FF00FFL));
    }

    public void testBitwiseOr() {
        assertEquals(0x00FFFFFF, Bitwise.iOr(0x0000FFFF, 0x00FF00FF));
        assertEquals(0x0000000000FFFFFFL, Bitwise.lOr(0x000000000000FFFFL, 0x0000000000FF00FFL));
    }

}