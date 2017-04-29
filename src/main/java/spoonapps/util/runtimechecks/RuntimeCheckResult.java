package spoonapps.util.runtimechecks;

import java.util.Vector;

import spoonapps.util.exception.ExceptionUtils;

/**
 * Cascade tree system testing.
 * 
 *
 */
final public class RuntimeCheckResult {

	private final String name;

	private boolean isError = false;

	private String errorMessage;

	private Vector<RuntimeCheckResult> childs = new Vector<RuntimeCheckResult>();
	private Throwable exception = null;;

	public static RuntimeCheckResult check(String systemName, RuntimeCheckInterface system) {
		RuntimeCheckResult ret = new RuntimeCheckResult(systemName);

		if (system == null) {
			ret.addError("The system to test:" + systemName + " is null.");
		} else {
			ret.add(system.check());
		}

		return ret;
	}

	public RuntimeCheckResult(String nombreDelSistema) {
		this.name = nombreDelSistema;
	}

	public RuntimeCheckResult(Class<?> clazz) {
		this.name = clazz.getName();
	}

	public RuntimeCheckResult(Class<?> clazz,String version) {
		this.name = clazz.getName()+" ["+version+"]";
	}

	public void add(String name, RuntimeCheckInterface system) {
		if (system == null) {
			addError("The system to check:" + name + ", is null.");
		} else {
			add(system.check());
		}
	}

	public void addError(String string) {
		this.errorMessage = string;
		this.exception = null;
		this.isError = true;
	}

	public void addError(String string, Throwable e) {
		this.errorMessage = string;
		this.exception = e;
		this.isError = true;
	}

	public void add(RuntimeCheckResult test) {
		if (test.isError == true) {
			this.isError = test.isError;
		}
		this.childs.add(test);
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		toString(buffer, "");
		return buffer.toString();
	}

	private void toString(StringBuilder buffer, String level) {
		buffer.append(level);
		buffer.append(name);
		buffer.append(": ");
		if (isError) {
			buffer.append("ERROR");
		} else {
			buffer.append("OK");
		}
		buffer.append('\n');

		if (errorMessage != null || exception != null) {
			buffer.append(level);
			buffer.append(errorMessage);
			buffer.append(ExceptionUtils.formatException(exception));
			buffer.append('\n');
		}

		for (RuntimeCheckResult test : childs) {
			test.toString(buffer, level + '\t');
		}
	}

	public boolean isOK() {
		return !isError;
	}

}
