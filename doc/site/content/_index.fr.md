# Démarrage

Comparator Verifier est une API fluent permettant de tester si une
implémentation de `Comparator` respecte le contrat requis.

Un comparateur qui ne respecte pas le contrat finira, tôt ou tard,
par probablement lancer cette exception :

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

Corrigeons cela.

## Avec Maven

Ajoutez `comparatorverifier` dans votre `pom.xml`.

``` xml
<dependency>
    <groupId>io.github.tomregan</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

## Avec Gradle

Ajoutez `comparatorverifier` dans votre fichier `build.gradle`.

### Utilisation du DSL Groovy

``` gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:x.y.x'
}
```

### Utilisation du DSL Kotlin

``` kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:x.y.x")
}
```

## Avec JUnit

Écrivez un test unitaire pour votre comparator.

``` java
import co.mp.ComparatorVerifier;
import org.junit.jupiter.api.Test;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(FooComparator.class).verify();
}
```

# Utilisation Avancée

## Exemple d'utilisation avec JUnit

Comparator Verifier permet une vérification configurable, offrant à la
fois des contrôles stricts et permissifs.

Voici des exemples de cas de test JUnit démontrant différentes
configurations de Comparator Verifier.

### Vérification permissive

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

### Vérification stricte

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

### Vérification avec avertissements supprimés

{{% hint danger %}} Un comparator qui ne respecte pas le contrat du
comparator ne sera pas sûr à utiliser avec les collections Java.

Envisagez d'utiliser `permissive()` ou `strict()` pour modifier le
comportement de vérification. {{% /hint %}}

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
