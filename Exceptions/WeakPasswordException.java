package exceptions;

public class WeakPasswordException extends Exception {

	private static final long serialVersionUID = -3411467091375164220L;

	public WeakPasswordException() {
		super();
	}

	public WeakPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeakPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeakPasswordException(String message) {
		super(message);
	}

	public WeakPasswordException(Throwable cause) {
		super(cause);
	}

	
}
