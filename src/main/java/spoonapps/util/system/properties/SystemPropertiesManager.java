package spoonapps.util.system.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class SystemPropertiesManager extends AbstractModule{
    public static final String ID="$Id$";

    private long lastModified=System.currentTimeMillis();
    
	public static final SystemPropertiesManager MANAGER=new SystemPropertiesManager();

    private SystemPropertiesManager(){    	
    }
    
	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {
		return ret;
	}

	@Override
	protected String getSvnHeaderString() {
		return "$Id$";
	}

	public Map<String,String>  getProperties() {
		Set<Entry<Object, Object>> entrySet = System.getProperties().entrySet();

        Map<String,String> ret=new HashMap<String,String>(entrySet.size());
        
		for (Entry<Object, Object> entry:entrySet){
			Object key=entry.getKey();
			Object value=entry.getValue();
            
			ret.put(key.toString(), value.toString());			
		}
		
		return ret;
	}

	public String put(String key,String value) {
		lastModified=System.currentTimeMillis();
        return System.setProperty(key, value);		
	}

	public String get(String key,String defaultValue) {
		try {
			String ret = System.getProperty(key);	
	    
			if (ret==null){
				return defaultValue;
			} else {
				return ret;
			}
		}catch (Exception e) {
			log.warn("Getting value for key:"+key,e);
			return defaultValue;
		}		
	}

	public boolean isProduction(){
		String devMode=get("development-mode",null);
		
		if (devMode == null){
			return true;
		} else {
			return false;
		}		
	}


    public long getLastModified() {
		return lastModified;
	}

}
