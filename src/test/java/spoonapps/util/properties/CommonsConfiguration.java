package spoonapps.util.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class CommonsConfiguration extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(GlobalPropertiesTest.class);

	@Test
	static public void testDefaultValue() throws Exception {
		try {
			
			File file=new File("./src/test/resources/CommonsConfiguration.properties");
			
			Properties prop=new Properties();
  			prop.setProperty("Name", "Value");
  			prop.store(new FileOutputStream(file), "MultiThread");
			
			Parameters params = new Parameters();

			// http://commons.apache.org/proper/commons-configuration/userguide/howto_properties.html#Properties_files

			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
					PropertiesConfiguration.class).configure(
							params.properties().setFile(file));

			PropertiesConfiguration config = (PropertiesConfiguration) builder.getConfiguration();

			PropertiesConfigurationLayout layout = config.getLayout();

			config.setHeader("Hello World");

			layout.setComment("pepe", "# Modified on:"+new Date());
			config.setProperty("pepe", "pepe");
			
			
			// This can be used to modify properties

			builder.save();
		} catch (Throwable e) {
			log.error("Error", e);

			fail("Got an exception:" + e);
			throw e;
		}
	}
}
