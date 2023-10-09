package com.pzy.common.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: MapTools 类型转换工具类
 * @Description: TODO
 * @author: 庞中原
 * @date: 2022/5/22 18:16
 */
public class MapTools {

    //把实体类（bean类型）转换为把Map类型
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            //利用反射机制获取类中的属性
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    //把Map类型转换为实体类（bean类型）
    public static <T> T mapToProperties(Map<String, Object> map, Class<T> tClass) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        T t = tClass.newInstance();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(t);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            if ("class".equals(name)) {
                continue;
            }
            Object val = map.get(name);
            if (val != null) {
                propertyUtilsBean.setProperty(t, name, val);
            }
        }
        return t;
    }

    //map转为实体类
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        if (null == map) {
            return null;
        }
        T t = null;
        try {
            t = entity.newInstance();
            for (Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

    //Map转Object
    public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
        if (map == null){
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }

/*    public static Map<String,Object> beanToMap(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        TreeMap<Integer,String> treeMap=new TreeMap<>();
        Class<?> aClass = obj.getClass();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
        Map<String,Object> resultMap=new HashMap<>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            if("class".equals(name)){
                continue;
            }
            Object val = propertyUtilsBean.getNestedProperty(obj, name);
            if(val != null){
                resultMap.put(name,val);
                if("cellStyleMap".equals(name)){
                    continue;
                }
                Field field = aClass.getDeclaredField(name);
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                if(excelProperty != null){
                    treeMap.put(excelProperty.index(),name);
                }
            }
        }

        Map<String,Object> result=new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            Object val = resultMap.get(entry.getValue());
            result.put(entry.getValue(),val);
        }

        return result;
    }*/
}
