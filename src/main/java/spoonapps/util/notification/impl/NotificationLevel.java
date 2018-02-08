package spoonapps.util.notification.impl;

import java.util.List;

import spoonapps.util.enumeration.AppEnum;

/**
 * @version $Id: NotificationLevel.java 720 2017-07-30 14:31:57Z amendogomez $
 *
 * This are the levels for the Notification Module
 *
 */
public class NotificationLevel extends AppEnum{
	
	private static final long serialVersionUID = "$Id: NotificationLevel.java 720 2017-07-30 14:31:57Z amendogomez $".hashCode();

	static final int FATAL_LEVEL = 0;
	static final int ERROR_LEVEL = FATAL_LEVEL+1;
	static final int WARN_LEVEL = ERROR_LEVEL+1;
	static final int INFO_LEVEL = WARN_LEVEL+1;
	static final int DEBUG_LEVEL = INFO_LEVEL+1;
	
	public static final NotificationLevel FATAL = new NotificationLevel("FATAL",FATAL_LEVEL);
	public static final NotificationLevel ERROR = new NotificationLevel("ERROR",ERROR_LEVEL);
	public static final NotificationLevel WARN = new NotificationLevel("WARN",WARN_LEVEL);
	public static final NotificationLevel INFO = new NotificationLevel("INFO",INFO_LEVEL);
	public static final NotificationLevel DEBUG = new NotificationLevel("DEBUG",DEBUG_LEVEL);

	final int level;

	private NotificationLevel(String id,int level){
		super(id,id,id);
		this.level=level;
	}

	public static NotificationLevel value(String value,NotificationLevel defaultValue) {
		return  (NotificationLevel)AppEnum.value(NotificationLevel.class, value,defaultValue);			
	}

	public static List<NotificationLevel> values() {
		return (List<NotificationLevel>)AppEnum.values(NotificationLevel.class);
	}
}
