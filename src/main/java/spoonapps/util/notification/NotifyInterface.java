package spoonapps.util.notification;

import spoonapps.util.runtimechecks.RuntimeCheckResult;

public interface NotifyInterface {
	public void debug(String message,Throwable e);
	public void info(String message,Throwable e);
	public void warn(String message,Throwable e);
	public void error(String message,Throwable e);
	public void fatal(String message,Throwable e);
	
	public RuntimeCheckResult check();
}
