package model;

public class ProcessedDrink extends Drink {

	private String ExpiredDate;

	public ProcessedDrink(String id, String name, int price, String expiredDate) {
		super(id, name, price);
		ExpiredDate = expiredDate;
	}

	public String getExpiredDate() {
		return ExpiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		ExpiredDate = expiredDate;
	}

	@Override
	public int calcPrice(int quantity) {
		return this.price * quantity; // facade pattern
	}

	@Override
	public void printInfo() {
		System.out.printf("| %-7s | %-21s | Rp. %-7d | %-10s |\n", this.id, this.name, this.price, 
				this.ExpiredDate);
	}
	
}
