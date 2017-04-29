package spoonapps.util.exception;

/**
 * @version $Id: Main.java 7 2016-12-20 21:27:48Z mendogomeza $
 *
 * Somme utilities regarding exceptions
 */
public class ExceptionUtils {

	public static String formatException(Throwable exception) {
		StringBuilder buffer = new StringBuilder();

		formatException(exception, buffer);

		return buffer.toString();
	}


	public static StringBuilder formatException(Throwable exception,
												StringBuilder buffer) {
		
		StringBuilder ret;
		
		if (exception == null) {
			ret=buffer;
			
		} else {
			buffer.append(exception.getClass().getName());
			buffer.append(": \"");
			buffer.append(exception.getMessage());
			buffer.append("\" \n");
	
			StackTraceElement array[] = exception.getStackTrace();
	
			for (StackTraceElement element : array) {
				buffer.append("\tat ");
				printStackTraceElement(element,buffer);
				buffer.append('\n');
			}
	
			if (exception.getCause() != null) {
				buffer.append("Parent exception: ");
				ret=formatException(exception.getCause(), buffer);
			} else {
				ret=buffer;
			}
		}
		return ret;
	}
	
	public static void printStackTraceElement(StackTraceElement element,StringBuilder buffer){		
		buffer.append(element.getClassName());
		buffer.append('.');
		buffer.append(element.getMethodName());
		buffer.append('(');
		buffer.append(element.getFileName());
		if (element.getLineNumber() > 0) {
			buffer.append(':');
			buffer.append(element.getLineNumber());
		}
		buffer.append(')');
	}


	public static String getMessage(Throwable exception) {
		if (exception==null){
			return "null";
		} else {
			String message=exception.getMessage();
			if (message==null){
				message=exception.getClass().getName();
			}
			
			return message;
		}
	}
}