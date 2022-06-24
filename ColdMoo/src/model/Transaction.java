package model;

import database.Connect;

public class Transaction {
	
	private String id, groceryId;
	private int quantity;
	
	public Transaction(String id, String groceryId, int quantity) {
		super();
		this.id = id;
		this.groceryId = groceryId;
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public String getGroceryId() {
		return groceryId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGroceryId(String groceryId) {
		this.groceryId = groceryId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void insert() {
		Connect con = Connect.getConnection();
		String query = String.format("INSERT INTO transaction VALUES ('%s', '%s', '%d')", id, groceryId, quantity);
		
		con.executeUpdate(query);
	}

}
