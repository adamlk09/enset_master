package com.vogdo.framework.context;

import com.vogdo.framework.xml.Bean;
import com.vogdo.framework.xml.Beans;
import com.vogdo.framework.xml.Property;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class XmlClassPathApplicationContext {

    private Map<String, Object> beansMap = new HashMap<>();

    public XmlClassPathApplicationContext(String xmlFileName) {
        try {
            InputStream xmlStream = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Beans.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Beans beansConfig = (Beans) unmarshaller.unmarshal(xmlStream);

            for (Bean bean : beansConfig.getBeans()) {
                Class<?> clazz = Class.forName(bean.getClassName());
                Object instance = clazz.getDeclaredConstructor().newInstance();
                beansMap.put(bean.getId(), instance);
            }

            for (Bean bean : beansConfig.getBeans()) {
                Object beanInstance = beansMap.get(bean.getId());
                for (Property property : bean.getProperties()) {
                    String dependencyName = property.getName();
                    String dependencyRefId = property.getRef();
                    Object dependencyInstance = beansMap.get(dependencyRefId);

                    String setterName = "set" + Character.toUpperCase(dependencyName.charAt(0)) + dependencyName.substring(1);
                    Method setterMethod = null;
                    for (Method method : beanInstance.getClass().getMethods()) {
                        if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                            Class<?> paramType = method.getParameterTypes()[0];
                            if(paramType.isInstance(dependencyInstance)) {
                                setterMethod = method;
                                break;
                            }
                        }
                    }

                    if (setterMethod != null) {
                        setterMethod.invoke(beanInstance, dependencyInstance);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String id) {
        return beansMap.get(id);
    }
}