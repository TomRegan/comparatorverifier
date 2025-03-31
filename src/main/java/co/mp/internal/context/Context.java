package co.mp.internal.context;

import co.mp.Warning;
import co.mp.internal.predicate.ComparatorPredicate;

import java.util.*;
import java.util.stream.Stream;

import static co.mp.internal.predicate.Predicates.*;

public class Context<T> {

    private final Comparator<T> comparator;
    private List<ComparatorPredicate<T>> predicates;
    private List<T> examples = new LinkedList<>();

    private Context(Comparator<T> comparator, List<ComparatorPredicate<T>> predicates, List<T> examples) {
        this.comparator = comparator;
        this.predicates = predicates;
        this.examples.addAll(examples);
    }

    public static <T> Context<T> create(Comparator<T> comparator) {
        return new Context<>(comparator, List.of(), List.of()).toDefaultContext();
    }

    public Context<T> toDefaultContext() {
        this.predicates = List.of(
                isConsistentWithEquals(comparator),
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator));
        return this;
    }

    public Context<T> toStrictContext() {
        this.predicates = List.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator));
        return this;
    }

    public Context<T> toPermissiveContext() {
        this.predicates = List.of(
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator));
        return this;
    }

    public Context<T> toCustomContext(List<Warning> suppressedWarnings) {
        this.predicates = Stream.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator))
                .filter(predicate -> !suppressedWarnings.contains(predicate.testsFor()))
                .toList();
        return this;
    }

    public Context<T> examples(List<T> examples) {
        this.examples = examples;
        return this;
    }

    @SafeVarargs
    public final Context<T> examples(T first, T second, T... examples) {
        this.examples.add(Objects.requireNonNull(first));
        this.examples.add(Objects.requireNonNull(second));
        this.examples.addAll(Arrays.asList(examples));
        return this;
    }

    public List<ComparatorPredicate<T>> getPredicates() {
        return predicates;
    }

    public List<T> getExamples() {
        return examples;
    }
}
