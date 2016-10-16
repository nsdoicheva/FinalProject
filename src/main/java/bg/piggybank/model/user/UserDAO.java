package bg.piggybank.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.exeptions.*;

@Component
public class UserDAO {
	
	private static final String CAPITAL_LETTER = "A";
	private static final String INSERT_INTO_COUNTRIES = "INSERT INTO countries VALUES(null,?);";
	private static final String INSERT_INTO_CITIES = "INSERT INTO cities VALUES(null,?,?);";
	private static final String INSERT_INTO_ADRESSES = "INSERT INTO adresses VALUES(null, ?, ?);";
	private static final String INSERT_USER_QUERY = "INSERT INTO users VALUES(null, ?, md5(?), ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_CITY_QUERY = "SELECT id FROM cities WHERE name= ? ;";
	private static final String SELECT_COUNTRY_QUERY = "SELECT id FROM countries WHERE name= ? ;";
	private static final String SELECT_ALL_INFO_FOR_USERS = "SELECT id, username, password, name, egn, email, phoneNum, citizenship, adress_id FROM users ORDER BY id";
	private static final String SELECT_COUNTRY_BY_ID = "SELECT c.name FROM countries c join cities cit ON c.id=cit.country_id WHERE cit.id=?;";
	private static final String SELECT_CITY_BY_ID = "SELECT c.name FROM cities c JOIN adresses a ON a.city_id= c.id WHERE a.id=? ;";
	private static final String SELECT_ADDRESS_BY_ID = "SELECT street FROM adresses WHERE id=?;";
	private static final String SELECT_USER_SQL = "SELECT username, password FROM users WHERE username = ? AND password = md5(?)";
	private static final String SELECT_USER_BY_USERNAME = "SELECT id, password, name, egn, email, phoneNum, citizenship, adress_id FROM users WHERE username= ?;";
	private static List<User> users = Collections.synchronizedList(new ArrayList<User>());


	public int saveUserAddress(String address, String city, String country) {
		int id = 0;
		Connection connection =null;
		try {
			connection = DBConnection.getInstance().getConnection();
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
				countryId = saveCountry(connection, country);
				cityId = saveCity(connection, city, countryId);
				addressId = saveAddress(connection, address, cityId);
			}
			if (addressId == 0) {
				System.out.println("Address wasnt added to DB");
				connection.rollback();
			} else {
				connection.commit();
				return addressId;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return id;
	}

	public boolean loginUser(String username, String password) {
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				System.out.println("Wrong username or password.");
				return false;
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("User cannot be logged right now.");
		} catch (FailedConnectionException e) {
			System.out.println("No connection");
		}
		return true;
	}
	
	
	public Set<User> getAllUsersFromDB() {
		Set<User> users = new TreeSet<User>();
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
			}
			statement.close();
			return users;
		} catch (SQLException | FailedConnectionException e) {
			System.out.println("Cannot make statement!");
			e.printStackTrace();
			return users;
		}
	}

	public int saveUser(User user) {
		int userId = 0;
		if (!users.contains(user)) {
			Connection connection =null;
			try {
				connection = DBConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
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
						connection.rollback();
						System.out.println("The user wasn't saved to the DB");
					}

					ResultSet res = statement.getGeneratedKeys();
					res.next();
					userId = res.getInt(1);
					statement.close();
					user.setId(userId);
					users.add(user);
					connection.commit();
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
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally{
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		return addressId;
	}
	
	public User getUserByUsername(String username) {
		User user = null;
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String name = result.getString("name");
				String password = result.getString("password")+CAPITAL_LETTER;
				String EGN = result.getString("EGN");
				String email = result.getString("email");
				String phoneNum = result.getString("phoneNum");
				String citizenship = result.getString("citizenship");
				String address = getAddressById(result.getInt("adress_id"));
				String city = getCityByAddressID(result.getInt("adress_id"));
				String country = getCountryByCityID(getCityId(city));
				user = new User(name, password, username, address, city, country, phoneNum, email, EGN, citizenship);
				user.setId(result.getInt("id"));
				statement.close();
				connection.commit();
				return user;
			} else {
				System.out.println("Country doesn't exist.");
				connection.rollback();
				return user;
			}
		} catch (Exception e) {
			System.out.println("Country connection\\query problem.");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return user;
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
