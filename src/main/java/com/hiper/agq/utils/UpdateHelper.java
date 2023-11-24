package com.hiper.agq.utils;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * andre on 24/11/2023
 */
public class UpdateHelper {

    /*public static boolean updateField(Object newValue, Object currentValue, Consumer<String> updater) {
        if (newValue != null && !newValue.equals(currentValue)) {
            updater.accept(newValue.toString());
            return true;
        }
        return false;
    }*/

    public static <T> boolean updateField(T newValue, T currentValue, Consumer<T> updater) {
        if (newValue != null && !newValue.equals(currentValue)) {
            updater.accept(newValue);
            return true;
        }
        return false;
    }

    public static <E extends Enum<E>> boolean updateEnumField(
            String newValue,
            Enum<E> currentValue,
            Function<String, E> valueOfFunction,
            Consumer<E> updater) {
        if (newValue != null) {
            E newEnum = valueOfFunction.apply(newValue);
            if (!newEnum.equals(currentValue)) {
                updater.accept(newEnum);
                return true;
            }
        }
        return false;
    }


}
