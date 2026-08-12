package co.mp.internal.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

public final class Types {
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getComparatorType(Class<? extends Comparator<T>> comparatorClass) {
        for (Type type : comparatorClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType &&
                Comparator.class.equals(((ParameterizedType) type).getRawType())) {

                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type actualType = parameterizedType.getActualTypeArguments()[0];
                if (actualType instanceof Class<?>) {
                    return (Class<T>) actualType;
                }
            }
        }
        throw new IllegalArgumentException("Cannot determine comparator type parameter for " + comparatorClass.getSimpleName());
    }
}
