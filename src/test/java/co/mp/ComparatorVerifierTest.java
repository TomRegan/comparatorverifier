package co.mp;

import static co.mp.Value.*;
import static co.mp.Violation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

final class ComparatorVerifierTest {

    @Test
    void it_should_detect_when_reflexivity_is_violated() {
        var a = new SimpleValue(1);
        var error = assertThrows(AssertionError.class, () ->
                ComparatorVerifier.forComparator(ViolatesReflexivity.class)
                        .withExamples(a, a)
                        .verify());
        var expected = "Reflexivity violated for instance " + a + ": compare(a, a) = 1";
        assertEquals(expected, error.getMessage());
    }

    @Test
    void it_should_detect_when_anti_symmetry_is_violated() {
        var a = new SimpleValue(1);
        var b = new SimpleValue(2);
        var error = assertThrows(AssertionError.class, () ->
                ComparatorVerifier.forComparator(ViolatesAntiSymmetry.class)
                        .withExamples(a, b)
                        .verify());
        var expected = "Anti-symmetry violated for instances " + a + " and " + b +
                ": compare(a, b) = 1, compare(b, a) = 1";
        assertEquals(expected, error.getMessage());
    }

    @Test
    void it_should_detect_when_transitivity_is_violated() {
        // use carefully chosen values that produce a cyclic ordering, as follows
        var a = new SimpleValue(3);  //        3 % 3 = 6 % 3 = 0   -> A(3%3)0, B(5+1%3)0 => 1
        var b = new SimpleValue(5);  //        5 % 3 = 5 % 3 = 2   -> B(5%3)2, C(4+1%3)2 => 1
        var c = new SimpleValue(4);  // (3 % 3 = 0) != (5 % 3 = 2) -> A(3%3)0, C(4+1%3)2 => -1
        // see comments in ViolatesTransitivity
        var error = assertThrows(AssertionError.class, () ->
                ComparatorVerifier.forComparator(ViolatesTransitivity.class)
                        .withExamples(a, b, c)
                        .verify());
        var expected = "Transitivity violated: " + a + " > " + b + " and " + b + " > " + c +
                " but " + a + " !> " + c;
        assertEquals(expected, error.getMessage());
    }

    @Test
    void it_should_detect_when_consistency_is_violated() {
        // Ensure that the comparator's statefulness leads to inconsistency when comparing against a common third value.
        var a = new ConsistentValue(5, 1);
        var b = new ConsistentValue(5, 2); // a == b, 0 == 0
        var c = new ConsistentValue(6, 0); // a == c, 6 == 6; b != c, 7 != 6
        var error = assertThrows(AssertionError.class, () ->
                ComparatorVerifier.forComparator(ViolatesConsistency.class)
                        .withExamples(a, b, c)
                        .verify());
        var expected = "Consistency violated: " + a + " and " + b +
                " compare equal but differ when compared with " + c;
        assertEquals(expected, error.getMessage());
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
        var error = assertThrows(AssertionError.class,
                () -> ComparatorVerifier.forComparator(Integer::compare, Integer.class)
                        .withGeneratedExamples(2)
                        .verify());
        var expected = "Too few examples to test transitivity! Disable this test using withLawDisabled(Laws.TRANSITIVITY) " +
                "or add more examples";
        assertEquals(expected, error.getMessage());
    }
}


