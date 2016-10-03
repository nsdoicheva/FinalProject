package bg.piggybank;

import java.sql.SQLException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.DataToTable;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.configurations.DAOConfig;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.transactions.Transaction;
import bg.piggybank.model.transactions.TransactionDAO;
import bg.piggybank.model.user.User;
import bg.piggybank.model.user.UserDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= DAOConfig.class)
public class PiggyBankApplicationTests {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private TransactionDAO transactionDAO;

	/*@Test
	public void testUserDao() throws IncorrectContactInfoException{
		userDAO.saveUser(new User("Ivancho", "Ivan123", "ivancho", "doma", "Sofiq", "Bulgaria", "0894235689", "ivancho@mail.bg", "9608222836", "siganin"));
	}
	
	@Test
	public void testAccountDao() throws UserInfoException, AccountException, SQLException, FailedConnectionException, IncorrectContactInfoException{
		accountDAO.registrateUserAccount(new User("Ivancho", "Ivan123", "ivancho", "doma", "Sofiq", "Bulgaria", "0894235689", "ivancho@mail.bg", "9608222836", "siganin"), new Account("smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000));
		System.out.println(accountDAO.getNumberOfAccounts());
	}*/
	
	@Test
	public void testTransactionDao() throws IncorrectContactInfoException, UserInfoException, AccountException, SQLException, FailedConnectionException{
		/*DataToTable.fillCurrencies();
		DataToTable.fillAccountTypes();
		User vancho=new User("Ivancho", "Ivan123", "ivancho", "doma", "Sofiq", "Bulgaria", "0894235689", "ivancho@mail.bg", "9608222836", "siganin");
		userDAO.saveUser(vancho);
		Account from= new Account("smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000);
		accountDAO.registrateUserAccount(vancho, from);
		Account to= new Account("vtora smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000);
		accountDAO.registrateUserAccount(vancho, to);
		transactionDAO.saveTransaction(from, to, 600, "na ti zaplata");*/
		for(Transaction transaction: transactionDAO.listAllTransactions()){
			System.out.println(transaction);
		}
	}

}
