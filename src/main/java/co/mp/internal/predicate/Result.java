package co.mp.internal.predicate;

import co.mp.Warning;

public interface Result {

    Warning warning();

    String message();

    boolean successful();

    default boolean failure() {
        return !successful();
    }

    static Result success(Warning warning) {
        return new Success(warning, null);
    }

    static Result failure(Warning warning, String message) {
        return new Failure(warning, message);
    }

    record Success(Warning warning, String message) implements Result {

        @Override
        public boolean successful() {
            return true;
        }
    }

    record Failure(Warning warning, String message) implements Result {

        @Override
        public boolean successful() {
            return false;
        }
    }
}
