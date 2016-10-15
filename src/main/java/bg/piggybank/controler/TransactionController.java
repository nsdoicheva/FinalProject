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
	private TransactionDAO dao;

	@Autowired
	private AccountDAO accountDao;

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String showTransactions(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = dao.listAllMyTransacions((String) request.getSession(false).getAttribute("username"));
		request.setAttribute("transactions", transactions);
		return "transactions";
	}

	@RequestMapping(value = "/makeTransaction", method = RequestMethod.GET)
	public String showTransactionForm(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		request.setAttribute("myAccounts",
				accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username")));
		return "makeTransaction";
	}

	@RequestMapping(value = "/makeTransaction", method = RequestMethod.POST)
	public String show(HttpServletRequest request, HttpServletResponse response) throws IncorrectContactInfoException {
		String fromIban = request.getParameter("fromIban");
		String toIban = request.getParameter("toIban");
		double sum = Double.parseDouble(request.getParameter("sum"));
		String description = request.getParameter("description");
		Account from = accountDao.getAccountByIBAN(fromIban);
		Account to = accountDao.getAccountByIBAN(toIban);
		if (from != null && to != null) {
			if (dao.isValidTransaction(from, to)) {
				try {
					try {
						dao.saveTransaction(from, to, sum, description);
					} catch (NotEnoughMoneyException e) {
						request.setAttribute("errorMessage", "Недостатъчна наличност за изпълнение на трансакцията.");
						return showTransactionForm(request, response);
					}
				} catch (InvalidTransactionInfoException e) {
					request.setAttribute("errorMessage", "Въведената сметка не съществува.");
					return showTransactionForm(request, response);
				}
				request.setAttribute("successMessage", "Трансакцията беше извършена успешно.");
				return showTransactionForm(request, response);
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