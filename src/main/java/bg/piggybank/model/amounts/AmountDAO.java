package bg.piggybank.model.amounts;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.IAccountDAO;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.user.IUserDAO;
import bg.piggybank.model.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class AmountDAO implements IAmountDAO {
	private static final String SELECT_AMOUNTS = "SELECT id, date, money, account_id FROM amounts WHERE account_id=?;";

	@Autowired
	private IAccountDAO accountDao;
	@Autowired
	private IUserDAO userDao;

	private static volatile int count = 0;

	@Override
	public void startAmountTrackingAfterServerRestart() {
		count++;
		if (count <= 1) {
			for (int accountId : accountDao.getAllAccounts()) {
				Thread amountSaver = new Thread(new AmountSaver(accountId, accountDao));
				amountSaver.setDaemon(true);
				amountSaver.start();
			}
		} else {
			return;
		}
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.amounts.IAmountDAO#getAllMyAmounts(java.lang.String)
	 */
	@Override
	public List<Amount> getAllMyAmounts(String username) {
		Connection connection;
		List<Amount> amounts = new ArrayList<Amount>();
		try {
			connection = DBConnection.getInstance().getConnection();
			User user = userDao.getUserByUsername(username);
			List<Integer> accountIds = accountDao.getAccountIDByUserID(user.getId());
			for (int accountID : accountIds) {
				PreparedStatement statement = connection.prepareStatement(SELECT_AMOUNTS);
				statement.setInt(1, accountID);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					int id = result.getInt("id");
					Timestamp date = result.getTimestamp("date");
					double money = result.getDouble("money");
					int accountId = result.getInt("account_id");
					Account account = accountDao.getAccountByID(accountId, connection);
					String IBAN = account.getIBAN();
					System.out.println(IBAN);
					Amount amount = new Amount(id, date, money, IBAN);
					amounts.add(amount);
				}
			}
		} catch (FailedConnectionException | SQLException e) {
			e.printStackTrace();
			return amounts;
		}
		return amounts;
	}

	/* (non-Javadoc)
	 * @see bg.piggybank.model.amounts.IAmountDAO#getAllAmountsForAccount(java.lang.String)
	 */
	@Override
	public List<Amount> getAllAmountsForAccount(String IBAN) {
		Connection connection;
		List<Amount> amounts = new ArrayList<Amount>();
		try {
			connection = DBConnection.getInstance().getConnection();
			Account account = accountDao.getAccountByIBAN(IBAN);
			PreparedStatement statement = connection.prepareStatement(SELECT_AMOUNTS);
			statement.setInt(1, account.getId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				Timestamp date = result.getTimestamp("date");
				double money = result.getDouble("money");
				Amount amount = new Amount(id, date, money, IBAN);
				amounts.add(amount);
			}
		} catch (FailedConnectionException | SQLException e) {
			e.printStackTrace();
			return amounts;
		}
		return amounts;
	}
	
	@Override
	public List<Amount> getAmountForMonth(int month, String IBAN, int year){
		List<Amount> amounts= new ArrayList<Amount>();
		List<Amount> allAmountsForAccount= getAllAmountsForAccount(IBAN);
		for(Amount amount: allAmountsForAccount){
			Timestamp time= amount.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(time.getTime()));
			int amountDay = cal.get(Calendar.DAY_OF_MONTH);
			amount.setDay(amountDay);
			int amountMonth = cal.get(Calendar.MONTH);
			amount.setMonth(amountMonth);
			int amountYear = cal.get(Calendar.YEAR);
			amount.setYear(amountYear);
			if(amountMonth==month && amountYear==year){
				amounts.add(amount);
			}
		}
		return amounts;
	}
}
