package model.domain;

public class Article {
	private String name;
	private double cost;
	private int quantity;
	private String category;

	public Article(String name, double cost, int quantity, String category) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Nome dell'articolo non valido.");
		}
		if (quantity <= 0) {
			quantity = 1;
		}
		if (category == null || category.isEmpty()) {
			category = "Non Categorizzati";
		}
		this.name = name;
		this.cost = cost;
		this.quantity = quantity;
		this.category = category;
	}

	public String getName() { return name; }
	public double getCost() { return cost; }
	public int getQuantity() { return quantity; }
	public String getCategory() { return category; }

	public void setCost(double cost) { this.cost = cost; }
	public void setQuantity(int quantity) { this.quantity = quantity; }
	public void setCategory(String category) { this.category = category; }
}
