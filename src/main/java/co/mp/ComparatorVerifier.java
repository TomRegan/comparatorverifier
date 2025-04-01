package co.mp;

import co.mp.internal.reflection.Types;
import org.instancio.Instancio;

import java.util.Comparator;

/**
 * {@code ComparatorVerifier} is intended for use in tests to validate the contract for Comparator is met. <p>
 * The comparison contract is described in the Javadoc for {@link Comparator#compare(Object, Object)}, and
 * additional guidance on consistency with equals, and serializability is given in the class Javadoc for
 * {@link Comparator}.
 */
public final class ComparatorVerifier {

    /**
     * Call {@link ComparatorVerifier#forComparator} instead.
     */
    private ComparatorVerifier() {
    }

    /**
     * Creates a ComparatorVerifier for the provided Comparator class.
     *
     * @param comparatorClass The class of the Comparator under test.
     * @param <T>             The type being compared.
     * @return An API for ComparatorVerifier.
     * @throws IllegalArgumentException If the type parameter for Comparator cannot be determined.
     */
    public static <T> ComparatorVerifierApi<T> forComparator(Class<? extends Comparator<T>> comparatorClass) {
        Comparator<T> comparator = Instancio.create(comparatorClass);
        Class<T> cls = Types.getComparatorType(comparatorClass);
        return ComparatorVerifierApi.create(comparator, cls);
    }

    /**
     * Creates a ComparatorVerifier for the provided Comparator.
     *
     * @param comparator The comparator under test.
     * @param <T>        The type being compared.
     * @return An API for ComparatorVerifier.
     * @throws IllegalArgumentException if the type parameter for Comparator cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> ComparatorVerifierApi<T> forComparator(Comparator<T> comparator) {
        var cls = Types.getComparatorType((Class<? extends Comparator<T>>) comparator.getClass());
        return ComparatorVerifierApi.create(comparator, cls);
    }

    /**
     * Creates a ComparatorVerifier for the provided Comparator and Comparable class. <p>
     * For use when the comparable type cannot be determined from the Comparator.
     *
     * @param comparator The comparator under test.
     * @param cls        The class of the type being compared.
     * @param <T>        The type being compared.
     * @return An API for ComparatorVerifier.
     */
    public static <T> ComparatorVerifierApi<T> forComparator(Comparator<T> comparator, Class<T> cls) {
        return ComparatorVerifierApi.create(comparator, cls);
    }
}
