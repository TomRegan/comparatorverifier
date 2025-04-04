package co.mp.internal.predicate;

import co.mp.Warning;

import static co.mp.internal.predicate.Result.failure;
import static co.mp.internal.predicate.Result.success;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Consistency (of the ordering) requires that if two elements are considered equal by the comparator,
 * then their ordering with respect to any other element must be the same. Formally, if
 * {@code compare(x, y) == 0}, then for every element {@code z}, the sign of
 * {@code compare(x, z)} must equal the sign of {@code compare(y, z)}. <p>
 * This property ensures that equal elements behave identically when compared to all other elements. </p>
 *
 * @see java.util.Comparator
 */
final class IsConsistent<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    IsConsistent(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public Result test(List<T> examples) {
        // if we have too few examples, we cannot test, so notify the user
        if (examples.size() < 3) {
            var enumName = Warning.class.getSimpleName();
            var memberName = warning().name();
            throw new IllegalArgumentException("Too few examples (" + examples.size() + "/3) to test consistency! " +
                    "Disable this test using suppress(" + enumName + "." + memberName + ") or add more examples");
        }
        // if comparator.compare(a, b) == 0 then for every c, sign(comparator.compare(a, c)) should equal sign(comparator.compare(b, c))
        for (T a : examples) {
            for (T b : examples.subList(1, examples.size())) {
                if (comparator.compare(a, b) == 0) {
                    for (T c : examples) {
                        var cmpAC = Integer.signum(comparator.compare(a, c));
                        var cmpBC = Integer.signum(comparator.compare(b, c));
                        if (cmpAC != cmpBC) {
                            return failure(
                                    comparator.getClass(),
                                    warning(),
                                    "(compare(" + a + ", " + b + ") = 0), " +
                                            "(signum(compare(" + a + ", " + c + ")) = " + cmpAC + "), " +
                                            "(signum(compare(" + b + ", " + c + ")) = " + cmpBC + ")");
                        }
                    }
                }
            }
        }
        return success(comparator.getClass(), warning());
    }

    @Override
    public Warning warning() {
        return Warning.CONSISTENCY;
    }
}
