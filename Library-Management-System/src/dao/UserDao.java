package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.User;
import utils.DBUtil;

public class UserDao implements AutoCloseable {
	private Connection connection;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtSelect;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtUpdate1;
	private PreparedStatement stmtUpdate2;

	public UserDao() throws Exception {
		this.connection = DBUtil.getConnection();
		this.stmtInsert = this.connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");
		this.stmtSelect = this.connection.prepareStatement("SELECT * FROM users");
		this.stmtUpdate = this.connection.prepareStatement("UPDATE users SET name=? WHERE user_id=?");
		this.stmtUpdate1 = this.connection.prepareStatement("UPDATE users SET phone=? WHERE user_id=?");
		this.stmtUpdate2 = this.connection.prepareStatement("UPDATE users SET password=? WHERE user_id=?");
	}

	
	// insert
	public int insert(User u) throws Exception 
	{
		String query = "Select max(user_id) as max from users ";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
		int num = 0;
		while (rs.next())
			num = rs.getInt("max") + 1;
		u.setUserId(num);
		
		this.stmtInsert.setInt(1, u.getUserId());
		this.stmtInsert.setString(2, u.getName());
		this.stmtInsert.setString(3, u.getEmail());
		this.stmtInsert.setString(4, u.getPhone());
		this.stmtInsert.setString(5, u.getPassword());
		this.stmtInsert.setString(6, u.getRole());
		
		return this.stmtInsert.executeUpdate();
	}

	
	// getUsers
	public List<User> getUsers() throws Exception {
		List<User> userList = new ArrayList<>();
		try (ResultSet rs = this.stmtSelect.executeQuery();) 
		{
			while (rs.next())
				userList.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"),
						rs.getString("phone"), rs.getString("password"), rs.getString("role")));
			
			System.out.printf("%-6s%-10s%-25s%-12s%-10s\n","ID","NAME","EMAIL","PHONE","ROLE");
			userList.forEach(System.out::println);
			
		}
		return userList;
	}


	
	public void updateName(User u) throws SQLException 
	{
		this.stmtUpdate.setString(1, u.getName());
		this.stmtUpdate.setInt(2, u.getUserId());
		int res = this.stmtUpdate.executeUpdate();
		if( res == 1)
			System.out.println("Name Updated ");
	}
	
	
	public void updatePhone(User u) throws SQLException {
		this.stmtUpdate1.setString(1, u.getPhone());
		this.stmtUpdate1.setInt(2, u.getUserId());
		int res = this.stmtUpdate1.executeUpdate();
		if( res == 1)
			System.out.println("Phone Updated ");
	}

	
	
	public void updatePassword(User u) throws SQLException {
		this.stmtUpdate2.setString(1, u.getPassword());
		this.stmtUpdate2.setInt(2, u.getUserId());
		int res = this.stmtUpdate2.executeUpdate();
		if( res == 1)
			System.out.println("Password changed.");	
		
	}

	@Override
	public void close() throws Exception {
		stmtInsert.close();
		stmtSelect.close();
		stmtUpdate.close();
		stmtUpdate1.close();
		stmtUpdate2.close();
		connection.close();
	}
	
	
}
