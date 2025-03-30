package co.mp.internal;

import java.io.Serializable;
import java.util.List;

@FunctionalInterface
public interface ComparatorPredicate<T> extends Serializable {

    void test(List<T> examples);
}
