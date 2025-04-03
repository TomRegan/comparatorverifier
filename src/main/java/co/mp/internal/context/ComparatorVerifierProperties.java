package co.mp.internal.context;

import co.mp.exception.PropertiesException;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Properties;

final class ComparatorVerifierProperties {

    private static final ComparatorVerifierProperties INSTANCE = new ComparatorVerifierProperties();

    private final String examplesCount;
    private final String mode;

    private ComparatorVerifierProperties() {
        var properties = load();
        this.examplesCount = properties.getProperty("comparatorverifier.examples.count");
        this.mode = properties.getProperty("comparatorverifier.mode");
    }

    public static ComparatorVerifierProperties getInstance() {
        return INSTANCE;
    }

    public static Properties load() {
        var properties = new Properties();
        try (var inputStream = ExampleGenerator.class.getResourceAsStream("/comparator-verifier.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException ignored) {
        }
        return properties;
    }

    public OptionalInt examplesCount() {
        try {
            return OptionalInt.of(Integer.parseInt(examplesCount));
        } catch (NumberFormatException ignored) {
            return OptionalInt.empty();
        }
    }

    public Optional<VerificationMode> mode() {
        try {
            return Optional.ofNullable(mode)
                    .map(String::toUpperCase)
                    .map(VerificationMode::valueOf);
        } catch (IllegalArgumentException e) {
            throw new PropertiesException("comparatorverifier.mode", mode, e);
        }
    }

}
