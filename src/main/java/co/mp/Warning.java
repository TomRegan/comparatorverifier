package co.mp;

import co.mp.internal.predicate.IsAntiSymmetric;
import co.mp.internal.predicate.IsConsistent;
import co.mp.internal.predicate.IsConsistentWithEquals;
import co.mp.internal.predicate.IsReflexive;
import co.mp.internal.predicate.IsSerializable;
import co.mp.internal.predicate.IsTransitive;

@SuppressWarnings("unused")
public enum Warning {
    ANTI_SYMMETRY(IsAntiSymmetric.class),
    CONSISTENCY(IsConsistent.class),
    CONSISTENT_WITH_EQUALS(IsConsistentWithEquals.class),
    REFLEXIVITY(IsReflexive.class),
    TRANSITIVITY(IsTransitive.class),
    SERIALIZABLE(IsSerializable.class),;

    private final Class<?> validator;

    Warning(Class<?> validator) {
        this.validator = validator;
    }

    public Class<?> getValidator() {
        return validator;
    }
}
