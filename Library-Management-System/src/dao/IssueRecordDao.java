package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import pojo.BookCopy;
import pojo.IssueRecord;
import utils.DBUtil;
import utils.PaymentsUtil;

public class IssueRecordDao implements AutoCloseable {
	static Scanner sc = new Scanner(System.in);
	BookCopyDao cdao=new BookCopyDao();
	BookCopy copy=new BookCopy();
	IssueRecord issue=new IssueRecord();
	
	private Connection connection;
	private Statement stm;
	private PreparedStatement stmInsert;
	private PreparedStatement stmIssue;
	private PreparedStatement stmUpdate;
	private PreparedStatement stmgetRecord;
	private static PreparedStatement stmissueMem;
	
	public IssueRecordDao() {
		 try {
			connection=DBUtil.getConnection();
			stm=connection.createStatement();
			stmInsert=connection.prepareStatement("insert into issuerecords values(?,?,?,?,?,?,?)");
			stmIssue=connection.prepareStatement("select * from issuerecord where user_id=? AND return_date is null");
			stmUpdate=connection.prepareStatement("update issuerecords set return_date=? where issue_id=?");
			stmgetRecord=connection.prepareStatement("select *from issuerecords where issue_id=?");
			stmissueMem=connection.prepareStatement("select *from issuerecords where email=?");
		 } 
		 catch (Exception e) 
		 {		
			e.printStackTrace();
		}	
	}	
	
	
	public void addRecord(IssueRecord issue) 
	{
		copy=cdao.getBook(issue.getCopyId());
		if(copy.getStatus().contentEquals("available")) 
		{
			try 
			{
				ResultSet rs=stm.executeQuery("select max(issue_id) from issuerecords");
			    int num=1;
				
			    while(rs.next())
				   num=rs.getInt(1)+1;
				
			    stmInsert.setInt(1, num);
				stmInsert.setInt(2, issue.getCopyId());
				stmInsert.setInt(3, issue.getUserId());
				stmInsert.setDate(4, issue.getIssueDate());
				stmInsert.setDate(5, issue.getReturnDueDate());
				stmInsert.setDate(6, issue.getReurnDate());
				stmInsert.setDouble(7, issue.getFine());
				stmInsert.executeUpdate();
				System.out.println("record added !!! ");
				rs.close();
			} 
			catch (Exception e) 
			{	
				e.printStackTrace();
			}
		}
	}
	

	public void returnBook(int userid, Date dt) {		
		try {
				stmIssue.setInt(1, userid);
				ResultSet res=stmIssue.executeQuery();
				System.out.println();
				ArrayList<IssueRecord> records=new ArrayList<IssueRecord>();
				while(res.next()) {
					   records.add(new IssueRecord(res.getInt(1),res.getInt(2),res.getInt(3),res.getDate(4),res.getDate(5),res.getDate(6),res.getDouble(7)));				  
				}
				res.close();
				System.out.println();
				for(IssueRecord rec: records) 
				{
					System.out.println(rec.toString());
				}
				System.out.print("Enter issue Id to be returned : ");
				int num=sc.nextInt();
				stmUpdate.setDate(1, dt);
				stmUpdate.setInt(2,num);
				stmUpdate.executeUpdate();
				int issueId=0;
				int copyID=0;
				for(IssueRecord rec: records) 
				{
					if(rec.getIssueId()==num) 
					{
						issueId=rec.getIssueId();
						copyID=rec.getCopyId();
						PaymentsUtil.fineReceive(userid,issueId,dt);
					}
									
				}
				cdao.changeStatusAvail(copyID);
				System.out.println("retun book record updated ");						
		} 
		catch (Exception e1) 
		{
				e1.printStackTrace();
		}		
	}
	
	
	public IssueRecord getRecord(int issueId, Date dt) {
		try {
			stmgetRecord.setInt(1, issueId);
			ResultSet rs=stmgetRecord.executeQuery();
			while(rs.next()) 
			{
				issue.setCopyId(rs.getInt(2));
				issue.setUserId(rs.getInt(3));
				issue.setIssueDate(rs.getDate(4));
				issue.setReturnDueDate(rs.getDate(5));
				issue.setReurnDate(dt);
			}
			rs.close();	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return issue;
	}	
	
	public static void checkStatus(String email) 
	{
		try {
			stmissueMem.setString(1, email);
			ResultSet rs=stmissueMem.executeQuery();
			ArrayList<IssueRecord> records=new ArrayList<IssueRecord>();
			while(rs.next()) {
				   records.add(new IssueRecord(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDate(4),rs.getDate(5),rs.getDate(6),rs.getDouble(7)));				  
			}
			rs.close();
			System.out.println();
			for(IssueRecord ref: records) 
			{
				System.out.println(ref.toString());
			}	
			System.out.println();
			
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void close() throws Exception {
		sc.close();
		cdao.close();
		copy.close();
		issue.close();
		connection.close();
		stm.close();
		stmInsert.close();
		stmIssue.close();
		stmUpdate.close();
		stmgetRecord.close();
		stmissueMem.close();
	}	

}
