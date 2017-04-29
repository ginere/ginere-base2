package spoonapps.util.module;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.runtimechecks.RuntimeCheckInterface;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

/**
 * This is the moder class for al the modules that can be runtime checked and have version
 *
 */
public abstract class AbstractModule implements RuntimeCheckInterface{
	public static final Logger log = LoggerFactory.getLogger(AbstractModule.class);

	/**
	 * This is the version of the code
	 */
	private String version="";
	
	protected AbstractModule(){
		getVersion(getSvnHeaderString());
	}

	@Override
	public RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(getClass(),version);
		
		check(ret);
		
		return ret;		
	}
	
	
	/**
	 * This check the status using the RuntimeCheckResult that already contains the version.
	 * 
	 * @param ret The Module Chec retuls.
	 */
	abstract protected RuntimeCheckResult check(RuntimeCheckResult ret);

	/**
	 * The svn:keywords should be on the file of the class file: LastChangedDate LastChangedRevision LastChangedBy HeadURL Id Header
	 * @return "$Header: $";
	 */
	abstract protected String getSvnHeaderString();
	
	/**
	 * Gets the application version from the SVN position.
	 * 
	 * @return the version
	 */
	protected String getVersion(String svnHeaderString) {
		if (StringUtils.isBlank(version)) {
			if (svnHeaderString.contains("trunk")) {
				version = "trunk";
			} else if (svnHeaderString.contains("tags")) {
				int index = svnHeaderString.lastIndexOf("tags");
				String tmp = svnHeaderString.substring(index + "tags".length(), svnHeaderString.length());
				String array[] = StringUtils.split(tmp, '/');
				if (array.length > 0) {
					version = array[0];
				} else {
					version = "unkownw-tag";
				}
			} else {
				version = "unkownw";
			}
		}

		return version;
	}

}
