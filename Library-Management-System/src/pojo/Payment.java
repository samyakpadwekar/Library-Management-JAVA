package pojo;

import java.sql.Date;

public class Payment {
	private static int payment_id; 
	private int userId;
	private double amount;
	private String type;
	private Date transanction_time;
	private Date nextpayement_duedate;
	
	public Payment() {
	}

	public Payment(int userId, double amount, String type, Date transanction_time, Date nextpayement_duedate) {
		this.userId = userId;
		this.amount = amount;
		this.type = type;
		this.transanction_time = transanction_time;
		this.nextpayement_duedate = nextpayement_duedate;
	}

	public static int getPayment_id() {
		return payment_id;
	}

	public static void setPayment_id(int payment_id) {
		Payment.payment_id = payment_id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTransanction_time() {
		return transanction_time;
	}

	public void setTransanction_time(Date transanction_time) {
		this.transanction_time = transanction_time;
	}

	public Date getNextpayement_duedate() {
		return nextpayement_duedate;
	}

	public void setNextpayement_duedate(Date nextpayement_duedate) {
		this.nextpayement_duedate = nextpayement_duedate;
	}

	@Override
	public String toString() {
		return "Payment [userId=" + userId + ", amount=" + amount + ", type=" + type + ", transanction_time="
				+ transanction_time + ", nextpayement_duedate=" + nextpayement_duedate + "]";
	}

	
	
}
