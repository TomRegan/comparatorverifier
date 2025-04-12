# Comparator Verifier

[![Java Build](https://github.com/tomregan/comparatorverifier/actions/workflows/java-build.yml/badge.svg)](https://github.com/tomregan/comparatorverifier/actions)
[![Apache License 2.0](https://img.shields.io/:license-Apache%20License%202.0-blue.svg?style=shield)](https://github.com/jqno/equalsverifier/blob/master/LICENSE.md)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tomregan/comparatorverifier)]([https://maven-badges.herokuapp.com/maven-central/nl.jqno.equalsverifier/equalsverifier/](https://central.sonatype.com/artifact/io.github.tomregan/comparatorverifier))
[![Javadoc](https://javadoc.io/badge2/io.github.tomregan/comparatorverifier/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.tomregan/comparatorverifier)

Comparator Verifier is intended for use in tests to validate the contract for Comparator is met.

## Getting Started

### With Maven

```xml
<dependency>
    <groupId>co.mp</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

### With Gradle

Add `comparatorverifier` to your `build.gradle` file.

#### Using Groovy DSL

```gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:1.0.0'
}
```

#### Using Kotlin DSL

```kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:1.0.0")
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

See [Comparator Verifier documentation](https://tomregan.github.io/comparatorverifier/).

## Developing

### Build Instructions

```bash
mvn verify
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
