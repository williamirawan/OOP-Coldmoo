package model;

public class UnprocessedDrink extends Drink{
	
	private int weight;

	public UnprocessedDrink(String id, String name, int price, int weight) {
		super(id, name, price);
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int calcPrice(int quantity) {
		return (quantity * this.weight)/100 * this.price; // facade pattern
	}

	@Override
	public void printInfo() {
		System.out.printf("| %-7s | %-21s | Rp. %-7d | %-10s |\n", this.id, this.name, this.price, 
				this.weight);
	}
	
}
