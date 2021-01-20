package pojo;

import java.sql.Date;

public class IssueRecord implements AutoCloseable{

	private int issueId,copyId,userId;
	private Date issueDate,returnDueDate,returnDate;
	private double fine;
	
	public IssueRecord() {}
	
	public IssueRecord(int issueId, int copyId, int userId, Date issueDate, Date returnDueDate, Date reurnDate,double fine) {
		this.issueId = issueId;
		this.copyId = copyId;
		this.userId = userId;
		this.issueDate = issueDate;
		this.returnDueDate = returnDueDate;
		this.returnDate = reurnDate;
		this.fine = fine;
	}

	
	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDueDate() {
		return returnDueDate;
	}

	public void setReturnDueDate(Date returnDueDate) {
		this.returnDueDate = returnDueDate;
	}

	public Date getReurnDate() {
		return returnDate;
	}

	public void setReurnDate(Date reurnDate) {
		this.returnDate = reurnDate;
	}

	public double getFine() {
		return fine;
	}
	
	public void setFine(double fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "IssueRecord [issueId=" + issueId + ", copyId=" + copyId + ", userId=" + userId + ", returnDate=" + returnDate + ", fine=" + fine
				+ "]";
	}

	

	@Override
	public void close() throws Exception {}
}
