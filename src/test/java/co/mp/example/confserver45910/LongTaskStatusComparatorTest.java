package co.mp.example.confserver45910;

import co.mp.ComparatorVerifier;
import co.mp.example.confserver45910.fixture.LongTaskStatus;
import co.mp.example.confserver45910.fixture.LongTaskStatusComparator;
import co.mp.example.confserver45910.fixture.Message;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class LongTaskStatusComparatorTest {

    /**
     * This is the original test provided in CONFSERVER-45910.
     *
     * @see <a href="https://jira.atlassian.com/browse/CONFSERVER-45910">CONFSERVER-45910</a>
     */
    @Test
    void it_should_cause_an_illegal_argument_exception_during_sorting() {
        var list = new ArrayList<LongTaskStatus>();
        // this is a common brute force approach, which depends on the fact that
        // TimSort will throw an IAE when it tries to merge. The merge after a
        // minimum of 32 elements, so the brute force algorithm uses at least
        // 32 elements.
        //
        // This breaks the law of symmetry; TimSort is handy as it tends
        // to end up testing two nulls, but it's unnecessary.
        for (int i = 1; i <= 32; i++) {
            LongTaskStatus n = i % 2 == 0 ? new LongTaskStatus(null) : new LongTaskStatus(new Message("somelabel"));
            list.add(n);
        }
        assertThrows(IllegalArgumentException.class, () -> list.sort(new LongTaskStatusComparator()));
    }

    @Disabled("under development")
    @Test
    void it_should_fail_for_anti_symmetry_violation() {
        // TODO need to test with null values to trigger the bug
        var error = assertThrows(AssertionError.class, () -> ComparatorVerifier.forComparator(LongTaskStatusComparator.class).verify());
    }

}
