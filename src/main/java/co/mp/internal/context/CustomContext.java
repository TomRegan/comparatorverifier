package co.mp.internal.context;

import co.mp.Warning;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static co.mp.internal.predicate.Predicates.*;
import static java.util.stream.Collectors.toSet;

public final class CustomContext<T> extends Context<T> {
    public CustomContext(Comparator<T> comparator, List<T> examples, List<Warning> suppressedWarnings) {
        super(examples, Stream.of(
                        isConsistentWithEquals(comparator),
                        isReflexive(comparator),
                        isAntiSymmetric(comparator),
                        isTransitive(comparator),
                        isConsistent(comparator),
                        isSerializable(comparator))
                .filter(law -> !suppressedWarnings.stream().map(Warning::getValidator)
                        .collect(toSet())
                        .contains(law.getClass()))
                .toList());
    }
}
