package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

import dao.IssueRecordDao;
import dao.PaymentsDao;
import pojo.IssueRecord;
import pojo.Payment;

public class PaymentsUtil {
	private static PaymentsDao pdao=new PaymentsDao();
	private static Payment payment=new Payment();
	private static Scanner sc=new Scanner(System.in);
	private static IssueRecordDao idao=new IssueRecordDao();
	
	public static void fineReceive(int userid, int issueId, Date dt) {
		IssueRecord issue=idao.getRecord(issueId,dt);
		LocalDate issueDate=issue.getIssueDate().toLocalDate();
		LocalDate returnDate=dt.toLocalDate();	
		
		int diffdays=Period.between(issueDate, returnDate).getDays();
		System.out.println("diff days :"+diffdays);
		if(diffdays>15) {
			issue.setFine((diffdays-15)*10);
			System.out.println("fine"+issue.getFine());
			pdao.fineAccept(issue);
		}
		
	}

	public static void acceptPayment() {
		System.out.print("Enter user id : ");
		payment.setUserId(sc.nextInt());
		payment.setType("fees");
		payment.setAmount(500.00);
	    pdao.acceptFees(payment);
		
	}

	public static void payReport() {
		System.out.print("\nEnter starting date : (yyyy:MM:dd)\n");
		LocalDate sdate=LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
		System.out.print("\nEnter end date : (yyyy:MM:dd)\n");
		LocalDate edate=LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
		pdao.getPaymentReport(Date.valueOf(sdate),Date.valueOf(edate));
		
	}
	
	
}
