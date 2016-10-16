package bg.piggybank.model.amounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;


import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.InvalidAccountException;

public class AmountSaver implements Runnable {

	private static final String INSERT_INTO_AMOUNTS = "INSERT INTO amounts VALUES(null, ?, ?, ?);";
	private int accountId;
	private AccountDAO accountDao;

	public AmountSaver(int accountId, AccountDAO accountDao) {
		if (accountId > 0) {
			this.accountId = accountId;
		} else {
			try {
				throw new InvalidAccountException("Invalid account id");
			} catch (InvalidAccountException e) {
				e.printStackTrace();
				return;
			}
		}
		if(accountDao!=null){
			this.accountDao=accountDao;
		}
	}

	@Override
	public void run() {
		while (true) {
			Connection connection;
			System.out.println(accountDao);
			try {
				connection = DBConnection.getInstance().getConnection();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					return;
				}
				long timeNow = Calendar.getInstance().getTimeInMillis();
				java.sql.Timestamp date = new java.sql.Timestamp(timeNow);
				Account account=accountDao.getAccountByID(this.accountId, connection);
				if(account!=null){
				PreparedStatement statement= connection.prepareStatement(INSERT_INTO_AMOUNTS);
				statement.setTimestamp(1, date);
				statement.setDouble(2, account.getSum());
				statement.setInt(3, account.getId());
				statement.executeUpdate();
				statement.close();
				}else{
					return;
				}
			} catch (FailedConnectionException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
