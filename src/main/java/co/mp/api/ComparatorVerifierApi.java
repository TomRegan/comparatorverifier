package co.mp.api;

import co.mp.Warning;
import co.mp.internal.context.Context;
import org.instancio.Instancio;

import java.util.*;

public final class ComparatorVerifierApi<T> {

    private final Class<T> cls;
    private final Context<T> context;

    /**
     * Call {@link #create(Comparator, Class)} instead.
     */
    private ComparatorVerifierApi(Comparator<T> comparator, Class<T> cls) {
        this.cls = cls;
        this.context = Context.create(comparator);
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
        List<T> examples = Instancio.ofList(cls).size(count).create();
        context.examples(examples);
        return this;
    }

    public ComparatorVerifierApi<T> permissive() {
        context.toPermissiveContext();
        return this;
    }

    public ComparatorVerifierApi<T> strict() {
        context.toStrictContext();
        return this;
    }

    public ComparatorVerifierApi<T> suppress(Warning first, Warning... warnings) {
        var suppressedWarnings = new ArrayList<Warning>();
        suppressedWarnings.add(Objects.requireNonNull(first));
        suppressedWarnings.addAll(Arrays.asList(warnings));
        context.toCustomContext(suppressedWarnings);
        return this;
    }

    public void verify() {
        if (context.getExamples().isEmpty()) {
            withGeneratedExamples(10);
        }
        performVerification(context);
    }

    private void performVerification(Context<T> context) {
        var predicates = context.getPredicates();
        var examples = context.getExamples();
        for (var predicate : predicates) {
            predicate.test(examples);
        }
    }
}
