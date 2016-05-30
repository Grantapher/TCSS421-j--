// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.Conditional;

public class ConditionalTest extends TestCase {

    public void testUnless() {
        assertEquals(1, Conditional.normal());
    }

}
