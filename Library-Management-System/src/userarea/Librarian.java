package userarea;

import java.util.Scanner;

import dao.PaymentsDao;
import utils.BookCopyUtil;
import utils.BookUtil;
import utils.IssueRecordUtil;
import utils.OwnerUtil;
import utils.PaymentsUtil;
import utils.UserUtil;

public class Librarian {
	PaymentsDao pdao = new PaymentsDao();
	static Scanner sc = new Scanner(System.in);
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Add Member");
		System.out.println("2. Edit Profile");
		System.out.println("3. Change Password");
		System.out.println("4. Add Book");
		System.out.println("5. Find Book");
		System.out.println("6. Edit Book");
		System.out.println("7. Book Availability");
		System.out.println("8. Add Copy");
		System.out.println("9. Change Rack");
		System.out.println("10. Issue Copy");
		System.out.println("11. Return Copy");
		System.out.println("12. Take Payment");
		System.out.println("13. Payment History");
		System.out.println("14. List All Users");
		System.out.print("eNTER CHOICE");
		
		return sc.nextInt();
	}
	public static void librarianArea(String email)  {
		System.out.println("LIBRARIAN AREA");
		int choice;
			while ((choice = Librarian.menuList()) != 0) {
				switch (choice) {
				case 1: 		//Add Member
					UserUtil.signUp();
					break;
				case 2:			//Edit profile
					UserUtil.editProfile(email);
					break;
				case 3:			//Change Password
					UserUtil.changePassword(email);
					break;
				case 4:  		//Add Book
					BookUtil.addBook();
					break;
				case 5:			 //Find Book
					BookUtil.findBook();
					break;
				case 6:	 		 //EDIT BOOK
					BookUtil.editBook();
					break;
				case 7:			//Book availability
					BookCopyUtil.checkAvail();
					break;
				case 8:			//Add copy
					BookCopyUtil.addBookCopy();
					break;
				case 9:			//Change Rack
					BookCopyUtil.changeRack();
					break;
				case 10:		//Issue Copy
					IssueRecordUtil.issueCopy();
					break;
				case 11:		 // Return copy
					IssueRecordUtil.returnCopy();
					break;
				case 12:		//Take Payment
					PaymentsUtil.acceptPayment();
					break;				
				case 13:		//Payment History
					PaymentsDao.paymentHistory();
					break;
				case 14:			//List all users
					OwnerUtil.listUsers();
					break;
					
				}
			}
	}

}
