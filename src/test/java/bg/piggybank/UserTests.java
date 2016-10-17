package bg.piggybank;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.AccountDAO;
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
public class UserTests {
	
	@Autowired
	private UserDAO userDAO;
	
	/*@Test
	public void testSaveUser() throws IncorrectContactInfoException{
		int id = userDAO.saveUser(new User("Joroto", "JoroGolemec1234", "jorko", "ulKazan", "Sofia", "Bulgaria", "0891115689", "goshko@mail.bg", "8608242836", "siganin"));
		assertNotEquals(id, 0);	
	}*/
	
	@Test
	public void testLoginUser() {
		boolean logged = userDAO.loginUser("jorko", "JoroGolemec1234");
		assertTrue(logged);
	}

	@Test
	public void testGetAllUsers() {
		Set<User> allUsers = new HashSet<User>();
		assertEquals(0, allUsers.size());
		allUsers = userDAO.getAllUsersFromDB();
		assertTrue(allUsers.size() > 0);
	}

	
	@Test
	public void testCountUsers() {
		int allUsers = userDAO.countOfUsers();
		assertNotEquals(allUsers, 0);		
	}
	

	@Test
	public void test() {
		Map<String, String> allCountries = new HashMap<String, String>();
		assertEquals(0, allCountries.size());
		allCountries = userDAO.getCurrentUserInfo("ivo");
		assertTrue(allCountries.size() > 0);
	}
	
  }
