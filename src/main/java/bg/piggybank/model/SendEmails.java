package bg.piggybank.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bg.piggybank.model.exeptions.FailedConnectionException;


public class SendEmails implements Runnable {
	
	private static final String LAST_EMAIL = "Select email from users where id = (SELECT MAX(ID) FROM users); ";
	private String receiver;
	
	public SendEmails() {
		setReceiver(receiver);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			return;
		}		
		String user = lastEmail();
		sendEmail(user, receiver);		
		
	}
	
	public String lastEmail() {
		String result = null;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(LAST_EMAIL);
			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				result = resultSet.getString("email");
			}

		} catch (SQLException e) {
			System.out.println("User cannot be logged right now.");
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return result;
	}
	

	public void sendEmail(String email, String user) {

		if (isValid(email) && isValid(user)) {

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
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("New registered user!");
				message.setText("Welcome to PiggyBank, " + user + "!");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}


	public void setReceiver(String receiver) {
		if (isValid(receiver)) {
			this.receiver = receiver;
		}
	}

	public boolean isValid(String string) {
		if (string != null && !string.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

}
