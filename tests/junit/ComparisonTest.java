package junit;

import junit.framework.TestCase;
import pass.Comparison;

public class ComparisonTest extends TestCase {

    public void testLessThan() {
        assertFalse(Comparison.lessThan(1, 1));
        assertTrue(Comparison.lessThan(0, 1));
        assertFalse(Comparison.lessThan(1, 0));
    }

    public void testGreaterThan() {
        assertFalse(Comparison.greaterThan(1, 1));
        assertFalse(Comparison.greaterThan(0, 1));
        assertTrue(Comparison.greaterThan(1, 0));
    }

    public void testLessEqual() {
        assertTrue(Comparison.lessEqual(1, 1));
        assertTrue(Comparison.lessEqual(0, 1));
        assertFalse(Comparison.lessEqual(1, 0));
    }

    public void testGreaterEqual() {
        assertTrue(Comparison.greaterEqual(1, 1));
        assertFalse(Comparison.greaterEqual(0, 1));
        assertTrue(Comparison.greaterEqual(1, 0));
    }

    public void testLLessThan() {
        assertFalse(Comparison.lLessThan(1L, 1L));
        assertTrue(Comparison.lLessThan(0L, 1L));
        assertFalse(Comparison.lLessThan(1L, 0L));
    }

    public void testLGreaterThan() {
        assertFalse(Comparison.lGreaterThan(1L, 1L));
        assertFalse(Comparison.lGreaterThan(0L, 1L));
        assertTrue(Comparison.lGreaterThan(1L, 0L));
    }

    public void testLLessEqual() {
        assertTrue(Comparison.lLessEqual(1L, 1L));
        assertTrue(Comparison.lLessEqual(0L, 1L));
        assertFalse(Comparison.lLessEqual(1L, 0L));
    }

    public void testLGreaterEqual() {
        assertTrue(Comparison.lGreaterEqual(1L, 1L));
        assertFalse(Comparison.lGreaterEqual(0L, 1L));
        assertTrue(Comparison.lGreaterEqual(1L, 0L));
    }

}
