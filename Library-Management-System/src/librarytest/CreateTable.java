package librarytest;

import java.sql.Connection;
import java.sql.Statement;
import utils.DBUtil;

public class CreateTable {
	public static void main(String[] args) {

		try (Connection conn = DBUtil.getConnection(); 
			Statement stmt = conn.createStatement();) {
			
			Class.forName("com.mysql.cj.jdbc.Driver");

			String sql1 = "CREATE TABLE users " + 
					"(user_id INT not NULL, " +
					" name VARCHAR(255), "+
					" email VARCHAR(255), " +
					" phone VARCHAR(255), " +
					" password VARCHAR(255)," +
					" role VARCHAR(255)," +
					" PRIMARY KEY ( user_id ))";
			stmt.executeUpdate(sql1);
			
			System.out.println("Users table created");

			String sql2 = "CREATE TABLE books " +
						"(book_id INT not NULL," +
						"name VARCHAR(255)," +
						"author VARCHAR(255)," +
						"subject VARCHAR(255)," +
						"price DOUBLE," +
						"ISBN VARCHAR(255)," +
						"PRIMARY KEY (book_id))";

			stmt.executeUpdate(sql2);
			System.out.println("Books table created");

		
			String sql3 = "CREATE TABLE copies" +
				"(copy_id INT not NULL," +
				"book_id INT not NULL," +
				"rack VARCHAR(255)," +
				"status VARCHAR(255)," +
				"PRIMARY KEY (copy_id)," +
				"FOREIGN KEY (book_id) REFERENCES books(book_id))";

			stmt.executeUpdate(sql3);
			System.out.println("Copies table created");
			
			String sql4 = "CREATE TABLE issuerecord " +
					"(issue_id INT not NULL," +
					"copy_id INT not NULL," +
					"user_id INT not NULL," +
					"issue_date DATETIME," +
					"return_duedate DATETIME," +
					"return_date DATETIME," +
					"fine DOUBLE,"+
					"PRIMARY KEY (issue_id),"+
					"FOREIGN KEY (user_id) REFERENCES users(user_id),"+
					"FOREIGN KEY (copy_id) REFERENCES copies(copy_id))";

			stmt.executeUpdate(sql4);
			System.out.println("IssueRecord table created");
	
			String sql5 = "CREATE TABLE payments" +
					"(payment_id INT, "+
					"user_id INT,"+
					"amount DOUBLE,"+
					"type VARCHAR(20),"+
					"transanction_time DATETIME,"+
					"nextpayement_duedate DATETIME,"+
					"PRIMARY KEY (payment_id),"+
					"FOREIGN KEY (user_id) REFERENCES users(user_id))";

			stmt.executeUpdate(sql5);
			System.out.println("Payments table created");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}