package co.mp.internal.context;

import java.util.Comparator;
import java.util.List;

import static co.mp.internal.predicate.Predicates.*;

public final class PermissiveContext<T> extends Context<T> {
    public PermissiveContext(Comparator<T> comparator, List<T> examples) {
        super(examples, List.of(
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator)));
    }
}
