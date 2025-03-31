package co.mp.internal.predicate;

import co.mp.Warning;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Consistency with equals is an often desirable property for a {@link java.util.Comparator}. It is generally
 * expected (but not strictly required) that if {@code compare(x, y) == 0} then {@code x.equals(y)}.
 * <p>
 * When a comparator does not satisfy this condition, it should be clearly documented by stating:
 * <em>"Note: this comparator imposes orderings that are inconsistent with equals."</em>
 * This warns users that the ordering defined by the comparator is not compatible with the natural
 * equality of the objects.
 * </p>
 *
 * @see java.util.Comparator
 */
final class IsConsistentWithEquals<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    IsConsistentWithEquals(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public void test(List<T> examples) {
        for (T a : examples) {
            for (T b : examples.subList(1, examples.size())) {
                int cmp = comparator.compare(a, b);
                boolean isEqual = a.equals(b);
                if (cmp == 0 && !isEqual || cmp != 0 && isEqual) {
                    throw new AssertionError("Equality violated: compare is inconsistent " +
                            "with equals for instances " + a + " and " + b);
                }
            }
        }
    }

    @Override
    public Warning testsFor() {
        return Warning.CONSISTENT_WITH_EQUALS;
    }
}
