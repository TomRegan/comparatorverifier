package co.mp;

public enum Law {
    ANTI_SYMMETRY(LawOfAntiSymmetry.class),
    CONSISTENCY(LawOfConsistency.class),
    EQUALITY(LawOfEquality.class),
    REFLEXIVITY(LawOfReflexivity.class),
    TRANSITIVITY(LawOfTransitivity.class),;

    private final Class<?> type;

    Law(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
