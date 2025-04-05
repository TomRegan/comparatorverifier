package co.mp.fixture;

import co.mp.fixture.Value.ConsistentValue;
import co.mp.fixture.Value.SimpleValue;

import java.util.Comparator;

public interface Violation {

    /**
     * A {@link java.util.Comparator} should be Serializable for compatibility with Collections
     * Implementations. This Violation simple doesn't implement Serializable and is
     * intended to make testing clearer and easier.
     */
    final class ViolatesSerializable implements Comparator<Integer> {

        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }

    final class ViolatesConsistentWithEquals implements Comparator<SimpleValue> {

        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compare(SimpleValue a, SimpleValue b) {
            // self-comparison returns 0 to satisfy reflexivity
            if (a == b) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * A {@link java.util.Comparator} implementation that violates the anti-symmetry rule.
     *
     * @implNote This comparator defines an ordering where any two distinct instances are
     * considered unequal, but in a non-symmetric manner. Specifically, it always
     * returns {@code 1} for any two distinct objects, which means {@code compare(a, b) > 0}
     * does not imply {@code compare(b, a) < 0}, thereby violating antisymmetry.
     */
    final class ViolatesAntiSymmetry implements Comparator<SimpleValue> {
        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compare(SimpleValue a, SimpleValue b) {
            // self-comparison returns 0 to satisfy reflexivity
            if (a == b) {
                return 0;
            }
            // compare(a, b) = compare(b, a), which violates anti-symmetry
            return 1;
        }
    }

    /**
     * A comparator that violates the consistency requirement of {@link java.util.Comparator}.
     *
     * @implNote This comparator considers two {@code ConsistentValue} objects equal if they have the same
     * {@code base} value, ignoring the {@code offset} in such cases. However, when the {@code base}
     * values differ, it sorts based on the sum of {@code base} and {@code offset}.
     */
    final class ViolatesConsistency implements Comparator<ConsistentValue> {
        @Override
        public int compare(ConsistentValue a, ConsistentValue b) {
            // if base values are equal, return 0, ignoring offset
            if (a.base() == b.base()) {
                return 0;
            }
            // otherwise, use the sum of base and offset
            return Integer.compare(a.base() + a.offset(), b.base() + b.offset());
        }
    }

    /**
     * This {@link java.util.Comparator} implementation intentionally violates the reflexivity property of comparison.
     *
     * @implNote This comparator defines an inconsistent comparison rule where comparing an object
     * with itself (`a == b`) does not return `0`, but instead returns `1`. This breaks
     * the expected contract of {@link java.util.Comparator}, which requires that
     * {@code compare(x, x) == 0} for all {@code x}.
     */
    final class ViolatesReflexivity implements Comparator<SimpleValue> {
        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compare(SimpleValue a, SimpleValue b) {
            // violate reflexivity if comparing the same instance
            if (a == b) {
                return 1;
            }
            // Otherwise, use natural ordering.
            return Integer.compare(a.value(), b.value());
        }
    }

    /**
     * This {@link java.util.Comparator} implementation deliberately violates the transitivity contract.
     * It introduces a cyclic ordering based on an assumed comparison set of 3.
     *
     * @implNote Inputs are grouped into one of three equivalence classes, and rather than comparing
     * absolute values, the comparator looks at which class an element falls into.
     * A three-way comparison is expected to test transitivity, so three groups are configured.
     * While violating transitivity, this comparator will comply with the contract for
     * pair-wise comparisons, antisymmetry, consistency, and reflexivity.
     */
    final class ViolatesTransitivity implements Comparator<SimpleValue> {

        @Override
        public int compare(SimpleValue a, SimpleValue b) {
            // THE NUMBER 3
            // 3 is the smallest non-transitive cycle for three elements, a, b, c
            // if we'd used a smaller number we'd have inconsistency between elements,
            // but not a proper cycle. we could correctly use a larger number, but this
            // would make it harder to visualise the relationship between elements
            //
            // MODULO ARITHMETIC
            // using modulo gives us grouping and rotational ordering
            //
            // GROUPING
            // * numbers that are 0 mod 3 (0, 3, 6, 9...)
            // * numbers that are 1 mod 3 (1, 4, 7, 10...)
            // * numbers that are 2 mod 3 (2, 5, 8, 11...)
            //
            // ROTATIONAL ORDERING
            // 1 → 0 → 2 → (back to 1)
            int remainderA = a.value() % 3;
            int remainderB = b.value() % 3;
            if (remainderA == remainderB) {
                // IS IN SAME GROUP
                // a and b are in the same group, treat them as equal in ordering
                return 0;
            }
            if (remainderA == (remainderB + 1) % 3) {
                // IS IN NEXT GROUP
                // intuitively, this is like a clock with three numbers, 0, 1, 2
                // and two hands, a, b. if a is at 0, b can be at 1 or 2. we compare
                // a to the "next" position of b (i.e. b + 1). if b is at 1, the next
                // position is 2, if b is at 2, it wraps around the clock to 0.
                // if b is 1 (1+1%3) then the next group is 2, therefore a is smaller
                // than b. if b is 2 (2+1%3) then the next group is 0, so a is larger than b.
                return 1;
            } else {
                return -1;
            }
        }

    }
}
