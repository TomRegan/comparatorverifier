package co.mp.internal.predicate;

import co.mp.Warning;

import static co.mp.internal.predicate.Result.failure;
import static co.mp.internal.predicate.Result.success;

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
final class IsReflexive<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    IsReflexive(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public Result test(List<T> examples) {
        // for every a, comparator.compare(a, a) should be 0.
        for (T a : examples) {
            @SuppressWarnings("EqualsWithItself")
            int cmp = comparator.compare(a, a);
            if (cmp != 0) {
                return failure(
                        comparator.getClass(),
                        warning(),
                        "compare(" + a + ", " + a + ") = " + cmp);
            }
        }
        return success(comparator.getClass(), warning());
    }

    @Override
    public Warning warning() {
        return Warning.REFLEXIVITY;
    }
}
