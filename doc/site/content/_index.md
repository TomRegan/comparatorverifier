# Getting Started

Comparator Verifier is a fluent API for testing that a `Comparator`
implementation adheres to the required contract.

## With Maven

Add `comparatorverifier` to your `pom.xml`.

``` xml
<dependency>
    <groupId>io.github.tomregan</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

## With Gradle

Add `comparatorverifier` to your `build.gradle` file.

### Using Groovy DSL

```gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:x.y.x'
}
```

### Using Kotlin DSL

```kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:x.y.x")
}
``` 

## With JUnit

Write a unit test for your comparator.

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.Test;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(FooComparator.class).verify();
}
```

# Further Usage

## Example Usage with JUnit

Comparator Verifier allows for configurable verification,
enabling both strict and permissive checks.

Below are example JUnit test cases demonstrating different
configurations of Comparator Verifier.

### Permissive Verification

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FooComparatorTest {

    @Test
    void comparator_contract_is_met() {
        ComparatorVerifier.forComparator(FooComparator.class)
            .permissive()
            .withExamples(new Foo("x"), new Foo("y"))
            .verify();
    }
}
```

### Strict Verification

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FooComparatorTest {

    @Test
    void comparator_can_be_serialized_in_a_tree_set() {
        ComparatorVerifier.forComparator(FooComparator.class)
            .strict()
            .withExamples(new Foo("a"), new Foo("b"))
            .verify();
    }
}
```

### Verification with Suppressed Warnings

{{% hint danger %}}
A comparator that does not meet the comparator contract will not be safe to use with Java collections.

Consider using `permissive()` or `strict()` instead to alter verification behaviour.
{{% /hint %}}


``` java
import co.mp.ComparatorVerifier;
import co.mp.Warning;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FooComparatorTest {

    @Test
    void comparator_contract_is_met() {
        ComparatorVerifier.forComparator(FooComparator.class)
            .withExamples(new Foo("1"), new Foo("2"), new Foo("3"))
            .suppress(Warning.TRANSITIVITY)
            .verify();
    }
}
```
