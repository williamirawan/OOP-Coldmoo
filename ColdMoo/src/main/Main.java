package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import database.Connect;
import model.*;

public class Main {
	
	Connect con = Connect.getConnection(); // this getConnection refers to getInstance where the new object was created inside the class itself
	ArrayList<Transaction> transactions = new ArrayList<>();
	ArrayList<Drink> drink = new ArrayList<>();
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();
	
	public void menu() {
		System.out.println("Cold Moo");
		System.out.println("==================");
		System.out.println("1. Buy Drink");
		System.out.println("2. View Transaction History");
		System.out.println("3. Delete Transaction");
		System.out.println("4. Exit");
		System.out.print("Choose>");
	}

	public Main() {
		int choice = -1;
		
		loadDrink();
		loadTrans();
		
		do {
			menu();
			choice = validInt();
			
			switch (choice) {
			case 1:
				buy();
				break;
			case 2:
				viewTrans();
				etc();
				break;
			case 3:
				delete();
				break;
			case 4:
				System.out.println("Thank You for Using Our Application...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid menu input");
				break;
			}
		} while (choice != 4);
	}
	
	public void delete() {
		viewTrans();
		if (transactions.size() > 0) {
			int choice;
			do {
				System.out.print("Choose transaction to be deleted [1-" + transactions.size() + "]: ");
				choice = validInt();
			} while (choice < 1 || choice > transactions.size());
			choice--;
			execDelete(transactions.get(choice).getId());
		}
		etc();
	}
	
	public void execDelete(String id) {
		String query = String.format("DELETE FROM transaction WHERE TransactionId = '%s'", id);
		con.executeUpdate(query);
		System.out.println("Successfully deleted");
	}
	
	public void buy() {
		String drinkType, drinkId;
		int qty;
		
		do {
			System.out.print("Input Drinks Type [processed | unprocessed](case insensitive): ");
			drinkType = sc.nextLine();
		} while (!(drinkType.equalsIgnoreCase("processed") || drinkType.equalsIgnoreCase("unprocessed")));
		
		viewDrink(drinkType);
		if (drinkType.equals("processed")) {
			do {
				System.out.print("Input Food ID (case sensitive): ");
				drinkId = sc.nextLine();
			} while (getProcessedDrink(drinkId) == null);
		}
		else {
			do {
				System.out.print("Input Food ID (case sensitive): ");
				drinkId = sc.nextLine();
			} while (getUnprocessedDrink(drinkId) == null);
		}
		
		do {
			System.out.print("Input quantity [1-50] (inclusive): ");
			qty = validInt();
		} while (qty < 1 || qty > 50);
		
		Drink bought = getDrink(drinkId);
		Transaction newTran = new Transaction(generateId(), drinkId, qty);
		newTran.insert();
		int totalPrice = bought.calcPrice(qty);
		
		System.out.println("Transaction Details");
		System.out.println("==============================");
		System.out.printf("%-17s : %s\n", "Food's Name", bought.getName());
		System.out.printf("%-17s : %d\n", "Food's Base Price", bought.getPrice());
		System.out.printf("%-17s : %d\n", "Quantity", newTran.getQuantity());
		System.out.printf("%-17s : %d\n", "Total Price", totalPrice);
		etc();
	}

	public Drink getDrink(String getId) {
		for (Drink drinks : drink) {
			if (drinks.getId().equals(getId)) {
				return drinks;
			}
		}
		return null;
	}
	
	public Drink getProcessedDrink(String getId) {
		for (Drink drinks : drink) {
			if (drinks.getId().equals(getId) && drinks instanceof ProcessedDrink) {
				return drinks;
			}
		}
		return null;
	}
	
	public Drink getUnprocessedDrink(String getId) {
		for (Drink drinks : drink) {
			if (drinks.getId().equals(getId) && drinks instanceof UnprocessedDrink) {
				return drinks;
			}
		}
		return null;
	}
	
	public void viewTrans() {
		loadTrans();
		if (transactions.size() <= 0) {
			System.out.println("No Transaction Yet");
		}
		else {
			int i = 1;
			System.out.printf("| %-4s | %-14s | %-22s | %-8s |\n", "No.", "Transaction Id", "Grocery Name", "Quantity");
			for (Transaction tr : transactions) {
				System.out.printf("| %-4s | %-14s | %-22s | %-8d |\n", i, tr.getId(), getDrink(tr.getGroceryId()).getName(), tr.getQuantity());
				i++;
			}
		}
	}
	
	public void viewDrink(String choice) {
		loadDrink();
		
		if (choice.equals("processed")) {
			System.out.printf("| %-7s | %-21s | Rp. %-7s | %-10s |\n", "ID", "Name", "Price", 
					"Exp Date");
		}
		else {
			System.out.printf("| %-7s | %-21s | Rp. %-7s | %-10s |\n", "ID", "Name", "Price", 
					"Weight");
		}
		for (Drink drinks : drink) {
			if (choice.equals("processed")) {
				if (drinks instanceof ProcessedDrink) {
					drinks.printInfo();
				}
			}
			else {
				if (drinks instanceof UnprocessedDrink) {
					drinks.printInfo();
				}
			}
		}
	}
	
	public void loadTrans() {
		transactions.clear();
		
		String id, drinkId;
		int quantity;
		
		String query = "SELECT * FROM transaction";
		ResultSet rs = con.executeQuery(query);
		
		try {
			while (rs.next()) {
				id = rs.getString("TransactionId");
				drinkId = rs.getString("DrinkId");
				quantity = rs.getInt("Quantity");
				
				transactions.add(new Transaction(id, drinkId, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadDrink() {
		drink.clear();
		loadProcessed();
		loadUnprocessed();
	}
	
	public void loadProcessed() {
		String id, name, expiredDate;
		int price;
		
		String query = "SELECT * FROM `processed drink`";
		ResultSet rs = con.executeQuery(query);
		
		try {
			while (rs.next()) {
				id = rs.getString("DrinkId");
				name = rs.getString("DrinkName");
				price = rs.getInt("DrinkPrice");
				expiredDate = rs.getString("ExpiredDate");
				
				drink.add(new ProcessedDrink(id, name, price, expiredDate));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadUnprocessed() {
		String id, name;
		int price, weight;
		
		String query = "SELECT * FROM `unprocessed drink`";
		ResultSet rs = con.executeQuery(query);
		
		try {
			while (rs.next()) {
				id = rs.getString("DrinkId");
				name = rs.getString("DrinkName");
				price = rs.getInt("DrinkPrice");
				weight = rs.getInt("DrinkWeight");
				
				drink.add(new UnprocessedDrink(id, name, price, weight));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String generateId() {
		return "TR" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
	}

	public int validInt() {
		int inp = -1;
		try {
			inp = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input");
		}
		sc.nextLine();
		
		return inp;
	}
	
	public void etc() {
		System.out.println("Press enter to continue");
		sc.nextLine();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
