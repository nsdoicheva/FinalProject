package exceptions;

public class InvalidAccountInfoException extends Exception {

	private static final long serialVersionUID = 4160969475319203203L;

	public InvalidAccountInfoException() {
		super();
	}

	public InvalidAccountInfoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidAccountInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAccountInfoException(String message) {
		super(message);
	}

	public InvalidAccountInfoException(Throwable cause) {
		super(cause);
	}

	
}
