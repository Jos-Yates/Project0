package repositories;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Customer;
import utils.JDBCConnection;

public class CustomerDAO implements TemplateRepository<Customer> {
	
	//This connects to the JDBCC to allow editing of Customer tables
	private Connection conn = JDBCConnection.getConnection();
	
	public Customer getByUsernameAndPassword(String username, String password) { //This will check the username and password for login
		
		String sql = "select * from customer where username = ? and \"password\" = ?;"; //The \ " \" allows you to insert quotations into a string
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setUsername(rs.getString("username"));
				customer.setPassword(rs.getString("password"));  // Builds up the customer object so as to return it to whatever calls it
				customer.setName(rs.getString("name"));
				customer.setEmployee(rs.getBoolean("employee"));
				return customer;
			}
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	public Customer getByUsername(String username) { 
		
		String sql = "select * from customer where username = ?;"; 
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setUsername(rs.getString("username"));
				customer.setPassword(rs.getString("password")); 
				customer.setName(rs.getString("name"));
				
				return customer;
			}
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}

	@Override
	public Customer add(Customer c) {
		
		String sql = "insert into customer values (default, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, c.getName());
			ps.setString(2, c.getUsername());
			ps.setString(3, c.getPassword());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				c.setId(rs.getInt("id"));
				return c;
			}
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}

		return null;
}

	@Override
	public List<Customer> getAll() {
		
		List<Customer> customer = new ArrayList<Customer>();
		
		String sql = "select * from customer order by id asc;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee"));


				
				customer.add(c);
			}
			return customer;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public boolean update(Customer c) { //This will house the custom stored procedure
			//String sql = "update customer set account_balance = ? where id = ?;";
			String sql = "call update_account(?, ?)";
			try {
				
				CallableStatement cs = conn.prepareCall(sql);
				cs.setInt(1,  c.getId());
				cs.setString(2,  c.getName());
				return cs.execute();
				
				//PreparedStatement ps = conn.prepareStatement(sql);
				//ps.setString(1, Integer.toString(c.getAccount_balance()));
				//ps.setInt(1, c.getAccount_balance());
				//ps.setString(2, Integer.toString(c.getId()));
				//ps.setInt(2, c.getId());
				
				//boolean success = ps.execute();
				
				//f (success) {
				//	return true;
				//}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
			
			
		}

	@Override
	public boolean delete(Customer c) {
		String sql = "delete from customer where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(c.getId()));
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer getById(Integer id) {
		String sql = "select * from customer where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee"));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
