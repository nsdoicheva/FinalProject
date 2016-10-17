package bg.piggybank.model.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface IUserDAO {

	int saveUserAddress(String address, String city, String country);

	boolean loginUser(String username, String password);

	Set<User> getAllUsersFromDB();

	int saveUser(User user);

	int getCityId(String city);

	int getCountryId(String country);

	String getCityByAddressID(int addressId);

	String getCountryByCityID(int cityId);

	int saveCity(Connection connection, String city, int countryId) throws SQLException;

	int saveCountry(Connection connection, String country) throws SQLException;

	User getUserByUsername(String username);

	int countOfUsers();

	boolean doesEmailExist(String email);

	String generateNewPassword(String email);

	boolean changePassword(String username, String password);

	Map<String, String> getCurrentUserInfo(String username);

	boolean passwordCheck(int userId, String password);

}