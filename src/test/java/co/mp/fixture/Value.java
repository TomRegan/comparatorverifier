package co.mp.fixture;

public interface Value {
    record ConsistentValue(int base, int offset) {
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ConsistentValue that = (ConsistentValue) o;
            return base == that.base || base() + offset() == that.base() + that.offset();
        }
    }

    record SimpleValue(int value) {}
}
