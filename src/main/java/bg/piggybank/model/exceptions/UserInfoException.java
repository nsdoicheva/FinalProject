package bg.piggybank.model.exeptions;

public class UserInfoException extends Exception {

	private static final long serialVersionUID = 1598164362291345128L;

	public UserInfoException() {
		super();
	}

	public UserInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInfoException(String message) {
		super(message);
	}

	public UserInfoException(Throwable cause) {
		super(cause);
	}

	
}
