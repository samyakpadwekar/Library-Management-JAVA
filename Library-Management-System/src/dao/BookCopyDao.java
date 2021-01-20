package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.BookCopy;
import utils.DBUtil;

public class BookCopyDao implements AutoCloseable {
	BookCopy copy=new BookCopy();
	private Connection connection;
	private PreparedStatement stmtCheck;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtRack;
	private PreparedStatement stmtGetBook;
	private PreparedStatement stmtStatIssue;
	private PreparedStatement stmtStatAvail;
	private PreparedStatement stmBookStat;
	
	public BookCopyDao() {
		try {
			this.connection = DBUtil.getConnection();
			this.stmtCheck = this.connection.prepareStatement("select copy_id,rack from copies where book_id=? and status='available'");
			this.stmtInsert =this.connection.prepareStatement("insert into copies values(?,?,?,?)");
			this.stmtRack=this.connection.prepareStatement("update copies set rack=? where copy_id=?");
			this.stmtGetBook=connection.prepareCall("select *from copies where copy_id=?");
			this.stmtStatIssue=this.connection.prepareStatement("update copies set status='issued' where copy_id=?");
			this.stmtStatAvail=this.connection.prepareStatement("update copies set status='available' where copy_id=?");
			this.stmBookStat=this.connection.prepareStatement("select book_id, if(status='available','available','issued') status,if(status='available',count(book_id),count(book_id)) 'count' from copies group by book_id,status");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public void checkBook(int num) {
		int count=0;
		try {
			stmtCheck.setInt(1, num);
			ResultSet rs=stmtCheck.executeQuery();	
			System.out.println();
				while(rs.next()) {
					System.out.println("Book Copy Available");
					System.out.printf("Copy ID: %-4d  Rack :%-10s\n",rs.getInt(1),rs.getString(2));
						++count;
			}
			System.out.println();
			rs.close();
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Available book copies with BookId " + num + ": " +count +" copies");
		
	}
	
	
	public void insertCopy(BookCopy copy) {
		try {
			ResultSet rs=connection.createStatement().executeQuery("select max(copy_id) from copies");
		    int num=1;
			while(rs.next())
		    	num=rs.getInt(1)+1;
			stmtInsert.setInt(1, num);
			stmtInsert.setInt(2, copy.getBookId());
			stmtInsert.setString(3,copy.getRack());
			stmtInsert.setString(4, copy.getStatus());
			stmtInsert.executeUpdate();
		    System.out.println("BookCopy added !!");
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void changeRack(int num, String rack) {
		try {				
			 stmtRack.setString(1,rack);
			 stmtRack.setInt(2, num);
			 stmtRack.executeUpdate();
			 System.out.println("Rack changed !!");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void changeStatusIssued(int copyId) {
		try {								
			stmtStatIssue.setInt(1, copyId);
			stmtStatIssue.executeUpdate();
		} 
		catch (SQLException e) 
		{				
			e.printStackTrace();
		}
		
	}
	
	
	public BookCopy getBook(int copyId) {
		try {
			stmtGetBook.setInt(1, copyId);
			ResultSet rs=stmtGetBook.executeQuery();
			while(rs.next()) 
			{
				BookCopy.setCopyId(1);
				copy.setBookId(rs.getInt(1));
				copy.setRack(rs.getString(3));
				copy.setStatus(rs.getString(4));				
			}
			rs.close();			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return copy;
	}
	
	
	public void changeStatusAvail(int copyID) {
		try {								
			stmtStatAvail.setInt(1, copyID);
			stmtStatAvail.executeUpdate();
		} 
		catch (Exception e) 
		{				
			e.printStackTrace();
		}
	}
	
	
	public void showBookStat() {
		try {
			ResultSet rs=stmBookStat.executeQuery();	
			System.out.println();
				while(rs.next()) {
					System.out.printf("Book ID: %-4d  status :%-10s   count : %-4d\n",rs.getInt(1),rs.getString(2),rs.getInt(3));
						
			}
			System.out.println();
			rs.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void close() throws Exception {
			copy.close();
			connection.close();
			stmtCheck.close();
			stmtInsert.close();
			stmtRack.close();
			stmtGetBook.close();
			stmtStatIssue.close();
			stmtStatAvail.close();
			stmBookStat.close();
	}
	
}
	
