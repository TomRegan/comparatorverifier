package co.mp.internal.predicate;

import co.mp.Warning;

import java.util.List;

public interface ComparatorPredicate<T> {

    void test(List<T> examples);

    Warning testsFor();
}
