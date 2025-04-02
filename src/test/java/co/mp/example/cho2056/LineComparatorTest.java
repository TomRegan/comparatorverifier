package co.mp.example.cho2056;

import co.mp.ComparatorVerifier;
import co.mp.example.cho2056.fixture.LineComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class LineComparatorTest {
    @Test
    void it_should_fail_for_anti_symmetry_violation() {
        assertThrows(AssertionError.class, () -> ComparatorVerifier.forComparator(LineComparator.class).verify());
    }
}