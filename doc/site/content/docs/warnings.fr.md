---
weight: 1
bookFlatSection: true
title: "Explication des avertissements"
---

# Explication des avertissements

Cette page présente divers avertissements pouvant survenir lors de la
vérification de la validité d'une implémentation de `Comparator`. Chaque
avertissement correspond à une propriété contractuelle spécifique qu'un
comparateur bien implémenté devrait respecter.

## `ANTI_SYMMETRY`

Un comparateur viole l'anti-symétrie s'il renvoie des résultats
incohérents lorsque l'ordre de ses arguments est inversé. Plus
précisément, pour tous objets `x` et `y`, si `compare(x, y) < 0`, alors
`compare(y, x)` devrait renvoyer une valeur supérieure à 0.

## `CONSISTENCY`

Un comparateur manque de cohérence s'il donne des résultats différents
lors de la comparaison d'une même paire d'objets à plusieurs reprises,
en supposant qu'aucune modification n'a été apportée aux objets. La
cohérence garantit que les appels répétés du comparateur produisent le
même résultat pour les mêmes entrées.

## `CONSISTENT_WITH_EQUALS`

Un comparateur est incohérent avec `equals` s'il considère deux objets
comme égaux (c'est-à-dire, `compare(x, y) == 0`) mais que `x.equals(y)`
renvoie `false`. Pour qu'un comparateur soit cohérent avec `equals`,
l'égalité implicite par le comparateur devrait s'aligner avec la méthode
`equals` des objets comparés.

## `REFLEXIVITY`

Un comparateur viole la réflexivité s'il ne renvoie pas zéro lors de la
comparaison d'un objet avec lui-même. Autrement dit, pour tout objet
`x`, `compare(x, x)` devrait toujours renvoyer 0.

## `TRANSITIVITY`

Un comparateur manque de transitivité s'il ne maintient pas un ordre
cohérent au travers de multiples comparaisons. Plus précisément, si
`compare(x, y) < 0` et `compare(y, z) < 0`, alors `compare(x, z)`
devrait également être inférieur à 0. La violation de la transitivité
peut conduire à des comportements de tri imprévisibles et erronés.

## `SERIALIZABLE`

_Désactivé par défaut._

Un comparateur devrait implémenter l'interface `Serializable` s'il est
destiné à être utilisé dans des contextes nécessitant la sérialisation,
comme dans des structures de données sérialisables telles que `TreeSet`
ou `TreeMap`. Le non-respect de cette exigence peut entraîner des
exceptions à l'exécution lors de tentatives de sérialisation.

## Références

- [Java Platform SE 8: `Comparator` Interface](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)
- [Why should a Comparator implement Serializable? - Stack Overflow](https://stackoverflow.com/questions/8642012/why-should-a-comparator-implement-serializable)
