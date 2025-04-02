package co.mp.example.confserver45910.fixture;

import java.util.Comparator;

/**
 * This comparator is incorrect because it does not return 0 when both
 * {@link LongTaskStatus#name} properties are null. A simple way to
 * make this comparator compliant with the contact would be to
 * check both names at the head of the compare method.
 * <pre>{@code
 * if (o1Name == null && o2Name == null) {
 *     return 0;
 * }
 * }</pre>
 */
public class LongTaskStatusComparator implements Comparator<LongTaskStatus> {
    @Override
    public int compare(LongTaskStatus o1, LongTaskStatus o2) {
        // Sorting by name is not as good as sorting by start time (unavailable) but better than anything
        // else we can think of this iteration. Changing this order later is unable to raise any blockers
        // in production.
        Message o1Name = o1.getName();
        if (o1Name == null || o1Name.key() == null) {
            return 1;  // push o1 to bottom
        }

        Message o2Name = o2.getName();
        if (o2Name == null || o2Name.key() == null) {
            return -1;  // push o2 to bottom
        }

        return o1Name.key().compareTo(o2Name.key());
    }

}
