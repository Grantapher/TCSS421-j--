package junit;

import junit.framework.TestCase;
import pass.Modulo;

public class ModuloTest extends TestCase {
    private Modulo modulo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        modulo = new Modulo();
    }

    public void testModulo() {
        assertEquals(modulo.mod(10, 3), 1);
        assertEquals(modulo.mod(42, 3), 0);
        assertEquals(modulo.mod(42, 4), 2);
    }
}
