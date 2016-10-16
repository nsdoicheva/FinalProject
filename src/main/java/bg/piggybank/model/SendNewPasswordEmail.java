package bg.piggybank.model;

import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendNewPasswordEmail {
	private String email;
	private String password;

	public void sendEmail() {
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.email));
			message.setSubject("Password change");
			message.setText("You requested a new password. The new one is: " + this.password
					+ " . For safety reasons, please change it after you login.");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendChangedPasswordEmail() {
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.email));
			message.setSubject("Password change");
			message.setText("Your password was changed on: " + LocalDate.now()
					+ ". If that wasn't you, please contact us trough our online contact form or by replying to this email. ");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setReceiver(String email) {
		if (isValid(email)) {
			this.email = email;
		}
	}

	public boolean isValid(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public void setPassword(String password) {
		if (isValid(password)) {
			this.password = password;
		}
	}
}