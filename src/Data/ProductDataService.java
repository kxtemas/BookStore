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

import beans.Products;

public class ProductDataService {
	
	private Database connection = null;

	/**
	 * Non defualt constructor takes database parameter
	 * @param connection
	 */
	public ProductDataService(Database connection) 
	{
		this.connection = connection;
	}

	/**
	 * Insert product to database
	 * @param product
	 * @return
	 */
	public boolean createProduct(Products product) {
		Connection conn = null;

		// tries connecting to the database and entering the product data into a
		// database table,
		// but prints an error message if it fails to connect or insert the data.
		try {
			// get database connection
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			String sql = "INSERT INTO clc235.products(id,book_name, book_author, price, book_description, book_genre, quantity) VALUES (?,?, ?, ?, ?, ?, ?)";

			// prepare sql statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, product.getId());
			stmt.setString(2, product.getBookName());
			stmt.setString(3, product.getBookAuthor());
			stmt.setFloat(4, product.getPrice());
			stmt.setString(5, product.getBookDescription());
			stmt.setString(6, product.getBookGenre());
			stmt.setInt(7, product.getQuantity());
			

			if (stmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}

		}
		// prints error message if fails to connect or insert data into the database
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Find all products in the products database table
	 * @return
	 */
	public List<Products> findAllProducts() 
	{
		Connection conn = null;

		List<Products> products = new ArrayList<Products>();
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			String sql = "SELECT * FROM clc235.products";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				products.add(new Products(rs.getInt("id"), rs.getString("book_name"), rs.getFloat("price"), rs.getString("book_author"), rs.getString("book_description"), rs.getString("book_genre"),  rs.getInt("quantity")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return products;
	}
	
	/**
	 * Find specific product using id parameter
	 * @param id
	 * @return
	 */
	public Products findByID(int id) 
	{
		
		Connection conn = null;
				Products p1 = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			String sql = "SELECT id, book_name, book_author, book_description, book_genre, price, quantity FROM clc235.products WHERE id = ?";
			
			//bind id to sql statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			//execute query
			ResultSet rs = stmt.executeQuery();

			//create new product model of result
			while(rs.next())
			{
				p1 = new Products(rs.getInt("id"), rs.getString("book_name"), rs.getFloat("price"),rs.getString("book_author"), rs.getString("book_description"), rs.getString("book_genre"), rs.getInt("quantity"));
					}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return p1;
	}
	
	
	/**
	 * Update product in the database
	 * @param product
	 * @return
	 */
	public boolean updateProduct(Products product) 
	{
		Connection conn = null;

		// tries connecting to the database and updating the product data into a
		// database table,
		// but prints an error message if it fails to connect or update the data.
		try {
			// get database connection
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			String sql = "UPDATE clc235.products SET book_name = ?, book_author = ?, price = ?, book_description = ?, book_genre = ?, quantity = ? WHERE id = ?";

			// prepare sql statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, product.getBookName());
			stmt.setString(2, product.getBookAuthor());
			stmt.setFloat(3, product.getPrice());
			stmt.setString(4, product.getBookDescription());
			stmt.setString(5, product.getBookGenre());
			stmt.setInt(6, product.getQuantity());
			stmt.setInt(7, product.getId());

			if (stmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}

		}
		// prints error message if fails to connect or insert data into the database
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Delete product from database
	 * @param id
	 * @return
	 */
	public boolean deleteProduct(int id) 
	{
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			String sql = "DELETE FROM clc235.products WHERE id = ?";
			
			//bind id to sql query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			if(stmt.executeUpdate() > 0)
			{
				return true;
			}
			else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
}
