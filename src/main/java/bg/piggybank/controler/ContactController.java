package bg.piggybank.controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.piggybank.model.accounts.Account;

@Controller
public class ContactController {

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response) {
		return "contact";
	}
	
	@RequestMapping(value = "/contactLogged", method = RequestMethod.GET)
	public String showLogged(HttpServletRequest request, HttpServletResponse response) {
		return "contactLogged";
	}
}
