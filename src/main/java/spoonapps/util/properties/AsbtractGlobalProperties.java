package spoonapps.util.properties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.properties.impl.GlobalPropertiesInterface;
import spoonapps.util.properties.impl.MemoryPropertiesImpl;
import spoonapps.util.runtimechecks.RuntimeCheckResult;


/**
 * This manage application global properties.... This properties can be changed at any time.
 *
 */
class AsbtractGlobalProperties{
	public static final Logger log = LoggerFactory.getLogger(AsbtractGlobalProperties.class);
	

	private static GlobalPropertiesInterface impl=MemoryPropertiesImpl.SINGLETON;

	private static final Map<String, String> cache = new ConcurrentHashMap<String, String>();
	private static long lastModified=0;
			
	private static Map<Class<?>, PropertiesChangedListener> listeners = new ConcurrentHashMap<Class<?>, PropertiesChangedListener>();
	
	public static interface PropertiesChangedListener{
		void propertiesChanged(long lastModifiedTime, Map<String, String> newCache);	
	}

	public static void addListener(PropertiesChangedListener listener, boolean callWhenAdded){
		if (listener == null){
			return ;
		} else {
			Class<?> clazz=listener.getClass();
			if (!listeners.containsKey(clazz)){
				listeners.put(clazz,listener);
				
				if (callWhenAdded){
					callListener(listener,System.currentTimeMillis(),cache);
				}
			} else {
				log.warn("Already added listener of type:"+clazz.getName());			}
		}
	}
	

	private static void callListener(PropertiesChangedListener listener,long lastModifiedTime, Map<String, String> newCache) {
		try {
			log.error("Calling listener:"+listener.getClass().getName());
			long starttime=System.currentTimeMillis();
			listener.propertiesChanged(lastModifiedTime,newCache);
			log.error("Listener "+listener.getClass().getName()+" done in:"+(System.currentTimeMillis()-starttime));
						
		} catch (Exception e){
			log.error("Calling listener:"+listener.getClass().getName(),e);
		}			
	}
	
	protected static RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(AsbtractGlobalProperties.class);
		
		ret.add("Implementation", impl);

		return ret;	
	}
	
	public static void setImplementation(GlobalPropertiesInterface implementation){
		if (implementation!=null){
			impl=implementation;
			lastModified=0;
		} else {
			log.error("The implementation passed id null");
		}
	}
		
//	/**
//	 * Thread exclusion zone.
//	 */
//	private static Map<String, String> verifyPropertiesChanged() {
//		
//		// ask the backend for the las time modification
//		long implLastModifiedTime=impl.getLastModified();
//		
//		// if we don't have this data/
//		if (lastModified<implLastModifiedTime){
//			// get the data from the server
//			ConcurrentHashMap<String, String> newCache=impl.getAll();
//			
//			// No one pass till the modifications are not in the server
//			synchronized (cache) {
//				// just tests that the updated are not already made by someone.
//				if (lastModified<implLastModifiedTime){
//					cache=newCache;
//					// sets the last modifications
//					lastModified=implLastModifiedTime;
//				}
//				return cache;
//			}			
//		} else {
//			synchronized (cache) {
//				return cache;
//			}
//		}
//	}
	
	
	/**
	 * Thread exclusion zone.
	 */
	private static void verifyPropertiesChanged() {
		
		// ask the backend for the las time modification
		long implLastModifiedTime=impl.getLastModified();
		
		// if we don't have this data/
		if (lastModified<implLastModifiedTime){
			// get the data from the server
			try {
				Map<String, String> newCache=impl.getAll();
				
				// No one pass till the modifications are not in the server
				synchronized (cache) {
					// just tests that the updated are not already made by someone.
					if (lastModified<implLastModifiedTime){
						cache.clear();
						cache.putAll(newCache);
						// sets the last modifications
						lastModified=implLastModifiedTime;
						
						firePropertiesChangedEvent(implLastModifiedTime,newCache);
					}
				}
			}catch(ApplicationException e){
				log.warn("Error reloading GlobalPRoperties from miplementation:"+impl.getClass(),e);
			}		
		} 
	}

//	private static String getValueInner(String propertyName) {
//		
//		// verify the properties chages before
//		Map<String, String> currentMap=verifyPropertiesChanged();
//
//		if (currentMap.containsKey(propertyName)){
//			return currentMap.get(propertyName);
//		} else {
//			return null;
//		}
//	}

	

	private static void firePropertiesChangedEvent(long LastModifiedTime, Map<String, String> newCache) {
		log.warn("Foring properties changed listeners");
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				callListeners(LastModifiedTime,newCache);
			}

			private void callListeners(long lastModifiedTime, Map<String, String> newCache) {
				for (PropertiesChangedListener listener:listeners.values()){
					callListener(listener,lastModifiedTime,newCache);
				}
				
			}
		});
		
		thread.start();
	}

	private static String getValueInner(String propertyName) {
		
		// verify the properties chages before
		verifyPropertiesChanged();
		synchronized (cache) {
			if (cache.containsKey(propertyName)){
				return cache.get(propertyName);
			} else {
				
//				for (String name: cache.keySet()){
//
//		            String key =name.toString();
//		            String value = cache.get(name).toString();  
//		            log.error("++++++++++++ Abstract '"+key + "': '" + value+"'");  
//
//
//				} 
				return null;
			}
		}
	}
	
	protected static String getValue(Class<?> section, String propertyName,String defaultValue) {
		if (section == null || StringUtils.isBlank(propertyName)) {
			log.warn("Section or name is null, Section:'" + section
					 + "' name:'" + propertyName + "'");
			return defaultValue;
		}
		// Getting the full name
		String fullPropertyName = getPropertyName(section, propertyName);
		
		String ret=getValueInner(fullPropertyName);
		if (ret!=null){
			return ret;
		} else {
			if (log.isDebugEnabled()){
				log.debug("Property long name not found:'"+fullPropertyName+"' ussing the sort name '"+propertyName+"'");
			}
			// try the sort name
			ret=getValueInner(propertyName);
			if (ret!=null){
				return ret;
			} else {
				if (log.isDebugEnabled()){
					log.debug("Property not found:'"+propertyName+"' returning the default value 2.");
					
//					for (String name: cache.keySet()){
//
//			            String key =name.toString();
//			            String value = cache.get(name).toString();  
//			            log.error("++++++++++++ Abstract2 '"+key + "': '" + value+"'");  
//
//
//					} 
				}
				return defaultValue;
			}
		}		
	};
	
	private static String getPropertyName(String section, String propertieName) {
		return section + "." + propertieName;
	}

	protected static String getPropertyName(Class<?> c, String propertieName) {
		return getPropertyName(c.getName(), propertieName);
	}	
}
