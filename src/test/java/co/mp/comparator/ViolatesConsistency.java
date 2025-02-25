package co.mp.comparator;

import co.mp.value.ConsistentValue;

import java.util.Comparator;

public class ViolatesConsistency implements Comparator<ConsistentValue> {
    @Override
    public int compare(ConsistentValue a, ConsistentValue b) {
        // If base values are equal, return 0, ignoring offset.
        if (a.base() == b.base()) {
            return 0;
        }
        // Otherwise, use the sum of base and offset.
        return Integer.compare(a.base() + a.offset(), b.base() + b.offset());
    }
}

