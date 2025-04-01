---
weight: 1
bookFlatSection: true
title: "Configuration Options"
---

# Configuration Options

### `withExamples(T first, T second, T... rest)`

Provides explicit examples for verification. At least two examples are
required.

### `withGeneratedExamples(int count)`

Automatically generates examples for verification.

### `strict()`

Enables strict verification. The following checks are included:

- `Warning.SERIALIZABLE`

### `permissive()`

Enables permissive verification, excluding the following checks:

- `Warning.CONSISTENT_WITH_EQUALS`
- `Warning.SERIALIZABLE`

### `suppress(Warning first, Warning... rest)`

Suppresses specific warnings during verification.

### `verify()`

Runs the verification process and throws an exception if validation
fails.