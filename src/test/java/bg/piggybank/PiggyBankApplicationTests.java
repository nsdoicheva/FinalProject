package bg.piggybank;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.amounts.AmountDAO;
import bg.piggybank.model.amounts.AmountSaver;
import bg.piggybank.model.configurations.Config;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.transactions.Transaction;
import bg.piggybank.model.transactions.TransactionDAO;
import bg.piggybank.model.user.User;
import bg.piggybank.model.user.UserDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= Config.class)
public class PiggyBankApplicationTests {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private TransactionDAO transactionDAO;
	@Autowired
	private AmountDAO amountDAO;
	
	/*@Test
	public void testUserDao() throws IncorrectContactInfoException{
		userDAO.saveUser(new User("Ivancho", "Ivan123", "ivancho", "doma", "Sofiq", "Bulgaria", "0894235689", "ivancho@mail.bg", "9608222836", "siganin"));
	}*/
	
	/*@Test
	public void testAccountDao() throws UserInfoException, AccountException, SQLException, FailedConnectionException, IncorrectContactInfoException{
		User user = new User("Ivancho", "Ivan123", "ivan123", "doma", "Sofiq", "Bulgaria", "0894835689", "ivancho8@mail.bg", "9308222836", "siganin");
		userDAO.saveUser(user);
		Account account= new Account("smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000);
		accountDAO.registrateUserAccount(user , account);
		System.out.println(AccountDAO.getNumberOfAccounts());
		System.out.println(account.getIBAN());
	}*/
	/*
	@Test
	public void testTransactionDao() throws IncorrectContactInfoException, UserInfoException, AccountException, SQLException, FailedConnectionException{
		DataToTable.fillCurrencies();
		DataToTable.fillAccountTypes();
		User vancho=new User("Ivancho", "Ivan123", "ivancho", "doma", "Sofiq", "Bulgaria", "0894235689", "ivancho@mail.bg", "9608222836", "siganin");
		userDAO.saveUser(vancho);
		Account from= new Account("smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000);
		accountDAO.registrateUserAccount(vancho, from);
		Account to= new Account("vtora smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000);
		accountDAO.registrateUserAccount(vancho, to);
		transactionDAO.saveTransaction(from, to, 600, "na ti zaplata");
		for(Transaction transaction: transactionDAO.listAllTransactions()){
			System.out.println(transaction);
		}
	}*/
	
	/*@Test
	public void testLoginUser(){
		userDAO.loginUser("nikolina", "Nikolina95");
		System.out.println(userDAO.getUserByUsername("nikolina"));
	}*/
	/*
	@Test
	public void testIBANCriptation(){
		String iban= "BG02BNBG45452011000067";
		String cripted= Account.cryptIban(iban);
		System.out.println(Account.cryptIban(iban));
		System.out.println(cripted);
		String decripted= Account.decryptIban(cripted);
		System.out.println(decripted);
	}*/
	
	/*@Test
	public void testGetAccountTypes(){
		System.out.println(accountDAO.getAllAccountTypes());
	}*/
	
	/*@Test
	public void testAmountSaver(){
		Thread amountSaver= new Thread(new AmountSaver(1, accountDAO));
		//amountSaver.start();
		amountSaver.run();
	}*/
	
	@Test
	public void testSaverAfterServerStop(){
		amountDAO.startAmountTrackingAfterServerRestart();
	}
  }
