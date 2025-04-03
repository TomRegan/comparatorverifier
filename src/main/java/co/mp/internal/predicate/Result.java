package co.mp.internal.predicate;

import co.mp.Warning;

public interface Result {

    Class<?> type();

    Warning warning();

    String message();

    boolean successful();

    default boolean failure() {
        return !successful();
    }

    static Result success(Class<?> type, Warning warning) {
        return new Success(type, warning);
    }

    static Result failure(Class<?> type, Warning warning, String message) {
        return new Failure(type, warning, message);
    }

    record Success(Class<?> type, Warning warning, String message) implements Result {

        Success(Class<?> type, Warning warning) {
            this(type, warning, null);
        }

        @Override
        public boolean successful() {
            return true;
        }
    }

    record Failure(Class<?> type, Warning warning, String message) implements Result {

        @Override
        public boolean successful() {
            return false;
        }
    }
}
