package bg.piggybank.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.exeptions.*;

public class UserDAO {

	private static final String INSERT_INTO_COUNTRIES = "INSERT INTO countries VALUES(null,?);";
	private static final String INSERT_INTO_CITIES = "INSERT INTO cities VALUES(null,?,?);";
	private static final String INSERT_INTO_ADRESSES = "INSERT INTO adresses VALUES(null, ?, ?);";
	private static final String INSERT_USER_QUERY = "INSERT INTO users VALUES(null, ?, md5(?), ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_CITY_QUERY = "SELECT id FROM cities WHERE name= ? ;";
	private static final String SELECT_COUNTRY_QUERY = "SELECT id FROM countries WHERE name= ? ;";
	private static final String SELECT_ALL_INFO_FOR_USERS = "SELECT id, username, password, name, egn, email, phoneNum, citizenship, adress_id FROM users";
	private static final String SELECT_COUNTRY_BY_ID = "SELECT c.name FROM countries c WHERE id=?;";
	private static final String SELECT_CITY_BY_ID = "SELECT c.name FROM cities c WHERE id=?;";
	private static final String SELECT_ADDRESS_BY_ID = "SELECT street FROM adresses WHERE id=?;";
	private static UserDAO instance;
	private static List<User> users = Collections.synchronizedList(new ArrayList<User>());
	private static List<String> cities = Collections.synchronizedList(new ArrayList<String>());
	private static List<String> countries = Collections.synchronizedList(new ArrayList<String>());

	private UserDAO() {

	}

	public synchronized static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	public int saveUserAddress(String address, String city, String country) {
		int id = 0;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			int cityId = 0;
			int countryId = 0;
			int addressId = 0;
			countryId = getCountryId(country);
			cityId = getCityId(city);
			if (countryId > 0) {
				if (cityId > 0) {
					addressId = saveAddress(connection, address, cityId);
				} else {
					cityId = saveCity(connection, city, countryId);
					addressId = saveAddress(connection, address, cityId);
				}
			} else {
				if (cityId > 0) {
					countryId = saveCountry(connection, country);
					addressId = saveAddress(connection, address, cityId);
				} else {
					countryId = saveCountry(connection, country);
					cityId = saveCity(connection, city, countryId);
					addressId = saveAddress(connection, address, cityId);
				}
			}
			if (addressId == 0) {
				System.out.println("Address wasnt added to DB");
				connection.rollback();
			} else {
				connection.commit();
				connection.setAutoCommit(true);
				return addressId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Set<User> getAllUsersFromDB() {
		Set<User> users = new HashSet<User>();
		try {
			Statement statement = DBConnection.getInstance().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_INFO_FOR_USERS);

			while (resultSet.next()) {
				String city = getCityByAddressID(resultSet.getInt("adress_id"));
				String country = getCountryByCityID(getCityId(city));
				String address = getAddressById(resultSet.getInt("adress_id"));
				try {
					User user = new User(resultSet.getString("name"), resultSet.getString("password"),
							resultSet.getString("username"), address, city, country, resultSet.getString("phoneNum"),
							resultSet.getString("email"), resultSet.getString("egn"),
							resultSet.getString("citizenship"));
					user.setId(resultSet.getInt("id"));
					users.add(user);
				} catch (IncorrectContactInfoException e) {
					System.out.println("User wasn't added to the set.");
					e.printStackTrace();
				}
				return users;
			}
		} catch (SQLException | FailedConnectionException e) {
			System.out.println("Cannot make statement!");
			e.printStackTrace();
			return users;

		}
		return users;
	}

	public int saveUser(User user) {
		int userId = 0;
		if (!users.contains(user)) {
			try {
				Connection connection = DBConnection.getInstance().getConnection();
				int addressId = saveUserAddress(user.getAddress(), user.getCity(), user.getCountry());
				System.out.println(addressId);
				if (addressId != 0) {
					PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY,
							Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, user.getUsername());
					statement.setString(2, user.getPassword());
					statement.setString(3, user.getName());
					statement.setString(4, user.getEGN());
					statement.setString(5, user.getEmail());
					statement.setString(6, user.getPhoneNum());
					statement.setString(7, user.getCitizenship());
					statement.setInt(8, addressId);
					int count2 = statement.executeUpdate();
					if (count2 > 0) {
						System.out.println("The user was saved to the DB");
					} else {
						System.out.println("The user wasn't saved to the DB");
					}

					ResultSet res = statement.getGeneratedKeys();
					res.next();
					userId = res.getInt(1);
					statement.close();
					user.setId(userId);
					users.add(user);
				} else {
					System.out.println("User wasn't inserted");
					throw new FailedInsertException("User wasn't inserted into DB");
				}
			} catch (FailedConnectionException e) {
				System.out.println("No connection.");
			} catch (SQLException e) {
				System.out.println("Invalid query");
				e.printStackTrace();
			} catch (FailedInsertException e) {
				System.out.println("Nothing was inserted");
			}
		} else {
			System.out.println("User is already in the DB");
		}
		return userId;
	}

	public int getCityId(String city) {
		int id = 0;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_CITY_QUERY);
			statement.setString(1, city);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				id = result.getInt("id");
				if (id > 0) {
					System.out.println("City found");
				} else {
					System.out.println("No such city in DB");
				}
				statement.close();
				return id;
			} else {
				System.out.println("City doesn't exist.");
				return id;
			}
		} catch (Exception e) {
			System.out.println("City connection\\query problem.");
			return id;
		}
	}

	public int getCountryId(String country) {
		int id = 0;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_COUNTRY_QUERY);
			statement.setString(1, country);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				id = result.getInt(1);
				if (id > 0) {
					System.out.println("Country found");
				} else {
					System.out.println("No such country in DB");
				}
				statement.close();
				return id;
			} else {
				System.out.println("Country doesn't exist.");
				return id;
			}
		} catch (Exception e) {
			System.out.println("Country connection\\query problem.");
			e.printStackTrace();
			return id;
		}
	}

	public String getCityByAddressID(int addressId) {
		String name = null;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_CITY_BY_ID);
			statement.setInt(1, addressId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				name = result.getString("name");
				if (name != null && !name.trim().equals("")) {
					System.out.println("City found");
				} else {
					System.out.println("No such city in DB");
				}
				statement.close();
				return name;
			} else {
				System.out.println("City doesn't exist.");
				return name;
			}
		} catch (Exception e) {
			System.out.println("City connection\\query problem.");
			return name;
		}
	}

	private String getAddressById(int addressId) {
		String address = null;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ADDRESS_BY_ID);
			statement.setInt(1, addressId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				address = result.getString("street");
				if (address != null && !address.trim().equals("")) {
					System.out.println("Address found");
				} else {
					System.out.println("No such address in DB");
				}
				statement.close();
				return address;
			} else {
				System.out.println("Address doesn't exist.");
				return address;
			}
		} catch (Exception e) {
			System.out.println("Address connection\\query problem.");
			return address;
		}
	}

	public String getCountryByCityID(int cityId) {
		String name = null;
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_COUNTRY_BY_ID);
			statement.setInt(1, cityId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				name = result.getString("name");
				if (name != null && !name.trim().equals("")) {
					System.out.println("Country found");
				} else {
					System.out.println("No such country in DB");
				}
				statement.close();
				return name;
			} else {
				System.out.println("Country doesn't exist.");
				return name;
			}
		} catch (Exception e) {
			System.out.println("Country connection\\query problem.");
			e.printStackTrace();
			return name;
		}
	}

	public int saveCity(Connection connection, String city, int countryId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(INSERT_INTO_CITIES, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, city);
		statement.setInt(2, countryId);

		int count1 = statement.executeUpdate();
		if (count1 > 0) {
			System.out.println("The city was saved to the DB");
		} else {
			System.out.println("The city was not saved to the DB");
			return 0;
		}

		ResultSet result = statement.getGeneratedKeys();
		result.next();
		int cityId = result.getInt(1);
		statement.close();
		return cityId;
	}

	public int saveCountry(Connection connection, String country) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(INSERT_INTO_COUNTRIES,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, country);

		int count = statement.executeUpdate();
		if (count > 0) {
			System.out.println("The country was saved to the DB");
		} else {
			System.out.println("The country was not saved to the DB");
			return 0;
		}

		ResultSet rs = statement.getGeneratedKeys();
		rs.next();
		int countryId = rs.getInt(1);
		statement.close();
		return countryId;
	}

	private int saveAddress(Connection connection, String address, int cityId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(INSERT_INTO_ADRESSES,
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, address);
		statement.setInt(2, cityId);
		int count2 = statement.executeUpdate();
		if (count2 > 0) {
			System.out.println("The address was saved to the DB");
		} else {
			System.out.println("The address was not saved to the DB");
			return 0;
		}

		ResultSet res = statement.getGeneratedKeys();
		res.next();
		int addressId = res.getInt(1);
		statement.close();
		connection.commit();
		return addressId;
	}
}

