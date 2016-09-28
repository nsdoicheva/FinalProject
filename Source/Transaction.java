package iSiktir;

import exceptions.InvalidTransactionInfoException;

public class Transaction extends BasicInfo {
	private Account sender;
	private Account receiver;
	private TransactionType type;

	public Transaction(double sum, Account sender, Account receiver, TransactionType type) {
		super(sum);
		try {
			if (isValidInfo(sender) && isValidInfo(receiver) && isValidInfo(type)) {
				this.sender = sender;
				this.receiver = receiver;
				this.type = type;
			} else {
				throw new InvalidTransactionInfoException("Incorrect transaction info entered");
			}
		} catch (InvalidTransactionInfoException e) {
			System.out.println("Invalid transaction info.");
			e.printStackTrace();
		}
	}

	private boolean isValidInfo(Object obj) {
		if (obj != null) {
			return true;
		} else {
			return false;
		}
	}

	public Account getSender() {
		return sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public TransactionType getType() {
		return type;
	}
}
