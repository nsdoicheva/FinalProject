package bg.piggybank.model.exeptions;

public class FailedInsertException extends Exception {

	private static final long serialVersionUID = -4199107222042789198L;

	public FailedInsertException() {
		super();
	}

	public FailedInsertException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FailedInsertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FailedInsertException(String arg0) {
		super(arg0);
	}

	public FailedInsertException(Throwable arg0) {
		super(arg0);
	}

	
}
