package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.CategoryNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/** Classe di test per la gestione delle categorie tramite la classe `CategoryManager`. */
public class CategoryManagerTest {
	/** Oggetto CategoryManager utilizzato nei test. */
	private CategoryManager categoryManager;

	/** Imposta l'ambiente di test creando un nuovo CategoryManager. */
	@BeforeEach
	public void setUp() {
		categoryManager = new CategoryManager();
	}

	/** Pulisce l'ambiente di test rilasciando l'oggetto CategoryManager. */
	@AfterEach
	public void tearDown() {
		categoryManager = null;
	}

	/** Verifica che il costruttore inizializzi correttamente le categorie con la categoria di default. */
	@Test
	public void testConstructorInitialCategories() {
		Set<String> categories = categoryManager.getCategories();
		assertNotNull(categories, "Il set delle categorie non dovrebbe essere nullo.");
		assertEquals(1, categories.size(), "Il set delle categorie dovrebbe contenere solo la categoria di default.");
		assertTrue(categories.contains(Article.DEFAULT_CATEGORY), "Il set delle categorie dovrebbe contenere la categoria di default.");
	}

	/** Verifica che l'insieme delle categorie restituito sia non modificabile. */
	@Test
	public void testGetCategoriesIsUnmodifiable() {
		Set<String> categories = categoryManager.getCategories();
		assertThrows(UnsupportedOperationException.class, () -> categories.add("Nuova categoria"), "Il set delle categorie dovrebbe essere non modificabile.");
	}

	/**
	 * Verifica che sia possibile aggiungere una categoria valida.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testAddCategoryValid() throws InvalidInputException {
		categoryManager.addCategory("Elettronica");
		assertTrue(categoryManager.getCategories().contains("Elettronica"), "La categoria 'Elettronica' dovrebbe essere stata aggiunta.");
	}

	/** Verifica che il metodo addCategory lanci un'eccezione se il nome della categoria è nullo. */
	@Test
	public void testAddCategoryNull() {
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.addCategory(null));
		assertEquals("nome categoria invalido.", exception.getMessage());
	}

	/** Verifica che il metodo addCategory lanci un'eccezione se il nome della categoria è vuoto. */
	@Test
	public void testAddCategoryEmpty() {
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.addCategory(""));
		assertEquals("nome categoria invalido.", exception.getMessage());
	}

	/**
	 * Verifica che non sia possibile aggiungere una categoria già esistente.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testAddCategoryAlreadyExists() throws InvalidInputException {
		categoryManager.addCategory("Libri");
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.addCategory("Libri"));
		assertEquals("nome categoria invalido.", exception.getMessage());
	}

	/** Verifica che non sia possibile aggiungere la categoria di default. */
	@Test
	public void testAddCategoryDefaultCategory() {
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.addCategory(Article.DEFAULT_CATEGORY));
		assertEquals("nome categoria invalido.", exception.getMessage());
	}

	/**
	 * Verifica che sia possibile rimuovere una categoria valida.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 * @throws CategoryNotFoundException se la categoria non esiste.
	 */
	@Test
	public void testRemoveCategoryValid() throws InvalidInputException, CategoryNotFoundException {
		categoryManager.addCategory("Sport");
		categoryManager.removeCategory("Sport");
		assertFalse(categoryManager.getCategories().contains("Sport"), "La categoria 'Sport' dovrebbe essere stata rimossa.");
	}

	/** Verifica che il metodo removeCategory lanci un'eccezione se il nome della categoria è nullo. */
	@Test
	public void testRemoveCategoryNull() {
		CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryManager.removeCategory(null));
		assertEquals("la categoria non esiste.", exception.getMessage());
	}

	/** Verifica che il metodo removeCategory lanci un'eccezione se il nome della categoria è vuoto. */
	@Test
	public void testRemoveCategoryEmpty() {
		CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryManager.removeCategory(""));
		assertEquals("la categoria non esiste.", exception.getMessage());
	}

	/** Verifica che il metodo removeCategory lanci un'eccezione se la categoria non esiste. */
	@Test
	public void testRemoveCategoryDoesNotExist() {
		CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryManager.removeCategory("NonEsistente"));
		assertEquals("la categoria non esiste.", exception.getMessage());
	}

	/** Verifica che non sia possibile rimuovere la categoria di default. */
	@Test
	public void testRemoveCategoryDefaultCategory() {
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.removeCategory(Article.DEFAULT_CATEGORY));
		String expectedMessage = "non è possibile rimuovere la categoria '" + Article.DEFAULT_CATEGORY + "'.";
		assertEquals(expectedMessage, exception.getMessage());
	}

	/**
	 * Verifica che il metodo updateCategoryInAllLists aggiorni la categoria di articoli con categoria corrispondente.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testUpdateCategoryInAllListsWithMatchingCategory() throws InvalidInputException {
		// Creazione degli articoli
		Article article1 = new Article("Latte", 1.0, 1, "Latticini");
		Article article2 = new Article("Formaggio", 2.0, 2, "Latticini");
		Article article3 = new Article("Pane", 1.5, 1, "Forno");

		// Creazione delle liste della spesa
		ShoppingList shoppingList1 = new ShoppingList("Lista 1");
		shoppingList1.addArticle(article1);
		shoppingList1.addArticle(article2);

		ShoppingList shoppingList2 = new ShoppingList("Lista 2");
		shoppingList2.addArticle(article3);

		Collection<ShoppingList> shoppingLists = Arrays.asList(shoppingList1, shoppingList2);

		// Aggiornamento della categoria "Latticini"
		categoryManager.updateCategoryInAllLists(shoppingLists, "Latticini");

		// Verifica che gli articoli con categoria "Latticini" siano aggiornati alla categoria di default
		assertEquals(Article.DEFAULT_CATEGORY, article1.getCategory());
		assertEquals(Article.DEFAULT_CATEGORY, article2.getCategory());

		// Verifica che gli altri articoli non siano stati modificati
		assertEquals("Forno", article3.getCategory());
	}

	/**
	 * Verifica che il metodo updateCategoryInAllLists non modifichi gli articoli se non ci sono categorie corrispondenti.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testUpdateCategoryInAllListsWithoutMatchingCategory() throws InvalidInputException {
		// Creazione degli articoli
		Article article1 = new Article("Mela", 0.5, 3, "Frutta");
		Article article2 = new Article("Pane", 1.0, 1, "Forno");

		// Creazione della lista della spesa
		ShoppingList shoppingList = new ShoppingList("Lista 1");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);

		Collection<ShoppingList> shoppingLists = Collections.singletonList(shoppingList);

		// Aggiornamento di una categoria che non esiste negli articoli
		categoryManager.updateCategoryInAllLists(shoppingLists, "Verdura");

		// Verifica che nessun articolo sia stato modificato
		assertEquals("Frutta", article1.getCategory());
		assertEquals("Forno", article2.getCategory());
	}

	/**
	 * Verifica che il metodo updateCategoryInAllLists non lanci eccezioni con liste di spesa vuote.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testUpdateCategoryInAllListsWithEmptyShoppingLists() throws InvalidInputException {
		Collection<ShoppingList> shoppingLists = new ArrayList<>();
		categoryManager.updateCategoryInAllLists(shoppingLists, "Latticini");
		// Nessuna eccezione dovrebbe essere lanciata
	}

	/** Verifica che il metodo updateCategoryInAllLists lanci un'eccezione se le liste di spesa sono nulle. */
	@Test
	public void testUpdateCategoryInAllListsWithNullShoppingLists() {
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.updateCategoryInAllLists(null, "Latticini"));
		assertEquals("le liste e la categoria da rimuovere non possono essere nulli.", exception.getMessage());
	}

	/**
	 * Verifica che il metodo updateCategoryInAllLists lanci un'eccezione se la categoria da rimuovere è nulla.
	 *
	 * @throws InvalidInputException se il nome della categoria è invalido.
	 */
	@Test
	public void testUpdateCategoryInAllListsWithNullCategoryToRemove() throws InvalidInputException {
		// Creazione di un articolo e una lista della spesa
		Article article = new Article("Latte", 1.0, 1, "Latticini");
		ShoppingList shoppingList = new ShoppingList("Lista 1");
		shoppingList.addArticle(article);

		Collection<ShoppingList> shoppingLists = Collections.singletonList(shoppingList);

		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> categoryManager.updateCategoryInAllLists(shoppingLists, null));
		assertEquals("le liste e la categoria da rimuovere non possono essere nulli.", exception.getMessage());
	}
}
