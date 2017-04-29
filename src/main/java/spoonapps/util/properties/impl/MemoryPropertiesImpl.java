package spoonapps.util.properties.impl;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class MemoryPropertiesImpl implements GlobalPropertiesInterface{
	public static final Logger log = LoggerFactory.getLogger(MemoryPropertiesImpl.class);

	
	static public MemoryPropertiesImpl SINGLETON= new MemoryPropertiesImpl();

	private ConcurrentHashMap<String, String> cache=new ConcurrentHashMap<String, String>();

	private long lastModified=System.currentTimeMillis();

	private Hashtable<String, String> cacheDescription=new Hashtable<String, String>();;
	
	private MemoryPropertiesImpl(){
	}

	@Override
	public RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(getClass());
		
		log.debug("Testing memory properties impl...");
		return ret;	
	}

//	@Override
//	public void setValue(String propertyName,String value) {
//		if (value!=null){
//			cache.put(propertyName,value);
//			lastModified=System.currentTimeMillis();
//		}
//	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	@Override
	public ConcurrentHashMap<String, String> getAll() {
		return cache;
	}

//	@Override
//	public void setPropertyDescription(String name,String description) {
//		if (name !=null && !cacheDescription.containsKey(name)){
//            cacheDescription.put(name,description);		
//		}
//	}

//	@Override
//	public Map<String, PropertyDefinition> getAllDefinitions() {
//		 Iterator<Entry<String, String>> it = cache.entrySet().iterator();
//		 Map<String, PropertyDefinition> ret=new HashMap<String, PropertyDefinition>(cache.size());
//		 
//	    while (it.hasNext()) {
//	        Map.Entry<String, String> pair =it.next();
//	        String name=pair.getKey();
//	        String value=pair.getValue();
//	        
//	        String description=cacheDescription.get(name);
//	        PropertyDefinition obj=new PropertyDefinition(name,description,value);
//	        
//	        ret.put(name, obj);
//	    }
//		return ret;
//	}

	

}
