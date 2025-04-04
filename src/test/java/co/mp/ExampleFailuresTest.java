package co.mp;

import co.mp.fixture.Value;
import co.mp.fixture.Value.ConsistentValue;
import co.mp.fixture.Violation;
import co.mp.fixture.Violation.ViolatesConsistency;
import co.mp.fixture.Violation.ViolatesConsistentWithEquals;
import co.mp.fixture.Violation.ViolatesSerializable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This suite demonstrates verification failures. This is intended to be
 * helpful in understanding how {@code ComparatorVerifier} works.
 */
@Disabled
final class ExampleFailuresTest {

    @Test
    void demonstrates_failure_when_reflexivity_is_violated() {
        var a = new Value.SimpleValue(1);
        ComparatorVerifier.forComparator(Violation.ViolatesReflexivity.class)
                .only(Warning.REFLEXIVITY)
                .withExamples(a, a)
                .verify();
    }

    @Test
    void demonstrates_failure_when_anti_symmetry_is_violated() {
        var a = new Value.SimpleValue(1);
        var b = new Value.SimpleValue(2);
        ComparatorVerifier.forComparator(Violation.ViolatesAntiSymmetry.class)
                .only(Warning.ANTI_SYMMETRY)
                .withExamples(a, b)
                .verify();
    }

    @Test
    void demonstrates_failure_when_transitivity_is_violated() {
        var a = new Value.SimpleValue(3);
        var b = new Value.SimpleValue(5);
        var c = new Value.SimpleValue(4);
        ComparatorVerifier.forComparator(Violation.ViolatesTransitivity.class)
                .withExamples(a, b, c)
                .verify();
    }

    @Test
    void demonstrates_failure_when_consistency_is_violated() {
        var a = new ConsistentValue(5, 1);
        var b = new ConsistentValue(5, 2);
        var c = new ConsistentValue(6, 0);
        ComparatorVerifier.forComparator(ViolatesConsistency.class)
                .withExamples(a, b, c)
                .verify();
    }

    @Test
    void demonstrates_failure_when_comparator_is_not_serializable() {
        ComparatorVerifier.forComparator(ViolatesSerializable.class)
                .strict()
                .verify();
    }

    @Test
    void demonstrates_failure_when_consistency_with_equals_is_violated() {
        var a = new Value.SimpleValue(1);
        var b = new Value.SimpleValue(1);
        var c = new Value.SimpleValue(1);
        ComparatorVerifier.forComparator(ViolatesConsistentWithEquals.class)
                .withExamples(a, b, c)
                .verify();
    }
}
