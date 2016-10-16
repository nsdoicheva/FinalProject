/*package bg.piggybank.model;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.configurations.Config;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.transactions.Transaction;
import bg.piggybank.model.transactions.TransactionDAO;
import bg.piggybank.model.user.User;
import bg.piggybank.model.user.UserDAO;


@ContextConfiguration(classes=Config.class)
public class Demo {
	
	@Autowired
	private static UserDAO userDAO;
	 
	public static void main(String[] args)
			throws UserInfoException, AccountException, SQLException, FailedConnectionException, IncorrectContactInfoException {
		User vancho;
		//Account vanchov;
		//try {
			vancho = new User("Vancho", "nQkva12k3", "vanchok123", "adrekds", "Sofdkia", "Budklgaria", "0894315131",
					"dadsd@mail.bg", "9208232125", "bugarin");
			vancho.setId(userDAO.saveUser(vancho));
			System.out.println(vancho.getId());
			DataToTable.fillCurrencies();
			DataToTable.fillAccountTypes();
			/*vanchov = new Account("VanchoviqAccout", "BG80BNBG96611020345688", AccountType.KREDIT, CurrencyType.BGN,
					25.6);
			AccountDAO.registrateUserAccount(vancho, vanchov);
			System.out.println(vanchov.getId());
		} catch (IncorrectContactInfoException e) {
			System.out.println("greshen input");
			e.printStackTrace();
		}
*/		
			
		/*
		User vancho = new User("Pesho", "Pesho123", "pesheto95", "blablaaa", "Montana", "Bulgaria", "0894515112",
				"peshencetoo@mail.bg", "9108232227", "bugarin");
		User gosho = new User("Gosho", "Gosho123", "gosheto95", "zdrkp", "Hannah Montana", "Bulgaria", "0894515133",
				"goshencetoo@mail.bg", "9108232237", "bugarin");
		
		User joro = new User("Joro", "Joro123", "joreto95", "zdrhikp", "Montana", "Russia", "0894514133",
				"jorencetoo@mail.bg", "9208232237", "maika rusiq");
		
		userDAO.saveUser(vancho);
		userDAO.saveUser(gosho);
		//UserDAO.getInstance().saveUser(joro);
		DataToTable.fillCurrencies();
		DataToTable.fillAccountTypes();
		
		System.out.println(TransactionDAO.getInstance().listAllTransactions());
		System.out.println(TransactionDAO.getInstance().listAllMyTransacions(vancho));
		
		Account from = new Account("VanchoviqAccout", "A", AccountType.KREDIT, CurrencyType.BGN,
				25.6);
		AccountDAO.registrateUserAccount(vancho, from);
		
		Account to = new Account("Goshovec", "A", AccountType.KREDIT, CurrencyType.BGN,
				200);
		AccountDAO.registrateUserAccount(gosho, to);
		System.out.println(TransactionDAO.getInstance().saveTransaction(from, to, 5, "se taq"));
		System.out.println(TransactionDAO.getInstance().saveTransaction(to, from, 25, "poveche mi e se taq"));
		for(Transaction transaction: TransactionDAO.getInstance().listAllTransactions()){
			System.out.println("Vsichki: " +transaction);
		}
		for(Transaction transaction: TransactionDAO.getInstance().listAllMyTransacions(vancho)){
			System.out.println("Na Vancho: "+transaction);
		}*//*
		for(User user: userDAO.getAllUsersFromDB()){
			System.out.println(user);
		}
			
	}
}

*/
