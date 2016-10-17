package bg.piggybank.model.accounts;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.exeptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.piggybank.model.user.*;

@Component
public class AccountDAO implements IAccountDAO {

	@Autowired
	private IUserDAO userDao;
	private static final String REGISTER_REQUEST = "INSERT INTO accounts(id, name, sum, iban, date, users_id, currencies_type, accountTypes_id) VALUES(null, ?, ?, ?, ?, ?, ?, ?);";
	private static final String GET_CURRENCY_ID = "SELECT type FROM currencies WHERE type  LIKE (?)";
	private static final String GET_ACCOUNT_ID = "SELECT id from accounttypes WHERE name LIKE (?)";
	private static final String GET_ACCOUNTS_COUNT = "SELECT COUNT(*) FROM accounts ;";
	private static final String SELECT_ACCOUNT_BY_IBAN = "SELECT id, name, sum, currencies_type, accountTypes_id FROM accounts WHERE IBAN=? ;";
	private static final String SELECT_ACCOUNT_TYPE_BY_ID = "SELECT name FROM accounttypes WHERE id=? ;";
	private static final String SELECT_USER_ACCOUNTS = "SELECT name, sum, IBAN, currencies_type, accountTypes_id FROM accounts WHERE users_id=?;";
	private static final String SELECT_ACCOUNT_BY_ID = "SELECT id, name, sum, IBAN, currencies_type, accountTypes_id FROM accounts WHERE id = ?;";
	private static final String SELECT_ACCOUNT_ID = "SELECT id FROM accounts WHERE IBAN= ?;";
	private static final String SELECT_ALL_ACCOUNTS = "SELECT id FROM accounts;";
	private static final String SELECT_ACCOUNT_ID_BY_USER_ID = "SELECT id FROM accounts WHERE users_id = ?;";

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#registrateUserAccount(bg.piggybank.model.user.User, bg.piggybank.model.accounts.Account)
	 */
	@Override
	public int registrateUserAccount(User user, Account account)
			throws UserInfoException, AccountException, InvalidAccountInfoException, InvalidAccountException {
		if (user == null) {
			throw new UserInfoException("User cannot be null");
		}
		if (account == null) {
			throw new AccountException("Account cannot be null");
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
				throw new InvalidAccountInfoException("Invalid type of account!");
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
				throw new InvalidAccountException("Invalid account.");
			}
			result = registerAccount.getGeneratedKeys();
			result.next();
			numberOfMark = result.getInt(1);
			account.setId(numberOfMark);
		} catch (SQLException | FailedConnectionException e) {
			System.out.println("Database problem");
			e.printStackTrace();
		}
		return numberOfMark;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAllMyAccounts(java.lang.String)
	 */
	@Override
	public List<Account> getAllMyAccounts(String username) {
		List<Account> list = new ArrayList<Account>();
		if (username == null || username.trim().equals("")) {
			try {
				throw new UserException("Username can't be empty!");
			} catch (UserException e) {
				e.printStackTrace();
			}
		} else {
			User user = userDao.getUserByUsername(username);
			Connection connection;
			try {
				connection = DBConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_ACCOUNTS);
				statement.setInt(1, user.getId());
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					String name = result.getString("name");
					double sum = result.getDouble("sum");
					String IBAN = Account.decryptIban(result.getString("IBAN"));
					System.out.println(IBAN);
					CurrencyType currency = getCurrencyType(result.getString("currencies_type"));
					AccountType accType = getAccountTypeByID(connection, result.getInt("accountTypes_id"));
					System.out.println(accType);
					Account account = new Account(name, accType, currency, sum);
					account.setIbanFromDB(IBAN);
					list.add(account);
					System.out.println(account.getIBAN());
				}
				return list;
			} catch (FailedConnectionException | SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public static int getNumberOfAccounts() {
		int number = 0;
		Connection connection;
		try {
			connection = DBConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(GET_ACCOUNTS_COUNT);
			if (result.next()) {
				number = result.getInt(1);
				System.out.println(number);
			} else {
				number = 0;
			}
			return number;
		} catch (SQLException | FailedConnectionException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountByIBAN(java.lang.String)
	 */
	@Override
	public Account getAccountByIBAN(String IBAN) {
		Connection connection = null;
		Account account = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_IBAN);
			statement.setString(1, Account.cryptIban(IBAN));
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int id= resultSet.getInt("id");
				String name = resultSet.getString("name");
				int typeID = resultSet.getInt("accountTypes_id");
				String currency = resultSet.getString("currencies_type");
				double sum = resultSet.getDouble("sum");
				AccountType type = getAccountTypeByID(connection, typeID);
				CurrencyType currType = getCurrencyType(currency);
				account = new Account(name, type, currType, sum);
				account.setIbanFromDB(IBAN);
				account.setId(id);
				return account;
			}else{
				System.out.println("No such IBAN!");
			}

		} catch (FailedConnectionException e) {
			System.out.println("Cannot establish connection!");
			return account;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return account;

	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getCurrencyType(java.lang.String)
	 */
	@Override
	public CurrencyType getCurrencyType(String currency) {
		CurrencyType currType = null;
		CurrencyType[] currencies = CurrencyType.values();
		for (int index = 0; index < currencies.length; index++) {
			if (currencies[index].toString().equals(currency)) {
				currType = currencies[index];
			}
		}
		return currType;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountTypeByID(java.sql.Connection, int)
	 */
	@Override
	public AccountType getAccountTypeByID(Connection connection, int typeID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_TYPE_BY_ID);
		statement.setInt(1, typeID);
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			String type = result.getString(1);
			return getAccountType(type);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountType(java.lang.String)
	 */
	@Override
	public AccountType getAccountType(String type) {
		AccountType[] types = AccountType.values();
		for (int index = 0; index < types.length; index++) {
			if (type.equals(types[index].toString())) {
				return types[index];
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAllCurrencies()
	 */
	@Override
	public List<String> getAllCurrencies() {
		List<String> currencies = new ArrayList<String>();
		for (CurrencyType type : CurrencyType.values()) {
			currencies.add(type.toString());
		}
		return currencies;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAllAccountTypes()
	 */
	@Override
	public List<String> getAllAccountTypes() {
		List<String> accounts = new ArrayList<String>();
		for (AccountType type : AccountType.values()) {
			accounts.add(type.toString());
		}
		return accounts;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountByID(int, java.sql.Connection)
	 */
	@Override
	public Account getAccountByID(int accountId, Connection connection) {
		Account account = null;
		try {
			PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
			statement.setInt(1, accountId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double sum = result.getDouble("sum");
				String iban = Account.decryptIban(result.getString("IBAN"));
				CurrencyType currency = getCurrencyType(result.getString("currencies_type"));
				AccountType type = getAccountTypeByID(connection, result.getInt("accountTypes_id"));
				account = new Account(name, type, currency, sum);
				account.setIbanFromDB(iban);
				account.setId(id);
				return account;
			} else {
				throw new InvalidAccountInfoException("Invalid account id.");
			}
		} catch (SQLException | InvalidAccountInfoException e) {
			e.printStackTrace();
			return account;
		}

	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountIDByIBAN(java.lang.String)
	 */
	@Override
	public int getAccountIDByIBAN(String IBAN) {
		Connection connection;
		int id = 0;
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_ID);
			statement.setString(1, IBAN);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				id = result.getInt("id");
			} else {
				throw new InvalidAccountInfoException("Incorrect IBAN.");
			}
		} catch (SQLException | InvalidAccountInfoException | FailedConnectionException e) {
			e.printStackTrace();
			return id;
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAllAccounts()
	 */
	@Override
	public List<Integer> getAllAccounts() {
		List<Integer> list = new ArrayList<Integer>();
		Connection connection;
		try {
			connection = DBConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(SELECT_ALL_ACCOUNTS);
			while (result.next()) {
				int accountId = result.getInt("id");
				list.add(accountId);
			}
			return list;
		} catch (FailedConnectionException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see bg.piggybank.model.accounts.IAccount#getAccountIDByUserID(int)
	 */
	@Override
	public List<Integer> getAccountIDByUserID(int userID){
		List<Integer> accounts= new ArrayList<Integer>();
		Connection connection;
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_ID_BY_USER_ID);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				accounts.add(id);
			}
		} catch (SQLException | FailedConnectionException e) {
			e.printStackTrace();
			return accounts;
		}
		return accounts;
		
	}

}
