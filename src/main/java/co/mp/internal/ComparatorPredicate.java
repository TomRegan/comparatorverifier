package co.mp.internal;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface ComparatorPredicate<T> extends Serializable {
    static <T> ComparatorPredicate<T> isConsistentWithEquals(Comparator<T> comparator) {
        return new IsConsistentWithEquals<>(comparator);
    }

    static <T> ComparatorPredicate<T> isReflexive(Comparator<T> comparator) {
        return new IsReflexive<>(comparator);
    }

    static <T> ComparatorPredicate<T> isAntiSymmetric(Comparator<T> comparator) {
        return new IsAntiSymmetric<>(comparator);
    }

    static <T> ComparatorPredicate<T> isTransitive(Comparator<T> comparator) {
        return new IsTransitive<>(comparator);
    }

    static <T> ComparatorPredicate<T> isConsistent(Comparator<T> comparator) {
        return new IsConsistent<>(comparator);
    }

    static <T> ComparatorPredicate<T> isSerializable(Comparator<T> comparator) {
        return new IsSerializable<>(comparator);
    }

    void test(List<T> examples);
}
