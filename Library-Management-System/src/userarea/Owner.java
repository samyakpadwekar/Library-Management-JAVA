package userarea;

import java.util.Scanner;
import utils.BookCopyUtil;
import utils.OwnerUtil;
import utils.PaymentsUtil;
import utils.UserUtil;

public class Owner {
	static Scanner sc = new Scanner(System.in);
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Appoint Librarian");
		System.out.println("2. Edit Profile");
		System.out.println("3. Change Password");
		System.out.println("4. Fees/Fine Report");
		System.out.println("5. Book Availability");
		System.out.println("6. List All Users");
		System.out.print("Enter Choice : ");
		return sc.nextInt();
	}
	
	public static void ownerArea(String email) {
		System.out.println("OWNER AREA");
		int choice;
        while ((choice = Owner.menuList()) != 0) {
			switch (choice) {
			case 1: //appoint librarian
				OwnerUtil.appointLibrarian();
				break;
			case 2://Edit Profile				
				UserUtil.editProfile(email);
				break;
			case 3://Change Password
				UserUtil.changePassword(email);
				break;
			case 4:   //fees/fine report
				PaymentsUtil.payReport();
				break;
			case 5://Book availability
				BookCopyUtil.showBookStat();
				break;
			case 6:	 //List all users
				OwnerUtil.listUsers();
				break;

			}
		}
	}

}
