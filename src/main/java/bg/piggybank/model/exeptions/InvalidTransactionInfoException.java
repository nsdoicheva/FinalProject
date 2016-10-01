package bg.piggybank.model.exeptions;

public class InvalidTransactionInfoException extends Exception {

	private static final long serialVersionUID = -8893695255046120541L;

	public InvalidTransactionInfoException() {
		super();
	}

	public InvalidTransactionInfoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidTransactionInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTransactionInfoException(String message) {
		super(message);
	}

	public InvalidTransactionInfoException(Throwable cause) {
		super(cause);
	}

}
