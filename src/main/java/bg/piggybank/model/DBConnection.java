package bg.piggybank.model;

import java.sql.Connection;
import bg.piggybank.model.exeptions.*;
import java.sql.DriverManager;



public class DBConnection {
	
	private Connection connection;
	private static DBConnection instanceDBConnection;
	
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "0877609235";
	
	
	private DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", DB_USER, DB_PASSWORD);
		} catch (Exception e) {
			System.out.println("TUKA E PROBLEMA");
		}
	}
	
	//lazy loading 
	public synchronized static DBConnection getInstance() {
		if (instanceDBConnection == null) {
			instanceDBConnection = new DBConnection();
		}
		return instanceDBConnection;
	}

	public Connection getConnection() throws FailedConnectionException {
		if(connection == null) {
			throw new FailedConnectionException("Cannot connect to the DB!");
		}
		return connection;
	}
}