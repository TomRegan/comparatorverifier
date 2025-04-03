package co.mp.exception;

import co.mp.ComparatorVerifierReport;
import co.mp.internal.predicate.Result;

public final class ComparatorVerificationException extends AssertionError {
    private final ComparatorVerifierReport report;

    public ComparatorVerificationException(ComparatorVerifierReport report) {
        super(String.join("\n", report.results().stream().map(Result::message).toList()));
        this.report = report;
    }

    public ComparatorVerifierReport report() {
        return report;
    }
}
