package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import dao.BookCopyDao;
import dao.IssueRecordDao;
import dao.PaymentsDao;
import pojo.IssueRecord;

public class IssueRecordUtil {
	static Scanner sc = new Scanner(System.in);
	public static void issueCopy() {
		try(	IssueRecordDao dao=new IssueRecordDao();
				BookCopyDao cdao=new BookCopyDao();
				IssueRecord issue=new IssueRecord();
				PaymentsDao pdao=new PaymentsDao();)
		{
		System.out.print("Enter copy id      : ");
		issue.setCopyId(sc.nextInt());		
		System.out.print("Enter user id      : ");
		issue.setUserId(sc.nextInt());
		if(pdao.paidMember(issue.getUserId())) {
			LocalDate date=LocalDate.now();
			issue.setIssueDate(Date.valueOf(date));
			issue.setReturnDueDate(Date.valueOf(date.plusDays(15)));	
			dao.addRecord(issue);
			cdao.changeStatusIssued(issue.getCopyId());
		}
		else System.out.println("User is not paid user !!! ");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	

	public static void returnCopy() {
		try(IssueRecordDao dao=new IssueRecordDao()){
			System.out.print("Enter user ID      : ");
			int id=sc.nextInt();
			System.out.println("Enter return date : ");
			System.out.print("day   : ");
			int a=sc.nextInt();
			System.out.print("month : ");
			int b=sc.nextInt();
			System.out.print("year  : ");
			int c=sc.nextInt();			
			dao.returnBook(id,Date.valueOf(LocalDate.of(c, b, a)));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
		
