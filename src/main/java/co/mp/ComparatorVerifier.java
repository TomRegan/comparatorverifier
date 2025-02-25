package co.mp;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Stream;

import org.instancio.Instancio;

public final class ComparatorVerifier<T> {
    private final List<ComparatorLaw<T>> laws;
    private final List<T> examples = new LinkedList<>();

    private ComparatorVerifier(List<T> examples, List<ComparatorLaw<T>> laws) {
        this.laws = laws;
        this.examples.addAll(examples);
    }

    public static final class Configuration<T> {

        private final Comparator<T> comparator;
        private final Class<T> cls;
        private final List<T> examples = new LinkedList<>();
        private final List<Law> filters = new LinkedList<>();

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
            return withLawDisabled(Law.EQUALITY);
        }

        public Configuration<T> withLawDisabled(Law law) {
            return withLawsDisabled(law);
        }

        public Configuration<T> withLawsDisabled(Law first, Law... laws) {
            this.filters.add(Objects.requireNonNull(first));
            this.filters.addAll(Arrays.asList(laws));
            return this;
        }

        private List<ComparatorLaw<T>> createComparatorLaws() {
            var filterTypes = filters.stream().map(Law::getType).toList();
            return Stream.of(
                            new LawOfEquality<>(comparator),
                            new LawOfReflexivity<>(comparator),
                            new LawOfAntiSymmetry<>(comparator),
                            new LawOfTransitivity<>(comparator),
                            new LawOfConsistency<>(comparator))
                    .filter(law -> !filterTypes.contains(law.getClass())).toList();
        }

        public void verify()  {
            if (examples.isEmpty()) {
                withGeneratedExamples(10);
            }
            var laws = createComparatorLaws();
            new ComparatorVerifier<>(examples, laws).verify();
        }
    }

    /**
     * Creates a ComparatorVerifier for the provided Comparator class
     *
     * @param comparatorClass the class of the Comparator
     * @param <T> the type being compared
     * @return a new ComparatorVerifier instance
     * @throws IllegalArgumentException if the type parameter for Comparator cannot be determined
     */
    public static <T> Configuration<T> forComparator(Class<? extends Comparator<T>> comparatorClass) {
        Comparator<T> comparator = Instancio.create(comparatorClass);
        Class<T> cls = getComparatorType(comparatorClass);
        return new Configuration<>(comparator, cls);
    }

    public static <T> Configuration<T> forComparator(Comparator<T> comparator) {
        var cls = getComparatorType((Class<? extends Comparator<T>>) comparator.getClass());
        return new Configuration<>(comparator, cls);
    }

    public static <T> Configuration<T> forComparator(Comparator<T> comparator, Class<T> cls) {
        return new Configuration<>(comparator, cls);
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getComparatorType(Class<? extends Comparator<T>> comparatorClass) {
        for (var type : comparatorClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType && Comparator.class.equals(parameterizedType.getRawType())) {
                var actualType = parameterizedType.getActualTypeArguments()[0];
                if (actualType instanceof Class<?>) {
                    return (Class<T>) actualType;
                }
            }
        }
        throw new IllegalArgumentException("Cannot determine comparator type parameter for " + comparatorClass.getName());
    }

    private void verify() {
        for (var law : laws) {
            law.test(examples);
        }
    }
}
