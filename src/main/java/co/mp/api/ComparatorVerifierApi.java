package co.mp.api;

import co.mp.Warning;
import co.mp.internal.context.Context;
import co.mp.internal.context.CustomContext;
import co.mp.internal.context.DefaultContext;
import co.mp.internal.context.PermissiveContext;
import co.mp.internal.context.StrictContext;
import org.instancio.Instancio;

import java.util.*;

public final class ComparatorVerifierApi<T> {

    private enum Mode {
        STRICT,
        DEFAULT,
        PERMISSIVE,
        CUSTOM
    }

    private final Comparator<T> comparator;
    private final Class<T> cls;
    private final List<T> examples = new LinkedList<>();
    private final List<Warning> suppressedWarnings = new LinkedList<>();
    private Mode mode = Mode.DEFAULT;

    public ComparatorVerifierApi(Comparator<T> comparator, Class<T> cls) {
        this.comparator = comparator;
        this.cls = cls;
    }

    @SafeVarargs
    public final ComparatorVerifierApi<T> withExamples(T first, T second, T... examples) {
        this.examples.add(Objects.requireNonNull(first));
        this.examples.add(Objects.requireNonNull(second));
        this.examples.addAll(Arrays.asList(examples));
        return this;
    }

    public ComparatorVerifierApi<T> withGeneratedExamples(int count) {
        List<T> examples = Instancio.ofList(cls).size(count).create();
        this.examples.addAll(examples);
        return this;
    }

    public ComparatorVerifierApi<T> permissive() {
        mode = Mode.PERMISSIVE;
        return this;
    }

    public ComparatorVerifierApi<T> strict() {
        mode = Mode.STRICT;
        return this;
    }

    public ComparatorVerifierApi<T> suppress(Warning first, Warning... warnings) {
        mode = Mode.CUSTOM;
        this.suppressedWarnings.add(Objects.requireNonNull(first));
        this.suppressedWarnings.addAll(Arrays.asList(warnings));
        return this;
    }

    public void verify() {
        if (examples.isEmpty()) {
            withGeneratedExamples(10);
        }
        var context = createContext();
        performVerification(context);
    }

    private Context<T> createContext() {
        return switch (mode) {
            case STRICT -> new StrictContext<>(comparator, examples);
            case DEFAULT -> new DefaultContext<>(comparator, examples);
            case PERMISSIVE -> new PermissiveContext<>(comparator, examples);
            case CUSTOM -> new CustomContext<>(comparator, examples, suppressedWarnings);
        };
    }

    private void performVerification(Context<T> context) {
        var predicates = context.getPredicates();
        for (var predicate : predicates) {
            predicate.test(examples);
        }
    }
}
