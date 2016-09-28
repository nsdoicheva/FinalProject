package iSiktir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.IncorrectContactInfoException;
import exceptions.UserInfoException;
import exceptions.WeakPasswordException;

public class User extends ContactInfo {
	private static final int MIN_PASSWORD_LENGTH = 6;
	private String name;
	private String password;
	private String username;
	private int id;
	private String EGN;
	private String citizenship;
	private List<Account> accounts = new ArrayList<Account>();

	public User(String name, String password, String username, String address, String city, String country,
			String phoneNum, String email, String EGN, String citizenship) throws IncorrectContactInfoException, WeakPasswordException {
		super(address, city, country, phoneNum, email);
		try {
			if (isValid(name) && isValid(password) && isValid(username) && isValid(citizenship) && isValidEGN(EGN)) {
					if(isStrongPass(password)){
						this.password = password;
					}else{
						throw new WeakPasswordException("Password is too weak.");
					}
					this.name = name;
				
				this.username = username;
				this.citizenship = citizenship;
				this.EGN = EGN;
			} else {
				throw new UserInfoException("Incorrect user info.");
			}
		} catch (UserInfoException e) {
			e.printStackTrace();
		}
	}

	public boolean isStrongPass(String password){

		if (password == null) {
			return false;
		}
		if (password.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}
		boolean containDigits = false;
		boolean containLowCaps = false;
		boolean containUpperCaps = false;

		for (char passChar : password.toCharArray()) {
			if (Character.isDigit(passChar)) {
				containDigits = true;
			}
			if (Character.isLowerCase(passChar)) {
				containLowCaps = true;
			}
			if (Character.isUpperCase(passChar)) {
				containUpperCaps = true;
			}

		}
		if (!containDigits) {
			return false;
		}
		if (!containLowCaps) {
			return false;
		}
		if (!containUpperCaps) {
			return false;
		}
		return true;
	}

	private boolean isValidEGN(String EGN) {
		String egnPattern = "[0-9]{2}[0,1,2,4][0-9][0-9]{2}[0-9]{4}";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(egnPattern);
		java.util.regex.Matcher m = p.matcher(EGN);
		return m.matches();
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(this.accounts);
	}

	public void addAccount(Account account) {
		if (account != null) {
			this.accounts.add(account);
		} else {
			System.out.println("Account doesn't exist");
		}
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}
}
