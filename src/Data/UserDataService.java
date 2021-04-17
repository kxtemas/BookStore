package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Database;
import beans.User;

public class UserDataService {
	
	private Database connection = null;

	/**
	 * Non default constructor takes database as parameter
	 * @param connection
	 */
	public UserDataService(Database connection) {
		this.connection = connection;
	}

	/**
	 * Insert a user to the database
	 * @param user
	 * @return
	 */
	public boolean createUser(User user) 
	{
		Connection conn = null;
		//tries connecting to the database and entering the users data into a database table, 
		//but prints an error message if it fails to connect or insert the data.
		try {
			//get database connection
			conn = DriverManager.getConnection(connection.getUrl(), connection.getUser(), connection.getPass());
			String sql = "INSERT INTO clc235.users(firstname, lastname, birthdate, username, password, email) VALUES (?, ?, ?, ?, ?, ?)";

			//prepare sql statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getBirthdate());
			stmt.setString(4, user.getUsername());
			stmt.setString(5, user.getPassword());
			stmt.setString(6, user.getEmail());
			
			if(stmt.executeUpdate() > 0)
			{
				return true;
			}
			else
			{
				return false;
			}

		} 
		//Prints an error message if fails to connect or enter data.
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Find user in database for login
	 * @param user
	 * @return
	 */
	public boolean findUser(User user) 
	{
		Connection conn = null;
		//tries connecting to the database and entering the users data into a database table, 
		//but prints an error message if it fails to connect or insert the data.
		try {
			//get database connection
			conn = DriverManager.getConnection(connection.getUrl(), connection.getUser(), connection.getPass());
			String sql = "SELECT * FROM clc235.users WHERE username = ? AND password = ?";

			//prepare sql statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst())
			{
				return false;
			}
			else
			{
				return true;
			}
		} 
		//Prints an error message if fails to connect or enter data.
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	//helps user rest service
	public List<User> findAllUsers() 
	{
		Connection conn = null;

		List<User> users = new ArrayList<User>();
		try {
			conn = DriverManager.getConnection(connection.getUrl(), connection.getUser(), connection.getPass());
			String sql = "SELECT * FROM clc235.users";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				User u1 = new User();
				u1.setUsername(rs.getString("username"));
				u1.setPassword(rs.getString("password"));
				u1.setFirstName(rs.getString("firstname"));
				u1.setLastName(rs.getString("lastname"));
				u1.setEmail(rs.getString("email"));
				u1.setBirthdate(rs.getString("birthdate"));
				users.add(u1);
				
			}
			rs.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return users;
	}
}