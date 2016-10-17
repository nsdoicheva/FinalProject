package bg.piggybank;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.configurations.Config;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.NotEnoughMoneyException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.transactions.TransactionDAO;
import bg.piggybank.model.user.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= Config.class)

public class AccountTest {
	
	@Autowired
	private AccountDAO accountDAO;
	
/*	@Test
	public void testSaveAccount() throws UserInfoException, AccountException, SQLException, FailedConnectionException, IncorrectContactInfoException{
		int id = accountDAO.registrateUserAccount(new User("Vanka", "IvannPetrov1245", "ivanchoto", "Udoma", "Sofia", "Bulgaria", "0894235746", "vanchou@mail.bg", "9801202836", "bulgarian"),
				new Account("smetka", AccountType.RAZPLASHTATELNA, CurrencyType.USD, 3000));
		assertTrue(id > 0);
	}
*/	
	
	@Test
	public void testDecreaseAmount() throws NotEnoughMoneyException {
		Account acc = new Account("Salary", AccountType.SPESTOVNA, CurrencyType.BGN, 700);
		acc.decreaseAmount(200);
		assertTrue(acc.getSum() == 500);
	}
	
	@Test
	public void testIncreaseAmount() {
		Account acc = new Account("Salary", AccountType.SPESTOVNA, CurrencyType.BGN, 700.50);
		acc.increaseAmount(300.50);
		assertTrue(acc.getSum() == 1001);
	}
	
	@Test
	public void testEncryption() {
		String randomtext = "BGBN90001562552";
		String crypted = Account.cryptIban(randomtext);
		String decrypted = Account.decryptIban(crypted);
		assertEquals(crypted, decrypted);
	}
	
/*	@Test
	public void testGetAllUsers() {
		Set<User> allUsers = new HashSet<User>();
		assertEquals(0, allUsers.size());
		allUsers = userDAO.getAllUsersFromDB();
		assertTrue(allUsers.size() > 0);
	}
*/
	


}
