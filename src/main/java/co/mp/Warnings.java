package co.mp;

import co.mp.internal.IsAntiSymmetric;
import co.mp.internal.IsConsistent;
import co.mp.internal.IsConsistentWithEquals;
import co.mp.internal.IsReflexive;
import co.mp.internal.IsSerializable;
import co.mp.internal.IsTransitive;

public enum Warnings {
    ANTI_SYMMETRY(IsAntiSymmetric.class),
    CONSISTENCY(IsConsistent.class),
    CONSISTENT_WITH_EQUALS(IsConsistentWithEquals.class),
    REFLEXIVITY(IsReflexive.class),
    TRANSITIVITY(IsTransitive.class),
    SERIALIZABLE(IsSerializable.class),;

    private final Class<?> validator;

    Warnings(Class<?> validator) {
        this.validator = validator;
    }

    public Class<?> getValidator() {
        return validator;
    }
}
