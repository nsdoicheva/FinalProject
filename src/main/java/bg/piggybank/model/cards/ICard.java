package bg.piggybank.model.cards;

import java.util.List;

import bg.piggybank.model.accounts.Account;
import bg.piggybank.model.exeptions.AccountException;
import bg.piggybank.model.exeptions.CardException;

public interface ICard {

	
	//Generate new card for existing account;
	void createCardForAccount(Account account, CardTypes type) throws AccountException, CardException;

	//Return all card that this account have;
	List<Card> getAllCardsForAccount(Account account) throws CardException, AccountException;

}
