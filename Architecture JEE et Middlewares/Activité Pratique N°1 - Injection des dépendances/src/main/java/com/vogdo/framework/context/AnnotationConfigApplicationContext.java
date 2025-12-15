package com.vogdo.framework.context;

import com.vogdo.framework.annotations.Autowired;
import com.vogdo.framework.annotations.Component;
import com.vogdo.framework.annotations.Qualifier;
import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationConfigApplicationContext {

    private Map<String, Object> beansMap = new HashMap<>();

    public AnnotationConfigApplicationContext(String basePackage) {
        try {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

            for (Class<?> clazz : componentClasses) {
                instantiateBean(clazz);
            }

            for (Object beanInstance : beansMap.values()) {
                injectDependencies(beanInstance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void instantiateBean(Class<?> clazz) throws Exception {
        Component component = clazz.getAnnotation(Component.class);
        String beanName = component.value().isEmpty() ? decapitalize(clazz.getSimpleName()) : component.value();

        if (beansMap.containsKey(beanName)) return;

        Constructor<?> constructorToUse = findAutowiredConstructor(clazz);

        Object instance;
        if (constructorToUse != null) {
            Parameter[] parameters = constructorToUse.getParameters();
            Object[] args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Qualifier qualifier = parameters[i].getAnnotation(Qualifier.class);
                args[i] = findDependency(parameters[i].getType(), qualifier);
            }
            instance = constructorToUse.newInstance(args);
        } else {
            instance = clazz.getDeclaredConstructor().newInstance();
        }

        beansMap.put(beanName, instance);
    }

    private void injectDependencies(Object beanInstance) throws Exception {
        for (Field field : beanInstance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependency = findDependency(field.getType(), qualifier);
                field.setAccessible(true);
                field.set(beanInstance, dependency);
            }
        }

        for (Method method : beanInstance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set") && method.getParameterCount() == 1) {
                Parameter parameter = method.getParameters()[0];
                Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
                Object dependency = findDependency(parameter.getType(), qualifier);
                method.invoke(beanInstance, dependency);
            }
        }
    }

    private Constructor<?> findAutowiredConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                return constructor;
            }
        }
        return null;
    }

    private Object findDependency(Class<?> type, Qualifier qualifier) throws Exception {
        if (qualifier != null) {
            Object bean = beansMap.get(qualifier.value());
            if (bean == null) throw new RuntimeException("No bean named '" + qualifier.value() + "' found!");
            return bean;
        } else {
            for (Object bean : beansMap.values()) {
                if (type.isInstance(bean)) {
                    return bean;
                }
            }
            throw new RuntimeException("No bean of type '" + type.getName() + "' found!");
        }
    }

    public <T> T getBean(Class<T> type) {
        for (Object bean : beansMap.values()) {
            if (type.isInstance(bean)) {
                return type.cast(bean);
            }
        }
        return null;
    }

    public Object getBean(String name) {
        return beansMap.get(name);
    }

    private String decapitalize(String str) {
        return str.length() == 0 ? "" : Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}