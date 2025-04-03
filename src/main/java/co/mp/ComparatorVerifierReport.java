package co.mp;

import co.mp.internal.predicate.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class ComparatorVerifierReport {
    private final List<Result> results = new ArrayList<>();

    public void add(Result result) {
        if (result.successful()) {
            return;
        }
        results.add(result);
    }

    public void addAll(Collection<Result> results) {
        results.forEach(this::add);
    }

    public Class<?> type() {
        return results.get(0).type();
    }

    public List<Result> results() {
        return Collections.unmodifiableList(results);
    }

    public static ReportCollector toReport() {
        return new ReportCollector();
    }

    public boolean hasFailures() {
        return results.stream().anyMatch(Result::failure);
    }

    @Override
    public String toString() {
        return """
                ComparatorVerifier found a problem in class %s
                -> %s
                """.formatted(
                type().getName(),
                String.join("\n-> ", results().stream().map(Result::message).toList()));
    }

    public boolean hasFailureReason(Warning warning) {
        return results.stream().map(Result::warning).anyMatch(w -> w == warning);
    }

    public static final class ReportCollector implements Collector<Result, ComparatorVerifierReport, ComparatorVerifierReport> {

        @Override
        public Supplier<ComparatorVerifierReport> supplier() {
            return ComparatorVerifierReport::new;
        }

        @Override
        public BiConsumer<ComparatorVerifierReport, Result> accumulator() {
            return ComparatorVerifierReport::add;
        }

        @Override
        public BinaryOperator<ComparatorVerifierReport> combiner() {
            return (r1, r2) -> {
                r1.addAll(r2.results());
                return r1;
            };
        }

        @Override
        public Function<ComparatorVerifierReport, ComparatorVerifierReport> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.IDENTITY_FINISH);
        }
    }
}

