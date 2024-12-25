package com.api.donation_api.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class CustomBeanUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);

        return Arrays.stream(sourceWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> sourceWrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
