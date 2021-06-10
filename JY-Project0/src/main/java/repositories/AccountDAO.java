package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import utils.JDBCConnection;

public class AccountDAO implements TemplateRepository<Account> {
	
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public Account add(Account a) {
		String sql = "insert into account values (default, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setFloat(1, a.getBalance());
			ps.setInt(2, a.getCustomer());
			ps.setBoolean(3, a.getPending());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				a.setId(rs.getInt("id"));
				return a;
			}
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Account getById(Integer id) {
		
		String sql = "select * from account where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Account> getAll() {
		List<Account> account = new ArrayList<Account>();
		
		String sql = "select * from account;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));


				
				account.add(a);
			}
			return account;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Account a) {

		String sql = "update account set balance = ?, pending = ? where id = ? returning *;";
		try {
			
			PreparedStatement ps = conn.prepareCall(sql);
			ps.setFloat(1,  a.getBalance());
			ps.setBoolean(2, a.getPending());
			ps.setInt(3,  a.getId());
			return ps.execute();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public boolean delete(Account a) {
		String sql = "delete from account where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	

	
	public List<Account> getAllByCustomerId(Integer id) {
		String sql = "select * from account where customer = ?;";
		List<Account> allAccountsPerId = new ArrayList<Account>();
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				allAccountsPerId.add(a);
				
			}
			return allAccountsPerId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Account> getAllPending() {
		String sql = "select * from account where pending = true;";
		List<Account> allAccountsPending = new ArrayList<Account>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				allAccountsPending.add(a);
				
			}
			return allAccountsPending;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
