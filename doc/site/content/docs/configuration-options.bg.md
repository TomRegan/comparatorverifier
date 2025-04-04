# Опции за конфигурация

Опциите могат да бъдат зададени чрез fluent API. Някои опции също са
налични за задаване в `comparator-verifier.properties`, който трябва да
бъде в тестовия classpath, например в `src/test/resources`. Използването
на API задава стойности на база на тест, докато задаването на стойност в
properties файла предоставя стойност по подразбиране за всички тестове.
Стойности, зададени чрез API, ще презапишат стойностите в properties
файла.

### `withExamples(T first, T second, T... rest)`

Предоставя явни примери за верификация. Изискват се поне два примера.

### `withGeneratedExamples(int count)` или `comparatorverifier.examples.count`

Автоматично генерира примери за верификация.

### `strict()` или `comparatorverifier.mode=strict`

Активира стриктна верификация. Включени са следните проверки:

- `Warning.SERIALIZABLE`

### `permissive()` или `comparatorverifier.mode=permissive`

Активира по-свободна верификация, изключвайки следните проверки:

- `Warning.CONSISTENT_WITH_EQUALS`
- `Warning.SERIALIZABLE`

### `suppress(Warning first, Warning... rest)` / `only(Warning first, Warning... rest)`

{{% hint danger %}} Компаратор, който не отговаря на договора за
компаратор, няма да бъде безопасен за използване с Java колекции.

Помислете за използване на `permissive()` или `strict()` вместо това, за
да промените поведението на верификацията. {{% /hint %}}

Потиска/включва само определени предупреждения по време на верификация.

### `verify()`

Изпълнява процеса на верификация и хвърля изключение, ако валидацията се
провали.

# Използване на Properties файл

Създайте файл в `src/test/resources`, наречен
`comparator-verifier.properties`.

```properties
# брой примери за генериране по подразбиране, int
comparatorverifier.examples.count=10
# режим на изпълнение, default | permissive | strict 
comparatorverifier.mode=default
```