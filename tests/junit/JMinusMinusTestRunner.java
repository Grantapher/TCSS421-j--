// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit test suite for running the j-- programs in tests/pass.
 */

public class JMinusMinusTestRunner {

    public static Test suite() {
        //todo needed tests - LOR, BCOMP, shift operators, bitwise ops, ternary

        //todo needed FAIL tests - BCOMP (floating point, char), shift ops (fp), bitwise ops, ternary (match type)

        TestSuite suite = new TestSuite();
        suite.addTestSuite(HelloWorldTest.class);
        suite.addTestSuite(DivisionTest.class);
        suite.addTestSuite(ModuloTest.class);
        suite.addTestSuite(LogicalTest.class);
        suite.addTestSuite(IntsTest.class);
        suite.addTestSuite(FPTest.class);
        suite.addTestSuite(ComparisonTest.class);
        suite.addTestSuite(CommentsTest.class);
        suite.addTestSuite(FactorialTest.class);
        suite.addTestSuite(GCDTest.class);
        suite.addTestSuite(SeriesTest.class);
        suite.addTestSuite(ClassesTest.class);
        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
