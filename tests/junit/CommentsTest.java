package junit;

import junit.framework.TestCase;
import pass.Comments;

public class CommentsTest extends TestCase {

    public void testMessage() {
        assertEquals(Comments.message(), "Hello World!");
    }

}
