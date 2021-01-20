package utils;

import java.util.Scanner;
import dao.UserDao;
import pojo.User;

public class OwnerUtil {
	static Scanner sc = new Scanner(System.in);
	
	public static void appointLibrarian() {
		try (UserDao dao = new UserDao()) {
			User u = UserUtil.acceptUser();
			u.setRole("Librarian");
			if (dao.insert(u) == 1)
				System.out.println(" Librian is added");
			else
				System.out.println("Librarian is not added");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void listUsers() {
		try (UserDao dao = new UserDao()) {
			dao.getUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
