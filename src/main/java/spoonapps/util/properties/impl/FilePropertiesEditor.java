package spoonapps.util.properties.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.module.AbstractModule;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class FilePropertiesEditor extends AbstractModule {
	public static final Logger log = LoggerFactory.getLogger(FilePropertiesEditor.class);


	public static FilePropertiesEditor SINGLETON=new FilePropertiesEditor();
	
	private FilePropertiesEditor(){		
	}
	
	
	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {
		
		return ret;
	}

	@Override
	protected String getSvnHeaderString() {
		return "$Header:$";
	}


	private File getFildes() throws ApplicationException{
		GlobalPropertiesInterface impl=GlobalProperties.getImplementation();

		if (impl instanceof FilePropertiesImpl){
			FilePropertiesImpl filePropertiesImpl=(FilePropertiesImpl)impl;

			File ret=filePropertiesImpl.getFildes();

			if (ret==null){
				throw new ApplicationException("The FilePropertiesImpl has a null file");				
			} else if (!ret.exists()){
				throw new ApplicationException("The FilePropertiesImpl file:"+ret.getAbsolutePath()+" does not exists.");				
			} else {
				return ret;
			}			
		} else {
			throw new ApplicationException("The GlobalPropertiesInterface is not an instance of FilePropertiesImpl.");				
		}
	}
	
	public Map<String,PropertyDefinition> getProperties() throws ApplicationException{
		PropertiesConfiguration config=getConfigurator();				
		PropertiesConfigurationLayout layout = config.getLayout();
				
		Iterator<String> keys = config.getKeys();
		Map <String,PropertyDefinition>ret=new HashMap<String, PropertyDefinition>(config.size());
				
		while(keys.hasNext()){
			String key=keys.next();
			String value=config.getString(key);
			String description=layout.getComment(key);
					
			PropertyDefinition prop=new PropertyDefinition(key, description, value);
					
			ret.put(key,prop);
					
		}
				
		return ret;
	}

	public void setProperties(Collection<PropertyDefinition> properties) throws ApplicationException{
		try {
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder=getBuilder();
			PropertiesConfiguration config = (PropertiesConfiguration) builder.getConfiguration();			
			PropertiesConfigurationLayout layout = config.getLayout();
	
			for (PropertyDefinition property:properties){
				config.setProperty(property.name, property.value);
				if (StringUtils.isNotBlank(property.description)){
					layout.setComment(property.name, property.description);
				}
	
				if (log.isInfoEnabled()){
					log.info("Properie updated:"+property);
				}
			}		
			
			builder.setAutoSave(true);
			builder.save();
			
//			Thread.currentThread().sleep(8000);
			
			GlobalProperties.verifyPropertiesChanged();
			
		}catch (Exception e) {
			throw new ApplicationException("While getting properties configurator",e);				
		}
	}

	private FileBasedConfigurationBuilder<FileBasedConfiguration> getBuilder() throws ApplicationException{
		try {
			File file=getFildes();
			
			Parameters params = new Parameters();
			
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
				new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class).configure(params.properties().setFile(file));

			return builder;
		}catch (Exception e) {
			throw new ApplicationException("While getting properties configurator",e);				
		}
	}
	

	
	private PropertiesConfiguration getConfigurator() throws ApplicationException{
		try {
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder=getBuilder();
			
			PropertiesConfiguration config = (PropertiesConfiguration) builder.getConfiguration();
			
			return config;
		}catch (Exception e) {
			throw new ApplicationException("While getting properties configurator",e);				
		}
	}
	

}
