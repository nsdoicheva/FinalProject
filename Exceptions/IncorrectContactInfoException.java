package exceptions;

public class IncorrectContactInfoException extends Exception {

	private static final long serialVersionUID = -2733493033612756280L;

	public IncorrectContactInfoException() {
		super();
	}

	public IncorrectContactInfoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncorrectContactInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectContactInfoException(String message) {
		super(message);
	}

	public IncorrectContactInfoException(Throwable cause) {
		super(cause);
	}

	
}
