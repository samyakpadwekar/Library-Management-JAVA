package utils;


import java.util.Scanner;

import dao.BookCopyDao;
import pojo.BookCopy;

public class BookCopyUtil {
	
	
	public static void checkAvail() {
		try(	BookCopyDao cdao = new BookCopyDao();
				Scanner sc = new Scanner(System.in);)
		{
			System.out.print("Enter Book id ");
			int bookId = sc.nextInt();
			cdao.checkBook(bookId);
		}	 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addBookCopy() {
		try(	BookCopyDao cdao = new BookCopyDao();
				BookCopy copy=new BookCopy();
				Scanner sc = new Scanner(System.in);)
		{
			System.out.print("Enter book id : ");
			copy.setBookId(sc.nextInt());
			sc.nextLine();
			System.out.print("Enter rack no : ");
			copy.setRack(sc.nextLine());
			copy.setStatus("available");
			cdao.insertCopy(copy);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeRack() {
		try(	BookCopyDao cdao = new BookCopyDao();
				Scanner sc = new Scanner(System.in);)
		{
			System.out.print("Enter copy id : ");
			int num=sc.nextInt();
			sc.nextLine();
			System.out.print("Enter new rack : ");
			String rack=sc.nextLine();
			cdao.changeRack(num,rack);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void showBookStat() {
		try(BookCopyDao cdao = new BookCopyDao();)
		{
			cdao.showBookStat();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
