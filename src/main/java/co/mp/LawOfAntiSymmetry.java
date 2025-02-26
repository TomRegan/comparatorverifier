package co.mp;

import java.util.Comparator;
import java.util.List;

/**
 * Anti‑symmetry is a fundamental property of a {@link java.util.Comparator}'s contract. It states that for any two
 * objects <code>x</code> and <code>y</code>, the signum of {@code compare(x, y)} must be the negation of the signum
 * of {@code compare(y, x)}.
 * <p>
 * In mathematical terms, this is expressed as:
 * </p>
 * <pre>
 *   sgn(compare(x, y)) == -sgn(compare(y, x))
 * </pre>
 * <p>
 * This ensures that if <code>x</code> is considered greater than <code>y</code> (i.e. {@code compare(x, y)}
 * returns a positive value), then <code>y</code> must be considered less than <code>x</code> (i.e.
 * {@code compare(y, x)} returns a negative value), and vice versa.
 * </p>
 * <p>
 * Additionally, if either {@code compare(x, y)} or {@code compare(y, x)} throws an exception, the other must
 * throw an exception as well.
 * </p>
 *
 * @see java.util.Comparator
 */

final class LawOfAntiSymmetry<T> implements ComparatorLaw<T> {

    private final Comparator<T> comparator;

    public LawOfAntiSymmetry(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void test(List<T> examples) {
        // for every pair (a, b), sign(comparator.compare(a, b)) == -sign(comparator.compare(b, a))
        for (T a : examples) {
            for (T b : examples.subList(1, examples.size())) {
                int cmp = this.comparator.compare(a, b);
                int cmpReverse = comparator.compare(b, a);
                if (Integer.signum(cmp) != -Integer.signum(cmpReverse)) {
                    throw new AssertionError("Anti-symmetry violated for instances " + a + " and " + b +
                            ": compare(a, b) = " + cmp + ", compare(b, a) = " + cmpReverse);
                }
            }
        }
    }
}