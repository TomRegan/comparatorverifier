package co.mp;

import java.util.List;

@FunctionalInterface
interface ComparatorLaw<T> {
    void test(List<T> examples);
}
