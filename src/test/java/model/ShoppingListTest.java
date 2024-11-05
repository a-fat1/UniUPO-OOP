package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ArticleNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe di test per verificare le funzionalità della classe `ShoppingList`,
 * incluse le operazioni di aggiunta, rimozione e ricerca degli articoli,
 * oltre alla gestione della quantità e del costo totale degli articoli.
 */
public class ShoppingListTest {
	/** Oggetto ShoppingList utilizzato nei test per la lista. */
	private ShoppingList shoppingList;

	/**
	 * Inizializza l'ambiente di test creando una nuova istanza di ShoppingList.
	 *
	 * @throws InvalidInputException se il nome della lista non è valido
	 */
	@BeforeEach
	public void setUp() throws InvalidInputException {
		shoppingList = new ShoppingList("TestLista");
	}

	/**
	 * Verifica che il costruttore crei una lista con un nome valido.
	 *
	 * @throws InvalidInputException se il nome della lista non è valido.
	 */
	@Test
	public void testConstructorWithValidName() throws InvalidInputException {
		ShoppingList list = new ShoppingList("LaMiaLista");
		assertEquals("LaMiaLista", list.getName());
	}

	/** Verifica che venga lanciata InvalidInputException quando si tenta di creare una lista con nome nullo. */
	@Test
	public void testConstructorWithNullName() {
		// Tentativo di creare una lista con nome null
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			new ShoppingList(null);
		});

		// Verifica che il messaggio dell'eccezione sia quello atteso
		assertEquals("nome lista non valido.", exception.getMessage());
	}

	/** Verifica che venga lanciata InvalidInputException quando si tenta di creare una lista con nome vuoto. */
	@Test
	public void testConstructorWithEmptyName() {
		// Tentativo di creare una lista con nome vuoto
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			new ShoppingList("");
		});

		// Verifica che il messaggio dell'eccezione sia quello atteso
		assertEquals("nome lista non valido.", exception.getMessage());
	}

	/**
	 * Verifica che sia possibile aggiungere un articolo valido alla lista della spesa.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testAddArticleWithValidArticle() throws InvalidInputException {
		Article article = new Article("Latte", 1.5, 2, "Latticini");
		shoppingList.addArticle(article);
		assertEquals(1, shoppingList.getArticles().size());
		assertTrue(shoppingList.getArticles().contains(article));
	}

	/** Verifica che venga lanciata InvalidInputException quando si tenta di aggiungere un articolo nullo. */
	@Test
	public void testAddArticleWithNullArticle() {
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			shoppingList.addArticle(null);
		});
		assertEquals("articolo non valido.", exception.getMessage());
	}

	/**
	 * Verifica che sia possibile recuperare un articolo esistente per nome.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testGetArticleByNameWithExistingArticle() throws InvalidInputException {
		// Creazione e aggiunta di un articolo alla lista
		Article article = new Article("Pane", 2.0, 1, "Panetteria");
		shoppingList.addArticle(article);

		// Recupero dell'articolo tramite il nome
		Article retrievedArticle = shoppingList.getArticleByName("Pane");

		// Verifica che l'articolo recuperato non sia null e che sia uguale a quello aggiunto
		assertNotNull(retrievedArticle);
		assertEquals(article, retrievedArticle);
	}

	/** Verifica che venga restituito null quando si cerca un articolo non esistente per nome. */
	@Test
	public void testGetArticleByNameWithNonExistingArticle() {
		// Tentativo di recuperare un articolo non presente nella lista
		Article article = shoppingList.getArticleByName("NonEsistente");

		// Verifica che il risultato sia nullo
		assertNull(article);
	}

	/**
	 * Verifica che sia possibile rimuovere un articolo esistente dalla lista.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 * @throws ArticleNotFoundException se l'articolo non viene trovato.
	 */
	@Test
	public void testRemoveArticleWithExistingArticle() throws InvalidInputException, ArticleNotFoundException {
		Article article = new Article("Burro", 1.0, 1, "Latticini");
		shoppingList.addArticle(article);
		shoppingList.removeArticle("Burro");
		assertFalse(shoppingList.getArticles().contains(article));
	}

	/** Verifica che venga lanciata ArticleNotFoundException quando si tenta di rimuovere un articolo non esistente. */
	@Test
	public void testRemoveArticleWithNonExistingArticle() {
		Exception exception = assertThrows(ArticleNotFoundException.class, () -> {
			shoppingList.removeArticle("NonEsistente");
		});
		assertEquals("articolo non trovato.", exception.getMessage());
	}

	/**
	 * Verifica che tutti gli articoli vengano rimossi dalla lista quando viene chiamato il metodo clearArticles.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testClearArticles() throws InvalidInputException {
		// Aggiunta di due articoli alla lista
		Article article1 = new Article("Mela", 0.5, 5, "Frutta");
		Article article2 = new Article("Carota", 0.3, 10, "Verdura");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);

		// Rimozione di tutti gli articoli
		shoppingList.clearArticles();

		// Verifica che la lista sia vuota
		assertTrue(shoppingList.getArticles().isEmpty());
	}

	/** Verifica che venga lanciata InvalidInputException quando la stringa di ricerca è nulla o vuota. */
	@Test
	public void testFindArticlesWithNullOrEmptySearchString() {
		// Test per stringa di ricerca nulla
		Exception exceptionNull = assertThrows(InvalidInputException.class, () -> {
			shoppingList.findArticles(null, false);
		});
		assertEquals("stringa di ricerca non valida.", exceptionNull.getMessage());

		// Test per stringa di ricerca vuota
		Exception exceptionEmpty = assertThrows(InvalidInputException.class, () -> {
			shoppingList.findArticles("", false);
		});
		assertEquals("stringa di ricerca non valida.", exceptionEmpty.getMessage());
	}

	/**
	 * Verifica che la ricerca non sia sensibile alla differenza tra maiuscole e minuscole
	 * sia per il nome che per la categoria.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 * @throws ArticleNotFoundException se non ci sono articoli che corrispondono al criterio di ricerca.
	 */
	@Test
	public void testFindArticlesCaseInsensitive() throws InvalidInputException, ArticleNotFoundException {
		Article articleByName = new Article("Banana", 0.2, 5, "Frutta");
		Article articleByCategory = new Article("Latte", 1.5, 2, "LATTICINI");
		shoppingList.addArticle(articleByName);
		shoppingList.addArticle(articleByCategory);

		// Verifica che la ricerca per nome "bAn" sia insensibile al case
		List<Article> nameResults = shoppingList.findArticles("bAn", false);
		assertEquals(1, nameResults.size());
		assertEquals(articleByName, nameResults.get(0));

		// Verifica che la ricerca per categoria "latticini" sia insensibile al case
		List<Article> categoryResults = shoppingList.findArticles("latticini", true);
		assertEquals(1, categoryResults.size());
		assertEquals(articleByCategory, categoryResults.get(0));
	}

	/**
	 * Verifica che sia possibile trovare articoli per prefisso con successo.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 * @throws ArticleNotFoundException se non ci sono articoli che corrispondono al criterio di ricerca.
	 */
	@Test
	public void testFindArticlesByNameWithResults() throws InvalidInputException, ArticleNotFoundException {
		// Aggiunta di articoli alla lista
		Article article1 = new Article("Banana", 0.2, 5, "Frutta");
		Article article2 = new Article("Biscotti", 1.0, 2, "Dolci");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);

		// Ricerca di articoli con il prefisso "Bi"
		List<Article> results = shoppingList.findArticles("Bi", false);

		// Verifica che solo l'articolo "Biscotti" sia stato trovato
		assertEquals(1, results.size());
		assertEquals(article2, results.get(0));
	}

	/**
	 * Verifica che venga lanciata ArticleNotFoundException quando nessun articolo corrisponde al prefisso.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testFindArticlesByNameNoResults() throws InvalidInputException {
		Article article1 = new Article("Acqua", 0.5, 6, "Bevande");
		shoppingList.addArticle(article1);
		Exception exception = assertThrows(ArticleNotFoundException.class, () -> {
			shoppingList.findArticles("NoCorrispondenza", false);
		});
		assertEquals("nessun articolo presente con il prefisso ricercato.", exception.getMessage());
	}

	/**
	 * Verifica che sia possibile trovare articoli per categoria con risultato positivo.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 * @throws ArticleNotFoundException se la categoria non esiste.
	 */
	@Test
	public void testFindArticlesByCategoryWithResults() throws InvalidInputException, ArticleNotFoundException {
		// Aggiunta di articoli con diverse categorie
		Article article1 = new Article("Latte", 1.5, 2, "Latticini");
		Article article2 = new Article("Formaggio", 2.5, 1, "Latticini");
		Article article3 = new Article("Pane", 1.0, 3, "Panetteria");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);
		shoppingList.addArticle(article3);

		// Ricerca di articoli per la categoria "Latticini"
		List<Article> results = shoppingList.findArticles("Latticini", true);

		// Verifica che entrambi gli articoli "Latte" e "Formaggio" siano stati trovati
		assertEquals(2, results.size());
		assertTrue(results.contains(article1));
		assertTrue(results.contains(article2));
	}

	/**
	 * Verifica che venga lanciata ArticleNotFoundException quando nessun articolo corrisponde alla categoria.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testFindArticlesByCategoryNoResults() throws InvalidInputException {
		Article article1 = new Article("Carne", 5.0, 1, "Macelleria");
		shoppingList.addArticle(article1);
		Exception exception = assertThrows(ArticleNotFoundException.class, () -> {
			shoppingList.findArticles("Pesce", true);
		});
		assertEquals("la categoria ricercata non esiste.", exception.getMessage());
	}

	/** Verifica che il metodo getTotalFromList restituisca quantità e costo totali pari a zero quando la lista è vuota. */
	@Test
	public void testGetTotalFromListEmptyList() {
		Map<String, Object> totals = shoppingList.getTotalFromList();
		assertEquals(0, totals.get(ShoppingList.KEY_QUANTITY));
		assertEquals(0.0, totals.get(ShoppingList.KEY_COST));
	}

	/**
	 * Verifica che il metodo getTotalFromList restituisca correttamente la quantità e il costo totali quando la lista contiene articoli.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testGetTotalFromListWithArticles() throws InvalidInputException {
		// Aggiunta di articoli alla lista
		Article article1 = new Article("Uova", 0.2, 12, "Alimentari");
		Article article2 = new Article("Farina", 1.0, 2, "Alimentari");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);

		// Ottenimento del totale quantità e costo per la lista
		Map<String, Object> totals = shoppingList.getTotalFromList();

		// Verifica che la quantità e il costo siano corretti
		assertEquals(14, totals.get(ShoppingList.KEY_QUANTITY));
		double expectedTotalCost = 0.2 * 12 + 1.0 * 2;
		assertEquals(expectedTotalCost, (double) totals.get(ShoppingList.KEY_COST), 0.0001);
	}

	/**
	 * Verifica che il metodo iterator restituisca un iteratore per scorrere tutti gli articoli della lista.
	 *
	 * @throws InvalidInputException se l'articolo non è valido.
	 */
	@Test
	public void testIterator() throws InvalidInputException {
		// Creazione e aggiunta di articoli alla lista
		Article article1 = new Article("Pasta", 1.0, 3, "Alimentari");
		Article article2 = new Article("Pomodoro", 0.5, 4, "Verdura");
		shoppingList.addArticle(article1);
		shoppingList.addArticle(article2);

		// Ottenimento di un iteratore per gli articoli della lista
		Iterator<Article> iterator = shoppingList.iterator();

		// Raccolta degli articoli tramite l'iteratore
		List<Article> articles = new ArrayList<>();
		while (iterator.hasNext()) {
			articles.add(iterator.next());
		}

		// Verifica che l'elenco contenga entrambi gli articoli
		assertEquals(2, articles.size());
		assertTrue(articles.contains(article1));
		assertTrue(articles.contains(article2));
	}

	/** Verifica che il metodo toString restituisca il nome della lista. */
	@Test
	public void testToString() {
		assertEquals("TestLista", shoppingList.toString());
	}
}
