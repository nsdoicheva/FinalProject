package bg.piggybank.model.amounts;

import java.sql.Timestamp;

import bg.piggybank.model.exeptions.AmountException;

public class Amount {
	private int id;
	private Timestamp date;
	private double money;
	private String IBAN;
	private int month;

	private int day;
	private int year;

	public Amount(int id, Timestamp date, double money, String IBAN) {
		try {
			if (id > 0) {
				this.id = id;
			} else {
				throw new AmountException("Invalid amount id");
			}
			if (date != null) {
				this.date = date;
			} else {
				throw new AmountException("Invalid amount date");
			}
			if (money > 0) {
				this.money = money;
			} else {
				throw new AmountException("Invalid amount money");
			}
			if (IBAN != null && !IBAN.trim().equals("")) {
				this.IBAN = IBAN;
			} else {
				throw new AmountException("Invalid amount IBAN");
			}
		} catch (AmountException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public Timestamp getDate() {
		return date;
	}

	public double getMoney() {
		return money;
	}

	public String getIBAN() {
		return IBAN;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		if (month >= 0) {
			this.month = month;
		}
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		if (day > 0) {
			this.day = day;
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		if (year > 0) {
			this.year = year;
		}
	}
}
