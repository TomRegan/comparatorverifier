package co.mp.value;

import java.util.Objects;

public record ConsistentValue(int base, int offset) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConsistentValue that = (ConsistentValue) o;
        return base == that.base || base() + offset() == that.base() + that.offset();
    }
}

