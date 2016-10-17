package bg.piggybank.model.exeptions;

public class NotEnoughMoneyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8043086126915259828L;

	protected NotEnoughMoneyException() {
		super();		
	}

	protected NotEnoughMoneyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	protected NotEnoughMoneyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotEnoughMoneyException(String arg0) {
		super(arg0);
	}

	
}
