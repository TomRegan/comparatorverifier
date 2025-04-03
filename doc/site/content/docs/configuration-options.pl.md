---
weight: 1
bookFlatSection: true
title: "Opcje konfiguracji"
---

# Opcje konfiguracji

Opcje mogą być ustawiane za pomocą płynnego API (fluent API). Niektóre opcje są również dostępne do ustawienia w pliku `comparator-verifier.properties`, który powinien znajdować się na ścieżce testowej, np. w `src/test/resources`. Używanie API ustawia wartości dla każdego testu z osobna, podczas gdy konfiguracja w pliku właściwości zapewnia wartość domyślną dla wszystkich testów. Wartości ustawione za pomocą API nadpisują wartości z pliku właściwości.  

### `withExamples(T first, T second, T... rest)`

Dostarcza jawnych przykładów do weryfikacji. Wymagane są co najmniej dwa przykłady.

### `withGeneratedExamples(int count)` lub `comparatorverifier.examples.count`

Automatycznie generuje przykłady do weryfikacji.

### `strict()` lub `comparatorverifier.mode=strict`

Włącza ścisłą weryfikację. Następujące kontrole są uwzględnione:

- `Warning.SERIALIZABLE`

### `permissive()` lub `comparatorverifier.mode=permissive`

Włącza łagodną weryfikację, wyłączając następujące kontrole:

- `Warning.CONSISTENT_WITH_EQUALS`
- `Warning.SERIALIZABLE`

### `suppress(Warning first, Warning... rest)` / `only(Warning first, Warning... rest)`

{{% hint danger %}}
Comparator, który nie spełnia kontraktu, nie będzie bezpieczny w użyciu z kolekcjami Javy.

Rozważ użycie `permissive()` lub `strict()`, aby zmienić zachowanie weryfikacji.
{{% /hint %}}

Pozwala na wyłączenie lub ograniczenie do określonych ostrzeżeń podczas weryfikacji.

### `verify()`

Uruchamia proces weryfikacji i rzuca wyjątek, jeśli walidacja nie powiedzie się.

# Używanie pliku właściwości

Utwórz plik w `src/test/resources` o nazwie `comparator-verifier.properties`.

```properties
# liczba przykładów generowanych domyślnie, int
comparatorverifier.examples.count=10
# tryb działania, default | permissive | strict
comparatorverifier.mode=default
```