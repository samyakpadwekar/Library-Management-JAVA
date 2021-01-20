package pojo;

public class BookCopy implements AutoCloseable{
	static private int copyId;
	private int bookId;
	private String rack;
	private String status;
	
	public BookCopy(int bookId, String rack, String status) {
		this.bookId = bookId;
		this.rack = rack;
		this.status = status;
	}
	public BookCopy() {
		// TODO Auto-generated constructor stub
	}
	public static int getCopyId() {
		return copyId;
	}
	public static void setCopyId(int copyId) {
		BookCopy.copyId = copyId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getRack() {
		return rack;
	}
	public void setRack(String rack) {
		this.rack = rack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public void close() throws Exception {
		
	}

	
	
}
