package bg.piggybank.model.exeptions;

public class AmountException extends Exception {

	private static final long serialVersionUID = 3066371681803521307L;

	public AmountException() {
		super();
	}

	public AmountException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AmountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AmountException(String arg0) {
		super(arg0);
	}

	public AmountException(Throwable arg0) {
		super(arg0);
	}

}
