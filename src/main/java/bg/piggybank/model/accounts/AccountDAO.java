package bg.piggybank.model.accounts;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.exeptions.*;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import bg.piggybank.*;
import bg.piggybank.model.user.*;

@Component
public class AccountDAO {

	private static final String REGISTER_REQUEST = "INSERT INTO accounts(id, name, sum, iban, date, users_id, currencies_type, accountTypes_id) VALUES(null, ?, ?, ?, ?, ?, ?, ?);";
	private static final String GET_CURRENCY_ID = "SELECT type FROM currencies WHERE type  LIKE (?)";
	private static final String GET_ACCOUNT_ID = "SELECT id from accounttypes WHERE name LIKE (?)";
	private static final String GET_ACCOUNTS_COUNT = "SELECT COUNT(*) FROM accounts ;";

	public int registrateUserAccount(User user, Account account)
			throws UserInfoException, AccountException, SQLException, FailedConnectionException {
		if (user == null) {
			throw new UserInfoException("User not found!");
		}
		if (account == null) {
			throw new AccountException("Account type must be point");
		}
		int numberOfMark = 1;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(true);
			int userID = user.getId();
			PreparedStatement getData = connection.prepareStatement(GET_CURRENCY_ID);
			getData.setString(1, account.getCurrency().toString());
			ResultSet result = getData.executeQuery();
			if (!result.next()) {
				throw new AccountException("Invalid Currency!");
			}

			String typeOfCurrency = result.getString(numberOfMark);
			getData = connection.prepareStatement(GET_ACCOUNT_ID);
			getData.setString(1, account.getType().toString());
			result = getData.executeQuery();
			if (!result.next()) {
				throw new AccountException("Invalid kind of account!");
			}

			int indexOfAccount = result.getInt(numberOfMark);
			long timeNow = Calendar.getInstance().getTimeInMillis();
			java.sql.Timestamp date = new java.sql.Timestamp(timeNow);

			PreparedStatement registerAccount = connection.prepareStatement(REGISTER_REQUEST,
					Statement.RETURN_GENERATED_KEYS);

			registerAccount.setString(numberOfMark++, account.getName());
			registerAccount.setDouble(numberOfMark++, account.getSum());
			registerAccount.setString(numberOfMark++, Account.cryptIban(account.getIBAN()));
			registerAccount.setTimestamp(numberOfMark++, date);
			registerAccount.setInt(numberOfMark++, userID);
			registerAccount.setString(numberOfMark++, typeOfCurrency);
			registerAccount.setInt(numberOfMark++, indexOfAccount);
			numberOfMark = -1;

			int count = registerAccount.executeUpdate();
			if (count > 0) {
				System.out.println("The account was saved to the DB");
			} else {
				System.out.println("The account was not saved to the DB");
				return 0;
			}
			result = registerAccount.getGeneratedKeys();
			result.next();
			numberOfMark = result.getInt(1);
			account.setId(numberOfMark);
		} catch (Exception e) {
			System.err.println("!!!");
			e.printStackTrace();
		}
		return numberOfMark;
	}

	public List<Account> getAllAccountsPerUser(User user) throws UserException {
		if (user == null) {
			throw new UserException("No such user found!");
		}
		return null;
	}

	public static int getNumberOfAccounts() {
		int number = 0;
		Connection connection;
		try {
			connection = DBConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(GET_ACCOUNTS_COUNT);
			if(result.next()){
			number = result.getInt(1);
			System.out.println(number);
			}else{
				number=0;
			}
			return number;
		} catch (SQLException | FailedConnectionException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
