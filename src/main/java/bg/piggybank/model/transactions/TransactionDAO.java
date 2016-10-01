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



import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.NotEnoughMoneyException;
import bg.piggybank.model.user.User;

public class TransactionDAO {
	private final static String SELECT_ALL_TRANSACTIONS = "SELECT t.sum , u.username as sender, us.username as receiver, t.description "
			+ "FROM transactions t join users u on t.fromAccount_id = u.id join users us on t.toAccount_id = us.id "
			+ " UNION SELECT  t.sum ,  u.username as sender, us.username as receiver, t.description "
			+ "FROM transactions t join users u on t.toAccount_id= u.id join users us on t.fromAccount_id = us.id; ";
	
	private final static String SELECT_ALL_USER_TRANSACTIONS = "SELECT t.sum , u.username as sender, us.username as receiver, t.description "
			+ "FROM transactions t join users u on t.fromAccount_id = u.id join users us on t.toAccount_id = us.id "
			+ " where us.username = ?  UNION SELECT  t.sum ,  u.username as sender, us.username as receiver, t.description "
			+ "FROM transactions t join users u on t.toAccount_id= u.id join users us on t.fromAccount_id = us.id where u.username = ?;  ";
	private final static String INSERT_TRANSACTION = "INSERT INTO transactions VALUES (null, ?, ?, ?, ?, ?);";
	private final static String SELECT_ACCOUNT_ID = "select id from accounts where IBAN = ? ;";
	
	
	private static TransactionDAO instance;
	private static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<Transaction>());
	
	private TransactionDAO() {	}

	public synchronized static TransactionDAO getInstance() {
		if (instance == null) {
			instance = new TransactionDAO();
		}
		return instance;
	}

	public int getAccountID(String iban, Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SELECT_ACCOUNT_ID);
		ps.setString(1, Account.cryptIban(iban));
		ResultSet resultSet = ps.executeQuery();
		resultSet.next();
		int accountID = resultSet.getInt("id");
		return accountID;
	}
	
	public boolean saveTransaction(Account from, Account to, double sum, String description) {
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
		    
			int fromAccountID = getAccountID(from.decryptIban(from.getIBAN()), connection);
			int toAccountID = getAccountID(to.decryptIban(to.getIBAN()), connection);
        
			PreparedStatement statement = DBConnection.getInstance().getConnection()
					.prepareStatement(INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
			long timeNow = Calendar.getInstance().getTimeInMillis();
			java.sql.Timestamp date = new java.sql.Timestamp(timeNow);
			
			statement.setTimestamp(1, date);
			statement.setDouble(2, sum);
			statement.setString(3, description );			
			statement.setInt(4, fromAccountID);		
			statement.setInt(5, toAccountID);
			int count = statement.executeUpdate();
			statement.close();
			
			double newSumFrom = 0;
			try {
			 newSumFrom = from.decreaseAmount(sum);
			} catch(NotEnoughMoneyException e) {
				e.printStackTrace();
			}
			double newSumTo = to.increaseAmount(sum);
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE accounts SET sum = ? where id = ?;");
			updateStatement.setDouble(1, newSumFrom);
			updateStatement.setInt(2, fromAccountID);
			int countUpdatesInFrom = updateStatement.executeUpdate();
			
			PreparedStatement updateStatement2 = connection.prepareStatement("UPDATE accounts SET sum = ? where id = ?;");
			updateStatement2.setDouble(1, newSumTo);
			updateStatement2.setInt(2, toAccountID);
			int countUpdatesInTo=updateStatement2.executeUpdate();

			connection.commit();
			connection.setAutoCommit(true);
			if (count > 0) {
				System.out.println("New transaction was made");
				
				return true;
			}      
            
		} catch (SQLException | FailedConnectionException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Tuka da hvurlq nov exception");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactions;
	}
	
	
	public List<Transaction> listAllMyTransacions(User user) {
		List<Transaction> myTransactions = new ArrayList<Transaction>();
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USER_TRANSACTIONS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getUsername());

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				myTransactions.add(new Transaction(resultSet.getInt("sum"), resultSet.getString("sender"),
						resultSet.getString("receiver"), resultSet.getString("description")));
			}

		} catch (SQLException e) {
			System.out.println("User cannot be logged right now.");
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return myTransactions;

	}

}
	
	
