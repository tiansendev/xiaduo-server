package com.tiansen.ordermanager.common.util;

import java.lang.reflect.Field;

public class ReflectUtils {
    public static void setProperty(Object obj, String PropertyName, Object value)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Class c = obj.getClass();
        Field f = c.getDeclaredField(PropertyName);
        f.setAccessible(true);
        f.set(obj, value);
    }
}
