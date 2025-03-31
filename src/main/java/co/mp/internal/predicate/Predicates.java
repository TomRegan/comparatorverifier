package co.mp.internal.predicate;

import java.util.Comparator;

public final class Predicates {
    public static <T> ComparatorPredicate<T> isConsistentWithEquals(Comparator<T> comparator) {
        return new IsConsistentWithEquals<>(comparator);
    }

    public static <T> ComparatorPredicate<T> isReflexive(Comparator<T> comparator) {
        return new IsReflexive<>(comparator);
    }

    public static <T> ComparatorPredicate<T> isAntiSymmetric(Comparator<T> comparator) {
        return new IsAntiSymmetric<>(comparator);
    }

    public static <T> ComparatorPredicate<T> isTransitive(Comparator<T> comparator) {
        return new IsTransitive<>(comparator);
    }

    public static <T> ComparatorPredicate<T> isConsistent(Comparator<T> comparator) {
        return new IsConsistent<>(comparator);
    }

    public static <T> ComparatorPredicate<T> isSerializable(Comparator<T> comparator) {
        return new IsSerializable<>(comparator);
    }
}
