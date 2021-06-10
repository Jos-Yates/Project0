package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Transaction;
import utils.JDBCConnection;

public class TransactionHIstoryDAO implements TemplateRepository<Transaction>{
	
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public Transaction add(Transaction t) {
		String sql = "insert into transactionhistory values(default, ?, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, t.getType());
			ps.setInt(2, t.getSource());
			ps.setFloat(3, t.getAmount());
			ps.setInt(4, t.getRecipient());

			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				t.setId(rs.getInt("id"));
				return t;
			}
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Transaction getById(Integer id) {
		String sql = "select * from transactionhistory where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setType(rs.getString("type"));
				t.setSource(rs.getInt("source"));
				t.setAmount(rs.getFloat("amount"));
				t.setRecipient(rs.getInt("recipient"));
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Transaction> getAll() {
		List<Transaction> transaction = new ArrayList<Transaction>();
		String sql = "select * from transactionhistory;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setType(rs.getString("type"));
				t.setSource(rs.getInt("source"));
				t.setAmount(rs.getFloat("amount"));
				t.setRecipient(rs.getInt("recipient"));
				
				transaction.add(t);
			}
			return transaction;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Transaction t) {
		String sql = "update transactionhistory set type = ?, amount = ? returning *;";
		try {
			
			PreparedStatement ps = conn.prepareCall(sql);
			ps.setString(1,  t.getType());
			ps.setFloat(2, t.getAmount());
			return ps.execute();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public boolean delete(Transaction t) {
		String sql = "delete from transactionhistory where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(t.getId()));
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
