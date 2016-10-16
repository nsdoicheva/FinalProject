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
import bg.piggybank.model.amounts.Amount;
import bg.piggybank.model.amounts.AmountDAO;

@Controller
public class AmountController {
	@Autowired
	private AmountDAO amountDao;
	@Autowired
	private AccountDAO accountDao;

	@RequestMapping(value = "/amounts", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			return "amounts";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/amounts", method = RequestMethod.POST)
	public String getAmounts(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showAmounts(request, response);
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/myAmounts", method = RequestMethod.GET)
	public String showAmounts(HttpServletRequest request, HttpServletResponse response) {
		List<Amount> amounts = new ArrayList<Amount>();
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			if (request.getParameter("fromIban").equals("Всички")) {
				amounts = amountDao
						.getAllMyAmounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("myAmounts", amounts);
			} else {
				String account= request.getParameter("fromIban");
				System.out.println(account);
				amounts = amountDao.getAllAmountsForAccount(account);
				request.setAttribute("myAmounts", amounts);
			}
			return "myAmounts";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/myAmounts", method = RequestMethod.POST)
	public String getMyAmounts(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showAmounts(request, response);
		} else {
			return "index";
		}
	}
}
