package bg.piggybank.model.transactions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.InvalidTransactionInfoException;
import bg.piggybank.model.exeptions.NotEnoughMoneyException;

public interface ITransactionDAO {

	int getAccountID(String iban, Connection connection) throws SQLException;

	boolean saveTransaction(Account from, Account to, double sum, String description)
			throws InvalidTransactionInfoException, NotEnoughMoneyException;

	List<Transaction> listAllTransactions();

	List<Transaction> listAllMyTransacions(String username);

	boolean isValidTransaction(Account from, Account to);

	List<Transaction> listAllTransactionsForAccount(String IBAN);

}