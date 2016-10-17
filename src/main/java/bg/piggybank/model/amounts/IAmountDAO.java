package bg.piggybank.model.amounts;

import java.util.List;

public interface IAmountDAO {

	void startAmountTrackingAfterServerRestart();

	List<Amount> getAllMyAmounts(String username);

	List<Amount> getAllAmountsForAccount(String IBAN);

	List<Amount> getAmountForMonth(int month, String IBAN, int year);
}