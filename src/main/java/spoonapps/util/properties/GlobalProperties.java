package spoonapps.util.properties;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.runtimechecks.RuntimeCheckResult;


/**
 * This manage application global properties.... 
 * This properties can be changed at any time.
 *
 */
public class GlobalProperties extends AsbtractGlobalProperties{
	public static final Logger log = LoggerFactory.getLogger(GlobalProperties.class);
	

	public static RuntimeCheckResult check() {
		return AsbtractGlobalProperties.check();
	}
			
	static public String getStringValue(Class<?> c,
                                        String propertyName,
                                        String defaultValue) {	

		return getValue(c,propertyName,defaultValue);
	}
	
	public static String getLongPropertyName(Class<?> c, String propertieName) {
		return getPropertyName(c, propertieName);
	}
	
	static public String getStringValue(Class<?> c, String propertyName) {
		return getStringValue(c,propertyName,null);
	}

	static public String[] getPropertyList(Class<?> c, String propertyName) {
		try {
			String value = getStringValue(c, propertyName,null);
			String ret[] = StringUtils.split(value, ",");
			
			if (ret == null) {
				return ArrayUtils.EMPTY_STRING_ARRAY;
			} else {				
				return ret;
			}
		} catch (Exception e) {
			log.warn("getPropertyList c:" + c +
					 "' propertyName:'"+ propertyName +
                     "'", e);
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
	}

	static public int getIntValue(Class<?> c, String propertyName, int defaultValue) {
		try {
			String ret = getStringValue(c, propertyName, null);

			if (ret == null) {
				return defaultValue;
			} else {
				try {
					return Integer.parseInt(ret);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			}
		} catch (Throwable e) {
			log.warn(String.format("getIntValue c:'%s' propertyName:'%s' defaultValue:'%s'", c, propertyName,
					defaultValue), e);
			return defaultValue;
		}
	}

	
	static public double getDoubleValue(Class<?> c, String propertyName,double defaultValue) {
		try {
			String ret = getStringValue(c, propertyName,null);
			
			if (ret == null) {
				return defaultValue;
			} else {
				try {
					return Double.parseDouble(ret);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			}
		} catch (Exception e) {
			log.warn("getIntValue c:" + c + 
					 "' propertyName:'" + propertyName +
                     "' defaultValue:'" + defaultValue +
                     "'", e);
			return defaultValue;
		}
	}
	
	static public long getLongValue(Class<?> c, String propertyName, long defaultValue) {
		try {
			String ret = getStringValue(c, propertyName);

			if (ret == null) {
				return defaultValue;
			} else {
				try {
					return Long.parseLong(ret);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			}
		} catch (Exception e) {
			log.warn("getIntValue c:" + c + 
					 "' propertyName:'" + propertyName +
					 "' defaultValue:'" + defaultValue +
                     "'", e);
			return defaultValue;
		}
	}
	
	static public boolean getBooleanValue(Class<?> section, 
										  String propertyName,
										  boolean defaultValue) {
		try {
			String ret = getStringValue(section, propertyName,null);
			return toBoolean(ret, defaultValue);
		} catch (Exception e) {
			log.warn("getBooleanValue c:" + section  +
					 "' propertyName:'" + propertyName + 
					 "' defaultValue:'" + defaultValue + "'", e);
			return defaultValue;
		}
	}
	
	static public HashSet<String> getPropertyMap(Class<?> c, String propertyName) {
		String array[]=getPropertyList(c, propertyName);
		
		HashSet<String> ret=new HashSet<String>(array.length);

		ret.addAll(Arrays.asList(array));
		
		return ret;
	}

	/**
	 */
	public static boolean toBoolean(String str, boolean defaultValue) {

		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		} else if (StringUtils.equalsIgnoreCase("true", str)) {
			return true;
		} else if (StringUtils.equalsIgnoreCase("false", str)) {
			return false;
		} else if (StringUtils.equalsIgnoreCase("1", str)) {
			return true;
		} else if (StringUtils.equalsIgnoreCase("0", str)) {
			return false;
		} else {
			return defaultValue;
		}

	}
	
}
