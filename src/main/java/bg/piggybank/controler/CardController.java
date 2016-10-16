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
import bg.piggybank.model.cards.Card;
import bg.piggybank.model.cards.CardDAO;
import bg.piggybank.model.cards.CardTypes;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.CardException;

@Controller
public class CardController {
	@Autowired
	private AccountDAO accountDao;
	@Autowired
	private CardDAO cardDao;

	@RequestMapping(value = "/makeCard", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		List<Account> accounts = new ArrayList<Account>();
		List<String> types = new ArrayList<String>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			types = cardDao.getAllCardTypes();
			request.setAttribute("myAccounts", accounts);
			request.setAttribute("cardTypes", types);
			return "makeCard";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/makeCard", method = RequestMethod.POST)
	public String getCards(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) != null) {
			String iban = request.getParameter("fromIban");
			CardTypes type = cardDao.getCardType((String) request.getParameter("type"));
			Account account = accountDao.getAccountByIBAN(iban);
			try {
				cardDao.createCardForAccount(account, type);
				request.setAttribute("successMessage", "Картата беше създадена успешно.");
			} catch (AccountException | CardException e) {
				request.setAttribute("errorMessage", "Тази карта вече съществува.");
				e.printStackTrace();
			}
			return show(request, response);
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/myCards", method = RequestMethod.GET)
	public String showMyCards(HttpServletRequest request, HttpServletResponse response) {
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			return "myCards";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/myCards", method = RequestMethod.POST)
	public String getMyCards(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showCards(request, response);
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/listCards", method = RequestMethod.GET)
	public String showCards(HttpServletRequest request, HttpServletResponse response) {
		List<Card> cards = new ArrayList<Card>();
		List<Account> accounts = new ArrayList<Account>();
		if (request.getSession(false) != null) {
			accounts = accountDao.getAllMyAccounts((String) request.getSession(false).getAttribute("username"));
			request.setAttribute("myAccounts", accounts);
			if (request.getParameter("fromIban").equals("Всички")) {
				try {
					cards = cardDao.getAllCardsForUser((String) request.getSession(false).getAttribute("username"));
				} catch (CardException | AccountException e) {
					request.setAttribute("errorMessage", "Не могат да бъдат показани.");
					e.printStackTrace();
				}
				request.setAttribute("cards", cards);
			} else {
				String iban = request.getParameter("fromIban");
				Account account = accountDao.getAccountByIBAN(iban);
				try {
					cards = cardDao.getAllCardsForAccount(account);
				} catch (CardException | AccountException e) {
					request.setAttribute("errorMessage", "Не могат да бъдат показани.");
					e.printStackTrace();
				}
				request.setAttribute("cards", cards);
			}
			return "listCards";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/listCards", method = RequestMethod.POST)
	public String getMyAmounts(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) != null) {
			String account = request.getParameter("fromIban");
			request.getSession(false).setAttribute("account", account);
			return showCards(request, response);
		} else {
			return "index";
		}
	}

}
