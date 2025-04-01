package co.mp.internal.reflection;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;

public final class Types {
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getComparatorType(Class<? extends Comparator<T>> comparatorClass) {
        for (var type : comparatorClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType && Comparator.class.equals(parameterizedType.getRawType())) {
                var actualType = parameterizedType.getActualTypeArguments()[0];
                if (actualType instanceof Class<?>) {
                    return (Class<T>) actualType;
                }
            }
        }
        throw new IllegalArgumentException("Cannot determine comparator type parameter for " + comparatorClass.getSimpleName());
    }
}
