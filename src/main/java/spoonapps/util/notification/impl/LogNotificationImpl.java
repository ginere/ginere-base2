package spoonapps.util.notification.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.notification.Notify;
import spoonapps.util.runtimechecks.RuntimeCheckResult;


public class LogNotificationImpl implements NotificationImplInterface{

	public static final Logger log = LoggerFactory.getLogger(LogNotificationImpl.class);
	
	static public LogNotificationImpl SINGLETON= new LogNotificationImpl();

	private LogNotificationImpl(){
	}

	@Override
	public RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(Notify.class);
		
		log.debug("Testing log notification impl...");
		return ret;	
	}

	@Override
	public boolean isEnabled(NotificationLevel level) {
		switch (level.level) {
		case NotificationLevel.DEBUG_LEVEL:
			return log.isDebugEnabled();
		case NotificationLevel.INFO_LEVEL:
			return log.isInfoEnabled();
		case NotificationLevel.WARN_LEVEL:
			return true;
		case NotificationLevel.ERROR_LEVEL:
			return true;
		case NotificationLevel.FATAL_LEVEL:
			return true;
		default:
			log.error("Unkown level:"+level);
			return false;
		}
	}

	@Override
	public void notify(NotificationLevel level, String message, Throwable e) {
		if (!isEnabled(level)){
			return ;
		} else {
			switch (level.level) {
			case NotificationLevel.DEBUG_LEVEL:
				log.debug(message,e);
				return;
			case NotificationLevel.INFO_LEVEL:
				log.info(message,e);
				return;
			case NotificationLevel.WARN_LEVEL:
				log.warn(message,e);
				return;
			case NotificationLevel.ERROR_LEVEL:
				log.error(message,e);
				return;
			case NotificationLevel.FATAL_LEVEL:
				log.error(message,e);
				return;
			default:
				log.error("Unkown level:"+level);
				log.error(message,e);
				return;
			}
		}
		
	}

}
