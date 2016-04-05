package pass;

import java.lang.System;

public class Modulo {
    public int mod(int x, int y) {
        return x % y;
    }

    public static void main(String[] args) {
        Modulo modulo = new Modulo();
        System.out.print("42 % 3 = ");
        System.out.println(modulo.mod(42, 3));
        System.out.print("42 % 4 = ");
        System.out.println(modulo.mod(42, 4));
    }
}
