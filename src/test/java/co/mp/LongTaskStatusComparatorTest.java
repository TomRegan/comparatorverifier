package co.mp;

import co.mp.fixture.BadExamples.Confserver45910.LongTaskStatus;
import co.mp.fixture.BadExamples.Confserver45910.LongTaskStatusComparator;
import co.mp.fixture.BadExamples.Confserver45910.Message;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

final class LongTaskStatusComparatorTest {

    /**
     * This is the original test provided in CONFSERVER-45910.
     *
     * @see <a href="https://jira.atlassian.com/browse/CONFSERVER-45910">CONFSERVER-45910</a>
     */
    @Test
    void control() {
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

    /**
     * This is our test which uses a test based on the law of symmetry instead.
     */
    @Test
    void candidate() {
        assertThrows(AssertionError.class, () -> ComparatorVerifier.forComparator(LongTaskStatusComparator.class).verify());
    }

}
