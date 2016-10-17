package bg.piggybank.model.exeptions;
public class InvalidAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1681760488400476949L;

	public InvalidAccountException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidAccountException(String arg0, Exception arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public InvalidAccountException(String arg0, Exception arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidAccountException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidAccountException(Exception arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
