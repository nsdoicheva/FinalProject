package bg.piggybank.model.cards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.piggybank.model.Checkers;
import bg.piggybank.model.Constants;
import bg.piggybank.model.DBConnection;
import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.accounts.AccountDAO;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.CardException;
import bg.piggybank.model.exeptions.FailedConnectionException;
import bg.piggybank.model.exeptions.GeneratorException;

@Component
public class CardDAO implements ICard {
	
	@Autowired
	private AccountDAO accountDao;
	private static final String CHECK_FOR_CARD = "SELECT id FROM cards WHERE number LIKE (?)";
	private static final String ADD_NEW_CARD = "INSERT INTO cards (id, number, type, account_id) VALUES (null, ?, ?, ?);";
	private static final String GET_ALL_CARDS_FOR_ACCOUNT = "SELECT id, number, type, account_id FROM cards WHERE account_id = (?);	";
	private static final String GET_ALL_CARDS_FOR_USER = "SELECT c.id, c.number, c.type, c.account_id FROM cards c JOIN accounts a "
			+ " ON c.account_id=a.id JOIN users u ON a.users_id=u.id WHERE username=?; ";

	@Override
	public void createCardForAccount(Account account, CardTypes type) throws AccountException, CardException {
		if (account == null) {
			throw new AccountException("No such account found!");
		}
		if (type == null) {
			throw new AccountException("No such card type.");
		}
		String accountIBAN = account.getIBAN();
		int accountId = account.getId();
		Card newCard = createCard(accountIBAN, type);

		if (!canCreateCard(newCard)) {
			throw new CardException("Cannot create card right now! Please try again later!");
		}
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(ADD_NEW_CARD);
			int numberOfValue = 1;
			statement.setString(numberOfValue++, newCard.getNumber());
			statement.setString(numberOfValue++, type.toString());
			statement.setInt(numberOfValue++, accountId);
			int count = statement.executeUpdate();
			if (count == 0) {
				throw new CardException("Card wasn't added to the DB");
			}
			statement.close();
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
		} catch (FailedConnectionException | SQLException e) {
			e.printStackTrace();
		}

	}

	// Creating new card by input IBAN;
	private Card createCard(String accounIBAN, CardTypes type) throws CardException {
		String cardNumber = generateCardNumber(accounIBAN);
		Card newCard = new Card(type, cardNumber);
		return newCard;
	}

	// check if existing (created) card can be added to DB;
	private boolean canCreateCard(Card card) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement cardCheck = connection.prepareStatement(CHECK_FOR_CARD);
			String cardNumber = card.getNumber();
			cardCheck.setString(1, cardNumber);
			ResultSet result = cardCheck.executeQuery();
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			if (result.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// Generate card number by account IBAN;
	private String generateCardNumber(String accounIBAN) throws CardException {
		String accountNumber = accounIBAN.substring(Constants.START_POSITION_OF_ACCOUNT_NUMBER);
		int randomNumber = ((int) (Math.random() * Constants.VISA_CARD_RANGE));
		String cardNumber = new String();
		cardNumber += Constants.VISA_CARD_IDENTIFY;
		cardNumber += randomNumber;
		cardNumber += Constants.BANK_ID_NUMBER;
		cardNumber += accountNumber;

		try {
			int lastDigit = Checkers.calculateDigitByLuhm(cardNumber);
			cardNumber += lastDigit;
		} catch (GeneratorException genExc) {
			throw new CardException("Cannot calculate card number!", genExc);
		}
		return cardNumber;
	}

	@Override
	public List<Card> getAllCardsForAccount(Account account) throws CardException, AccountException {
		if (account == null) {
			throw new AccountException("No such account found!");
		}
		List<Card> accountCards = new ArrayList<Card>();
		int accountID = account.getId();
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement getAllCards = connection.prepareStatement(GET_ALL_CARDS_FOR_ACCOUNT);
			getAllCards.setInt(1, accountID);
			ResultSet allCards = getAllCards.executeQuery();
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			while (allCards.next()) {
				int cardID = allCards.getInt("id");
				String cardNumber = allCards.getString("number");
				CardTypes type = getCardType(allCards.getString("type"));
				Card card = new Card(type, cardNumber);
				card.setIBAN(account.getIBAN());
				accountCards.add(card);
			}
		} catch (FailedConnectionException e) {
			throw new CardException("Cannot get cards", e);
		} catch (SQLException e) {
			throw new CardException("Cannot access Database", e);
		}
		return accountCards;
	}
	
	public List<Card> getAllCardsForUser(String username) throws CardException, AccountException {
		if (username== null || username.trim().equals("")) {
			throw new AccountException("No such account found!");
		}
		List<Card> userCards = new ArrayList<Card>();
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement getAllCards = connection.prepareStatement(GET_ALL_CARDS_FOR_USER);
			getAllCards.setString(1, username);
			ResultSet allCards = getAllCards.executeQuery();
			
			while (allCards.next()) {
				int cardID = allCards.getInt("id");
				String cardNumber = allCards.getString("number");
				CardTypes type = getCardType(allCards.getString("type"));
				Card card = new Card(type, cardNumber);
				int accountId= allCards.getInt("account_id");
				Account account= accountDao.getAccountByID(accountId, connection);
				card.setIBAN(account.getIBAN());
				userCards.add(card);
			}
		} catch (FailedConnectionException e) {
			throw new CardException("Cannot get cards");
		} catch (SQLException e) {
			throw new CardException("Cannot access Database");
		}
		return userCards;
	}

	public CardTypes getCardType(String type) {
		CardTypes cardType = null;
		CardTypes[] cards = CardTypes.values();
		for (int index = 0; index < cards.length; index++) {
			if (cards[index].toString().equals(type)) {
				cardType = cards[index];
			}
		}
		return cardType;
	}

	public List<String> getAllCardTypes() {
		List<String> cards = new ArrayList<String>();
		for (CardTypes type : CardTypes.values()) {
			cards.add(type.toString());
		}
		return cards;
	}
}