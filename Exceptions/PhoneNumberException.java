package exceptions;

public class PhoneNumberException extends Exception {

	private static final long serialVersionUID = 6302686048172518781L;

	public PhoneNumberException() {
		super();
	}

	public PhoneNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PhoneNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public PhoneNumberException(String message) {
		super(message);
	}

	public PhoneNumberException(Throwable cause) {
		super(cause);
	}

}
