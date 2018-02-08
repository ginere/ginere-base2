package spoonapps.util.notification;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import spoonapps.util.notification.Notify;
import spoonapps.util.notification.impl.LogNotificationImpl;

public class NotificationTest extends TestCase{


    private static final Logger log = LoggerFactory.getLogger(NotificationTest.class);

    @Test
   	static public void testDefaultValue() throws Exception {
   		try {
            log.info("test:"+Notify.check());

            Notify.setConnector(LogNotificationImpl.SINGLETON);
            
   			Notify.debug("debug");
   			Notify.info("debug");
   			Notify.warn("debug");
   			Notify.error("debug");
   			Notify.fatal("debug");

            TestCase.assertTrue(Notify.check().isOK());
            
   		} catch (Exception e) {
   			log.error("Got an exception:",e);
   			
   			fail("Got an exception:" + e);	
   			throw e;
   		}
   	}
    
    

  
}
