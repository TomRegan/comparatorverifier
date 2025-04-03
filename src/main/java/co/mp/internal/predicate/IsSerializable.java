package co.mp.internal.predicate;

import co.mp.Warning;

import static co.mp.internal.predicate.Result.failure;
import static co.mp.internal.predicate.Result.success;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Comparators should be serializable. <p>
 * Note: It is generally a good idea for comparators to also implement
 * {@code java.io.Serializable}, as they may be used as ordering methods in
 * serializable data structures (like {@link TreeSet}, {@link TreeMap}).  In
 * order for the data structure to serialize successfully, the comparator (if
 * provided) must implement {@code Serializable}.<p>
 *
 * @implNote Simple serialization check, does not guarantee serializability of
 * class members
 */
final class IsSerializable<T> implements ComparatorPredicate<T> {
    private final Comparator<T> comparator;

    IsSerializable(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator, "comparator cannot be null");
    }

    @Override
    public Result test(List<T> ignored) {
        if (Serializable.class.isAssignableFrom(comparator.getClass())) {
            return success(comparator.getClass(), warning());
        }
        return failure(
                comparator.getClass(),
                warning(),
                "");
    }

    @Override
    public Warning warning() {
        return Warning.SERIALIZABLE;
    }
}
