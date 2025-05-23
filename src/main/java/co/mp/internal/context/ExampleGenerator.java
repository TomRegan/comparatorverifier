package co.mp.internal.context;

import static co.mp.internal.context.ExampleGenerator.Configuration.DEFAULT_EXAMPLE_COUNT;

import java.util.Collection;
import java.util.OptionalInt;

import org.instancio.Instancio;

final class ExampleGenerator<T> {

    private final Class<T> cls;
    private Configuration configuration;

    private ExampleGenerator(Class<T> cls, Configuration configuration) {
        this.cls = cls;
        this.configuration = configuration;
    }

    static <T> ExampleGenerator<T> create(Class<T> cls) {
        var propertyConfiguration = new PropertyConfiguration();
        return propertyConfiguration.isAvailable()
                ? new ExampleGenerator<>(cls, propertyConfiguration)
                : new ExampleGenerator<>(cls, new ImmutableConfiguration(DEFAULT_EXAMPLE_COUNT));
    }

    Collection<? extends T> generate() {
        return Instancio.ofList(cls).size(configuration.count()).create();
    }

    void update(Configuration configuration) {
        this.configuration = configuration;
    }

    interface Configuration {

        int DEFAULT_EXAMPLE_COUNT = 10;

        int count();

        static Configuration create(int count) {
            return new ImmutableConfiguration(count);
        }
    }

    record ImmutableConfiguration(int count) implements Configuration {
    }

    record PropertyConfiguration(OptionalInt _count) implements Configuration {

        PropertyConfiguration() {
            this(ComparatorVerifierProperties.getInstance().examplesCount());
        }

        public int count() {
            return _count.isPresent() ? _count.getAsInt() : DEFAULT_EXAMPLE_COUNT;
        }

        public boolean isAvailable() {
            return _count.isPresent();
        }
    }
}
