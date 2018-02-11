package spoonapps.util.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import spoonapps.util.properties.AsbtractGlobalProperties.PropertiesChangedListener;
import spoonapps.util.properties.impl.FilePropertiesEditor;
import spoonapps.util.properties.impl.FilePropertiesImpl;
import spoonapps.util.properties.impl.PropertyDefinition;

public class GlobalPropertiesEditorTest extends TestCase{


    private static final Logger log = LoggerFactory.getLogger(GlobalPropertiesEditorTest.class);


    
    @Test
	static public void testReadFileShortName() throws Exception {
		try {
			
			File file=new File("src/test/resources/testPropertyEditor.prop");
			String key="key";
			String value="Value";

			file.delete();
			
			Properties prop=new Properties();
			prop.setProperty(key, value);
			prop.store(new FileOutputStream(file), "testReadFileShortName");
			
			if (!file.exists()){
				throw new Exception("File not created:"+file.getAbsolutePath());
			} else {
				log.info("File created:"+file.getAbsolutePath());
			}
			
			FilePropertiesImpl implementation=FilePropertiesImpl.getFromFile(file);
			
			GlobalProperties.setImplementation(implementation);
			
			// testing short property names
			String newValue=GlobalProperties.getStringValue(GlobalPropertiesEditorTest.class, key);
			
			assertEquals(newValue, value);
			
			newValue=GlobalProperties.getStringValue(GlobalPropertiesEditorTest.class, key,null);
			
			assertEquals(newValue, value);

			// Getting the properties
			Map<String, PropertyDefinition> properties = FilePropertiesEditor.SINGLETON.getProperties();
			assertExists(properties);
			
			// adding new properties
			List <PropertyDefinition> properyCollection=new ArrayList<PropertyDefinition>();
			PropertyDefinition p;
			
			p=new PropertyDefinition("Name1", "Description numero 1", "Value Uno");
			properyCollection.add(p);
			
			p=new PropertyDefinition(key, "Modification existing key",value);
			properyCollection.add(p);
			
			
			GlobalProperties.addListener(new PropertiesChangedListener() {
				
				@Override
				public void propertiesChanged(long lastModifiedTime, Map<String, String> newCache) {
					try {
						log.info("Props changed");
						
						Map<String, PropertyDefinition>  properties = FilePropertiesEditor.SINGLETON.getProperties();			
						assertExists(properties);
					} catch (Exception e) {
						log.error("Test error",e);
						
						fail("Got an exception:" + e);	
					
					}
				}
			}, false);

			FilePropertiesEditor.SINGLETON.setProperties(properyCollection);
			
		} catch (Exception e) {
			log.error("Test error",e);
			
			fail("Got an exception:" + e);	
			throw e;
		}
	}
    
    
    private static void assertExists(Map<String, PropertyDefinition> properties){
    	for (Map.Entry<String, PropertyDefinition>entry:properties.entrySet()){
    		
    		PropertyDefinition prop=entry.getValue();
    		String value=GlobalProperties.getValueInner(prop.name);
    		
    		assertEquals(prop.value, value);    				
    	}
    }
}
