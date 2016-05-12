package junit;

import junit.framework.TestCase;
import pass.Throw;

public class ThrowTest extends TestCase {

    public void testThrow() {
        try {
            Throw.main(null);
            fail("Throw did not throw the expected RuntimeException.");
        } catch (RuntimeException e) {
            //pass
        }
    }
}
