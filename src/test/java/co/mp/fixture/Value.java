package co.mp.fixture;

import java.util.Objects;

public interface Value {

    final class ConsistentValue {

        private final int base;
        private final int offset;

        public ConsistentValue(int base, int offset) {
            this.base = base;
            this.offset = offset;
        }

        @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                ConsistentValue that = (ConsistentValue) o;
                return base == that.base || base() + offset() == that.base() + that.offset();
            }

        public int base() {
            return base;
        }

        public int offset() {
            return offset;
        }

        @Override
        public int hashCode() {
            return Objects.hash(base, offset);
        }

        @Override
        public String toString() {
            return "ConsistentValue[" +
                "base=" + base + ", " +
                "offset=" + offset + ']';
        }

        }

    final class SimpleValue {

        private final int value;

        public SimpleValue(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            SimpleValue that = (SimpleValue) obj;
            return this.value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "SimpleValue[" +
                "value=" + value + ']';
        }
    }
}
