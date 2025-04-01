package co.mp;

/**
 * Represents contractual properties of a {@link java.util.Comparator} that can be verified.
 * These warnings indicate potential violations of comparator correctness.
 */
public enum Warning {

    /**
     * Indicates that the comparator violates anti-symmetry.
     * A comparator should not return inconsistent results for swapped arguments. <p>
     * That is, for all x and y: if {@code compare(x, y) < 0}, then {@code compare(y, x) > 0} should hold.
     */
    ANTI_SYMMETRY,

    /**
     * Indicates that the comparator violates consistency.
     * A comparator should return consistent ordering when applied multiple times. <p>
     * That is, for all x and y: if {@code compare(x, y) < 0}, then repeated calls should not yield a different result.
     */
    CONSISTENCY,

    /**
     * Indicates that the comparator is not consistent with {@code equals()}.
     * If this warning is triggered, the comparator does not return 0 when comparing two objects that are equal
     * according to {@code equals()}. <p>
     * That is, if {@code x.equals(y)}, then {@code compare(x, y) == 0} should hold.
     */
    CONSISTENT_WITH_EQUALS,

    /**
     * Indicates that the comparator violates reflexivity.
     * A comparator should return 0 when comparing an object with itself. <p>
     * That is, for all x: {@code compare(x, x) == 0} should hold.
     */
    REFLEXIVITY,

    /**
     * Indicates that the comparator violates transitivity.
     * If {@code compare(x, y) < 0} and {@code compare(y, z) < 0}, then {@code compare(x, z) < 0} should hold.
     */
    TRANSITIVITY,

    /**
     * Indicates that the comparator does not properly support serialization.
     * A comparator should be serializable if it is intended to be used in contexts where serialization is required.
     */
    SERIALIZABLE
}
