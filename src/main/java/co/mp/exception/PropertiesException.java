package co.mp.exception;

public final class PropertiesException extends RuntimeException {

    public PropertiesException(String property, String value, Throwable cause) {
        super("`" +value + "' is not a valid value for property `" + property + "'", cause);
    }

}
