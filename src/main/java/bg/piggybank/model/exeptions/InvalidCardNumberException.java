package bg.piggybank.model.exeptions;

public class InvalidCardNumberException extends Exception {

	private static final long serialVersionUID = 4348106023465565105L;

	public InvalidCardNumberException() {
		super();
	}

	public InvalidCardNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidCardNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCardNumberException(String message) {
		super(message);
	}

	public InvalidCardNumberException(Throwable cause) {
		super(cause);
	}

}
