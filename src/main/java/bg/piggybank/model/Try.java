package bg.piggybank.model;

public class Try {

	public static void main(String[] args) {
		Thread ch = new Thread(new SendEmails());
		ch.start();
	}
}
