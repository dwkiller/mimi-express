package com.mimi.wx.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

public class MapHelper {

	public static Integer getIntValue(Map m , String key){
		Object o = m.get(key);
		if ( o != null ){
			if ( o instanceof BigInteger)
			{
				return ((BigInteger) o).intValue();
			}else if ( o instanceof String)
			{
				return Integer.parseInt(o.toString());
			}
			
			return (int)o;
		}
		
		return null;
	}
	
	public static Long getLongValue(Map m, String key) {
		Object o = m.get(key);
		if ( o != null ){
			if ( o instanceof Long)
			{
				return  (Long)o;
			}else if ( o instanceof String)
			{
				return Long.parseLong(o.toString());
			}else if(o instanceof Integer) {
				return ((Integer)o).longValue();
			}
		}
		
		return null;
	}
	public static Byte getByteValue(Map m , String key){
		Object o = m.get(key);
		if ( o != null ){
			if ( o instanceof Byte)
			{
				return (Byte) o;
			}		
			else if ( o instanceof String)
			{
				return Byte.parseByte(o.toString());
			}
		}
		
		return null;
	}
	
	public static BigDecimal getBigDecimalValue(Map m , String key){
		Object o = m.get(key);
		if ( o != null ){
			if ( o instanceof BigDecimal)
			{
				return (BigDecimal) o;
			}else
			{
				return new BigDecimal(o.toString());
			}					
		}
		
		return null;
	}
	
	public static String getStringValue(Map m , String key){
		Object o = m.get(key);
		if ( o != null ) {
			return o.toString();
		}
		
		return null;
	}
	
	public static Double getDoubleValue(Map<String,Object> m , String key){
		Object o = m.get(key);
		if ( o != null ) {
			return (Double)o;
		}
		
		return null;
	}
	
	public static Date getDateValue(Map m , String key){
		Object o = m.get(key);
		if ( o != null ){
			if ( o instanceof Date)
			{
				return (Date) o;
			}				
		}
		
		return null;
	}
}
