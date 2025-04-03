package co.mp;

import co.mp.exception.ComparatorVerificationException;
import static co.mp.fixture.Value.ConsistentValue;
import static co.mp.fixture.Value.SimpleValue;
import static co.mp.fixture.Violation.ViolatesAntiSymmetry;
import static co.mp.fixture.Violation.ViolatesConsistency;
import static co.mp.fixture.Violation.ViolatesConsistentWithEquals;
import static co.mp.fixture.Violation.ViolatesReflexivity;
import static co.mp.fixture.Violation.ViolatesSerializable;
import static co.mp.fixture.Violation.ViolatesTransitivity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

final class ComparatorVerifierTest {

    @Test
    void it_should_detect_when_reflexivity_is_violated() {
        var a = new SimpleValue(1);
        var error = assertThrows(ComparatorVerificationException.class, () ->
                ComparatorVerifier.forComparator(ViolatesReflexivity.class)
                        .only(Warning.REFLEXIVITY)
                        .withExamples(a, a)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.REFLEXIVITY));
    }

    @Test
    void it_should_detect_when_anti_symmetry_is_violated() {
        var a = new SimpleValue(1);
        var b = new SimpleValue(2);
        var error = assertThrows(ComparatorVerificationException.class, () ->
                ComparatorVerifier.forComparator(ViolatesAntiSymmetry.class)
                        .only(Warning.ANTI_SYMMETRY)
                        .withExamples(a, b)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.ANTI_SYMMETRY));
    }

    @Test
    void it_should_detect_when_transitivity_is_violated() {
        // use carefully chosen values that produce a cyclic ordering, as follows
        var a = new SimpleValue(3);  //        3 % 3 = 6 % 3 = 0   -> A(3%3)0, B(5+1%3)0 => 1
        var b = new SimpleValue(5);  //        5 % 3 = 5 % 3 = 2   -> B(5%3)2, C(4+1%3)2 => 1
        var c = new SimpleValue(4);  // (3 % 3 = 0) != (5 % 3 = 2) -> A(3%3)0, C(4+1%3)2 => -1
        // see comments in ViolatesTransitivity
        var error = assertThrows(ComparatorVerificationException.class, () ->
                ComparatorVerifier.forComparator(ViolatesTransitivity.class)
                        .withExamples(a, b, c)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.TRANSITIVITY));
    }

    @Test
    void it_should_detect_when_consistency_is_violated() {
        // Ensure that the comparator's statefulness leads to inconsistency when comparing against a common third value.
        var a = new ConsistentValue(5, 1);
        var b = new ConsistentValue(5, 2); // a == b, 0 == 0
        var c = new ConsistentValue(6, 0); // a == c, 6 == 6; b != c, 7 != 6
        var error = assertThrows(ComparatorVerificationException.class, () ->
                ComparatorVerifier.forComparator(ViolatesConsistency.class)
                        .withExamples(a, b, c)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.CONSISTENCY));
    }

    @Test
    void it_should_detect_when_comparator_is_not_serializable() {
        var error = assertThrows(ComparatorVerificationException.class,
                () -> ComparatorVerifier.forComparator(ViolatesSerializable.class)
                        .strict()
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.SERIALIZABLE));
    }

    @Test
    void it_should_detect_when_consistency_with_equals_is_violated() {
        var a = new SimpleValue(1);
        var b = new SimpleValue(1);
        var error = assertThrows(ComparatorVerificationException.class, () ->
                ComparatorVerifier.forComparator(ViolatesConsistentWithEquals.class)
                        .suppress(Warning.TRANSITIVITY, Warning.SERIALIZABLE)
                        .withExamples(a, b)
                        .verify());
        assertTrue(error.report().hasFailureReason(Warning.CONSISTENT_WITH_EQUALS));
    }

    @Test
    void it_should_accept_an_instance_of_a_comparator() {
        var integerComparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        ComparatorVerifier.forComparator(integerComparator)
                .verify();
    }

    @Test
    void it_should_automatically_generate_examples() {
        ComparatorVerifier.forComparator(Integer::compare, Integer.class).verify();
    }

    @Test
    void it_should_give_feedback_when_there_are_too_few_examples_to_test_with() {
        var error = assertThrows(IllegalArgumentException.class,
                () -> ComparatorVerifier.forComparator(Integer::compare, Integer.class)
                        .withGeneratedExamples(2)
                        .verify());
        var expected = "Too few examples (2) to test transitivity! Disable this test using suppress(Warning.TRANSITIVITY) " +
                "or add more examples";
        assertEquals(expected, error.getMessage());
    }
}


