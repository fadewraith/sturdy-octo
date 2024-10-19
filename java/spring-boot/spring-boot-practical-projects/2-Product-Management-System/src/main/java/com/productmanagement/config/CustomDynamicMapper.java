package com.productmanagement.config;

import java.lang.reflect.Field;

public class CustomDynamicMapper {

    public static <S, D> D map(S source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }

        try {
            D destination = destinationClass.getDeclaredConstructor().newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

                try {
                    Field destinationField = destinationClass.getDeclaredField(sourceField.getName());
                    destinationField.setAccessible(true);
                    destinationField.set(destination, value);
                } catch (NoSuchFieldException e) {
                    // Ignore fields that don't exist in the destination class
                }
            }

            return destination;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
