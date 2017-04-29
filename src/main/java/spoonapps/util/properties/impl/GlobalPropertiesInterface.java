package spoonapps.util.properties.impl;

import java.util.Map;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckInterface;

public interface GlobalPropertiesInterface extends RuntimeCheckInterface{
	public long getLastModified();

	public Map<String, String> getAll() throws ApplicationException;
//	public ConcurrentHashMap<String, String> getAll();
	
	
//	public void setValue(String propertyName, String value);
//	public Map<String, PropertyDefinition> getAllDefinitions();
//	
//	public void setPropertyDescription(String propertyName,String value);
	

}
