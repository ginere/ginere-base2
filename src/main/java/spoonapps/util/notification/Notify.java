package spoonapps.util.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.notification.impl.LogNotificationImpl;
import spoonapps.util.notification.impl.NotificationImplInterface;
import spoonapps.util.notification.impl.NotificationLevel;
import spoonapps.util.runtimechecks.RuntimeCheckResult;


public class Notify {

	public static final Logger log = LoggerFactory.getLogger(Notify.class);

	private static NotificationImplInterface connector=LogNotificationImpl.SINGLETON;
	
	public static RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(Notify.class);
		
		ret.add("Notify connector", connector);

		return ret;			
	}
	

	/**
	 * @param c
	 */
	public static void setConnector(NotificationImplInterface c){
		if (c!=null){
			connector=c;
			log.info("Connector changed to:"+c);
		}
	}
	
	public static boolean isDegubEnabled(){
		return connector.isEnabled(NotificationLevel.DEBUG);
	}

	public static boolean isInfoEnabled(){
		return connector.isEnabled(NotificationLevel.INFO);
	}

	public static boolean isWarnEnabled(){
		return connector.isEnabled(NotificationLevel.WARN);
	}

	public static boolean isErrorEnabled(){
		return true;
	}

	public static boolean isFatalEnabled(){
		return true;
	}


	public static void debug(String message){
		connector.notify(NotificationLevel.DEBUG,message,null);
	}

	
	public static void debug(String message,Exception e){
		connector.notify(NotificationLevel.DEBUG,message,e);
	}
	
	public static void debug(Logger log,String message){
		log.debug(message);

		connector.notify(NotificationLevel.DEBUG,message,null);
	}

	public static void debug(Logger log,String message,Exception e){
		log.debug(message,e);

		connector.notify(NotificationLevel.DEBUG,message,e);
	}

	public static void info(String message){
		connector.notify(NotificationLevel.INFO,message,null);
	}

	public static void info(String message,Exception e){
		connector.notify(NotificationLevel.INFO,message,e);
	}

	public static void info(Logger log,String message){
		log.info(message);

		connector.notify(NotificationLevel.INFO,message,null);
	}

	public static void info(Logger log,String message,Exception e){
		log.info(message,e);

		connector.notify(NotificationLevel.INFO,message,e);
	}

	public static void warn(String message){
		connector.notify(NotificationLevel.WARN,message,null);
	}

	public static void warn(String message,Exception e){
		connector.notify(NotificationLevel.WARN,message,e);
	}

	public static void warn(Logger log,String message){
		log.warn(message);

		connector.notify(NotificationLevel.WARN,message,null);
	}

	public static void warn(Logger log,String message,Exception e){
		log.warn(message,e);

		connector.notify(NotificationLevel.WARN,message,e);
	}

	public static void error(String message){
		connector.notify(NotificationLevel.ERROR,message,null);
	}

	public static void error(String message,Exception e){
		connector.notify(NotificationLevel.ERROR,message,e);
	}

	public static void error(Logger log,String message){
		log.error(message);

		connector.notify(NotificationLevel.ERROR,message,null);
	}

	public static void error(Logger log,String message,Exception e){
		log.error(message,e);

		connector.notify(NotificationLevel.ERROR,message,e);
	}

	public static void fatal(String message){
		connector.notify(NotificationLevel.FATAL,message,null);
	}

	public static void fatal(String message,Exception e){
		connector.notify(NotificationLevel.FATAL,message,e);
	}

	public static void fatal(Logger log,String message){
		log.error(message);

		connector.notify(NotificationLevel.FATAL,message,null);
	}

	public static void fatal(Logger log,String message,Exception e){
		log.error(message,e);

		connector.notify(NotificationLevel.FATAL,message,e);
	}
}
