package spoonapps.util.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.runtimechecks.RuntimeCheckResult;


/**
 * @version $Id$
 *
 * Send notification related to important events in the application.
 *
 */
public class Notify {

	private static Logger log = LoggerFactory.getLogger(Notify.class);

	private static NotifyInterface impl=NotifyLog.SINGLETON;
	
	public static void init(NotifyInterface newImpl){
		if (newImpl!=null){
			impl=newImpl;
			debug("Notify, new implementattion installed"+impl);
		} else {
			warn("Notify,Not setted a null implementation");
		}
	}
	
	public static RuntimeCheckResult check() {
		return impl.check();
	}

	public static boolean isDegubEnabled(){
		return log.isDebugEnabled();
	}

	public static boolean isInfoEnabled(){
		return log.isInfoEnabled();
	}

	public static boolean isWarnEnabled(){
		return log.isWarnEnabled();
	}

	public static boolean isErrorEnabled(){
		return true;
	}

	public static boolean isFatalEnabled(){
		return true;
	}


	public static void debug(String message){
		debug(message,null);
	}

	
	public static void debug(String message,Throwable e){
		impl.debug(message,e);
	}
	

	public static void info(String message){
		info(message,null);
	}

	public static void info(String message,Throwable e){
		impl.info(message,e);
	}


	public static void warn(String message){
		warn(message,null);
	}

	public static void warn(String message,Throwable e){
		impl.warn(message,e);
	}


	public static void error(String message){
		error(message,null);
	}

	public static void error(String message,Throwable e){
		impl.error(message,e);
	}

	public static void fatal(String message){
		fatal(message);
	}
	
	public static void fatal(String message,Throwable e){
		impl.fatal(message,e);
	}

}
