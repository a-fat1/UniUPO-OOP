package model.domain;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class ListManager {
	private Map<String, ShoppingList> shoppingLists;
	private Set<String> categories;

	public ListManager() {
		shoppingLists = new HashMap<>();
		categories = new HashSet<>();
		categories.add("Non Categorizzati");
	}

	public void addCategory(String category) {
		if (category == null || category.isEmpty()) {
			throw new IllegalArgumentException("nome della categoria non valido.");
		}
		categories.add(category);
	}

	public void removeCategory(String category) {
		if ("Non Categorizzati".equals(category)) {
			throw new IllegalArgumentException("non è possibile rimuovere la categoria 'Non Categorizzati'.");
		}
	
		if (!categories.remove(category)) {
			throw new IllegalArgumentException("categoria non esistente.");
		}
	
		for (ShoppingList list : shoppingLists.values()) {
			list.updateCategory(category, "Non Categorizzati");
		}
	}	

	public void addShoppingList(String listName) {
		if (listName == null || listName.isEmpty()) {
			throw new IllegalArgumentException("nome della lista non valido.");
		}
		if (shoppingLists.containsKey(listName)) {
			throw new IllegalArgumentException("lista già esistente.");
		}
		shoppingLists.put(listName, new ShoppingList(listName));
	}

	public void removeShoppingList(String listName) {
		if (shoppingLists.remove(listName) == null) {
			throw new IllegalArgumentException("lista non trovata.");
		}
	}

	public ShoppingList getShoppingList(String listName) {
		ShoppingList list = shoppingLists.get(listName);
		if (list == null) {
			throw new IllegalArgumentException("lista non trovata.");
		}
		return list;
	}

	public Map<String, ShoppingList> getShoppingLists() {
		return shoppingLists;
	}

	public Set<String> getCategories() {
		return new HashSet<>(categories);
	}
}
