package net.risesoft.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanPropertyUtils {
	
	private BeanPropertyUtils() {}
	
	@SuppressWarnings("rawtypes")
	public static void populate(Object bean, Map properties) {
		PropertyDescriptor propertyDescriptors[] = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String name = descriptor.getName();
			Object value = properties.get(name);
			if (value == null)
				continue;      
			try {
				Class c = PropertyUtils.getPropertyType(bean, name);    	 
				if(c.isInstance(value) || c.isPrimitive()){
					PropertyUtils.setSimpleProperty(bean, name, value);
				}    
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {    
				e.printStackTrace();
			} catch (NoSuchMethodException e) {  
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T populate(Class<T> beanClass, Map properties){
		PropertyDescriptor propertyDescriptors[] = PropertyUtils.getPropertyDescriptors(beanClass);
		T beanInstance = null;		
		try{
			beanInstance = beanClass.newInstance();
		}catch(InstantiationException ie){
			ie.printStackTrace();
		}catch(IllegalAccessException iae){
			iae.printStackTrace();
		}
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String name = descriptor.getName();
			Object value = properties.get(name);
			if (value == null)
				continue;
			try {
				Class c = PropertyUtils.getPropertyType(beanInstance, name);
				if(!c.isPrimitive()){
					value = convertData(value);
				}
				if(c.isInstance(value) || c.isPrimitive()){
					PropertyUtils.setSimpleProperty(beanInstance, name, value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {    
				e.printStackTrace();
			} catch (NoSuchMethodException e) {  
				e.printStackTrace();
			}
			
		}
		return beanInstance;
	}
	
	public static final Object convertData(Object data){
		if(data == null){
			return data;
		}else if(BigDecimal.class.isInstance(data)){
			BigDecimal bd = new BigDecimal(data.toString());			
	        int precision = bd.precision();
	        int scale = bd.scale();	        
	        if (scale <= 0){
	        	if (precision < 9){
	        		return new Integer(data.toString());
	        	}else{
	        		return new Long(data.toString());
	        	}
	        }else{
	        	if (precision < 9){
	        		return new Float(data.toString());
	        	}else{
	        		return new Double(data.toString());
	        	}
	        }
		}else{
			return data;
		}
	}
	
	public static final Object convertDataType(Object data, int dataType){
		if(data == null){
			return data;
		}
		switch (dataType) {
		//字符串
		case Types.CHAR :
		case Types.VARCHAR :
		case Types.LONGVARCHAR :
			return data.toString();
			//在Sybase的人员表中，人员照片使用此字段类型
		case Types.LONGVARBINARY :
			return data.toString().getBytes();
		case Types.BLOB :
			
			return data;
		case Types.CLOB :
			
			return data;
			//整数
		case Types.BIGINT :
		case Types.BIT :
		case Types.INTEGER :
		case Types.SMALLINT :
		case Types.TINYINT :			
			return new Integer(data.toString());
			//浮点数
		case Types.DECIMAL :
		case Types.DOUBLE :
		case Types.FLOAT :
		case Types.REAL :
			return new Double(data.toString());
		case Types.NUMERIC : //Oracle的Number都是此类型，不论是NUMBER(4)或NUMBER(28,2)。
	        //必须从rsmt获取Number的长度和精度，判断后返回Integer、Long、Float或Double。
			BigDecimal bd = new BigDecimal(data.toString());
			
	        int precision = bd.precision();
	        int scale = bd.scale();	        
	        if (scale <= 0){
	        	if (precision < 9){
	        		return new Integer(data.toString());
	        	}else{
	        		return new Long(data.toString());
	        	}
	        }else{
	        	if (precision < 9){
	        		return new Float(data.toString());
	        	}else{
	        		return new Double(data.toString());
	        	}
	        }
			//日期时间
		case Types.DATE :
		case Types.TIME :
		case Types.TIMESTAMP :
			return data;
		default :			
			return data;
	}
	}

	// Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
	    public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
	        if (map == null || obj == null) {  
	            return;  
        }  
	       try {  
	            BeanUtils.populate(obj, map);  
	       } catch (Exception e) {  
	            System.out.println("transMap2Bean2 Error " + e);  
	        }  
	    }  
	  
	    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
	    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
	  
	        try {  
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	 
	            for (PropertyDescriptor property : propertyDescriptors) {  
	               String key = property.getName();  
	  
	                if (map.containsKey(key)) {  
	                    Object value = map.get(key);  
	                    // 得到property对应的setter方法  
	                   Method setter = property.getWriteMethod();  
	                    setter.invoke(obj, value);  
	                }  
	  
	            }  
	  
	        } catch (Exception e) {  
	            System.out.println("transMap2Bean Error " + e);  
	        }  
	 
	       return;  
	  
	    }  
	  
	   // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
	    public static Map<String, Object> transBean2Map(Object obj) {  
	   if(obj == null){  
	            return null;  
	        }          
	        Map<String, Object> map = new HashMap<String, Object>();  
	        try {  
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	            for (PropertyDescriptor property : propertyDescriptors) {  
	                String key = property.getName();  
	  
	                // 过滤class属性  
	                if (!key.equals("class")) {  
	                    // 得到property对应的getter方法  
	                   Method getter = property.getReadMethod();  
	                   Object value = getter.invoke(obj);  
	  
	                    map.put(key, value);  
	               }  
	 
	            }  
	        } catch (Exception e) {  
	            System.out.println("transBean2Map Error " + e);  
	       }  
	 
	        return map;  
	  
	    }  

}
