package co.mp.comparator;

import co.mp.value.SimpleValue;

import java.util.Comparator;

public class ViolatesReflexivity implements Comparator<SimpleValue> {
    @Override
    public int compare(SimpleValue a, SimpleValue b) {
        // Violate reflexivity if comparing the same instance
        if (a == b) {
            return 1;
        }
        // Otherwise, use natural ordering.
        return Integer.compare(a.value(), b.value());
    }
}
