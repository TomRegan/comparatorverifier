package co.mp.internal.predicate;

import co.mp.Warning;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Anti‑symmetry is a fundamental property of a {@link java.util.Comparator}'s contract. It states that for any two
 * objects {@code x} and {@code y}, the signum of {@code compare(x, y)} must be the negation of the signum
 * of {@code compare(y, x)}. <p>
 * In mathematical terms, this is expressed as:
 * <pre>
 *   sgn(compare(x, y)) == -sgn(compare(y, x))
 * </pre>
 * This ensures that if {@code x} is considered greater than {@code y} (i.e. {@code compare(x, y)}
 * returns a positive value), then {@code y} must be considered less than {@code x} (i.e.
 * {@code compare(y, x)} returns a negative value), and vice versa. <p>
 *
 * @see java.util.Comparator
 */
final class IsAntiSymmetric<T> implements ComparatorPredicate<T> {

    private final Comparator<T> comparator;

    IsAntiSymmetric(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
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

    @Override
    public Warning warning() {
        return Warning.ANTI_SYMMETRY;
    }
}