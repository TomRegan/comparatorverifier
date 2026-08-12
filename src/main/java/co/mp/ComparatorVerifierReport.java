package co.mp;

import co.mp.internal.predicate.Result;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ComparatorVerifierReport {
    private final List<Result> results = new ArrayList<>();
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

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
        MessageFormat reportFormat = new MessageFormat(bundle.getString("report"));
        return reportFormat.format(
                new Object[]{
                        type().getName(),
                        String.join("\n-> ", results().stream().map(this::resultToString).collect(Collectors.toList()))
                });
    }

    private String resultToString(Result result) {
        String key = "warning." + result.warning().name().toLowerCase().replace('_', '.');
        MessageFormat resultFormat = new MessageFormat(bundle.getString(key));
        String message = result.message();
        return resultFormat.format(new Object[]{
                message
        });
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

