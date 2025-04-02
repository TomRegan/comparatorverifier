package co.mp;

import co.mp.fixture.BadExamples.Cho2056.LineComparator;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LineComparatorTest {
    @Test
    void comparator() {
        var error = assertThrows(AssertionError.class, () -> ComparatorVerifier.forComparator(LineComparator.class)
                .verify());
        System.out.println(error);
    }

}
