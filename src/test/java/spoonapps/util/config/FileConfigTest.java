package spoonapps.util.config;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class FileConfigTest extends TestCase{


    private static final Logger log = LoggerFactory.getLogger(FileConfigTest.class);

    @Test
   	static public void testDefaultValue() throws Exception {
   		try {
   			// Add a System path
   			File roots[]=File.listRoots();
   			System.setProperty("ep.triloedi.util.config.FileConfig.ConfigurationPath",roots[0].getAbsolutePath());
   			RuntimeCheckResult result=FileConfig.SINGLETON.check();
   			
   			log.info("Check:"+result);
   			FileConfig.SINGLETON.printConfigurationPath();
   			
   			assertTrue(result.isOK());
   			
   			// add a custom path
   			assertTrue(FileConfig.SINGLETON.addPath("."));
   			FileConfig.SINGLETON.printConfigurationPath();
   			
   			// read a file
   			String relativePath="./src/test/resources/test.prop";
   			File file=new File(relativePath);
   			Properties prop=System.getProperties();
   			prop.save(new FileOutputStream(file), "Test:"+FileConfigTest.class.getName());
   			
   			File ret=FileConfig.SINGLETON.getConfigurationFile(relativePath, null);
   			
   			assertNotNull(ret);
   			
   			file.delete();
   			
   		} catch (Exception e) {
   			log.error("Got an exception:",e);
   			
   			fail("Got an exception:" + e);	
   			throw e;
   		}
   	}
    
    

  
}
