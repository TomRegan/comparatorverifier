package co.mp.exception;

public final class ComparatorVerifierPropertiesException extends RuntimeException {

    public ComparatorVerifierPropertiesException(String property, String value, Throwable cause) {
        super("`" +value + "' is not a valid value for property `" + property + "'", cause);
    }

}
