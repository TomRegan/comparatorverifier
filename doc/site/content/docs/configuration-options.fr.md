---
weight: 1
bookFlatSection: true
title: Options de configuration
---

# Options de configuration

Les options peuvent être définies en utilisant l'API fluent. Certaines
options sont également disponibles dans le fichier
`comparator-verifier.properties` qui doit se trouver sur le classpath
des tests, par exemple dans `src/test/resources`. L'utilisation de l'API
permet de définir des valeurs pour chaque test individuellement, tandis
que la configuration dans le fichier de propriétés fournit une valeur
par défaut pour tous les tests. Les valeurs définies via l'API auront la
priorité sur celles du fichier de propriétés.

### `withExamples(T first, T second, T... rest)`

Fournit des exemples explicites pour la vérification. Au moins deux
exemples sont requis.

### `withGeneratedExamples(int count)` ou `comparatorverifier.examples.count`

Génère automatiquement des exemples pour la vérification.

### `strict()` ou `comparatorverifier.mode=strict`

Active une vérification stricte. Les contrôles suivants sont inclus :

- `Warning.SERIALIZABLE`

### `permissive()` ou `comparatorverifier.mode=permissive`

Active une vérification permissive, en excluant les contrôles suivants :

- `Warning.CONSISTENT_WITH_EQUALS`
- `Warning.SERIALIZABLE`

### `suppress(Warning first, Warning... rest)` / `only(Warning first, Warning... rest)`

{{% hint danger %}} Un comparateur qui ne respecte pas le contrat du
comparateur ne sera pas sûr à utiliser avec les collections Java.

Envisagez d'utiliser `permissive()` ou `strict()` pour modifier le
comportement de vérification. {{% /hint %}}

Permet de supprimer ou de restreindre à certains avertissements lors de
la vérification.

### `verify()`

Lance le processus de vérification et lève une exception si la
validation échoue.

# Utilisation d'un fichier de propriétés

Créez un fichier dans `src/test/resources` nommé
`comparator-verifier.properties`.

``` properties
# nombre d'exemples à générer par défaut, int
comparatorverifier.examples.count=10
# mode d'exécution, default | permissive | strict
comparatorverifier.mode=default
```