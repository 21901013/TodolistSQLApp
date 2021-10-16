package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nCreate item has been selected\n"
				+ "Please enter the title");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("Title can't be duplicate");
			return;
		}
		System.out.println("\nPlease enter the category");
		category = sc.next();
		sc.nextLine();
		System.out.println("\nPlease enter the description");
		desc = sc.nextLine().trim();
		System.out.println("\nPlease enter the due date");
		due_date = sc.next().trim();

		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t)>0) System.out.println("It's been added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nDelete item has been selected\n"
				+ "Please enter the index of item you want to remove");
		
		int index = sc.nextInt();
		if(l.deleteItem(index)>0) System.out.println("Item has been deleted");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String new_title, new_category, new_description, new_due_date;
		
		System.out.println("\n"
				+ "Edit item has been selected\n"
				+ "Please enter the index of the item you want to update\n");
		
		int index = sc.nextInt();
		
		System.out.println("\nEnter the new title of the item\n");
		new_title = sc.next().trim();
		
		System.out.print("New Category\n");
		new_category = sc.next();
		sc.nextLine();
		
		System.out.print("Enter the new description\n");
		new_description = sc.nextLine().trim();
		
		System.out.println("Enter the new due date\n");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0) System.out.println("Item has been updated\n");
	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[List All, Total %d items\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("List all items, %d items ]", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.getId() + item.toString());
		}
	}
	
	public static void Command(TodoList l, String keyword) {
		
	}
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());	
			count++;
		}
		System.out.printf("Total of %d items have been found.\n", count);
	}
	
	public static void find_cateItem(TodoList l, String key_word) {
		for(TodoItem item : l.getList()) {
			if(item.toString().contains(key_word)) {
				System.out.println(item.toString());
			}
		}
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		} System.out.printf("\n%d of items have been found.'n", count);
	}
	
	public static void listCategory(TodoList l) {
		HashSet<String> category = new HashSet<String>(l.size());
		for(TodoItem item : l.getList()) {
			category.add(item.getCategory());
		}
		
		Iterator<String> iter = category.iterator();
		int count=0;
		while(iter.hasNext()) {
			System.out.print(iter.next());
			count++;
			if(iter.hasNext()) {
				System.out.print("/");
			}
		}
		System.out.println("\n"+count+" of category have been registered");
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
			} System.out.printf("\n%d of category have been registered\n", count);
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item:l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("All data has been saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(filename));
			String oneline;
			int number=0;
			while((oneline=bf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				TodoItem it = new TodoItem(category,title,desc,due_date);
				l.addItem(it);
				number++;
			}
			bf.close();
			System.out.println("In total "+number+" file(s) has been read.");
		} catch (FileNotFoundException e) {
			System.out.println(filename+" file doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
