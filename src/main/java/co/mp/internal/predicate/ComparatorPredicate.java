package co.mp.internal.predicate;

import java.util.List;

public interface ComparatorPredicate<T> {

    void test(List<T> examples);
}
