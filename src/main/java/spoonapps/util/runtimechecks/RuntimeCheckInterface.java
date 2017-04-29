package spoonapps.util.runtimechecks;

/**
 * Interface to be implemented by the modules that want to be tested
 */
public interface RuntimeCheckInterface {

	/**
	 * @return The test result.
	 */
	public RuntimeCheckResult check();
}
