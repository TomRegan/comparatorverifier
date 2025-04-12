# Comparator Verifier

## Comparison method violates its general contract!

Si vous avez écrit un comparateur qui ne respecte pas le contrat défini dans la
[Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html),
voici généralement comment vous vous en rendez compte :

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

# Démarrage

Comparator Verifier est une API fluide permettant de tester si une implémentation de `Comparator`
respecte le contrat requis, en écrivant un simple test unitaire.


## Ajouter la Dépendance

{{% tabs "id" %}}
{{% tab "Maven" %}}
Ajoutez `comparatorverifier` à votre fichier `pom.xml`.

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
Ajoutez comparatorverifier à votre fichier build.gradle.

```gradle
dependencies {
    testImplementation 'io.github.tomregan:comparatorverifier:1.0.0'
}
```
{{% /tab %}}
{{% tab "Gradle Kotlin" %}}
Ajoutez comparatorverifier à votre fichier build.gradle.kts.

```kotlin
dependencies {
    testImplementation("io.github.tomregan:comparatorverifier:1.0.0")
}
``` 
{{% /tab %}}
{{% /tabs %}}

## Écrire un Test

Écrivez un test unitaire pour votre comparateur.

``` java
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
