package spoonapps.util.notification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class NotifyLog implements NotifyInterface{
	
	private static Logger log = LoggerFactory.getLogger(NotifyLog.class);
	
	public static final NotifyLog SINGLETON=new NotifyLog();

	private static final String MSG_PREFIX = "NOTIFICATION,";

	private NotifyLog(){
	}

	private String getText(String message) {
		if (StringUtils.isBlank(message)){
			return MSG_PREFIX;
		} else {
			return MSG_PREFIX+message;
		}
	}
	

	@Override
	public void debug(String message,Throwable e){
		if (e!=null){
			log.debug(getText(message),e);
		} else {
			log.debug(message);
		}
	}
	

	@Override
	public void info(String message,Throwable e){
		if (e!=null){
			log.info(getText(message),e);
		} else {
			log.info(message);
		}
	}

	@Override
	public void warn(String message,Throwable e){
		if (e!=null){
			log.warn(getText(message),e);
		} else {
			log.warn(message);
		}
	}

	@Override
	public void error(String message,Throwable e){
		if (e!=null){
			log.error(getText(message),e);
		} else {
			log.error(message);
		}
	}

	@Override	
	public void fatal(String message,Throwable e){
		// TODO DO a fatal
		if (e!=null){
			log.error(getText(message),e);
		} else {
			log.error(message);
		}
	}

	@Override
	public RuntimeCheckResult check() {
		return new RuntimeCheckResult(NotifyLog.class);		
	}

}
