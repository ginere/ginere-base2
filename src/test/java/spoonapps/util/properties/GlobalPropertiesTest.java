package spoonapps.util.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import spoonapps.util.properties.impl.FilePropertiesImpl;

public class GlobalPropertiesTest extends TestCase{


    private static final Logger log = LoggerFactory.getLogger(GlobalPropertiesTest.class);

    @Test
   	static public void testDefaultValue() throws Exception {
   		try {
   			String value=GlobalProperties.getStringValue(GlobalPropertiesTest.class, "TEST");

   			assertNull("Default null", value);
   			
   			String defaultValue="DefaultValue";
   			 value=GlobalProperties.getStringValue(GlobalPropertiesTest.class, "TEST",defaultValue);

   			assertEquals("Default Value", defaultValue,value);
   			
   			
   		} catch (Exception e) {
   			log.error("Called SOAP Service %s.",e);
   			
   			fail("Got an exception:" + e);	
   			throw e;
   		}
   	}
    
    

    
    @Test
	static public void testReadFileShortName() throws Exception {
		try {
			
			File file=new File("src/test/resources/testReadFileShortName.prop");
			String key="key";
			String value="Value";

			
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
			String newValue=GlobalProperties.getStringValue(GlobalPropertiesTest.class, key);
			
			assertEquals(newValue, value);
			
			newValue=GlobalProperties.getStringValue(GlobalPropertiesTest.class, key,null);
			
			assertEquals(newValue, value);
			
			
			// Testing For other class
			newValue=GlobalProperties.getStringValue(GlobalProperties.class, key);			
			assertEquals(newValue, value);			
			newValue=GlobalProperties.getStringValue(GlobalProperties.class, key,null);			
			assertEquals(newValue, value);

			file.delete();
			
			
		} catch (Exception e) {
			log.error("Test error",e);
			
			fail("Got an exception:" + e);	
			throw e;
		}
	}
    
    @Test
  	static public void testReadFileLongName() throws Exception {
  		try {
  			
  			File file=new File("src/test/resources/testReadFileLongName.prop");
  			String key="key";
  			String value="Value";
  			String longPropertyName=GlobalProperties.getLongPropertyName(GlobalPropertiesTest.class, key);
  			
  			Properties prop=new Properties();
  			prop.setProperty(longPropertyName, value);
  			prop.store(new FileOutputStream(file), "testReadFileLongName");
  			
			if (!file.exists()){
				throw new Exception("File not created:"+file.getAbsolutePath());
			} else {
				log.info("File created:"+file.getAbsolutePath());
			}
			
  			FilePropertiesImpl implementation=FilePropertiesImpl.getFromFile(file);
  			
  			GlobalProperties.setImplementation(implementation);
  			
  			// testing short property names
  			String newValue=GlobalProperties.getStringValue(GlobalPropertiesTest.class, key); 			
  			assertEquals(newValue, value); 			
  			newValue=GlobalProperties.getStringValue(GlobalPropertiesTest.class, key,null); 			
  			assertEquals(newValue, value);
  			
  			
			// For other classes the value should be null
			newValue=GlobalProperties.getStringValue(GlobalProperties.class, key);			
			assertNull(newValue);			
			newValue=GlobalProperties.getStringValue(GlobalProperties.class, key,null);			
			assertNull(newValue);
			newValue=GlobalProperties.getStringValue(GlobalProperties.class, key,"test");			
			assertEquals(newValue, "test");
  			
			file.delete();
  			
  		} catch (Exception e) {
  			log.error("Test error",e);
  			
  			fail("Got an exception:" + e);	
  			throw e;
  		}
  	}

    
    
    @Test
  	static public void testMultyTread() throws Exception {
  		try {
  			
  			File file=new File("src/test/resources/Test.prop");
  			String key=KEY;
  			String value=EXPECTED_VALUE;
  			String longPropertyName=GlobalProperties.getLongPropertyName(GlobalPropertiesTest.class, key);
  			
  			Properties prop=new Properties();
  			prop.setProperty(longPropertyName, value);
  			prop.store(new FileOutputStream(file), "MultiThread");
  			
			if (!file.exists()){
				throw new Exception("File not created:"+file.getAbsolutePath());
			} else {
				log.info("File created:"+file.getAbsolutePath());
			}
			
  			FilePropertiesImpl implementation=FilePropertiesImpl.getFromFile(file);
  			
  			GlobalProperties.setImplementation(implementation);
  			int threadNumber=6;
  			
  			for (threadNumber=1;threadNumber<2;threadNumber++){
  				log.error("++++NUMBER OF THREADS:"+threadNumber);
	  			ThreadReader THREAD[]=new ThreadReader[threadNumber];
	  			
	  			for (int i=0;i<threadNumber;i++){
	  				THREAD[i]=new ThreadReader();
	  				THREAD[i].start();
	  			}
	  			
	  			do {
	  				synchronized (GlobalProperties.class) {
	  	  				GlobalProperties.class.wait();	
					}
	  			} while(!allStopped(THREAD));
  			}
  			
  			file.delete();
  		} catch (Exception e) {
  			log.error("Test error",e);
  			
  			fail("Got an exception:" + e);	
  			throw e;
  		}
  	}
    
    private static boolean allStopped(ThreadReader[] array) {
    	for (ThreadReader threadReader:array){
    		if (threadReader.isRunning){
    			return false;
    		}
    	}
    	return true;
	}

	static int READING_NUMER=100000;
    static String EXPECTED_VALUE="MultyThread Value";
    static String KEY="MultyThread Key";
    
    static class ThreadReader extends Thread{
    	public boolean isRunning=false;
    	public boolean isError=false;
    	
    	@Override
    	public void run(){
    		isRunning=true;
    		
    		long time=System.currentTimeMillis();
    		int i=0;
    		for (i=0;i<READING_NUMER;i++){
    			String value=GlobalProperties.getStringValue(GlobalPropertiesTest.class, KEY);
    			if (!StringUtils.equals(EXPECTED_VALUE, value)){
    				log.error("++++Found not expected value:"+value);
    				log.error("++++Found not expected value:"+value);
    				isError=true;
    				break;
    			}
    		}
    		
    		long laps=(System.currentTimeMillis()-time);
    		log.info(String.format("Readed number:%s in %s millis",i,laps));
    		isRunning=false;
			synchronized (GlobalProperties.class) {
				GlobalProperties.class.notifyAll();
			}
    	}
    }
}
