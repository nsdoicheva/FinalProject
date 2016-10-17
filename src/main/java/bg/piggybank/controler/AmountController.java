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
import bg.piggybank.model.accounts.IAccountDAO;
import bg.piggybank.model.amounts.Amount;
import bg.piggybank.model.amounts.IAmountDAO;

@Controller
public class AmountController {
	@Autowired
	private IAmountDAO amountDao;
	@Autowired
	private IAccountDAO accountDao;

	@RequestMapping(value = "/amounts", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Account> accounts = new ArrayList<Account>();
			if (request.getSession(false) != null) {
				accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("myAccounts", accounts);
				return "amounts";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/amounts", method = RequestMethod.POST)
	public String getAmounts(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				String account = request.getParameter("fromIban");
				request.getSession(false).setAttribute("account", account);
				return showAmounts(request, response);
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/myAmounts", method = RequestMethod.GET)
	public String showAmounts(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Amount> amounts = new ArrayList<Amount>();
			List<Account> accounts = new ArrayList<Account>();
			if (request.getSession(false) != null) {
				accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("myAccounts", accounts);
				if (request.getParameter("fromIban").equals("Всички")) {
					amounts = amountDao.getAllMyAmounts((String) request.getSession(false).getAttribute("username"));
					request.setAttribute("myAmounts", amounts);
				} else {
					String account = request.getParameter("fromIban");
					System.out.println(account);
					amounts = amountDao.getAllAmountsForAccount(account);
					request.setAttribute("myAmounts", amounts);
				}
				return "myAmounts";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/myAmounts", method = RequestMethod.POST)
	public String getMyAmounts(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				String account = request.getParameter("fromIban");
				request.getSession(false).setAttribute("account", account);
				return showAmounts(request, response);
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/monthAmounts", method = RequestMethod.GET)
	public String showMonth(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Account> accounts = new ArrayList<Account>();
			if (request.getSession(false) != null) {
				accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("myAccounts", accounts);
				return "monthAmounts";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/monthAmounts", method = RequestMethod.POST)
	public String getMonth(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				String account = request.getParameter("fromIban");
				request.getSession(false).setAttribute("account", account);
				int month = Integer.parseInt(request.getParameter("month"));
				request.getSession(false).setAttribute("month", month);
				int year = Integer.parseInt(request.getParameter("year"));
				request.getSession(false).setAttribute("year", year);
				return showMonthAmounts(request, response);
			} else {
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "errorPage";
		}
	}

	@RequestMapping(value = "/myMonthAmounts", method = RequestMethod.GET)
	public String showMonthAmounts(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Amount> amounts = new ArrayList<Amount>();
			List<Account> accounts = new ArrayList<Account>();
			if (request.getSession(false) != null) {
				accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("myAccounts", accounts);
				String iban = request.getParameter("fromIban");
				int month = Integer.parseInt(request.getParameter("month"));
				int year = Integer.parseInt(request.getParameter("year"));
				amounts = amountDao.getAmountForMonth(month, iban, year);
				request.setAttribute("myAmounts", amounts);
				return "myMonthAmounts";
			} else {
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "errorPage";
		}
	}

	@RequestMapping(value = "/myMonthAmounts", method = RequestMethod.POST)
	public String getMonthAmounts(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				String account = request.getParameter("fromIban");
				request.getSession(false).setAttribute("account", account);
				int month = Integer.parseInt(request.getParameter("month"));
				request.getSession(false).setAttribute("month", month);
				int year = Integer.parseInt(request.getParameter("year"));
				request.getSession(false).setAttribute("year", year);
				return showMonthAmounts(request, response);
			} else {
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "errorPage";
		}
	}
}
