package business;

import java.util.ArrayList; 
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import beans.Database;
import beans.Products;
import Data.ProductDataService;

@Local
@Stateless
public class ProductBusinessService {

	// array list of products
	List<Products> products = new ArrayList<>();

	// non default constructor
	public ProductBusinessService() 
	{
		if(!findAllProducts())
		{
			Products p1 = new Products(0, "Romeo and Juliet", (float) 9.99, "Novel", "Shakespeare", "Love and death", 100);
			products.add(p1);
		}
	}
		
	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	/**
	 * Add product to database and arraylist
	 * @param product
	 * @return
	 */
	public boolean addProduct(Products product)
	{
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		
		if(pds.createProduct(product))
		{
			products.add(product);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean findAllProducts()
	{
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		
		if(pds.findAllProducts() != null)
		{
			products.addAll(pds.findAllProducts());
			return true;
		}
		else
		{
			return false;
		}
	}/**
	 * Find specific product from database
	 * @param id
	 * @return
	 */
	public Products findByID(int id)
	{
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		
		return pds.findByID(id);
	}
	
	/**
	 * Edit specific product from database
	 * @param product
	 * @return
	 */
	public boolean editProduct(Products product)
	{
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		
		if(pds.updateProduct(product))
		{
			System.out.print("it passed business");
			return true;
			
		}
		else
		{
			System.out.print("it failed business");
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
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		
		if(pds.deleteProduct(id))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Remove all products from arraylist and add updated list back to arraylist
	 */
	public void updateList()
	{
		Database db = new Database();
		ProductDataService pds = new ProductDataService(db);
		products.removeAll(products);
		products.addAll(pds.findAllProducts());
	}
}