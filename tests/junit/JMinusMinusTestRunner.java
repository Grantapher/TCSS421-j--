// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit test suite for running the j-- programs in tests/pass.
 */

public class JMinusMinusTestRunner {

    public static Test suite() {
        //todo needed FAIL tests - BCOMP (floating point, char), shift ops (fp),
        //todo bitwise ops, ternary (match type), assignmentExpressions (match type)


        //todo much later - throws, throw, for,
        //todo switch, try, arity, do-while/do-until

        //todo bug - method signitures can't tell differences between same name methods

        TestSuite suite = new TestSuite();
        suite.addTestSuite(AssignmentTest.class);
        suite.addTestSuite(BitwiseTest.class);
        suite.addTestSuite(ClassesTest.class);
        suite.addTestSuite(ConditionalTest.class);
        suite.addTestSuite(CommentsTest.class);
        suite.addTestSuite(ComparisonTest.class);
        suite.addTestSuite(DivisionTest.class);
        suite.addTestSuite(FactorialTest.class);
        suite.addTestSuite(FPTest.class);
        suite.addTestSuite(GCDTest.class);
        suite.addTestSuite(HelloWorldTest.class);
        suite.addTestSuite(IntsTest.class);
        suite.addTestSuite(LogicalTest.class);
        suite.addTestSuite(LoopTest.class);
        suite.addTestSuite(ModuloTest.class);
        suite.addTestSuite(SeriesTest.class);
        suite.addTestSuite(ShiftTest.class);
        suite.addTestSuite(TernaryTest.class);
        suite.addTestSuite(ThrowTest.class);
        suite.addTestSuite(UnaryTest.class);
        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
