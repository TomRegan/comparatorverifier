# Първи стъпки

Comparator Verifier е fluent API за тестване дали имплементацията на
`Comparator` спазва изисквания договор.

## С Maven

Добавете `comparatorverifier` към вашия `pom.xml`.

``` xml
<dependency>
    <groupId>io.github.tomregan</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

## С Gradle

Добавете `comparatorverifier` към вашия файл `build.gradle`.

### С Groovy DSL

``` gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:x.y.x'
}
```

### С Kotlin DSL

``` kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:x.y.x")
}
```

## С JUnit

Напишете unit тест за вашия компаратор.

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.Test;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(FooComparator.class).verify();
}
```

# Допълнително използване

## Примерно използване с JUnit

Comparator Verifier позволява конфигурируема верификация, включваща
както строги, така и по-свободни проверки.

По-долу са показани примерни JUnit тестове с различни конфигурации на
Comparator Verifier.

### По-свободна верификация (Permissive)

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

### Строга верификация (Strict)

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

### Верификация с потиснати предупреждения

{{% hint danger %}} Компаратор, който не отговаря на договора за
Comparator, не е безопасен за използване с Java колекции.

Обмислете използване на `permissive()` или `strict()` за промяна на
поведението на верификацията. {{% /hint %}}

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
