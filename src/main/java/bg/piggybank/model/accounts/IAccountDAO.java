package bg.piggybank.model.accounts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.InvalidAccountException;
import bg.piggybank.model.exeptions.InvalidAccountInfoException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.user.User;

public interface IAccountDAO {

	int registrateUserAccount(User user, Account account)
			throws UserInfoException, AccountException, InvalidAccountInfoException, InvalidAccountException;

	List<Account> getAllMyAccounts(String username);

	Account getAccountByIBAN(String IBAN);

	CurrencyType getCurrencyType(String currency);

	AccountType getAccountTypeByID(Connection connection, int typeID) throws SQLException;

	AccountType getAccountType(String type);

	List<String> getAllCurrencies();

	List<String> getAllAccountTypes();

	Account getAccountByID(int accountId, Connection connection);

	int getAccountIDByIBAN(String IBAN);

	List<Integer> getAllAccounts();

	List<Integer> getAccountIDByUserID(int userID);

}