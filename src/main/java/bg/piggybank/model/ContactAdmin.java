package bg.piggybank.model;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactAdmin {

	private String senderName;
	private String email;
	private String description;
	
	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public void sendEmail(String senderName, String email, String description) {

		setSenderName(senderName);
		setEmail(email);
		setDescription(description);
		
		final String username = "piggycontact.system@gmail.com";
		final String password = "PiggyBank";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("piggycontact.system@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("piggybank@abv.bg"));
			message.setSubject("New message from user " + senderName);
			message.setText("User's e-mail: " + email + " \n " + description);			

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}




}
