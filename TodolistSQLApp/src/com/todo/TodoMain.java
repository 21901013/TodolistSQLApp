package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;
import com.todo.service.DBConnect;

public class TodoMain {
	
	public static void start() {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("Sorting completed by Name Order");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("Sorting completed by Reverse Name Order");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
		 		System.out.println("Sorting completed by Date Order");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":
				System.out.println("Sorting completed by Reverse Date Order");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.find_cateItem(l,cate);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "comp":
				String num = sc.nextLine().trim();
				TodoUtil.Command(l,num);
				break;

//			case "ls_comp":
//				TodoUtil.
			
			case "exit":
				quit = true;
				DBConnect.closeConnection();
				break;

			default:
				System.out.println("Please enter one of the command mentioned above (see menu - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}