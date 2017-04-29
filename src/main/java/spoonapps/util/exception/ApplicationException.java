package spoonapps.util.exception;

/**
 * @version $Id: Main.java 7 2016-12-20 21:27:48Z mendogomeza $
 *
 * General Purpose Application Exception.
 *	
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApplicationException(String message, Throwable cause){
		super(message,cause);
	}
	
	public ApplicationException(String message){
		super(message);
	}

}
