package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nonnull;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.springframework.util.StringUtils.capitalize;

/**
 * Class with different static util methods
 */
public final class Reflect {
    private static final Logger logger = LoggerFactory.getLogger(Reflect.class);
    private static final boolean FULL_STACK_TRACE_ENABLED = false;
    private static final int STACK_TRACE_LENGTH = 10;

    public static void testStarterStringPrint() {
        logger.info("\n***************" + getMethodName(4) + "***************\n");
    }

    /**
     * Logs error message
     *
     * @param sender - class of invoking object
     * @param e      - exception
     */
    public static void errorLog(Class<?> sender, Exception e, String customMessage) {
        Logger logger = LoggerFactory.getLogger(sender);
        if (customMessage != null) logger.error(customMessage);
        logger.error("Error ocured in " + sender.getName()
                + "; in method " + getErrorMethodName() + "; exception message: " + e.getMessage());
        if (FULL_STACK_TRACE_ENABLED)
            e.printStackTrace();
        else {
            int i = STACK_TRACE_LENGTH;
            for (StackTraceElement element : e.getStackTrace()) {
                System.out.println(element);
                if (--i == 0)
                    return;
            }
        }
    }

    public static void errorLog(Class<?> sender, Exception e) {
        errorLog(sender, e, null);
    }

    private static void errorLog(Exception e) {
        errorLog(Reflect.class, e, null);
    }

    public static void errorLog(Object sender, Exception e) {
        errorLog(sender.getClass(), e);
    }

    public static void errorLog(Object sender, Exception e, String customMessage) {
        errorLog(sender.getClass(), e, customMessage);
    }

    /**
     * Return method's name
     * [0] - return "getMethodName"
     * [1] - return calling method's name...
     */
    public static String getMethodName(final int depth) {
        return Thread.currentThread().getStackTrace()[depth - 1].getMethodName();
    }

    private static String getErrorMethodName() {
        String method;
        int i = 4;
        do {
            method = getMethodName(i++);
        } while (method.equals("errorLog"));
        return method;
    }

    public static Class<?> getClassByName(String className, String packagePath) {
        className = packagePath + "." + capitalize(className);
        return getClassByName(className);
    }

    public static Class<?> getClassByName(String className) {
        try {
            if (className.contains("."))
                return Class.forName(className);
            else {
                return Class.forName(capitalize(className));
            }
        } catch (ClassNotFoundException e) {
            logger.error("Class with name " + className + " not found");
            return null;
        }
    }

    public static List<String> getEnumAsStringList(Class<? extends Enum> enumClass) {
        List<String> list = new LinkedList<>();
        EnumSet.allOf(enumClass).forEach((o) -> list.add(o.toString()));
        return list;
    }

    public static <T> T cloneEntity(@Nonnull T entity) {
        @SuppressWarnings("unchecked")
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        T newEntity = BeanUtils.instantiate(type);
        BeanUtils.copyProperties(entity, newEntity);
        getAllFields(type).forEach(f -> {
            if (f.getAnnotation(OneToMany.class) != null)
                try {
                    f.setAccessible(true);
                    f.set(newEntity, null);
                } catch (IllegalAccessException e) {
                    errorLog(e);
                }
        });
        return newEntity;
    }

    public static <T> T createObjectByClass(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Failed to create new instance of " + clazz + ", get exception: " + e.getMessage());
            return null;
        }
    }

    public static Object simplifiedInvoke(Method method, Object invoker, Object... args) {
        if (method != null) {
            try {
                method.setAccessible(true);
                return method.invoke(invoker, args);
            } catch (Exception e) {
                logger.error("error while invoking method: " + method.getName() + ", message: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static Field simplifiedGetField(Class<?> clazz, String fieldName) {
        if (clazz != null) {
            try {
                return getAllFields(clazz).stream().filter(f -> f.getName().equals(fieldName)).findAny().orElse(null);
            } catch (Exception e) {
                logger.error("error while getting field: " + fieldName + ", message: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    public static Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        Class<?> clazzParam = clazz;
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().equals(methodName)) return method;
            clazz = clazz.getSuperclass();
        }
        throw new NoSuchMethodException(" No method '" + methodName + "' in class '" + clazzParam + "' found");
    }

    public static Method getGetter(Class<?> entity, Field field) {
        try {
            return entity.getMethod(Reflect.getGetterName(field));
        } catch (Exception e) {
            if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class))
                try {
                    return entity.getMethod("is" + capitalize(field.getName()));
                } catch (Exception ex) {
                    // logging
                }
            logger.error("No getter method for field: " + field.getName());
            throw new RuntimeException("No getter method for field: " + field.getName());
        }
    }

    public static String getGetterName(Field field) {
        return "get" + capitalize(field.getName());
    }

    public static Method getSetter(Class<?> entity, Field field) {
        try {
            String setterName = getSetterName(field);
            return entity.getMethod(setterName, field.getType());
        } catch (Exception e) {
            logger.error("No setter method for annotated field: " + field.getName());
            return null;
        }
    }

    public static String getSetterName(Field field) {
        String prefix = "set";
        return prefix + capitalize(field.getName());
    }

    public static Object getEnumValue(String value, Class<Enum> type) {
        try {
            for (Object o : EnumSet.allOf(type))
                if (o.toString().equals(value))
                    return o;
        } catch (Exception e) {
            return Enum.valueOf(type, value);
        }
        return Enum.valueOf(type, value);
    }
}
