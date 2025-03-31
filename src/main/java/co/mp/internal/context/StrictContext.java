package co.mp.internal.context;

import java.util.Comparator;
import java.util.List;

import static co.mp.internal.predicate.Predicates.*;

public final class StrictContext<T> extends Context<T> {
    public StrictContext(Comparator<T> comparator, List<T> examples) {
        super(examples, List.of(
                isConsistentWithEquals(comparator),
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator),
                isSerializable(comparator)));
    }
}
