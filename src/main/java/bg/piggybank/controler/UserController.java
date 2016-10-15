package bg.piggybank.controler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.SendEmails;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.IncorrectContactInfoException;
import bg.piggybank.model.user.User;
import bg.piggybank.model.user.UserDAO;


@Controller
public class UserController {

	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login1(HttpServletRequest request, HttpServletResponse response) throws IncorrectContactInfoException {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws IncorrectContactInfoException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user=userDao.getUserByUsername(username);
		if (userDao.loginUser(username, password)) {
			HttpSession session= request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("id", user.getId());
			session.setAttribute("name", user.getName());
			return "index";
		} else {
			request.setAttribute("errorMessage", "Грешно потребителско име и/или парола.");
			return "login";
			}
		}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("successMessage", "Излязохте успешно.");
		request.getSession(false).invalidate();
		return "login";
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showFrom(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request, HttpServletResponse response)
			throws IncorrectContactInfoException {
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
		Thread ch = new Thread(new SendEmails());
		ch.start();
		if(userDao.saveUser(user) > 0 ) {	
			request.setAttribute("successMessage", "Успешна регистрация.");
		return "index";
		} else {
			request.setAttribute("errorMessage", "Неуспешна регистрация.");
			return "register";
		}
	}
}
