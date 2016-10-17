package bg.piggybank;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.configurations.Config;
import bg.piggybank.model.transactions.Transaction;
import bg.piggybank.model.transactions.TransactionDAO;
import bg.piggybank.model.user.User;
import bg.piggybank.model.user.UserDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= Config.class)
public class TransactionTest {
	
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	
	@Test
	public void testGetAllTransactions() {
		List<Transaction> transactions = new LinkedList<Transaction>();
		assertEquals(0, transactions.size());
		transactions = transactionDAO.listAllTransactions();
		assertTrue(transactions.size() > 0);
	}


	
  }