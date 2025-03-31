package co.mp.internal.context;

import co.mp.internal.predicate.ComparatorPredicate;

import java.util.LinkedList;
import java.util.List;

public class Context<T> {

    private final List<ComparatorPredicate<T>> predicates;
    private final List<T> examples = new LinkedList<>();

    protected Context(List<T> examples, List<ComparatorPredicate<T>> predicates) {
        this.predicates = predicates;
        this.examples.addAll(examples);
    }

    public List<ComparatorPredicate<T>> getPredicates() {
        return predicates;
    }
}
