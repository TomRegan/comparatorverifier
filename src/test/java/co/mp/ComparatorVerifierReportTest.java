package co.mp;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static co.mp.ComparatorVerifierReport.toReport;
import static co.mp.internal.predicate.Result.failure;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class ComparatorVerifierReportTest {

    @Test
    void it_should_format_a_report_with_multiple_failures() {
        var expected = """
                ComparatorVerifier found a problem in java.util.Comparators$NaturalOrderComparator
                -> Anti-symmetry violated: compare(1, 1) = 1, compare(1, 1) = 1
                   Constraint: sgn(compare(x, y)) == -sgn(compare(y, x))
                   See https://tomregan.github.io/comparatorverifier/docs/warnings/#anti_symmetry for further details.
                -> Transitivity violated: (compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)
                   Constraint: (compare(x, y) > 0) && (compare(y, z) > 0) implies that compare(x, z) > 0
                   See https://tomregan.github.io/comparatorverifier/docs/warnings/#transitivity for further details.""";
        var results = List.of(
                failure(Comparator.naturalOrder().getClass(),
                        Warning.ANTI_SYMMETRY,
                        "compare(1, 1) = 1, compare(1, 1) = 1"),
                failure(Comparator.naturalOrder().getClass(),
                        Warning.TRANSITIVITY,
                        "(compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)"));
        var report = results.stream().collect(toReport());
        assertEquals(expected, report.toString());
    }

    @Test
    @DefaultLocale("fr-FR")
    void it_should_format_a_localised_report_with_multiple_failures() {
        assertEquals(Locale.getDefault(), Locale.forLanguageTag("fr-FR"));
        var expected = """
                ComparatorVerifier a détecté un problème dans java.util.Comparators$NaturalOrderComparator
                -> Violation de l'antisymétrie : compare(1, 1) = 1, compare(1, 1) = 1
                   Contrainte : sgn(compare(x, y)) == -sgn(compare(y, x))
                   Voir https://tomregan.github.io/comparatorverifier/fr/docs/warnings/#anti_symmetry pour plus de détails.
                -> Violation de la transitivité : (compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)
                   Contrainte : (compare(x, y) > 0) && (compare(y, z) > 0) implique que compare(x, z) > 0
                   Voir https://tomregan.github.io/comparatorverifier/fr/docs/warnings/#transitivity pour plus de détails.""";
        var results = List.of(
                failure(Comparator.naturalOrder().getClass(),
                        Warning.ANTI_SYMMETRY,
                        "compare(1, 1) = 1, compare(1, 1) = 1"),
                failure(Comparator.naturalOrder().getClass(),
                        Warning.TRANSITIVITY,
                        "(compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)"));
        var report = results.stream().collect(toReport());
        assertEquals(expected, report.toString());
    }
}