package bg.piggybank.model.exeptions;

public class AccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7275640408406294416L;

	public AccountException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountException(String arg0, Exception arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public AccountException(String arg0, Exception arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public AccountException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AccountException(Exception arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
