package spoonapps.module.dao;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckInterface;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public interface AbstractDAOInterface extends RuntimeCheckInterface{

	public void init() throws ApplicationException;

	public void deleteAll() throws ApplicationException;

	public RuntimeCheckResult check();
}
