package co.mp.exception;

import co.mp.ComparatorVerifierReport;

public final class ComparatorVerificationException extends AssertionError {
    private final ComparatorVerifierReport report;

    public ComparatorVerificationException(ComparatorVerifierReport report) {
        super(report.toString());
        this.report = report;
    }

    public ComparatorVerifierReport report() {
        return report;
    }
}
