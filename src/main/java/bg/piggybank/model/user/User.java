package bg.piggybank.model.user;

import bg.piggybank.model.*;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User extends ContactInfo implements Comparable<User>{
	private static final int MIN_PASSWORD_LENGTH = 6;
	private String name;
	private String password;
	private String username;
	private int id;
	private String EGN;
	private String citizenship;
	private List<Account> accounts = new ArrayList<Account>();

	public User() {
		super();
	}
	
	
	public User(String name, String password, String username, String address, String city, String country,
			String phoneNum, String email, String EGN, String citizenship) throws IncorrectContactInfoException {
		super(address, city, country, phoneNum, email);
		try {
			if (isValid(name) && isValid(password) && isValid(username) && isValid(citizenship) && isValidEGN(EGN)) {
				try {
					isStrongPass(password);
				} catch (WeakPasswordException e) {
					System.out.println("Weak password!");
					return;
				}
				this.name = name;
				this.password = password;
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

	public boolean isStrongPass(String password) throws WeakPasswordException {
		if (isValid(password)) {
			boolean isLongEnough = password.length() > MIN_PASSWORD_LENGTH;
			boolean hasLowercase = !password.equals(password.toUpperCase());
			boolean hasDigit = password.matches(".*\\d+.*");
			if (isLongEnough && hasLowercase && hasDigit) {
				return true;
			} else {
				throw new WeakPasswordException("Your password is too weak.");
			}
		}
		return false;
	}

	private boolean isValidEGN(String EGN) {
		String egnPattern = "[0-9]{2}[0,1,2,4,5][0-9][0-9]{2}[0-9]{4}";
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

	public String getPassword() {
		return this.password;
	}

	public String getEGN() {
		return EGN;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", username=" + username + ", id=" + id + ", EGN=" + EGN + ", citizenship="
				+ citizenship + "]";
	}

	@Override
	public int compareTo(User user) {
		return this.getName().compareTo(user.getName());
	}

}

