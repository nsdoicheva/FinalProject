package bg.piggybank.controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.ContactAdmin;

@Controller
public class ContactController {

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String showContacts(Model model) {
		return "contact";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String receiveMessage(HttpServletRequest request, HttpServletRequest response) {
		try {
			String senderName = request.getParameter("name");
			String email = request.getParameter("email");
			String description = request.getParameter("text");
			new ContactAdmin().sendEmail(senderName, email, description);
			return "contact";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/contactLogged", method = RequestMethod.GET)
	public String showContactLogged(Model model) {
		return "contactLogged";
	}

	@RequestMapping(value = "/contactLogged", method = RequestMethod.POST)
	public String receiveMessageLogged(HttpServletRequest request, HttpServletRequest response) {
		try {
			String senderName = request.getParameter("name");
			String email = request.getParameter("email");
			String description = request.getParameter("text");
			new ContactAdmin().sendEmail(senderName, email, description);
			return "contactLogged";
		} catch (Exception e) {
			return "errorPage";
		}
	}
}
