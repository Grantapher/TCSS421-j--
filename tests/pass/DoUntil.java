package pass;

public class DoUntil {
    public static int normal() {
        int i = 0;
        do {
            i++;
        } until(i >= 10);
        return i; //10
    }

    public static int minimal() {
        int i = 0;
        do {
            i++;
        } until (true);
        return i; //1
    }

    public static int wReturn() {
        int i = 0;
        do {
            i++;
            if (i == 5) {
                return i; //5
            }
        } until (i >= 10);
        return i; //unreachable
    }
}
