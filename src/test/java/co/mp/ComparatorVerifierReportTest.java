package co.mp;

import co.mp.internal.predicate.Result;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static co.mp.ComparatorVerifierReport.toReport;
import static co.mp.internal.predicate.Result.failure;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class ComparatorVerifierReportTest {

    @Test
    void it_should_format_a_report_with_multiple_failures() {
        String expected = "ComparatorVerifier found a problem in java.util.Comparators$NaturalOrderComparator\n"
            + "-> Anti-symmetry violated: compare(1, 1) = 1, compare(1, 1) = 1\n"
            + "   Constraint: sgn(compare(x, y)) == -sgn(compare(y, x))\n"
            + "   See https://tomregan.github.io/comparatorverifier/docs/warnings/#anti_symmetry for further details.\n"
            + "-> Transitivity violated: (compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)\n"
            + "   Constraint: (compare(x, y) > 0) && (compare(y, z) > 0) implies that compare(x, z) > 0\n"
            + "   See https://tomregan.github.io/comparatorverifier/docs/warnings/#transitivity for further details.";
        List<Result> results = new ArrayList<>();
        results.add(failure(Comparator.naturalOrder().getClass(),
            Warning.ANTI_SYMMETRY,
            "compare(1, 1) = 1, compare(1, 1) = 1"));
        results.add(failure(Comparator.naturalOrder().getClass(),
            Warning.TRANSITIVITY,
            "(compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)"));
        ComparatorVerifierReport report = results.stream().collect(toReport());
        assertEquals(expected, report.toString());
    }

    @Test
    void it_should_format_a_localised_report_with_multiple_failures() {
        Locale originalDefault = Locale.getDefault();

        try {
            Locale.setDefault(Locale.forLanguageTag("fr-FR"));
            String expected = "ComparatorVerifier a détecté un problème dans java.util.Comparators$NaturalOrderComparator\n"
                + "-> Violation de l'antisymétrie : compare(1, 1) = 1, compare(1, 1) = 1\n"
                + "   Contrainte : sgn(compare(x, y)) == -sgn(compare(y, x))\n"
                + "   Voir https://tomregan.github.io/comparatorverifier/fr/docs/warnings/#anti_symmetry pour plus de détails.\n"
                + "-> Violation de la transitivité : (compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)\n"
                + "   Contrainte : (compare(x, y) > 0) && (compare(y, z) > 0) implique que compare(x, z) > 0\n"
                + "   Voir https://tomregan.github.io/comparatorverifier/fr/docs/warnings/#transitivity pour plus de détails.";
            List<Result> results = new ArrayList<>();
            results.add(failure(Comparator.naturalOrder().getClass(),
                Warning.ANTI_SYMMETRY,
                "compare(1, 1) = 1, compare(1, 1) = 1"));
            results.add(failure(Comparator.naturalOrder().getClass(),
                Warning.TRANSITIVITY,
                "(compare(SimpleValue[value=3], SimpleValue[value=5]) = 1), (compare(SimpleValue[value=5], SimpleValue[value=4]) = 1), (compare(SimpleValue[value=3], SimpleValue[value=4]) = -1)"));
            ComparatorVerifierReport report = results.stream().collect(toReport());
            assertEquals(expected, report.toString());
        } finally {
            Locale.setDefault(originalDefault);
        }
    }
}