package bg.piggybank.controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.InvalidTransactionInfoException;
import bg.piggybank.model.exeptions.NotEnoughMoneyException;
import bg.piggybank.model.transactions.Transaction;
import bg.piggybank.model.transactions.TransactionDAO;

@Controller
public class TransactionController {

	@Autowired
	private TransactionDAO transactionDao;

	@Autowired
	private AccountDAO accountDao;

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String showTransactions(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			return "transactions";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public String getAmounts(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showTransaction(request, response);
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/myTransactions", method = RequestMethod.GET)
	public String showTransaction(HttpServletRequest request, HttpServletResponse response) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			if (request.getParameter("fromIban").equals("Всички")) {
				transactions = transactionDao
						.listAllMyTransacions((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("transactions", transactions);
			} else {
				String account = (String) request.getSession(false).getAttribute("account");
				System.out.println(account);
				transactions = transactionDao.listAllTransactionsForAccount(account);
				request.setAttribute("transactions", transactions);
			}
			return "myTransactions";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/myTransactions", method = RequestMethod.POST)
	public String getMyAmounts(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showTransaction(request, response);
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/makeTransaction", method = RequestMethod.GET)
	public String showTransactionForm(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		if (request.getSession(false) != null) {
			request.setAttribute("myAccounts",
					accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username")));
			return "makeTransaction";
		}
		return "index";
	}

	@RequestMapping(value = "/makeTransaction", method = RequestMethod.POST)
	public String show(HttpServletRequest request, HttpServletResponse response) throws IncorrectContactInfoException {
		String fromIban = request.getParameter("fromIban");
		String toIban = request.getParameter("toIban");
		double sum = Double.parseDouble(request.getParameter("sum"));
		String description = request.getParameter("description");
		Account from = accountDao.getAccountByIBAN(fromIban);
		Account to = accountDao.getAccountByIBAN(toIban);
		if(sum<=0){
			request.setAttribute("errorMessage", "Сумата за всяка трансакция трябва да е по-голяма от 0.");
			return showTransactionForm(request, response);
		}
		if (from != null && to != null) {
			if (transactionDao.isValidTransaction(from, to)) {
				if (!from.equals(to)) {
					try {
						try {
							transactionDao.saveTransaction(from, to, sum, description);
						} catch (NotEnoughMoneyException e) {
							request.setAttribute("errorMessage",
									"Недостатъчна наличност за изпълнение на трансакцията.");
							return showTransactionForm(request, response);
						}
					} catch (InvalidTransactionInfoException e) {
						request.setAttribute("errorMessage", "Въведената сметка не съществува.");
						return showTransactionForm(request, response);
					}
					request.setAttribute("successMessage", "Трансакцията беше извършена успешно.");
					return showTransactionForm(request, response);
				} else {
					request.setAttribute("errorMessage", "Трансакция между една и съща сметка не е възможна.");
					return showTransactionForm(request, response);
				}
			} else {
				request.setAttribute("errorMessage", "Трансакция между сметки от различна валута не е възможна.");
				return showTransactionForm(request, response);
			}
		} else {
			request.setAttribute("errorMessage", "Въведената сметка не съществува.");
			return showTransactionForm(request, response);
		}
	}

}