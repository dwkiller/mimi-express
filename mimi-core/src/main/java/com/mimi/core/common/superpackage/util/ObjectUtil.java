package com.mimi.core.common.superpackage.util;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author dw
 */
public class ObjectUtil {
	
	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}
	
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Field> getAllFields(Class clazz) {
    	List<Field> fieldList = new ArrayList<>() ;
    	while (clazz != null) {
    	      fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
    	      clazz = clazz.getSuperclass();
    	}
    	return fieldList;
    }
	
	public static Field getField(Class clazz,String name) {
		Field f = null;
		
		while(clazz!=null) {
			try {
				f =clazz.getDeclaredField(name);
				break;
			} catch (Exception e) {
				clazz = clazz.getSuperclass();
			}
		}
		
		
		return f;
	}
	
	public static Map<String,Object> toMap(Object o) throws IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = o.getClass();
		List<Field> fieldList = getAllFields(clazz);
		Map<String,Object> tempMap = new HashMap<String,Object>(128);
		for(Field field:fieldList) {
			field.setAccessible(true);
			Object value = field.get(o);
			tempMap.put(field.getName(), value);
		}
		return tempMap;
	}
	
	public static Class<?> getGenericType(Class<?> paramClass){
	    return getGenericType(paramClass, 0);
	}
	
	public static Class<?> getGenericType(Class<?> paramClass, int paramInt){
	    Type localType = paramClass.getGenericSuperclass();

	    if (!(localType instanceof ParameterizedType))
	    {
	      return Object.class;
	    }

	    Type[] arrayOfType = ((ParameterizedType)localType).getActualTypeArguments();

	    if ((paramInt >= arrayOfType.length) || (paramInt < 0))
	    {
	      return Object.class;
	    }
	    if (!(arrayOfType[paramInt] instanceof Class))
	    {
	      return Object.class;
	    }

	    return ((Class)arrayOfType[paramInt]);
	  }
	
	public static List<Field> getAllField(Class clazz) {
		List<Field> l = new ArrayList<Field>();
		l.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if(clazz.getSuperclass()!=null) {
			l.addAll(getAllField(clazz.getSuperclass()));
		}
		return l;
	}
	
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException{
		try(
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream sIn = new ObjectInputStream(in);
	    ){
	        return sIn.readObject();
	    }
    }
	
	public static byte[] toByte(Object obj) throws IOException {
		try(
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            ObjectOutputStream sOut = new ObjectOutputStream(out);
	    ){
	        sOut.writeObject(obj);
	        sOut.flush();
	        byte[] bytes = out.toByteArray();
	        return bytes;
	    }
	}

}
