package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.io.FileOperationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Classe di test per verificare le funzionalità di `InputOutputList`
 * per il salvataggio ed il caricamento delle liste della spesa su file.
 */
public class InputOutputListTest {
	/** Istanza di InputOutputList utilizzata nei test. */
	private InputOutputList inputOutputList;

	/** Lista della spesa di test. */
	private ShoppingList shoppingList;

	/** Gestore delle categorie di test. */
	private CategoryManager categoryManager;

	/** File di test temporaneo. */
	private File testFile;

	/** Directory di test per i file. */
	private static final String TEST_DIRECTORY = "test_liste";

	/**
	 * Inizializza l'ambiente di test, creando le istanze necessarie e il file temporaneo.
	 *
	 * @throws InvalidInputException se c'è un errore nell'inizializzazione di un oggetto.
	 * @throws IOException se c'è un errore di I/O durante la creazione del file di test.
	 */
	@BeforeEach
	public void setUp() throws InvalidInputException, IOException {
		inputOutputList = new InputOutputList();
		shoppingList = new ShoppingList("test_lista");
		categoryManager = new CategoryManager();

		// Creazione di una cartella temporanea per i test
		File dir = new File(TEST_DIRECTORY);
		if (!dir.exists()) {
			dir.mkdir();
		}

		// Creazione di un file temporaneo per i test
		testFile = new File(TEST_DIRECTORY + "/test_file.txt");
		if (!testFile.exists()) {
			testFile.createNewFile();
		}
	}

	/** Pulisce l'ambiente di test eliminando il file e la cartella di test. */
	@AfterEach
	public void tearDown() {
		// Eliminazione del file di test
		if (testFile.exists()) {
			testFile.delete();
		}

		// Eliminazione della cartella di test
		File dir = new File(TEST_DIRECTORY);
		if (dir.exists()) {
			dir.delete();
		}
	}

	/**
	 * Verifica che il metodo saveToFile salvi correttamente una lista della spesa non vuota su file.
	 *
	 * @throws FileOperationException se c'è un errore durante il salvataggio.
	 * @throws InvalidInputException se la lista contiene dati non validi.
	 * @throws IOException se c'è un errore di I/O.
	 */
	@Test
	public void testSaveToFileWithNonEmptyShoppingList() throws FileOperationException, InvalidInputException, IOException {
		// Aggiunta di articoli alla lista della spesa
		shoppingList.addArticle(new Article("Latte", 1.5, 2, "Latticini"));
		shoppingList.addArticle(new Article("Pane", 2.0, 1, "Panetteria"));

		// Salvataggio su file
		inputOutputList.saveToFile(shoppingList, testFile);

		// Verifica del contenuto del file
		try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
			assertEquals("Latte,1.5,2,Latticini", reader.readLine());
			assertEquals("Pane,2.0,1,Panetteria", reader.readLine());
			assertNull(reader.readLine());
		}
	}

	/**
	 * Verifica che il metodo saveToFile salvi correttamente una lista della spesa vuota, lasciando il file vuoto.
	 *
	 * @throws FileOperationException se c'è un errore durante il salvataggio.
	 * @throws IOException se c'è un errore di I/O.
	 */
	@Test
	public void testSaveToFileWithEmptyShoppingList() throws FileOperationException, IOException {
		// Salvataggio di una lista vuota su file
		inputOutputList.saveToFile(shoppingList, testFile);

		// Verifica che il file sia vuoto
		try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
			assertNull(reader.readLine());
		}
	}

	/** Verifica che il metodo saveToFile lanci una FileOperationException con un percorso file non valido. */
	@Test
	public void testSaveToFileWithInvalidFilePath() {
		// Percorso file non valido
		File invalidFile = new File("/percorso/non/valido/test_file.txt");

		// Verifica che venga lanciata FileOperationException
		Exception exception = assertThrows(FileOperationException.class, () -> {
			inputOutputList.saveToFile(shoppingList, invalidFile);
		});

		assertTrue(exception.getMessage().contains("il salvataggio della lista non è andato a buon fine."));
	}

	/**
	 * Verifica che il metodo loadFromFile carichi correttamente una lista della spesa da un file contenente dati validi.
	 *
	 * @throws IOException se c'è un errore di I/O durante la lettura del file.
	 * @throws FileOperationException se c'è un errore durante il caricamento.
	 * @throws InvalidInputException se i dati del file non sono validi.
	 */
	@Test
	public void testLoadFromFileWithValidData() throws IOException, FileOperationException, InvalidInputException {
		// Scrittura di dati validi nel file di test
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
			writer.write("Latte,1.5,2,Latticini");
			writer.newLine();
			writer.write("Pane,2.0,1,Panetteria");
			writer.newLine();
		}

		// Caricamento dal file
		inputOutputList.loadFromFile(shoppingList, categoryManager, testFile);

		// Verifica del contenuto della lista della spesa
		assertEquals(2, shoppingList.getArticles().size());

		List<Article> articles = new ArrayList<>(shoppingList.getArticles());

		Article article1 = articles.get(0);
		assertEquals("Latte", article1.getName());
		assertEquals(1.5, article1.getCost());
		assertEquals(2, article1.getQuantity());
		assertEquals("Latticini", article1.getCategory());

		Article article2 = articles.get(1);
		assertEquals("Pane", article2.getName());
		assertEquals(2.0, article2.getCost());
		assertEquals(1, article2.getQuantity());
		assertEquals("Panetteria", article2.getCategory());

		// Verifica del contenuto del gestore delle categorie
		assertTrue(categoryManager.getCategories().contains("Latticini"));
		assertTrue(categoryManager.getCategories().contains("Panetteria"));
	}

	/** Verifica che il metodo loadFromFile lanci una FileOperationException quando il file non esiste. */
	@Test
	public void testLoadFromFileWithNonExistentFile() {
		// File non esistente
		File nonExistentFile = new File(TEST_DIRECTORY + "/inesistente.txt");

		// Verifica che venga lanciata FileOperationException
		Exception exception = assertThrows(FileOperationException.class, () -> {
			inputOutputList.loadFromFile(shoppingList, categoryManager, nonExistentFile);
		});

		assertTrue(exception.getMessage().contains("il file specificato non esiste."));
	}

	/**
	 * Verifica che il metodo loadFromFile lanci una FileOperationException quando il file contiene dati in un formato non valido.
	 *
	 * @throws IOException se c'è un errore di I/O durante la scrittura del file.
	 */
	@Test
	public void testLoadFromFileWithInvalidDataFormat() throws IOException {
		// Scrittura di dati non validi nel file di test
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
			writer.write("DatiNonValidiSenzaVirgole");
			writer.newLine();
		}

		// Verifica che venga lanciata FileOperationException
		Exception exception = assertThrows(FileOperationException.class, () -> {
			inputOutputList.loadFromFile(shoppingList, categoryManager, testFile);
		});

		assertTrue(exception.getMessage().contains("presenza di un articolo invalido all'interno del file"));
	}

	/**
	 * Verifica che il metodo loadFromFile lanci una InvalidInputException quando il file contiene valori non validi per costo o quantità.
	 *
	 * @throws IOException se c'è un errore di I/O durante la scrittura del file.
	 */
	@Test
	public void testLoadFromFileWithInvalidCostOrQuantity() throws IOException {
		// Scrittura di dati con costo e quantità non validi
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
			writer.write("Latte,costoNonValido,quantitàNonValida,Latticini");
			writer.newLine();
		}

		// Verifica che venga lanciata InvalidInputException
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			inputOutputList.loadFromFile(shoppingList, categoryManager, testFile);
		});

		assertTrue(exception.getMessage().contains("valori non validi per costo/quantità sull'articolo 'Latte'"));
	}

	/**
	 * Verifica che il metodo loadFromFile carichi correttamente una lista vuota quando il file è vuoto.
	 *
	 * @throws FileOperationException se c'è un errore durante il caricamento.
	 * @throws InvalidInputException se i dati del file non sono validi.
	 * @throws IOException se c'è un errore di I/O.
	 */
	@Test
	public void testLoadFromFileWithEmptyFile() throws FileOperationException, InvalidInputException, IOException {
		// Assicurarsi che il file di test sia vuoto
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
			// Non scrivere nulla
		}

		// Caricamento dal file
		inputOutputList.loadFromFile(shoppingList, categoryManager, testFile);

		// Verifica che la lista della spesa sia vuota
		assertTrue(shoppingList.getArticles().isEmpty());
	}

	/**
	 * Verifica che checkListsDir crei una cartella 'lists' quando non esiste.
	 *
	 * @throws FileOperationException se c'è un errore durante il controllo della cartella.
	 */
	@Test
	public void testCheckListsDirWhenDirectoryDoesNotExist() throws FileOperationException {
		// Eliminare la cartella 'lists' se esiste
		File dir = new File("lists");
		if (dir.exists()) {
			dir.delete();
		}

		// Verifica della creazione della cartella
		String dirPath = inputOutputList.checkListsDir();

		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		assertEquals("lists/", dirPath);

		// Pulizia
		dir.delete();
	}

	/**
	 * Verifica che checkListsDir restituisca correttamente il percorso se la cartella 'lists' esiste già.
	 *
	 * @throws FileOperationException se c'è un errore durante il controllo della cartella.
	 */
	@Test
	public void testCheckListsDirWhenDirectoryExists() throws FileOperationException {
		// Creazione della cartella 'lists' se non esiste
		File dir = new File("lists");
		if (!dir.exists()) {
			dir.mkdir();
		}

		// Verifica che la cartella esista
		String dirPath = inputOutputList.checkListsDir();

		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		assertEquals("lists/", dirPath);

		// Pulizia
		dir.delete();
	}

	/**
	 * Verifica che checkListsDir lanci una FileOperationException se esiste un file con il nome 'lists' invece di una cartella.
	 * ATTENZIONE: questo test cancella la cartella 'lists' e tutto il suo contenuto.
	 *
	 * @throws IOException se c'è un errore di I/O durante la creazione del file.
	 */
	@Test
	public void testCheckListsDirWhenDirectoryIsAFile() throws IOException {
		// Creazione di un file chiamato 'lists'
		File file = new File("lists");

		// Se 'lists' esiste ed è una directory, eliminarla insieme al suo contenuto
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					f.delete();
				}
				file.delete();
			} else {
				// Se 'lists' esiste ed è un file, eliminarlo
				file.delete();
			}
		}

		// Creazione del file 'lists'
		file.createNewFile();

		// Verifica che venga lanciata FileOperationException
		Exception exception = assertThrows(FileOperationException.class, () -> {
			inputOutputList.checkListsDir();
		});

		assertTrue(exception.getMessage().contains("'lists'' esiste ma non è una cartella."));

		// Pulizia
		file.delete();
	}
}
