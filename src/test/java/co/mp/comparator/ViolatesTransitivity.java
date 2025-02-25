package co.mp.comparator;

import co.mp.value.SimpleValue;

import java.util.Comparator;

public class ViolatesTransitivity implements Comparator<SimpleValue> {
    @Override
    public int compare(SimpleValue a, SimpleValue b) {
        // set up a situation where a > b, and b > c, but a
        int modA = a.value() % 3;
        int modB = b.value() % 3;
        if (modA == modB) {
            return 0;
        }
        // define a cyclic order: a > b if modA equals (modB+1) mod 3.
        int modBb = (modB + 1) % 3;
        if (modA == modBb) {
            return 1;
        } else {
            return -1;
        }
    }
}

