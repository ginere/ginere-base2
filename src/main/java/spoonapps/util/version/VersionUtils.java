package spoonapps.util.version;

import org.apache.commons.lang3.StringUtils;

public class VersionUtils {
	
	/**
	 * Gets the application version from the SVN position.
	 * 
	 * @return the version
	 */
	public static String getVersion(String svnHeaderString,String defaultValue) {
		if (StringUtils.isBlank(svnHeaderString)){
			return defaultValue;
		} else if (svnHeaderString.contains("trunk")) {
            return defaultValue;
        } else if (svnHeaderString.contains("tags")) {
            int index = svnHeaderString.lastIndexOf("tags");
            String tmp = svnHeaderString.substring(index + "tags".length(), svnHeaderString.length());
            String array[] = StringUtils.split(tmp, '/');
            if (array.length > 0) {
                return array[0];
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
	}
	
	public static String getDate(String svnDateString) {
		if (StringUtils.isBlank(svnDateString) || svnDateString.length()<17){
			return svnDateString;
		} else {
			return svnDateString.substring(7,17);
		}
	}
}
