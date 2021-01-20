package userarea;

import java.util.Scanner;

import dao.IssueRecordDao;
import dao.PaymentsDao;
import utils.BookCopyUtil;
import utils.BookUtil;
import utils.UserUtil;

public class Member {
	static Scanner sc = new Scanner(System.in);
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Edit Profile");
		System.out.println("2. Change Password");
		System.out.println("3. Book Availability");
		System.out.println("4. Find a Book");
		System.out.println("6. Issue Record");
		System.out.println("6. Payment History");
		return sc.nextInt();
	}

	public static void memberArea(String email) {
		System.out.println("MEMEBR AREA");
		int choice;
		while ((choice = Member.menuList()) != 0) {
			switch (choice) {
			case 1: // Edit Profile
				UserUtil.editProfile(email);
				break;
			case 2: // Change Password
				UserUtil.changePassword(email);
				break;
			case 3: // Find a Book
				BookUtil.findBook();
				break;
			case 4:  // Book availability
				BookCopyUtil.checkAvail();
				break;				
			case 5: // Issue Record
				IssueRecordDao.checkStatus(email);
				break;
			case 6: // Payment History
				PaymentsDao.paymentHistory(email);
				break;

			}
		}
	}

}
