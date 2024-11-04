package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.CategoryNotFoundException;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

/**
 * `CategoryManager` gestisce le categorie degli articoli,
 * permettendo di aggiungere, rimuovere e aggiornare le categorie.
 */
public class CategoryManager {
	/** L'insieme delle categorie disponibili. */
	private Set<String> categories;

	/**
	 * Inizializza il gestore delle categorie creando un nuovo `HashSet` vuoto
	 * e aggiunge la categoria predefinita {@link Article#DEFAULT_CATEGORY}.
	 */
	public CategoryManager() {
		categories = new HashSet<>();
		categories.add(Article.DEFAULT_CATEGORY);
	}

	/**
	 * Restituisce un insieme immutabile delle categorie disponibili.
	 * 
	 * @return l'insieme delle categorie, vuoto se non ci sono categorie.
	 */
	public Set<String> getCategories() {
		return categories.isEmpty() ? Collections.emptySet() : Collections.unmodifiableSet(categories);
	}

	/**
	 * Aggiunge una nuova categoria se valida e non già presente.
	 * 
	 * @param category il nome della nuova categoria.
	 * @throws InvalidInputException se la categoria è nulla, vuota, già presente, o è la categoria predefinita.
	 */
	public void addCategory(String category) throws InvalidInputException {
		if(category == null || category.isEmpty() || categories.contains(category) || category.equals(Article.DEFAULT_CATEGORY)) {
			throw new InvalidInputException("nome categoria invalido.");
		}
		categories.add(category);
	}

	/**
	 * Rimuove una categoria specificata, se esistente e valida.
	 * Non è possibile rimuovere la categoria predefinita.
	 * 
	 * @param category il nome della categoria da rimuovere.
	 * @throws InvalidInputException se si tenta di rimuovere la categoria predefinita.
	 * @throws CategoryNotFoundException se la categoria specificata non esiste.
	 */
	public void removeCategory(String category) throws InvalidInputException, CategoryNotFoundException {
		if (Article.DEFAULT_CATEGORY.equals(category)) {
			throw new InvalidInputException("non è possibile rimuovere la categoria '" + Article.DEFAULT_CATEGORY + "''.");
		}
		if (!categories.remove(category)) {
			throw new CategoryNotFoundException("la categoria non esiste.");
		}
	}

	/**
	 * Scorre tutte le liste della spesa e sostituisce la categoria cancellata
	 * con la categoria predefinita {@link Article#DEFAULT_CATEGORY}
	 * per tutti gli articoli che appartengono alla categoria rimossa.
	 * 
	 * @param shoppingLists le liste in cui aggiornare le categorie.
	 * @param categoryToRemove la categoria da rimuovere.
	 * @throws InvalidInputException se le liste della spesa o la categoria da rimuovere sono nulli.
	 */
	public void updateCategoryInAllLists(Collection<ShoppingList> shoppingLists, String categoryToRemove) throws InvalidInputException {
		if (shoppingLists == null || categoryToRemove == null) {
			throw new InvalidInputException("le liste e la categoria da rimuovere non possono essere nulli.");
		}
		for (ShoppingList shoppingList : shoppingLists) {
			for (Article article : shoppingList) {
				if (categoryToRemove.equals(article.getCategory())) {
					article.setCategory(Article.DEFAULT_CATEGORY);
				}
			}
		}
	}
}
