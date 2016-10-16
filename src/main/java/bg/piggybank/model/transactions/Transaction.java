package bg.piggybank.model.transactions;

import bg.piggybank.model.*;
import bg.piggybank.model.exeptions.*;

public class Transaction extends BasicInfo {
	private String sender; //username
	private String receiver;
	private String description;
	private String fromIBAN;
	private String toIBAN;

	public Transaction(double sum, String sender, String receiver, String description) {
		super(sum);
		try {
			if (isValidInfo(sender) && isValidInfo(receiver) && isValidInfo(description)) {
				this.sender = sender;
				this.receiver = receiver;
				this.description = description;
			} else {
				throw new InvalidTransactionInfoException("Incorrect transaction info entered");
			}
		} catch (InvalidTransactionInfoException e) {
			System.out.println("Invalid transaction info.");
			e.printStackTrace();
		}
	}

	private boolean isValidInfo(Object obj) {
		if (obj instanceof String) {
			if (obj != null && !((String) obj).trim().equals("")) {
				return true;
			}
			return false;
		} else {
			if (obj != null) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	

	@Override
	public String toString() {
		return "Transaction [sum = " + sum + " sender=" + sender + ", receiver=" + receiver + ", description=" + description + "\n]";
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getDescription() {
		return description;
	}
	

	public String getFromIBAN() {
		return fromIBAN;
	}

	public void setFromIBAN(String IBAN) {
		if(isValidInfo(IBAN)){
		this.fromIBAN = IBAN;
		}
	}
	
	public String getToIBAN() {
		return toIBAN;
	}

	public void setToIBAN(String IBAN) {
		if(isValidInfo(IBAN)){
		this.toIBAN = IBAN;
		}
	}
}
