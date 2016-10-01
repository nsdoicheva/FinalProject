package bg.piggybank.model;

import bg.piggybank.model.exeptions.*;

public abstract class ContactInfo {
//	private static final int MAX_LENGTH_LONG_NUMBER = 13;
//	private static final String HOW_TO_START_LONG_NUMBER = "+3598";
//	private static final int MAX_LENGTH_SHORT_NUMBER = 10;
//	private static final String HOW_TO_START_NUMBER = "08";
	private String address;
	private String city;
	private String country;
	private String phoneNum;
	private String email;

	public ContactInfo(String address, String city, String country, String phoneNum, String email) throws IncorrectContactInfoException {
			if (isValid(address) && isValid(city) && isValid(country) && isValidEmailAddress(email)) {
			Checkers.isPhoneNumberValid(phoneNum);
				this.address = address;
				this.city = city;
				this.country = country;
				this.phoneNum = phoneNum;
				this.email=email;
			} else {
				throw new IncorrectContactInfoException("Incorrect value for contact info");
			}
	}

	public boolean isValid(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

//	private boolean isValidPhoneNumber(String telephone) {
//		boolean isValidNum = false;
//		if (isValid(telephone)) {
//			if ((telephone.substring(0, 2).equals(HOW_TO_START_NUMBER) && telephone.length() == MAX_LENGTH_SHORT_NUMBER)
//					|| (telephone.substring(0, 5).equals(HOW_TO_START_LONG_NUMBER)
//							&& telephone.length() == MAX_LENGTH_LONG_NUMBER)) {
//				isValidNum = true;
//			}
//		}
//		return isValidNum;
//	}

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
