package co.mp.internal.predicate;

import co.mp.Warning;

import static co.mp.internal.predicate.Result.failure;
import static co.mp.internal.predicate.Result.success;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Transitivity is an essential property for establishing a consistent order. It requires that for any three
 * elements {@code x}, {@code y}, and {@code z}, if {@code compare(x, y) &gt; 0} and
 * {@code compare(y, z) &gt; 0}, then {@code compare(x, z) &gt; 0}. <p>
 * Similarly, if {@code compare(x, y) &lt; 0} and {@code compare(y, z) &lt; 0}, then
 * {@code compare(x, z) &lt; 0} must hold. This law guarantees that the ordering is consistent across
 * multiple comparisons. </p>
 *
 * @see java.util.Comparator
 */
final class IsTransitive<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    IsTransitive(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public Result test(List<T> examples) {
        // if we have too few examples, we cannot test, so notify the user
        if (examples.size() < 3) {
            var enumName = Warning.class.getSimpleName();
            var memberName = Warning.TRANSITIVITY.name();
            throw new IllegalArgumentException("Too few examples (" + examples.size() + "/3) to test transitivity! " +
                    "Disable this test using suppress(" + enumName + "." + memberName + ") or add more examples");
        }
        for (T a : examples) {
            for (T b : examples.subList(1, examples.size())) {
                for (T c : examples.subList(2, examples.size())) {
                    int cmpAB = comparator.compare(a, b);
                    int cmpBC = comparator.compare(b, c);
                    int cmpAC = comparator.compare(a, c);
                    if ((cmpAB > 0 && cmpBC > 0 && cmpAC <= 0) || (cmpAB < 0 && cmpBC < 0 && cmpAC >= 0)) {
                        return failure(
                                comparator.getClass(),
                                warning(),
                                "(compare(" + a + ", " + b + ") = " + cmpAB + "), " +
                                        "(compare(" + b + ", " + c + ") = " + cmpBC + "), " +
                                        "(compare(" + a + ", " + c + ") = " + cmpAC + ")");
                    }
                }
            }
        }
        return success(comparator.getClass(), warning());
    }

    @Override
    public Warning warning() {
        return Warning.TRANSITIVITY;
    }
}
