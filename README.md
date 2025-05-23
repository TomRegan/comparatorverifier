# Comparator Verifier

[![Java Build](https://github.com/tomregan/comparatorverifier/actions/workflows/java-build.yml/badge.svg)](https://github.com/tomregan/comparatorverifier/actions)
[![Apache License 2.0](https://img.shields.io/:license-Apache%20License%202.0-blue.svg?style=shield)](https://github.com/TomRegan/comparatorverifier/blob/main/LICENSE.md)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tomregan/comparatorverifier)](https://central.sonatype.com/artifact/io.github.tomregan/comparatorverifier)
[![Javadoc](https://javadoc.io/badge2/io.github.tomregan/comparatorverifier/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.tomregan/comparatorverifier)

Comparator Verifier is intended for use in tests to validate the contract for Comparator is met.

## Getting Started

### With Maven

```xml
<dependency>
    <groupId>io.github.tomregan</groupId>
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

* 🇬🇧 [Comparator Verifier documentation](https://tomregan.github.io/comparatorverifier/)
* 🇫🇷 [Documentation de Comparator Verifier](https://tomregan.github.io/comparatorverifier/fr/)
* 🇵🇱 [Dokumentacja Comparator Verifier](https://tomregan.github.io/comparatorverifier/pl/)
* 🇧🇬 [Документация на Comparator Verifier](https://tomregan.github.io/comparatorverifier/bg/)

## Developing

### Build Instructions

```bash
mvn verify
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
