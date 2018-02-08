package spoonapps.util.notification.impl;

import spoonapps.util.runtimechecks.RuntimeCheckInterface;

public interface NotificationImplInterface extends RuntimeCheckInterface{

	public boolean isEnabled(NotificationLevel level);

	public void notify(NotificationLevel fatal, String message, Exception e);
}
