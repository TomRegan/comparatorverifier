package co.mp.example.cho2056;

import co.mp.ComparatorVerifier;
import co.mp.Warning;
import co.mp.example.cho2056.fixture.LineComparator;
import co.mp.exception.ComparatorVerificationException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LineComparatorTest {
    @Test
    void it_should_fail_for_anti_symmetry_violation() {
        var error = assertThrows(
                ComparatorVerificationException.class,
                () -> ComparatorVerifier.forComparator(LineComparator.class).verify());
        assertTrue(error.report().hasFailureReason(Warning.ANTI_SYMMETRY));
    }
}