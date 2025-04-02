# Comparator Verifier

Comparator Verifier is intended for use in tests to validate the contract for Comparator is met.

## Getting Started

### With Maven

```xml
<dependency>
    <groupId>co.mp</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

### With Gradle

Add `comparatorverifier` to your `build.gradle` file.

#### Using Groovy DSL

```gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:x.y.x'
}
```

#### Using Kotlin DSL

```kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:x.y.x")
}
``` 

### With JUnit

```java
import co.mp.ComparatorVerifier;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(FooComparator.class).verify();
}
```

### Further Documentation

See [Comparator Verifier documentation](https://comparatorverifier.github.io).

## Developing

### Build Instructions

```bash
mvn verify
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
