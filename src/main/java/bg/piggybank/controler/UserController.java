package bg.piggybank.controler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.SendEmails;
import bg.piggybank.model.amounts.IAmountDAO;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.user.IUserDAO;
import bg.piggybank.model.user.User;

@Controller
public class UserController {

	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IAmountDAO amountDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexRestart(HttpServletRequest request, HttpServletResponse response) {
		try {
			amountDao.startAmountTrackingAfterServerRestart();
			return "index";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		try {
			return "index";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/toDoList", method = RequestMethod.GET)
	public String toDoList(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				return "toDoList";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login1(HttpServletRequest request, HttpServletResponse response) {
		try {
			int count = userDao.countOfUsers();
			request.setAttribute("count", count);
			return "login";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userDao.getUserByUsername(username);
			if (userDao.loginUser(username, password)) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(60 * 5);
				session.setAttribute("username", username);
				session.setAttribute("id", user.getId());
				session.setAttribute("name", user.getName());
				session.setAttribute("email", user.getEmail());
				return "index";
			} else {
				int count = userDao.countOfUsers();
				request.setAttribute("count", count);
				request.setAttribute("errorMessage", "Грешно потребителско име и/или парола.");
				return "login";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("successMessage", "Излязохте успешно.");
			if (request.getSession(false) != null) {
				request.getSession(false).invalidate();
				int count = userDao.countOfUsers();
				request.setAttribute("count", count);
				return "login";
			} else {
				return "login";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String loginAfterLogout(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userDao.getUserByUsername(username);
			if (userDao.loginUser(username, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("id", user.getId());
				session.setAttribute("name", user.getName());
				return "index";
			} else {
				int count = userDao.countOfUsers();
				request.setAttribute("count", count);
				request.setAttribute("errorMessage", "Грешно потребителско име и/или парола.");
				return "login";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showFrom(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String country = request.getParameter("country");
			String egn = request.getParameter("egn");
			String citizen = request.getParameter("citizen");
			User user = new User(name, password, username, address, city, country, telephone, email, egn, citizen);
			if (userDao.saveUser(user) > 0) {
				SendEmails mailingSystem = new SendEmails();
				mailingSystem.setReceiver(username);
				Thread checker = new Thread(mailingSystem);
				checker.start();
				return login(request, response);
			} else {
				return "register";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
	public String showInfo(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		try {
			if (request.getSession(false) != null) {
				Map<String, String> userInfo = userDao
						.getCurrentUserInfo((String) request.getSession(false).getAttribute("username"));
				request.setAttribute("username", userInfo.get("username"));
				request.setAttribute("name", userInfo.get("name"));
				request.setAttribute("egn", userInfo.get("egn"));
				request.setAttribute("email", userInfo.get("email"));
				request.setAttribute("phoneNum", userInfo.get("phoneNum"));
				request.setAttribute("citizenship", userInfo.get("citizenship"));
				request.setAttribute("street", userInfo.get("street"));
				request.setAttribute("city", userInfo.get("city"));
				request.setAttribute("country", userInfo.get("country"));
				return "personalInfo";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletRequest response) throws IncorrectContactInfoException {
		return "errorPage";
	}
}
