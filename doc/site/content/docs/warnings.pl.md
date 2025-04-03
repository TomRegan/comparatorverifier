---
weight: 1
bookFlatSection: true
title: "Wyjaśnienie ostrzeżeń"
---

# Wyjaśnienie ostrzeżeń

Ta strona przedstawia różne ostrzeżenia, które mogą pojawić się podczas
weryfikacji poprawności implementacji `Comparatora`. Każde ostrzeżenie
odpowiada konkretnej właściwości umownej, którą dobrze zaimplementowany
comparator powinien spełniać.

## `ANTI_SYMMETRY`

Comparator narusza zasadę antysymetrii, jeśli zwraca niespójne wyniki,
gdy kolejność jego argumentów zostanie odwrócona. Konkretnie, dla
dowolnych obiektów `x` i `y`, jeśli `compare(x, y) < 0`, to
`compare(y, x)` powinno zwrócić wartość większą od 0.

## `CONSISTENCY`

Comparator jest niespójny, jeśli przy wielokrotnym porównywaniu tej
samej pary obiektów (przy założeniu, że obiekty nie uległy zmianie)
zwraca różne wyniki. Spójność gwarantuje, że powtarzające się wywołania
comparatora dają ten sam wynik dla tych samych danych wejściowych.

## `CONSISTENT_WITH_EQUALS`

Comparator jest niespójny z metodą `equals`, jeśli uznaje dwa obiekty za
równe (tzn. `compare(x, y) == 0`), a jednocześnie `x.equals(y)` zwraca
`false`. Aby comparator był spójny z `equals`, równość implikowana przez
comparatora powinna być zgodna z metodą `equals` porównywanych obiektów.

## `REFLEXIVITY`

Comparator narusza zasadę zwrotności, jeśli nie zwraca zera przy
porównywaniu obiektu z samym sobą. Innymi słowy, dla dowolnego obiektu
`x`, `compare(x, x)` powinno zawsze zwracać 0.

## `TRANSITIVITY`

Comparator jest pozbawiony przechodniości, jeśli nie utrzymuje spójnego
porządku przy wielokrotnych porównaniach. Konkretnie, jeśli
`compare(x, y) < 0` oraz `compare(y, z) < 0`, to `compare(x, z)` również
powinno być mniejsze od 0. Naruszenie przechodniości może prowadzić do
nieprzewidywalnych i błędnych zachowań sortowania.

## `SERIALIZABLE`

_Wyłączone domyślnie._

Comparator powinien implementować interfejs `Serializable`, jeśli ma być
używany w kontekstach wymagających serializacji, takich jak struktury
danych obsługujące serializację, np. `TreeSet` lub `TreeMap`.
Nieprzestrzeganie tego może skutkować wyjątkami w czasie wykonywania
podczas próby serializacji.

## Źródła

- [Java Platform SE 8: `Comparator` Interface](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)
- [Why should a Comparator implement Serializable? - Stack Overflow](https://stackoverflow.com/questions/8642012/why-should-a-comparator-implement-serializable)
