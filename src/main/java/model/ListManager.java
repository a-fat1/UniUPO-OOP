package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * `ListManager` gestisce le liste della spesa, e permette
 * di aggiungere, rimuovere e ottenere liste specifiche.
 */
public class ListManager {
	/** Mappa delle liste della spesa, con i nomi delle liste come chiavi. */
	private Map<String, ShoppingList> shoppingLists;

	/**
	 * Inizializza un gestore di liste della spesa creando
	 * una nuova mappa `HashMap` vuota per gestire le liste della spesa.
	 */
	public ListManager() {
		shoppingLists = new HashMap<>();
	}

	/**
	 * Restituisce una lista della spesa specificata dal nome.
	 *
	 * @param listName il nome della lista della spesa da recuperare.
	 * @return La lista della spesa corrispondente al nome specificato.
	 * @throws ListNotFoundException se la lista con il nome specificato non esiste.
	 */
	public ShoppingList getShoppingList(String listName) throws ListNotFoundException {
		ShoppingList list = shoppingLists.get(listName);
		if (list == null) {
			throw new ListNotFoundException("lista non trovata.");
		}
		return list;
	}

	/**
	 * Restituisce tutte le liste della spesa.
	 *
	 * @return Una collezione di tutte le liste della spesa.
	 */
	public Collection<ShoppingList> getShoppingLists() {
		return shoppingLists.values();
	}

	/**
	 * Aggiunge una nuova lista della spesa con il nome specificato.
	 *
	 * @param listName il nome della nuova lista della spesa.
	 * @throws InvalidInputException se il nome è nullo, vuoto, o se una lista con lo stesso nome è già presente.
	 */
	public void addShoppingList(String listName) throws InvalidInputException {
		if (listName == null || listName.isEmpty()) {
			throw new InvalidInputException("nome lista invalido.");
		} else if (shoppingLists.containsKey(listName)) {
			throw new InvalidInputException("la lista è già presente.");
		}
		shoppingLists.put(listName, new ShoppingList(listName));
	}

	/**
	 * Rimuove una lista della spesa specificata dal nome.
	 *
	 * @param listName il nome della lista della spesa da rimuovere.
	 * @throws ListNotFoundException se la lista da rimuovere non esiste.
	 */
	public void removeShoppingList(String listName) throws ListNotFoundException {
		if (shoppingLists.remove(listName) == null) {
			throw new ListNotFoundException("la lista da eliminare non esiste.");
		}
	}
}
