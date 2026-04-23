package co.mp.internal.context;

import static co.mp.internal.context.ExampleGenerator.Configuration.DEFAULT_EXAMPLE_COUNT;

import java.util.Collection;
import java.util.Objects;
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
        PropertyConfiguration propertyConfiguration = new PropertyConfiguration();
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

    static final class ImmutableConfiguration implements Configuration {

        private final int count;

        ImmutableConfiguration(int count) {
            this.count = count;
        }

        @Override
        public int count() {
            return count;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            ImmutableConfiguration that = (ImmutableConfiguration) obj;
            return this.count == that.count;
        }

        @Override
        public int hashCode() {
            return Objects.hash(count);
        }

        @Override
        public String toString() {
            return "ImmutableConfiguration[" + "count=" + count + ']';
        }
    }

    static final class PropertyConfiguration implements Configuration {

        private final OptionalInt count;

        PropertyConfiguration(OptionalInt count) {
            this.count = count;
        }

            PropertyConfiguration() {
                this(ComparatorVerifierProperties.getInstance().examplesCount());
            }

            public int count() {
                return count.isPresent() ? count.getAsInt() : DEFAULT_EXAMPLE_COUNT;
            }

            public boolean isAvailable() {
                return count.isPresent();
            }

        public OptionalInt propertyConfigurationCount() {
            return count;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            PropertyConfiguration that = (PropertyConfiguration) obj;
            return Objects.equals(this.count, that.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(count);
        }

        @Override
        public String toString() {
            return "PropertyConfiguration[" +
                "propertyConfigurationCount=" + count + ']';
        }
    }
}
