package co.mp.internal.predicate;

import co.mp.Warning;
import java.util.Objects;

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

    final class Success implements Result {

        private final Class<?> type;
        private final Warning warning;
        private final String message;

        public Success(Class<?> type, Warning warning, String message) {
            this.type = type;
            this.warning = warning;
            this.message = message;
        }

            Success(Class<?> type, Warning warning) {
                this(type, warning, null);
            }

            @Override
            public boolean successful() {
                return true;
            }

        @Override
        public Class<?> type() {
            return type;
        }

        @Override
        public Warning warning() {
            return warning;
        }

        @Override
        public String message() {
            return message;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Success that = (Success) obj;
            return Objects.equals(this.type, that.type) &&
                Objects.equals(this.warning, that.warning) &&
                Objects.equals(this.message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, warning, message);
        }

        @Override
        public String toString() {
            return "Success[" +
                "type=" + type + ", " +
                "warning=" + warning + ", " +
                "message=" + message + ']';
        }

        }

    final class Failure implements Result {

        private final Class<?> type;
        private final Warning warning;
        private final String message;

        public Failure(Class<?> type, Warning warning, String message) {
            this.type = type;
            this.warning = warning;
            this.message = message;
        }

            @Override
            public boolean successful() {
                return false;
            }

        @Override
        public Class<?> type() {
            return type;
        }

        @Override
        public Warning warning() {
            return warning;
        }

        @Override
        public String message() {
            return message;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Failure that = (Failure) obj;
            return Objects.equals(this.type, that.type) &&
                Objects.equals(this.warning, that.warning) &&
                Objects.equals(this.message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, warning, message);
        }

        @Override
        public String toString() {
            return "Failure[" +
                "type=" + type + ", " +
                "warning=" + warning + ", " +
                "message=" + message + ']';
        }

        }
}
