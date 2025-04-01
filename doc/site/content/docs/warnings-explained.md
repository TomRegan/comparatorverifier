---
weight: 1
bookFlatSection: true
title: "Warnings Explained"
---

# Warnings Explained

This page outlines various warnings that may arise when verifying
the correctness of a `Comparator` implementation. Each warning
corresponds to a specific contractual property that a well-implemented
comparator should uphold.

## `ANTI_SYMMETRY`

A comparator violates anti-symmetry if it returns inconsistent results
when the order of its arguments is reversed. Specifically, for any
objects `x` and `y`, if `compare(x, y) < 0`, then `compare(y, x)` should
return a value greater than 0.

## `CONSISTENCY`

A comparator lacks consistency if it yields different results when
comparing the same pair of objects multiple times, assuming no
modifications have been made to the objects. Consistency ensures that
repeated invocations of the comparator produce the same result for the
same inputs.

## `CONSISTENT_WITH_EQUALS`

A comparator is inconsistent with `equals` if it considers two objects
equal (i.e., `compare(x, y) == 0`) but `x.equals(y)` returns `false`.
For a comparator to be consistent with `equals`, the equality implied by
the comparator should align with the `equals` method of the objects
being compared.

## `REFLEXIVITY`

A comparator violates reflexivity if it does not return zero when
comparing an object to itself. That is, for any object `x`,
`compare(x, x)` should always return 0.

## `TRANSITIVITY`

A comparator lacks transitivity if it does not maintain consistent
ordering across multiple comparisons. Specifically, if
`compare(x, y) < 0` and `compare(y, z) < 0`, then `compare(x, z)` should
also be less than 0. Violating transitivity can lead to unpredictable
and erroneous sorting behaviors.

## `SERIALIZABLE`

_Disabled by default._

A comparator should implement the `Serializable` interface if it is
intended to be used in contexts where serialization is required, such as
in serializable data structures like `TreeSet` or `TreeMap`. Failure to
do so may result in runtime exceptions when serialization is attempted.

## References

- [Java Platform SE 8: `Comparator` Interface](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)
- [Why should a Comparator implement Serializable? - Stack Overflow](https://stackoverflow.com/questions/8642012/why-should-a-comparator-implement-serializable)

