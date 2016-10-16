package bg.piggybank.model.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.InvalidTransactionInfoException;
import bg.piggybank.model.exeptions.NotEnoughMoneyException;

@Component
public class TransactionDAO {
	@Autowired
	private AccountDAO accountDao;
	private final static String SELECT_ALL_TRANSACTIONS = "SELECT t.sum , u.username as sender, us.username as receiver, t.description "
			+ "FROM transactions t join accounts a on t.fromAccount_id = a.id join accounts ac on t.toAccount_id = ac.id "
			+ "JOIN users u ON u.id=a.users_id JOIN users us ON us.id=ac.users_id ; ";

	private final static String SELECT_ALL_USER_TRANSACTIONS = "SELECT t.sum , u.username as sender, us.username as receiver, t.description, t.fromAccount_id, t.toAccount_id "
			+ "FROM transactions t join accounts a on t.fromAccount_id = a.id join accounts ac on t.toAccount_id = ac.id "
			+ "JOIN users u ON u.id=a.users_id JOIN users us ON us.id=ac.users_id WHERE u.username= ? OR us.username=?; ";
	private final static String SELECT_ALL_ACCOUNT_TRANSACTIONS = "SELECT t.sum , u.username as sender, us.username as receiver, t.description, t.fromAccount_id, t.toAccount_id "
			+ "FROM transactions t join accounts a on t.fromAccount_id = a.id join accounts ac on t.toAccount_id = ac.id "
			+ "JOIN users u ON u.id=a.users_id JOIN users us ON us.id=ac.users_id WHERE t.fromAccount_id =? OR t.toAccount_id=?; ";
	private final static String INSERT_TRANSACTION = "INSERT INTO transactions VALUES (null, ?, ?, ?, ?, ?);";
	private final static String SELECT_ACCOUNT_ID = "select id from accounts where IBAN = ? ;";

	private static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<Transaction>());

	public int getAccountID(String iban, Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SELECT_ACCOUNT_ID);
		String ibanche = Account.cryptIban(iban);
		System.out.println(ibanche);
		ps.setString(1, ibanche);
		ResultSet resultSet = ps.executeQuery();
		resultSet.next();
		int accountID = resultSet.getInt(1);
		return accountID;
	}

	public boolean saveTransaction(Account from, Account to, double sum, String description)
			throws InvalidTransactionInfoException, NotEnoughMoneyException {
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);

			int fromAccountID = getAccountID(from.getIBAN(), connection);
			int toAccountID = getAccountID(to.getIBAN(), connection);
			if (fromAccountID != toAccountID) {
				PreparedStatement statement = DBConnection.getInstance().getConnection()
						.prepareStatement(INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp date = new java.sql.Timestamp(timeNow);

				statement.setTimestamp(1, date);
				statement.setDouble(2, sum);
				statement.setString(3, description);
				statement.setInt(4, fromAccountID);
				statement.setInt(5, toAccountID);
				int count = statement.executeUpdate();
				statement.close();

				double newSumFrom = 0;
				newSumFrom = from.decreaseAmount(sum);
				double newSumTo = to.increaseAmount(sum);
				PreparedStatement updateStatement = connection
						.prepareStatement("UPDATE accounts SET sum = ? where id = ?;");
				updateStatement.setDouble(1, newSumFrom);
				updateStatement.setInt(2, fromAccountID);
				updateStatement.executeUpdate();

				PreparedStatement updateStatement2 = connection
						.prepareStatement("UPDATE accounts SET sum = ? where id = ?;");
				updateStatement2.setDouble(1, newSumTo);
				updateStatement2.setInt(2, toAccountID);
				updateStatement2.executeUpdate();

				connection.commit();
				if (count > 0) {
					System.out.println("New transaction was made");

					return true;
				}
			} else {
				throw new InvalidTransactionInfoException("Can't make a transaction between only one account!");
			}

		} catch (SQLException | FailedConnectionException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Tuka da hvurlq nov exception");
			}

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public List<Transaction> listAllTransactions() {
		try {
			Statement statement = DBConnection.getInstance().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_TRANSACTIONS);

			while (resultSet.next()) {
				transactions.add(new Transaction(resultSet.getInt("sum"), resultSet.getString("sender"),
						resultSet.getString("receiver"), resultSet.getString("description")));
			}

		} catch (FailedConnectionException e) {
			System.out.println("Cannot make statement!");
			return transactions;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public List<Transaction> listAllMyTransacions(String username) {
		List<Transaction> myTransactions = new ArrayList<Transaction>();
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USER_TRANSACTIONS);
			ps.setString(1, username);
			ps.setString(2, username);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Transaction transaction = new Transaction(resultSet.getInt("sum"), resultSet.getString("sender"),
						resultSet.getString("receiver"), resultSet.getString("description"));
				Account from = accountDao.getAccountByID(resultSet.getInt("fromAccount_id"), connection);
				Account to = accountDao.getAccountByID(resultSet.getInt("toAccount_id"), connection);
				transaction.setFromIBAN(from.getIBAN());
				transaction.setToIBAN(to.getIBAN());
				myTransactions.add(transaction);
			}

		} catch (SQLException e) {
			System.out.println("Transaction problem");
			e.printStackTrace();
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return myTransactions;

	}

	public boolean isValidTransaction(Account from, Account to) {
		System.out.println(from.getCurrency().toString());
		System.out.println(to.getCurrency().toString());
		if (from.getCurrency().toString().equals(to.getCurrency().toString())) {
			return true;
		} else {
			return false;
		}
	}

	public List<Transaction> listAllTransactionsForAccount(String IBAN) {
		Account account = accountDao.getAccountByIBAN(IBAN);
		List<Transaction> myTransactions = new ArrayList<Transaction>();
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ACCOUNT_TRANSACTIONS);
			ps.setInt(1, account.getId());
			ps.setInt(2, account.getId());

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Transaction transaction = new Transaction(resultSet.getInt("sum"), resultSet.getString("sender"),
						resultSet.getString("receiver"), resultSet.getString("description"));
				Account from = accountDao.getAccountByID(resultSet.getInt("fromAccount_id"), connection);
				Account to = accountDao.getAccountByID(resultSet.getInt("toAccount_id"), connection);
				transaction.setFromIBAN(from.getIBAN());
				transaction.setToIBAN(to.getIBAN());
				myTransactions.add(transaction);
			}

		} catch (SQLException e) {
			System.out.println("Transaction problem.");
			e.printStackTrace();
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return myTransactions;

	}

}