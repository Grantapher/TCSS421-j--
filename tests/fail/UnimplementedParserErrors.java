package fail;

import pass.Unary;

import java.lang.Exception;
import java.lang.System;
import java.util.ArrayList;

// This program has unimplemented constructs and shouldn't compile past parser.

public class UnimplementedParserErrors {

    public UnimplementedParserErrors() throws DummyException {
        //nothing
    }

    public static void test() throws DummyException, DummyException2 {
        int[] ints = new int[10];
        int n = 3;
        do {
            ints[n--] = n;
        } until(n <= 0);
        do {
            ints[++n] = n;
        } while (n <= 0);
        int prev = ints.get(0);
        for (Integer i : ints) {
            try {
                test2(i, prev);
            } catch (DummyException e) {
                throw e;
            }
        }
    }

    private static int test2(int... i) throws DummyException {
        for (int n = 0; n < i.length; n++) ;
        switch (i[n]) {
            case 1:
                return n;
            case 2:
            default:
                throw new DummyException();

        }
    }


}

class DummyException extends Exception {
    //empty
}

class DummyException2 extends DummyException {
    //empty
}