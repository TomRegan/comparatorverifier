# Rozpoczęcie pracy

Comparator Verifier to płynne API do testowania, czy implementacja
`Comparatora` spełnia wymagany kontrakt.

## Z Maven

Dodaj `comparatorverifier` do pliku `pom.xml`.

``` xml
<dependency>
    <groupId>io.github.tomregan</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

## Z Gradle

Dodaj `comparatorverifier` do pliku `build.gradle`.

### Używając Groovy DSL

``` gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:x.y.x'
}
```

### Używając Kotlin DSL

``` kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:x.y.x")
}
```

## Z JUnit

Napisz test jednostkowy dla swojego comparatora.

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.Test;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(FooComparator.class).verify();
}
```

# Dalsze wykorzystanie

## Przykładowe użycie z JUnit

Comparator Verifier umożliwia konfigurowalną weryfikację, pozwalając na
stosowanie zarówno ścisłych, jak i łagodnych sprawdzeń.

Poniżej znajdują się przykładowe przypadki testowe JUnit demonstrujące
różne konfiguracje Comparator Verifier.

### Weryfikacja łagodna

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

### Weryfikacja ścisła

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

### Weryfikacja z wyłączonymi ostrzeżeniami

{{% hint danger %}} Comparator, który nie spełnia kontraktu comparatora,
nie będzie bezpieczny w użyciu z kolekcjami Javy.

Rozważ użycie `permissive()` lub `strict()` aby zmienić zachowanie
weryfikacji. {{% /hint %}}

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
