package co.mp.internal.context;

import org.instancio.Instancio;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

final class ExampleGenerator<T> {

    private final Class<T> cls;
    private Configuration configuration;

    private ExampleGenerator(Class<T> cls, Configuration configuration) {
        this.cls = cls;
        this.configuration = configuration;
    }

    static <T> ExampleGenerator<T> create(Class<T> cls) {
        try (var inputStream = ExampleGenerator.class.getResourceAsStream("/comparatorverifier.properties")) {
            var properties = new Properties();
            properties.load(inputStream);
            var count = Integer.parseInt(properties.getProperty("comparatorverifier.examples.count", "10"));
            var configuration = new ImmutableConfiguration(count);
            return new ExampleGenerator<>(cls, configuration);
        } catch (IOException | NullPointerException e) {
            var configuration = new ImmutableConfiguration(10);
            return new ExampleGenerator<>(cls, configuration);
        }
    }

    public Collection<? extends T> generate() {
        return Instancio.ofList(cls).size(configuration.count()).create();
    }

    public void update(Configuration configuration) {
        this.configuration = configuration;
    }

    interface Configuration {
        int count();

        static Configuration create(int count) {
            return new ImmutableConfiguration(count);
        }
    }

    record ImmutableConfiguration(int count) implements Configuration {
    }
}
