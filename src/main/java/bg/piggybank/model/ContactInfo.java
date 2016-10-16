package bg.piggybank.model;

import bg.piggybank.model.exeptions.*;

public abstract class ContactInfo {
	private String address;
	private String city;
	private String country;
	private String phoneNum;
	private String email;

	public ContactInfo() {
		
	}
	
	public ContactInfo(String address, String city, String country, String phoneNum, String email) throws IncorrectContactInfoException {
		if (isValid(address)) {
			this.address = address;
		} else {
			throw new IncorrectContactInfoException("Incorrect value for address");
		}
		if (isValid(city)) {
			this.city = city;
		} else {
			throw new IncorrectContactInfoException("Incorrect value for city");
		}
		if (isValid(country)) {
			this.country = country;
		} else {
			throw new IncorrectContactInfoException("Incorrect value for country");
		}
		if (Checkers.isPhoneNumberValid(phoneNum)) {
			this.phoneNum = phoneNum;
		} else {
			throw new IncorrectContactInfoException("Incorrect value for phone number");
		}
		if (isValidEmailAddress(email)) {
			this.email = email;
		} else {
			throw new IncorrectContactInfoException("Incorrect value for email");
		}
	}

	public boolean isValid(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getEmail() {
		return email;
	}

}
