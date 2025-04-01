package co.mp.internal.context;

import co.mp.Warning;
import co.mp.internal.context.ExampleGenerator.Configuration;
import co.mp.internal.predicate.ComparatorPredicate;

import java.util.*;
import java.util.stream.Stream;

import static co.mp.internal.predicate.Predicates.*;

public class Context<T> {

    private final List<T> examples = new LinkedList<>();
    private final Comparator<T> comparator;
    private List<ComparatorPredicate<T>> predicates;
    private final ExampleGenerator<T> exampleGenerator;

    private Context(Comparator<T> comparator, Class<T> cls, List<ComparatorPredicate<T>> predicates, List<T> examples) {
        this.comparator = comparator;
        this.predicates = predicates;
        this.examples.addAll(examples);
        this.exampleGenerator = ExampleGenerator.create(cls);
    }

    public static <T> Context<T> create(Comparator<T> comparator, Class<T> cls) {
        Context<T> context = new Context<>(comparator, cls, List.of(), List.of());
        context.asDefaultContext();
        return context;
    }

    public void asDefaultContext() {
        this.predicates = List.of(
                isConsistentWithEquals(comparator),
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator));
    }

    public void asStrictContext() {
        this.predicates = List.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator));
    }

    public void asPermissiveContext() {
        this.predicates = List.of(
                isReflexive(comparator),
                isAntiSymmetric(comparator),
                isTransitive(comparator),
                isConsistent(comparator));
    }

    public void asCustomContext(List<Warning> suppressedWarnings) {
        this.predicates = Stream.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator))
                .filter(predicate -> !suppressedWarnings.contains(predicate.testsFor()))
                .toList();
    }

    public void examples(List<T> examples) {
        this.examples.addAll(examples);
    }

    public void examples(int count) {
        exampleGenerator.update(Configuration.create(count));
    }

    @SafeVarargs
    public final void examples(T first, T second, T... rest) {
        var examples = new LinkedList<T>();
        examples.add(Objects.requireNonNull(first));
        examples.add(Objects.requireNonNull(second));
        examples.addAll(Arrays.asList(rest));
        examples(examples);
    }

    public void verify() {
        if (examples.isEmpty()) {
            examples.addAll(exampleGenerator.generate());
        }
        for (var predicate : predicates) {
            predicate.test(examples);
        }
    }
}
