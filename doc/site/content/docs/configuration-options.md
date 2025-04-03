---
weight: 1
bookFlatSection: true
title: "Configuration Options"
---

# Configuration Options

Options can be set using the fluent API. Some options are also available
to be set in `comparator-verifier.properties` which should be on the
test classpath, e.g. in `src/test/resources`. Using the API sets values
on a per-test basis, whereas setting a value in the properties file
provides a default value for all tests. Values set in via the API
will override values in the properties file.  

### `withExamples(T first, T second, T... rest)`

Provides explicit examples for verification. At least two examples are
required.

### `withGeneratedExamples(int count)` or `comparatorverifier.examples.count`

Automatically generates examples for verification.

### `strict()` or `comparatorverifier.mode=strict`

Enables strict verification. The following checks are included:

- `Warning.SERIALIZABLE`

### `permissive()` or `comparatorverifier.mode=permissive`

Enables permissive verification, excluding the following checks:

- `Warning.CONSISTENT_WITH_EQUALS`
- `Warning.SERIALIZABLE`

### `suppress(Warning first, Warning... rest)`

Suppresses specific warnings during verification.

### `verify()`

Runs the verification process and throws an exception if validation
fails.