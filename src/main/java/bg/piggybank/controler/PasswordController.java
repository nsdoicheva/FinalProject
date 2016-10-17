package bg.piggybank.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.SendNewPasswordEmail;
import bg.piggybank.model.user.IUserDAO;

@Controller
public class PasswordController {
	@Autowired
	private IUserDAO userDao;

	@RequestMapping(value = "/passwordForgotten", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) == null) {
				return "passwordForgotten";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/passwordForgotten", method = RequestMethod.POST)
	public String getPassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			String email = request.getParameter("email");
			if (request.getSession(false) == null) {
				if (userDao.doesEmailExist(email)) {
					String password = userDao.generateNewPassword(email);
					SendNewPasswordEmail send = new SendNewPasswordEmail();
					send.setReceiver(email);
					send.setPassword(password);
					System.out.println(password);
					send.sendEmail();
					return "index";
				}
			}
			return "index";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String showChange(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				return "changePassword";
			} else {
				return "index";
			}
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String setPassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) != null) {
				String username = (String) request.getSession(false).getAttribute("username");
				String password = request.getParameter("password");
				if (userDao.changePassword(username, password)) {
					SendNewPasswordEmail send = new SendNewPasswordEmail();
					send.setReceiver((String) request.getSession(false).getAttribute("email"));
					send.sendChangedPasswordEmail();
					request.setAttribute("successMessage", "Паролата беше сменена успешно.");
				} else {
					request.setAttribute("errorMessage", "Старата парола не съвпада.");
				}
				return showChange(request, response);
			}
			return "index";
		} catch (Exception e) {
			return "errorPage";
		}
	}

}
