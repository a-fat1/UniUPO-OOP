package model;

import model.exceptions.io.InvalidInputException;

/**
 * `Article` rappresenta un articolo con attributi come
 * nome, costo, quantità e categoria. Permette di creare
 * e gestire un articolo, applicando le varie convalide sui campi.
 */
public class Article {
	/** Il nome dell'articolo. */
	private String name;
	
	/** Il costo dell'articolo. */
	private double cost;
	
	/** La quantità disponibile dell'articolo. */
	private int quantity;
	
	/** La categoria dell'articolo. */
	private String category;

	/** La categoria predefinita utilizzata quando nessuna categoria è specificata. */
	public static final String DEFAULT_CATEGORY = "Non Categorizzati";

	/**
	 * Crea un nuovo articolo con i parametri specificati.
	 *
	 * @param name      il nome dell'articolo.
	 * @param cost      il costo dell'articolo.
	 * @param quantity  la quantità disponibile dell'articolo.
	 * @param category  la categoria dell'articolo.
	 * @throws InvalidInputException se il nome o la categoria sono invalidi.
	 */
	public Article(String name, double cost, int quantity, String category) throws InvalidInputException {
		setName(name);
		setCost(cost);
		setQuantity(quantity);
		setCategory(category);
	}

	/**
	 * Restituisce il nome dell'articolo.
	 *
	 * @return il nome dell'articolo.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Restituisce il costo dell'articolo.
	 *
	 * @return il costo dell'articolo.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Restituisce la quantità disponibile dell'articolo.
	 *
	 * @return la quantità dell'articolo.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Restituisce la categoria dell'articolo.
	 *
	 * @return la categoria dell'articolo.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Imposta il nome dell'articolo.
	 *
	 * @param name il nuovo nome dell'articolo.
	 * @throws InvalidInputException se il nome è nullo o vuoto.
	 */
	public void setName(String name) throws InvalidInputException {
		if (name == null || name.isEmpty()) {
			throw new InvalidInputException("nome articolo invalido.");
		}
		this.name = name;
	}

	/**
	 * Imposta il costo dell'articolo. Se il costo è inferiore a zero, viene impostato a zero.
	 *
	 * @param cost il nuovo costo dell'articolo.
	 */
	public void setCost(double cost) {
		this.cost = (cost < 0) ? 0 : cost;
	}

	/**
	 * Imposta la quantità disponibile dell'articolo. Se la quantità è minore o uguale a zero, 
	 * viene impostata a uno.
	 *
	 * @param quantity la nuova quantità dell'articolo.
	 */
	public void setQuantity(int quantity) {
		this.quantity = (quantity <= 0) ? 1 : quantity;
	}

	/**
	 * Imposta la categoria dell'articolo. Se la categoria è nulla o vuota, viene impostata alla
	 * categoria predefinita {@link #DEFAULT_CATEGORY}.
	 *
	 * @param category la nuova categoria dell'articolo.
	 * @throws InvalidInputException se la categoria è invalida.
	 */
	public void setCategory(String category) throws InvalidInputException {
		this.category = (category == null || category.isEmpty()) ? DEFAULT_CATEGORY : category;
	}
}
