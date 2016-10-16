package bg.piggybank.model;

import bg.piggybank.model.exeptions.PhoneNumberException;

public class Checkers {

	private static final String WRONG_NUMBER = "WrongNumber";
	private static final int NUMBER_DIGITS_IN_PHONE_NUMBER = 10;
	private static final String BULGARIAN_PHONE_CODE = "359";
	private static final String EXTENDED_BG_PHONE_CODE = "+359";
	private static final String ZERO = "0";

	private Checkers() {
	}

	public static boolean isPhoneNumberValid(String phone) throws PhoneNumberException {
		String phoneNumber = phone.replaceAll(" ", "");
		if (phoneNumber == null || phoneNumber.length() == 0) {
			throw new PhoneNumberException("Empty phone number!");
		}
		if (phoneNumber.startsWith(EXTENDED_BG_PHONE_CODE)) {
			phoneNumber = phoneNumber.replace(EXTENDED_BG_PHONE_CODE, ZERO);
		} else {
			if (phoneNumber.startsWith(BULGARIAN_PHONE_CODE)) {
				phoneNumber = phoneNumber.replace(BULGARIAN_PHONE_CODE, ZERO);
			}
		}
		phoneNumber = phoneNumber.replaceAll("[^-?0-9]+", WRONG_NUMBER);
		if (phoneNumber.length() != NUMBER_DIGITS_IN_PHONE_NUMBER) {
			throw new PhoneNumberException("Invalid phone number!");
		}
		return true;
	}
}
