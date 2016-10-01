package bg.piggybank.model.accounts;

import bg.piggybank.model.exeptions.*;

public class Card {
	private int id;
	private String type;
	private String number;

	public Card(String type, String number) {
		if (type != null && !type.trim().equals("")) {
			this.type = type;
		}
		try {
			if (isValidNumber(number)) {
				this.number = number;
			} else {
				throw new InvalidCardNumberException("Incorrect card number entered.");
			}
		} catch (InvalidCardNumberException e) {
			return;
		}
	}

	private boolean isValidNumber(String number) {
		String cardPattern = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(cardPattern);
		java.util.regex.Matcher m = p.matcher(number);
		return m.matches();
	}

}
