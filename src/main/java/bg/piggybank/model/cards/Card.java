package bg.piggybank.model.cards;

import bg.piggybank.model.Checkers;
import bg.piggybank.model.exeptions.*;

public class Card {
	private static final String ERROR_MESSAGE = "Cannot create card right now!";
	private int id;
	private CardTypes type;
	private String number;
	private String IBAN;

	public Card(int id, CardTypes type, String number) throws CardException {
		try {
			if (id <= 0) {
				throw new CardException("Invalid card ID");
			}
			if (type == null) {
				throw new CardException("Ivalid card TYPE");
			}
			if (!Checkers.isStringValid(number)) {
				throw new CardException("Invalid card NUMBER");
			}
			int indexOfLastChar = number.length() - 1;
			int lastDigit = number.charAt(indexOfLastChar) - '0';

			// Cutting the string to match required by checker;
			String numberToCheck = number.substring(0, indexOfLastChar);
			int controlDigit = Checkers.calculateDigitByLuhm(numberToCheck);

			// Test if last digit is equal to returned control digit;
			if (lastDigit != controlDigit) {
				throw new CardException("Control sum doesn't match!");
			}
		} catch (CardException cardExc) {
			throw new CardException(ERROR_MESSAGE, cardExc);
		} catch (GeneratorException genExc) {
			throw new CardException(ERROR_MESSAGE, genExc);
		}
	}

	public Card(CardTypes type, String number) {
		if (type != null) {
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

	public int getId() {
		return id;
	}

	public CardTypes getType() {
		return type;
	}

	public String getNumber() {
		return number;
	}

	protected boolean setID(int id) {
		if (id > 0) {
			this.id = id;
			return true;
		}
		return false;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String IBAN) {
		if (IBAN != null && !IBAN.trim().equals("")) {
			this.IBAN = IBAN;
		}
	}

}
