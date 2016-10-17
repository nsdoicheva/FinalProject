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
import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.accounts.IAccountDAO;
import bg.piggybank.model.amounts.AmountSaver;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.exeptions.InvalidAccountException;
import bg.piggybank.model.exeptions.InvalidAccountInfoException;
import bg.piggybank.model.exeptions.UserInfoException;
import bg.piggybank.model.user.IUserDAO;
import bg.piggybank.model.user.User;

@Controller
public class AccountController {

	@Autowired
	private IAccountDAO accountDao;
	@Autowired
	private IUserDAO userDao;

	@RequestMapping(value = "/myAccounts", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Account> accounts = new ArrayList<Account>();
			if (request.getSession(false) != null) {
				accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("accounts", accounts);
				return "myAccounts";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/makeAccount", method = RequestMethod.GET)
	public String create(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				request.setAttribute("currencies", accountDao.getAllCurrencies());
				request.setAttribute("accounts", accountDao.getAllAccountTypes());
				return "makeAccount";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/makeAccount", method = RequestMethod.POST)
	public String createAccount(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		try {
			String username = (String) request.getSession(false).getAttribute("username");
			User user = userDao.getUserByUsername(username);
			String nameOfAccount = request.getParameter("name");
			CurrencyType currency = accountDao.getCurrencyType(request.getParameter("currency"));
			AccountType type = accountDao.getAccountType(request.getParameter("type"));
			double sum = Double.parseDouble(request.getParameter("sum"));
			if (sum <= 0) {
				request.setAttribute("errorMessage", "Сумата в сметката не може да е 0 или по-малко.");
				return create(request, response);
			}
			Account account = new Account(nameOfAccount, type, currency, sum);
			try {
				accountDao.registrateUserAccount(user, account);
				request.setAttribute("successMessage", "Сметката е създадена успешно.");
				int accountId = accountDao.getAccountIDByIBAN(Account.cryptIban(account.getIBAN()));
				Thread amountSaver = new Thread(new AmountSaver(accountId, accountDao));
				amountSaver.setDaemon(true);
				amountSaver.start();
				return show(request, response);
			} catch (InvalidAccountInfoException e) {
				request.setAttribute("errorMessage", "Невалиден тип на сметката");
				return create(request, response);
			} catch (InvalidAccountException e) {
				request.setAttribute("errorMessage", "Невалидна информация");
				return create(request, response);
			} catch (UserInfoException e) {
				return create(request, response);
			} catch (AccountException e) {
				return create(request, response);
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}
}
