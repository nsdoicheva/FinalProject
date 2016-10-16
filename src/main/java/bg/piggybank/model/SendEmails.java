package bg.piggybank.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.transactions.Transaction;

public class SendEmails implements Runnable {
	private List<String> emails;

	public SendEmails() {
		this.emails = new LinkedList<String>();
	}

	public String lastEmail() {
		String result = null;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("Select email from users;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				emails.add(resultSet.getString("email"));
			}

			result = emails.get(emails.size() - 1);

		} catch (SQLException e) {
			System.out.println("User cannot be logged right now.");
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return result;
	}

	public void sendEmail(String email) {

		final String username = "nsdoicheva@gmail.com";
		final String password = "nikolinaeqka";

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
			message.setFrom(new InternetAddress("nsdoicheva@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("New registered user!");
			message.setText("Welcome to PiggyBank!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		int size = emails.size();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return;
		}

		// if emails.size() - size == 1
		String user = lastEmail();
		sendEmail(user);

	}
}
