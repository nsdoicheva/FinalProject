package iSiktir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.InvalidAccountInfoException;

public class Account extends BasicInfo {
	private String name;
	private String IBAN;
	private AccountType type;
	private CurrencyType currency;
	private List<Card> cards = new ArrayList<Card>();

	public Account(String name, String IBAN, AccountType type, CurrencyType currency, double sum) {
		super(sum);
		try {
			if (isValidString(name)) {
				this.name = name;
			} else {
				throw new InvalidAccountInfoException("Invalid name");
			}
			if (isValidIBAN(IBAN)) {
				this.IBAN = IBAN;
			} else {
				throw new InvalidAccountInfoException("Invalid IBAN");
			}
			if (isValidType(type)) {
				this.type = type;
			} else {
				throw new InvalidAccountInfoException("Invalid type");
			}
			if (isValidType(currency)) {
				this.currency = currency;
			} else {
				throw new InvalidAccountInfoException("Invalid currency");
			}
		} catch (InvalidAccountInfoException e) {
			e.printStackTrace();
		}
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(this.cards);
	}

	public void addCard(Card card) {
		if (card != null) {
			this.cards.add(card);
		} else {
			System.out.println("Card doesn't exist");
		}
	}

	private boolean isValidType(Object type) {
		if (type != null) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidString(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidIBAN(String IBAN) {
		String ibanPattern = "^[DE]{2}([0-9a-zA-Z]{20})$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ibanPattern);
		java.util.regex.Matcher m = p.matcher(IBAN);
		return m.matches();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIBAN() {
		return IBAN;
	}

	public AccountType getType() {
		return type;
	}

	public CurrencyType getCurrency() {
		return currency;
	}

}
