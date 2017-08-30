package spoonapps.module;

import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckInterface;

public abstract class AbstractManager extends AbstractModule implements RuntimeCheckInterface{

	/**
	 * The svn:keywords should be on the file of the class file: LastChangedDate LastChangedRevision LastChangedBy HeadURL Id Header
	 * @return "$Header: $";
	 */
	abstract protected String getSvnHeaderString();

	
}
