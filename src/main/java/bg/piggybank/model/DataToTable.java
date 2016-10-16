package bg.piggybank.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import bg.piggybank.model.accounts.AccountType;
import bg.piggybank.model.accounts.CurrencyType;
import bg.piggybank.model.exeptions.FailedConnectionException;



public class DataToTable {
	
	private final static String SEND_TO_CURRENCIES = "INSERT INTO currencies VALUES (?)";
	private static final String SEND_TO_ACCOUNT_TYPES = "INSERT INTO accounttypes VALUES (null, ?)";
	
	private DataToTable(){
	}
	
		public static void fillCurrencies(){
			try {
				Connection connection = DBConnection.getInstance().getConnection();
			
				PreparedStatement currency = connection.prepareStatement(SEND_TO_CURRENCIES);
				for (CurrencyType currencyType : CurrencyType.values()) {
					currency.setString(1, currencyType.toString());
					int success = currency.executeUpdate();
					if(success != 1){
						System.out.println("Fail to do!");
					}
				}	
				
			} catch (SQLException | FailedConnectionException e ) {
				e.printStackTrace();
			}
		
		}
		
		public static void fillAccountTypes(){
			try {
				Connection connection = DBConnection.getInstance().getConnection();
			
				PreparedStatement accounts = connection.prepareStatement(SEND_TO_ACCOUNT_TYPES);
				for (AccountType type : AccountType.values()){
					accounts.setString(1, type.toString());
						int success = accounts.executeUpdate();
						if (success != 1){
							System.out.println("Something goes wrong!");
						}
				}
			} catch (Exception e) {
				System.out.println("DB Problem");
				e.printStackTrace();
			}
		}
		
}
