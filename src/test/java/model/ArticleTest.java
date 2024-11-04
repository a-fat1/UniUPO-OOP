package model;

import model.exceptions.io.InvalidInputException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Classe di test per la classe `Article`.
 * Verifica la corretta gestione dei campi e delle eccezioni
 * in fase di creazione e modifica di un oggetto `Article`.
 */
public class ArticleTest {
	/** Istanza di `Article` utilizzata nei test. */
	private Article article;

	/**
	 * Configura un'istanza di `Article` prima di ogni test.
	 * 
	 * @throws InvalidInputException se i parametri dell'articolo sono non validi.
	 */
	@BeforeEach
	public void setUp() throws InvalidInputException {
		article = new Article("Test articolo", 10.0, 5, "Test categoria");
	}

	/** Rimuove l'istanza di `Article` dopo ogni test. */
	@AfterEach
	public void tearDown() {
		article = null;
	}

	/**
	 * Verifica che il costruttore inizializzi correttamente un articolo con valori validi.
	 *
	 * @throws InvalidInputException se i parametri dell'articolo sono non validi.
	 */
	@Test
	public void testConstructorWithValidInputs() throws InvalidInputException {
		// Creazione di un articolo con parametri validi
		Article art = new Article("Nome valido", 15.0, 10, "Categoria valida");

		// Verifica dei valori impostati
		assertEquals("Nome valido", art.getName());
		assertEquals(15.0, art.getCost());
		assertEquals(10, art.getQuantity());
		assertEquals("Categoria valida", art.getCategory());
	}

	/** Verifica che il costruttore lanci un'eccezione per un nome nullo o vuoto. */
	@Test
	public void testConstructorWithInvalidName() {
		// Test per nome nullo
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			new Article(null, 10.0, 5, "Categoria");
		});
		assertEquals("nome articolo invalido.", exception.getMessage());

		// Test per nome vuoto
		exception = assertThrows(InvalidInputException.class, () -> {
			new Article("", 10.0, 5, "Categoria");
		});
		assertEquals("nome articolo invalido.", exception.getMessage());
	}

	/**
	 * Verifica che il costruttore imposti il costo a zero per un valore negativo.
	 *
	 * @throws InvalidInputException se i parametri dell'articolo sono non validi.
	 */
	@Test
	public void testConstructorWithNegativeCost() throws InvalidInputException {
		Article art = new Article("Nome", -5.0, 5, "Categoria");
		assertEquals(0.0, art.getCost());
	}

	/**
	 * Verifica che il costruttore imposti la quantità a 1 per un valore non positivo.
	 *
	 * @throws InvalidInputException se i parametri dell'articolo sono non validi.
	 */
	@Test
	public void testConstructorWithNonPositiveQuantity() throws InvalidInputException {
		Article artZero = new Article("Nome", 10.0, 0, "Categoria");
		assertEquals(1, artZero.getQuantity());

		Article artNegative = new Article("Nome", 10.0, -3, "Categoria");
		assertEquals(1, artNegative.getQuantity());
	}

	/**
	 * Verifica che il costruttore imposti la categoria predefinita
	 * quando la categoria è nulla o vuota.
	 *
	 * @throws InvalidInputException se i parametri dell'articolo sono non validi.
	 */
	@Test
	public void testConstructorWithInvalidCategory() throws InvalidInputException {
		Article artNullCategory = new Article("Nome", 10.0, 5, null);
		assertEquals(Article.DEFAULT_CATEGORY, artNullCategory.getCategory());

		Article artEmptyCategory = new Article("Nome", 10.0, 5, "");
		assertEquals(Article.DEFAULT_CATEGORY, artEmptyCategory.getCategory());
	}

	/**
	 * Verifica che `setName` aggiorni il nome con un valore valido.
	 *
	 * @throws InvalidInputException se il nome è non valido.
	 */
	@Test
	public void testSetNameWithValidInput() throws InvalidInputException {
		article.setName("Nuovo nome valido");
		assertEquals("Nuovo nome valido", article.getName());
	}

	/** Verifica che `setName` lanci un'eccezione per un nome nullo o vuoto. */
	@Test
	public void testSetNameWithInvalidInput() {
		// Test per nome nullo
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			article.setName(null);
		});
		assertEquals("nome articolo invalido.", exception.getMessage());

		// Test per nome vuoto
		exception = assertThrows(InvalidInputException.class, () -> {
			article.setName("");
		});
		assertEquals("nome articolo invalido.", exception.getMessage());
	}

	/** Verifica che `setCost` aggiorni correttamente il costo con un valore valido. */
	@Test
	public void testSetCostWithValidInput() {
		article.setCost(20.0);
		assertEquals(20.0, article.getCost());
	}

	/** Verifica che `setCost` imposti il costo a zero per un valore negativo. */
	@Test
	public void testSetCostWithNegativeInput() {
		article.setCost(-10.0);
		assertEquals(0.0, article.getCost());
	}

	/** Verifica che `setQuantity` aggiorni la quantità con un valore valido. */
	@Test
	public void testSetQuantityWithValidInput() {
		article.setQuantity(8);
		assertEquals(8, article.getQuantity());
	}

	/** Verifica che `setQuantity` imposti la quantità a 1 per un valore non positivo. */
	@Test
	public void testSetQuantityWithNonPositiveInput() {
		article.setQuantity(0);
		assertEquals(1, article.getQuantity());

		article.setQuantity(-5);
		assertEquals(1, article.getQuantity());
	}

	/**
	 * Verifica che `setCategory` aggiorni correttamente la categoria con un valore valido.
	 *
	 * @throws InvalidInputException se la categoria è non valida.
	 */
	@Test
	public void testSetCategoryWithValidInput() throws InvalidInputException {
		article.setCategory("Nuova categoria valida");
		assertEquals("Nuova categoria valida", article.getCategory());
	}

	/**
	 * Verifica che `setCategory` imposti la categoria predefinita per un valore nullo o vuoto.
	 *
	 * @throws InvalidInputException se la categoria è non valida.
	 */
	@Test
	public void testSetCategoryWithInvalidInput() throws InvalidInputException {
		article.setCategory(null);
		assertEquals(Article.DEFAULT_CATEGORY, article.getCategory());

		article.setCategory("");
		assertEquals(Article.DEFAULT_CATEGORY, article.getCategory());
	}
}
