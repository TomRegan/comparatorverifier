package co.mp.internal.predicate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Reflexivity is a fundamental property of a {@link java.util.Comparator}. It requires that every element is
 * considered equal to itself. In other words, for any element {@code x},
 * {@code compare(x, x)} must return {@code 0}. <p>
 * This ensures that an element does not compare as greater than or less than itself.
 *
 * @see java.util.Comparator
 */
public final class IsReflexive<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    public IsReflexive(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public void test(List<T> examples) {
        // for every a, comparator.compare(a, a) should be 0.
        for (T a : examples) {
            @SuppressWarnings("EqualsWithItself")
            int cmp = comparator.compare(a, a);
            if (cmp != 0) {
                throw new AssertionError("Reflexivity violated for instance " + a + ": compare(a, a) = " + cmp);
            }
        }
    }
}