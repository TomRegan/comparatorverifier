package co.mp;

import co.mp.internal.context.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Provides a fluent API to verify the correctness of a {@link Comparator} implementation. <p>
 * Use {@link #create(Comparator, Class)} to instantiate this class and configure verification options
 * using methods such as {@link #withExamples(Object, Object, Object...)}, {@link #strict()}, or {@link #suppress(Warning, Warning...)}.
 * Finally, call {@link #verify()} to run the verification process. </p>
 *
 * @param <T> the type of objects compared by the {@link Comparator}
 */
public final class ComparatorVerifierApi<T> {

    private final Context<T> context;

    /**
     * Private constructor. Use {@link #create(Comparator, Class)} to obtain an instance.
     */
    private ComparatorVerifierApi(Comparator<T> comparator, Class<T> cls) {
        this.context = Context.create(comparator, cls);
    }

    /**
     * Creates a new instance of {@code ComparatorVerifierApi} for the given comparator and class.
     *
     * @param <T>        the type of objects compared by the comparator
     * @param comparator the comparator to verify
     * @param cls        the class of the objects being compared
     * @return a new instance of {@code ComparatorVerifierApi}
     */
    static <T> ComparatorVerifierApi<T> create(Comparator<T> comparator, Class<T> cls) {
        return new ComparatorVerifierApi<>(comparator, cls);
    }

    /**
     * Provides explicit rest to guide verification. <p>
     * At least two examples are required.
     *
     * @param first  the first example
     * @param second the second example
     * @param rest   additional examples
     * @return {@code this}, for method chaining
     */
    @SafeVarargs
    final ComparatorVerifierApi<T> withExamples(T first, T second, T... rest) {
        context.examples(first, second, rest);
        return this;
    }

    /**
     * Generates a set of examples for verification.
     *
     * @param count the number of examples to generate
     * @return {@code this}, for method chaining
     */
    ComparatorVerifierApi<T> withGeneratedExamples(int count) {
        context.examples(count);
        return this;
    }

    /**
     * Enables permissive verification, allowing minor inconsistencies.
     *
     * @return {@code this}, for method chaining
     */
    ComparatorVerifierApi<T> permissive() {
        context.asPermissiveContext();
        return this;
    }

    /**
     * Enables strict verification, enforcing full compliance.
     *
     * @return {@code this}, for method chaining
     */
    ComparatorVerifierApi<T> strict() {
        context.asStrictContext();
        return this;
    }

    /**
     * Suppresses specific warnings during verification.
     *
     * @param first the first warning to suppress
     * @param rest  additional warnings to suppress
     * @return {@code this}, for method chaining
     * @throws NullPointerException if {@code first} is {@code null}
     * @see Warning
     */
    ComparatorVerifierApi<T> suppress(Warning first, Warning... rest) {
        var suppressedWarnings = new ArrayList<Warning>();
        suppressedWarnings.add(Objects.requireNonNull(first));
        suppressedWarnings.addAll(Arrays.asList(rest));
        context.asCustomContext(suppressedWarnings);
        return this;
    }

    /**
     * Runs the verification process for the configured comparator.
     * Throws an exception if verification fails.
     */
    void verify() {
        context.verify();
    }
}
