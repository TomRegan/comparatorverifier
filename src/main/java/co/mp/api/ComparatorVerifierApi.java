package co.mp.api;

import co.mp.Warning;
import co.mp.internal.context.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class ComparatorVerifierApi<T> {

    private final Context<T> context;

    /**
     * Call {@link #create(Comparator, Class)} instead.
     */
    private ComparatorVerifierApi(Comparator<T> comparator, Class<T> cls) {
        this.context = Context.create(comparator, cls);
    }

    public static <T> ComparatorVerifierApi<T> create(Comparator<T> comparator, Class<T> cls) {
        return new ComparatorVerifierApi<>(comparator, cls);
    }

    @SafeVarargs
    public final ComparatorVerifierApi<T> withExamples(T first, T second, T... examples) {
        context.examples(first, second, examples);
        return this;
    }

    public ComparatorVerifierApi<T> withGeneratedExamples(int count) {
        context.examples(count);
        return this;
    }

    public ComparatorVerifierApi<T> permissive() {
        context.asPermissiveContext();
        return this;
    }

    public ComparatorVerifierApi<T> strict() {
        context.asStrictContext();
        return this;
    }

    public ComparatorVerifierApi<T> suppress(Warning first, Warning... rest) {
        var suppressedWarnings = new ArrayList<Warning>();
        suppressedWarnings.add(Objects.requireNonNull(first));
        suppressedWarnings.addAll(Arrays.asList(rest));
        context.asCustomContext(suppressedWarnings);
        return this;
    }

    public void verify() {
        context.verify();
    }

}
