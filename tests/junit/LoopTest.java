package junit;

import junit.framework.TestCase;
import pass.For;
import pass.DoWhile;
import pass.DoUntil;

public class LoopTest extends TestCase {

    public void testFor() {
        assertEquals("complicated", For.complicated(), 20);
        assertEquals("minimal", For.minimal(), 2);
        assertEquals("normal", For.normal(), 9);
        assertEquals("wReturn", For.wReturn(), 5);
    }

    public void testDoUntil() {
        assertEquals("minimal", DoUntil.minimal(), 1);
        assertEquals("normal", DoUntil.normal(), 10);
        assertEquals("wReturn", DoUntil.wReturn(), 5);
    }

    public void testDoWhile() {
        assertEquals("minimal", DoWhile.minimal(), 1);
        assertEquals("normal", DoWhile.normal(), 10);
        assertEquals("wReturn", DoWhile.wReturn(), 5);
    }

}
