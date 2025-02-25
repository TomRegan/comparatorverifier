package co.mp;

import java.util.Comparator;
import java.util.List;

/**
 * Consistency (of the ordering) requires that if two elements are considered equal by the comparator,
 * then their ordering with respect to any other element must be the same. Formally, if
 * <code>compare(x, y) == 0</code>, then for every element <code>z</code>, the sign of
 * <code>compare(x, z)</code> must equal the sign of <code>compare(y, z)</code>.
 * <p>
 * This property ensures that equal elements behave identically when compared to all other elements.
 * </p>
 *
 * @see java.util.Comparator
 */

final class LawOfConsistency<T> implements ComparatorLaw<T> {
    private final Comparator<T> comparator;

    LawOfConsistency(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void test(List<T> examples) {
        // if comparator.compare(a, b) == 0 then for every c, sign(comparator.compare(a, c)) should equal sign(comparator.compare(b, c))
        for (T a : examples) {
            for (T b : examples.subList(1, examples.size())) {
                if (comparator.compare(a, b) == 0) {
                    for (T c : examples) {
                        if (Integer.signum(comparator.compare(a, c)) != Integer.signum(comparator.compare(b, c))) {
                            throw new AssertionError("Consistency violated: " + a + " and " + b +
                                    " compare equal but differ when compared with " + c);
                        }
                    }
                }
            }
        }
    }
}