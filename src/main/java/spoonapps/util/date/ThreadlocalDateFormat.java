package spoonapps.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version $Id: Main.java 7 2016-12-20 21:27:48Z mendogomeza $
 * 
 *          The SimpleDateFormat is not a thread safe class. To avoid the issue
 *          use this class.
 *
 */
public class ThreadlocalDateFormat {
	public static final Logger log = LoggerFactory.getLogger(ThreadlocalDateFormat.class);

	private static final ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
		@Override
		protected Map<String, SimpleDateFormat> initialValue() {
			return new Hashtable<String, SimpleDateFormat>();
		}
	};

	public static String format(final String dateFormatString, Date date) {
		return format(dateFormatString, date, null);
	}

	public static String format(final String dateFormatString, Date date, String defaultValue) {
		return format(dateFormatString, date,null,defaultValue);

	}
	
	public static String format(final String dateFormatString, Date date, TimeZone tz,String defaultValue) {
		if (date == null) {
			return defaultValue;
		} else if (StringUtils.isEmpty(dateFormatString)) {
			return defaultValue;
		} else {
			Map<String, SimpleDateFormat> cache = threadLocal.get();

			SimpleDateFormat sdf;
			String key;
			if (tz!=null){
				key=dateFormatString+tz.getID();
			} else{
				key=dateFormatString;
			}
			if (cache.containsKey(key)) {
				sdf = cache.get(key);
			} else {
				sdf = new SimpleDateFormat(dateFormatString);
				sdf.setTimeZone(tz);
				cache.put(key, sdf);
			}

			try {
				return sdf.format(date);
			} catch (Throwable e) {
				log.error("While formationd date:" + date, e);
				
				log.error(String.format("While formationd date:%s, format:%s, Timezone:%s", date,dateFormatString,tz),e);
				return defaultValue;
			}
		}
	}
}
