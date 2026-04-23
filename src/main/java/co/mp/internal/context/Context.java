package co.mp.internal.context;

import static co.mp.ComparatorVerifierReport.toReport;
import static co.mp.internal.predicate.Predicates.isAntiSymmetric;
import static co.mp.internal.predicate.Predicates.isConsistent;
import static co.mp.internal.predicate.Predicates.isConsistentWithEquals;
import static co.mp.internal.predicate.Predicates.isReflexive;
import static co.mp.internal.predicate.Predicates.isSerializable;
import static co.mp.internal.predicate.Predicates.isTransitive;

import co.mp.Warning;
import co.mp.exception.ComparatorVerificationException;
import co.mp.internal.context.ExampleGenerator.Configuration;
import co.mp.internal.predicate.ComparatorPredicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Context<T> {

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
        Context<T> context = new Context<>(comparator, cls, Collections.emptyList(), Collections.emptyList());
        VerificationMode mode = ComparatorVerifierProperties.getInstance()
                .mode()
                .orElse(VerificationMode.DEFAULT);

        if (mode == VerificationMode.PERMISSIVE) {
            context.asPermissiveContext();
        } else if (mode == VerificationMode.STRICT) {
            context.asStrictContext();
        } else {
            context.asDefaultContext();
        }

        return context;
    }

    public void asDefaultContext() {
        ArrayList<ComparatorPredicate<T>> mutablePredicateList = new ArrayList<>();
        mutablePredicateList.add(isConsistentWithEquals(comparator));
        mutablePredicateList.add(isReflexive(comparator));
        mutablePredicateList.add(isAntiSymmetric(comparator));
        mutablePredicateList.add(isTransitive(comparator));
        mutablePredicateList.add(isConsistent(comparator));

        this.predicates = Collections.unmodifiableList(mutablePredicateList);
    }

    public void asStrictContext() {
        ArrayList<ComparatorPredicate<T>> mutablePredicateList = new ArrayList<>();
        mutablePredicateList.add(isConsistentWithEquals(comparator));
        mutablePredicateList.add(isReflexive(comparator));
        mutablePredicateList.add(isAntiSymmetric(comparator));
        mutablePredicateList.add(isTransitive(comparator));
        mutablePredicateList.add(isConsistent(comparator));
        mutablePredicateList.add(isSerializable(comparator));

        this.predicates = Collections.unmodifiableList(mutablePredicateList);
    }

    public void asPermissiveContext() {
        ArrayList<ComparatorPredicate<T>> mutablePredicateList = new ArrayList<>();
        mutablePredicateList.add(isReflexive(comparator));
        mutablePredicateList.add(isAntiSymmetric(comparator));
        mutablePredicateList.add(isTransitive(comparator));
        mutablePredicateList.add(isConsistent(comparator));

        this.predicates = Collections.unmodifiableList(mutablePredicateList);
    }

    public void asCustomContext(List<Warning> suppressedWarnings) {
        this.predicates = Stream.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator))
                .filter(predicate -> !suppressedWarnings.contains(predicate.warning()))
                .collect(Collectors.toList());
    }

    public void examples(List<T> examples) {
        this.examples.addAll(examples);
    }

    public void examples(int count) {
        exampleGenerator.update(Configuration.create(count));
    }

    @SafeVarargs
    public final void examples(T first, T second, T... rest) {
        List<T> examples = new LinkedList<>();
        examples.add(Objects.requireNonNull(first));
        examples.add(Objects.requireNonNull(second));
        examples.addAll(Arrays.asList(rest));
        examples(examples);
    }

    public void verify() {
        if (examples.isEmpty()) {
            examples.addAll(exampleGenerator.generate());
        }
        co.mp.ComparatorVerifierReport report = predicates.stream()
                .map(predicate -> predicate.test(examples))
                .collect(toReport());
        if (report.hasFailures()) {
            throw new ComparatorVerificationException(report);
        }
    }
}
