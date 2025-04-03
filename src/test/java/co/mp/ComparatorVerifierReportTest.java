package co.mp;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static co.mp.ComparatorVerifierReport.toReport;
import static co.mp.internal.predicate.Result.failure;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class ComparatorVerifierReportTest {

    @Test
    void it_should_format_a_report_with_multiple_failures() {
        var expected = """
                ComparatorVerifier found a problem in class java.util.Comparators$NaturalOrderComparator
                -> Anti-symmetry violated: 1 and 1: compare(a, b) = 1, compare(b, a) = 1
                    See https://tomregan.github.io/comparatorverifier/docs/warnings/#anti_symmetry for further details.
                -> Transitivity violated: 1 > 1 and 1 > 1 but 1 !> 1
                    See https://tomregan.github.io/comparatorverifier/docs/warnings/#transitivity for further details.""";
        var results = List.of(
                failure(Comparator.naturalOrder().getClass(),
                        Warning.ANTI_SYMMETRY,
                        "1 and 1: compare(a, b) = 1, compare(b, a) = 1"),
                failure(Comparator.naturalOrder().getClass(),
                        Warning.TRANSITIVITY,
                        "1 > 1 and 1 > 1 but 1 !> 1"));
        var report = results.stream().collect(toReport());
        assertEquals(expected, report.toString());
    }
}