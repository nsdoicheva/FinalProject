package bg.piggybank.model.exeptions;
public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 616790327149196671L;

	public UserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserException(String arg0, Exception arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public UserException(String message, Exception cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
