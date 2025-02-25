package co.mp.comparator;

import co.mp.value.SimpleValue;

import java.util.Comparator;

public class ViolatesAntiSymmetry implements Comparator<SimpleValue> {
    @Override
    public int compare(SimpleValue a, SimpleValue b) {
        // Self-comparison returns 0.
        if (a == b) {
            return 0;
        }
        // For any two distinct instances, always return 1.
        return 1;
    }
}

