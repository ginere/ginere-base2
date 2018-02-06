package spoonapps.util.collections;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.date.ThreadlocalDateFormat;
import spoonapps.util.enumeration.AppEnum;

public class QueryUtils {
	
	public static final Logger log = LoggerFactory.getLogger(QueryUtils.class);
	
	public static String cleanQuery(String query) {
		if (StringUtils.isBlank(query)){
			return "";
		} else {
//			Escaper htmlEscaper = com.google.common.html.HtmlEscapers.htmlEscaper();
//			htmlEscaper.escape(arg0)
			
//			String ret=StringEscapeUtils.escapeHtml4(query);
			String ret=query;
			
			ret=StringUtils.trim(ret).toLowerCase();
			ret=StringUtils.stripAccents(ret);
			
			// Normalize white spaces
			ret = ret.replaceAll("\\s+", " ");
			
			return ret;
		}
	}
	

	public static <T> Collection<T> filter(Collection<T> list, Field field, Set<? extends AppEnum> set) {
		if (set == null || set.isEmpty()){
			return list;
		} else {
			List<T> ret=new ArrayList<T>(list.size());
			for (T obj:list){
				AppEnum value=beanEnum(obj,field,null);
				
				if (value!=null && set.contains(value)){
					ret.add(obj);
				}
			}
			
			return ret;
		}		
	}

	public static <T> List<T> filter(List<T> list, Field field, Set<? extends AppEnum> set) {
		if (set == null || set.isEmpty()){
			return list;
		} else {
			List<T> ret=new ArrayList<T>(list.size());
			for (T obj:list){
				AppEnum value=beanEnum(obj,field,null);
				
				if (value!=null && set.contains(value)){
					ret.add(obj);
				}
			}
			
			return ret;
		}		
	}

	public static <T> Collection<T> filter(Collection<T> list, Field field,String query) {
		if (StringUtils.isBlank(query)){
			return list;
		} else {
			List<T> ret=new ArrayList<T>(list.size());
			for (T obj:list){
				String value=beanString(obj,field,null);
				
				if (value!=null && StringUtils.containsIgnoreCase(value, query)){
					ret.add(obj);
				}
			}
			
			return ret;
		}		
	}
	
	public static <T> List<T> filter(List<T> list, Field field,String query) {
		if (StringUtils.isBlank(query)){
			return list;
		} else {
			List<T> ret=new ArrayList<T>(list.size());
			for (T obj:list){
				String value=beanString(obj,field,null);
				
				if (value!=null && StringUtils.containsIgnoreCase(value, query)){
					ret.add(obj);
				}
			}
			
			return ret;
		}		
	}
	
	private static Object beanObject(Object bean,Field field,Object defaultValue) {
		if (bean == null){
			return defaultValue;
		} else if (field==null){
//            return toString(bean);
            return bean;
            
		} else {

			try {
//				Object value=PropertyUtils.getProperty(bean, path);
				Object value=field.get(bean);
				
				if (value == null){
					return defaultValue;
				} else {
					return value;
				}
			} catch (Exception e) {
                log.warn(" getting value for fiel:"+field+" bean:"+bean,e);
                return defaultValue;
			}
		}
	}
		
	public static  AppEnum beanEnum(Object bean,Field field,AppEnum defaultValue) {

		Object obj=beanObject(bean,field,null);
		
		if (obj==null){
			return defaultValue;
		} else if (obj instanceof AppEnum){
			return (AppEnum)obj;
		} else {
			return defaultValue;
		}
	}
	
	public static String beanString(Object bean,Field field,String defaultValue) {

			Object obj=beanObject(bean,field,null);
			
			if (obj==null){
				return defaultValue;
			} else {
				return toString(obj, defaultValue);
			}
	}
	
	private static String toString(Object obj,String defaultValue) {
		if (obj == null){
			return defaultValue;
		} else {
            if (obj instanceof String){
                return (String)obj;
//            } else if (obj instanceof I18n){
//            	I18n i18n=(I18n)obj;
//                
//            	return LanguageManager.getValue(i18n, defaultValue);
            } else if (obj instanceof URL){
            	URL url=(URL)obj;
                
                return url.toExternalForm();
            } else if (obj instanceof URI){
                    URI uri=(URI)obj;
                    
                    return uri.toASCIIString();
            } else if (obj instanceof Date){
                Date date=(Date)obj;
                    
                return ThreadlocalDateFormat.format("yyyy-MM-dd'T'HH:mm:ss'Z'", date);
            } else if (obj instanceof Boolean || 
            		obj instanceof Integer || 
            		obj instanceof Long 
            		){
            	return obj.toString();
            } else if (obj instanceof Object){
                return ToStringBuilder.reflectionToString(obj,ToStringStyle.MULTI_LINE_STYLE);
            } else {
                return obj.toString();
            }
		}
	}


}
