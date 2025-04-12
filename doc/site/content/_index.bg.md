# Comparator Verifier

## Comparison method violates its general contract!

Ако сте написали компаратор, който не спазва договора, описан в
[Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html),
ето как обикновено разбирате за това:

```terminal
java.lang.IllegalArgumentException: Comparison method violates its general contract!
    at java.util.ComparableTimSort.mergeHi(ComparableTimSort.java:835)
    at java.util.ComparableTimSort.mergeAt(ComparableTimSort.java:453)
    at java.util.ComparableTimSort.mergeForceCollapse(ComparableTimSort.java:392)
    at java.util.ComparableTimSort.sort(ComparableTimSort.java:191)
    at java.util.ComparableTimSort.sort(ComparableTimSort.java:146)
    at java.util.Arrays.sort(Arrays.java:472)
    at java.util.Collections.sort(Collections.java:155)
    ...
```

Нека го оправим.

# Първи стъпки

Comparator Verifier е интуитивно API за тестване дали дадена имплементация на `Comparator`
спазва изисквания договор, чрез написване на обикновен юнит тест.

## Добавяне на Зависимост

{{% tabs "id" %}}
{{% tab "Maven" %}}

Добавете `comparatorverifier` към вашия файл `pom.xml`.

``` xml
<dependency>
    <groupId>io.github.tomregan</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

{{% /tab %}}
{{% tab "Gradle Groovy" %}}
Добавете `comparatorverifier` към вашия файл `build.gradle`.

```gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:1.0.0'
}
```
{{% /tab %}}
{{% tab "Gradle Kotlin" %}}
Добавете `comparatorverifier` към вашия файл `build.gradle.kts`.

```kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:1.0.0")
}
```
{{% /tab %}}
{{% /tabs %}}


## Напишете Тест

Напишете юнит тест за вашия компаратор.

``` java
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
