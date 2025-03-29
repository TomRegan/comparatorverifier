package co.mp;

import co.mp.internal.ComparatorPredicate;
import co.mp.internal.Types;
import static co.mp.internal.ComparatorPredicate.isAntiSymmetric;
import static co.mp.internal.ComparatorPredicate.isConsistent;
import static co.mp.internal.ComparatorPredicate.isConsistentWithEquals;
import static co.mp.internal.ComparatorPredicate.isReflexive;
import static co.mp.internal.ComparatorPredicate.isSerializable;
import static co.mp.internal.ComparatorPredicate.isTransitive;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.instancio.Instancio;

public sealed abstract class ComparatorVerifier<T> {
    private final List<ComparatorPredicate<T>> predicates;
    private final List<T> examples = new LinkedList<>();

    /**
     * Call {@code ComparatorVerifier#forComparator} instead.
     */
    private ComparatorVerifier(List<T> examples, List<ComparatorPredicate<T>> predicates) {
        this.predicates = predicates;
        this.examples.addAll(examples);
    }

    public static final class Configuration<T> {

        private enum Mode {
            STRICT,
            DEFAULT,
            PERMISSIVE,
            CUSTOM
        }

        private final Comparator<T> comparator;
        private final Class<T> cls;
        private final List<T> examples = new LinkedList<>();
        private final List<Warnings> suppressedWarnings = new LinkedList<>();
        private Mode mode = Mode.DEFAULT;

        public Configuration(Comparator<T> comparator, Class<T> cls) {
            this.comparator = comparator;
            this.cls = cls;
        }

        @SafeVarargs
        public final Configuration<T> withExamples(T first, T second, T... examples) {
            this.examples.add(Objects.requireNonNull(first));
            this.examples.add(Objects.requireNonNull(second));
            this.examples.addAll(Arrays.asList(examples));
            return this;
        }

        public Configuration<T> withGeneratedExamples(int count) {
            List<T> examples = Instancio.ofList(cls).size(count).create();
            this.examples.addAll(examples);
            return this;
        }

        public Configuration<T> permissive() {
            mode = Mode.PERMISSIVE;
            return this;
        }

        public Configuration<T> strict() {
            mode = Mode.STRICT;
            return this;
        }

        public Configuration<T> suppress(Warnings first, Warnings... warnings) {
            mode = Mode.CUSTOM;
            this.suppressedWarnings.add(Objects.requireNonNull(first));
            this.suppressedWarnings.addAll(Arrays.asList(warnings));
            return this;
        }

        public void verify() {
            if (examples.isEmpty()) {
                withGeneratedExamples(10);
            }
            var verifier = createVerifier();
            verifier.verify();
        }

        private ComparatorVerifier<T> createVerifier() {
            return switch (mode) {
                case STRICT -> new StrictComparatorVerifier<>(comparator, examples);
                case DEFAULT -> new DefaultComparatorVerifier<>(comparator, examples);
                case PERMISSIVE -> new PermissiveComparatorVerifier<>(comparator, examples);
                case CUSTOM -> new CustomComparatorVerifier<>(comparator, examples, suppressedWarnings);
            };
        }
    }

    static final class PermissiveComparatorVerifier<T> extends ComparatorVerifier<T> {
        private PermissiveComparatorVerifier(Comparator<T> comparator, List<T> examples) {
            super(examples, List.of(
                    isReflexive(comparator),
                    isAntiSymmetric(comparator),
                    isTransitive(comparator),
                    isConsistent(comparator)));
        }
    }

    static final class DefaultComparatorVerifier<T> extends ComparatorVerifier<T> {
        private DefaultComparatorVerifier(Comparator<T> comparator, List<T> examples) {
            super(examples, List.of(
                    isConsistentWithEquals(comparator),
                    isReflexive(comparator),
                    isAntiSymmetric(comparator),
                    isTransitive(comparator),
                    isConsistent(comparator)));
        }
    }

    static final class StrictComparatorVerifier<T> extends ComparatorVerifier<T> {
        private StrictComparatorVerifier(Comparator<T> comparator, List<T> examples) {
            super(examples, List.of(
                    isConsistentWithEquals(comparator),
                    isReflexive(comparator),
                    isAntiSymmetric(comparator),
                    isTransitive(comparator),
                    isConsistent(comparator),
                    isSerializable(comparator)));
        }
    }

    static final class CustomComparatorVerifier<T> extends ComparatorVerifier<T> {
        private CustomComparatorVerifier(Comparator<T> comparator, List<T> examples, List<Warnings> suppressedWarnings) {
            super(examples, Stream.of(
                            isConsistentWithEquals(comparator),
                            isReflexive(comparator),
                            isAntiSymmetric(comparator),
                            isTransitive(comparator),
                            isConsistent(comparator),
                            isSerializable(comparator))
                    .filter(law -> !suppressedWarnings.stream().map(Warnings::getValidator)
                            .collect(toSet())
                            .contains(law.getClass()))
                    .toList());
        }
    }

    /**
     * Creates a ComparatorVerifier for the provided Comparator class
     *
     * @param comparatorClass the class of the Comparator
     * @param <T>             the type being compared
     * @return a new ComparatorVerifier instance
     * @throws IllegalArgumentException if the type parameter for Comparator cannot be determined
     */
    public static <T> Configuration<T> forComparator(Class<? extends Comparator<T>> comparatorClass) {
        Comparator<T> comparator = Instancio.create(comparatorClass);
        Class<T> cls = Types.getComparatorType(comparatorClass);
        return new Configuration<>(comparator, cls);
    }

    @SuppressWarnings("unchecked")
    public static <T> Configuration<T> forComparator(Comparator<T> comparator) {
        var cls = Types.getComparatorType((Class<? extends Comparator<T>>) comparator.getClass());
        return new Configuration<>(comparator, cls);
    }

    public static <T> Configuration<T> forComparator(Comparator<T> comparator, Class<T> cls) {
        return new Configuration<>(comparator, cls);
    }

    private void verify() {
        for (var law : predicates) {
            law.test(examples);
        }
    }
}
