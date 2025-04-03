package co.mp.example.confserver45910;

import co.mp.ComparatorVerifier;
import co.mp.Warning;
import co.mp.example.confserver45910.fixture.LongTaskStatus;
import co.mp.example.confserver45910.fixture.LongTaskStatusComparator;
import co.mp.example.confserver45910.fixture.Message;
import co.mp.exception.ComparatorVerificationException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * A comparator can fail in {@code TimSort} in case the results are
 * not transitive. The properties which cover this are transitivity
 * and anti symmetry (which is a two-element case of transitivity).
 *
 * @see java.util.TimSort
 */
final class LongTaskStatusComparatorTest {

    /**
     * This is the original test provided in CONFSERVER-45910.
     *
     * @see <a href="https://jira.atlassian.com/browse/CONFSERVER-45910">CONFSERVER-45910</a>
     */
    @Test
    void tim_sort_should_fail_due_to_an_anti_symmetry_violation() {
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

    @Test
    void it_should_detect_an_anti_symmetry_violation() {
        var a = new LongTaskStatus(null);
        var b = new LongTaskStatus(null);
        var error = assertThrows(ComparatorVerificationException.class,
                () -> ComparatorVerifier.forComparator(LongTaskStatusComparator.class)
                        .only(Warning.ANTI_SYMMETRY)
                        .withExamples(a, b)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.ANTI_SYMMETRY));
    }

}
