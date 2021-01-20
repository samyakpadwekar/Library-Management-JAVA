package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import pojo.IssueRecord;
import pojo.Payment;
import utils.DBUtil;

public class PaymentsDao implements AutoCloseable {
	
private Connection connection;
private PreparedStatement stmtpaidmem;
private PreparedStatement stmInsert;
private PreparedStatement stmpaydue;
private static PreparedStatement stmAllPay;
private static PreparedStatement stmPayHis;
private PreparedStatement stmpayrep;

	public PaymentsDao()  {
		try {
			this.connection=DBUtil.getConnection();
			this.stmtpaidmem=this.connection.prepareStatement("select max(nextpayement_duedate) from payments where user_id=?");
			this.stmInsert=this.connection.prepareStatement("insert into payments values(?,?,?,?,?,?)");
			this.stmpaydue=this.connection.prepareStatement("select max(nextpayement_duedate)from payments where user_id=? and type='fees'");
			this.stmpayrep=this.connection.prepareStatement("select if(type='fine','Total Fine','Total Fees') 'Type',  if(type='fine',sum(amount)  ,sum(amount) ) as 'Amount' from payments where transanction_time between ? and ? group by type");
			PaymentsDao.stmAllPay=this.connection.prepareStatement("select *from payments order by user_id");
			PaymentsDao.stmPayHis=this.connection.prepareStatement("select *from payments where email=?");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}	
	
	
	public boolean paidMember(int userId) {
		try {
			stmtpaidmem.setInt(1, userId);
			ResultSet rs=stmtpaidmem.executeQuery();
			Date date=null;
			
			while(rs.next())
				date=rs.getDate(1);
			rs.close();
			
			if(date!=null) 
			{
				LocalDate rdate=date.toLocalDate();
				LocalDate cdate=LocalDate.now();
				if((rdate.compareTo(cdate))>=0) 
				 return true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	return false;
    }
	
		
    public void fineAccept(IssueRecord issue) {
    	try {
			ResultSet rs=connection.createStatement().executeQuery("select max(payment_id) from payments");
			int num=1;
			
			while(rs.next())
				num=rs.getInt(1)+1;
			rs.close();
			
			stmInsert.setInt(1, num);
			stmInsert.setInt(2,issue.getUserId());
			stmInsert.setDouble(3, issue.getFine());
			stmInsert.setString(4,"fine");
			stmInsert.setDate(5,issue.getReurnDate());
			stmInsert.setDate(6,null);
			stmInsert.executeUpdate();
			System.out.println("fine received !!! ");
		} 
    	catch (Exception e) 
    	{			
			e.printStackTrace();
		}	
	}
    
    
    public void acceptFees(Payment payment) {
    	Date lastdate=null;
    	try {
			ResultSet rs=connection.createStatement().executeQuery("select max(payment_id) max from payments");
			int num=1;
			
			while(rs.next())
				num=rs.getInt(1)+1;
			
			stmpaydue.setInt(1,payment.getUserId());
			ResultSet rs1=stmpaydue.executeQuery();	
			
			while(rs1.next())
				lastdate =rs1.getDate(1);
			
			if(lastdate==null)
				lastdate=Date.valueOf(LocalDate.now());
			
			LocalDate nextdate=lastdate.toLocalDate().plusDays(30);
			stmInsert.setInt(1, num);
			stmInsert.setInt(2, payment.getUserId());
			stmInsert.setDouble(3, payment.getAmount());
			stmInsert.setString(4,payment.getType());
			stmInsert.setDate(5,lastdate);
			stmInsert.setDate(6, Date.valueOf(nextdate));
			stmInsert.executeUpdate();
			System.out.println("fees received");			
			rs.close();
			rs1.close();
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
		
	}
    
    
    public static void paymentHistory() {
		try {
			ResultSet rs=stmAllPay.executeQuery();
			System.out.println();
			while(rs.next()) {
				System.out.printf("paymentID: %-4d userID: %-5d amount:%-8.2f type:%-7s time:%-10s nextdate: %-10s\n",rs.getInt(1),rs.getInt(2),rs.getDouble(3),rs.getString(4),rs.getDate(5).toString(),rs.getDate(6));
			}
			System.out.println();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void getPaymentReport(Date sdate, Date edate) {
		try {
			stmpayrep.setDate(1, sdate);
			stmpayrep.setDate(2, edate);
			ResultSet rs=stmpayrep.executeQuery();
			System.out.println();
			while(rs.next()) {
				System.out.printf("%-15s  %-8.2f \n",rs.getString(1),rs.getDouble(2));
			}
			System.out.println();
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	public static void paymentHistory(String email) {
		try {
			stmPayHis.setString(1, email);
			ResultSet rs=stmPayHis.executeQuery();
			System.out.println();
			while(rs.next()) {
				System.out.printf("PaymentId: %-5d UserId: %-5d Amount:%-8.2f Type:%-10s TransTime:%-10s NextPaymentDate: %-10s\n",rs.getInt(1),rs.getInt(2),rs.getDouble(3),rs.getString(4),rs.getDate(5).toString(),rs.getDate(6));
			}
			System.out.println();
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void close() throws Exception {
		connection.close();
		stmtpaidmem.close();
		stmInsert.close();
		stmpaydue.close();
		stmAllPay.close();
		stmPayHis.close();
		stmpayrep.close();
	}

}



