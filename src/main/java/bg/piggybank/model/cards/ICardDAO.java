package bg.piggybank.model.cards;

import java.util.List;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.CardException;

public interface ICardDAO {

	void createCardForAccount(Account account, CardTypes type) throws AccountException, CardException;

	List<Card> getAllCardsForAccount(Account account) throws CardException, AccountException;

	List<Card> getAllCardsForUser(String username) throws CardException, AccountException;

	CardTypes getCardType(String type);

	List<String> getAllCardTypes();

}