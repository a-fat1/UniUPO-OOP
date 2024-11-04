package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/** Classe di test per la gestione delle liste della spesa tramite la classe `ListManager`. */
public class ListManagerTest {
	/** Istanza di ListManager utilizzata nei test. */
	private ListManager listManager;

	/** Inizializza l'ambiente di test creando una nuova istanza di ListManager. */
	@BeforeEach
	public void setUp() {
		listManager = new ListManager();
	}

	/**
	 * Verifica che sia possibile aggiungere una lista della spesa con un nome valido.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 * @throws ListNotFoundException se la lista non viene trovata.
	 */
	@Test
	public void testAddShoppingListWithValidName() throws InvalidInputException, ListNotFoundException {
		String listName = "ListaSpesa";

		listManager.addShoppingList(listName);

		// Verifica che la lista sia stata aggiunta
		assertNotNull(listManager.getShoppingList(listName));
	}

	/** Verifica che venga lanciata un'eccezione InvalidInputException quando si tenta di aggiungere una lista con nome null. */
	@Test
	public void testAddShoppingListWithNullName() {
		// Verifica che venga lanciata InvalidInputException
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			listManager.addShoppingList(null);
		});

		assertTrue(exception.getMessage().contains("nome lista invalido."));
	}

	/** Verifica che venga lanciata un'eccezione InvalidInputException quando si tenta di aggiungere una lista con nome vuoto. */
	@Test
	public void testAddShoppingListWithEmptyName() {
		// Verifica che venga lanciata InvalidInputException
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			listManager.addShoppingList("");
		});

		assertTrue(exception.getMessage().contains("nome lista invalido."));
	}

	/**
	 * Verifica che venga lanciata un'eccezione InvalidInputException quando si tenta di aggiungere una lista con nome duplicato.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 */
	@Test
	public void testAddShoppingListWithDuplicateName() throws InvalidInputException {
		String listName = "ListaSpesa";

		listManager.addShoppingList(listName);

		// Verifica che venga lanciata InvalidInputException per nome duplicato
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			listManager.addShoppingList(listName);
		});

		assertTrue(exception.getMessage().contains("la lista è già presente."));
	}

	/**
	 * Verifica che sia possibile recuperare una lista della spesa esistente tramite il suo nome.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 * @throws ListNotFoundException se la lista non viene trovata.
	 */
	@Test
	public void testGetShoppingListWithExistingList() throws InvalidInputException, ListNotFoundException {
		String listName = "ListaSpesa";

		listManager.addShoppingList(listName);

		ShoppingList shoppingList = listManager.getShoppingList(listName);

		assertNotNull(shoppingList);
		assertEquals(listName, shoppingList.getName());
	}

	/** Verifica che venga lanciata un'eccezione ListNotFoundException quando si tenta di recuperare una lista non esistente. */
	@Test
	public void testGetShoppingListWithNonExistentList() {
		String listName = "ListaInesistente";

		// Verifica che venga lanciata ListNotFoundException
		Exception exception = assertThrows(ListNotFoundException.class, () -> {
			listManager.getShoppingList(listName);
		});

		assertTrue(exception.getMessage().contains("lista non trovata."));
	}

	/** Verifica che il metodo getShoppingLists restituisca un insieme vuoto quando non ci sono liste. */
	@Test
	public void testGetShoppingListsWhenEmpty() {
		Collection<ShoppingList> shoppingLists = listManager.getShoppingLists();

		assertNotNull(shoppingLists);
		assertTrue(shoppingLists.isEmpty());
	}

	/**
	 * Verifica che il metodo getShoppingLists restituisca tutte le liste della spesa aggiunte.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 */
	@Test
	public void testGetShoppingListsWithMultipleLists() throws InvalidInputException {
		// Aggiunge due liste della spesa con nomi distinti
		listManager.addShoppingList("ListaSpesa1");
		listManager.addShoppingList("ListaSpesa2");

		// Ottiene tutte le liste della spesa presenti nel gestore
		Collection<ShoppingList> shoppingLists = listManager.getShoppingLists();

		// Verifica che il numero di liste recuperate sia pari a due
		assertEquals(2, shoppingLists.size());

		// Crea un set per memorizzare i nomi delle liste ottenute
		Set<String> listNames = new HashSet<>();
		for (ShoppingList list : shoppingLists) {
			listNames.add(list.getName());
		}

		// Verifica che entrambe le liste aggiunte siano presenti nella collezione
		assertTrue(listNames.contains("ListaSpesa1"));
		assertTrue(listNames.contains("ListaSpesa2"));
	}

	/**
	 * Verifica che sia possibile rimuovere una lista esistente e che non sia più accessibile dopo la rimozione.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 * @throws ListNotFoundException se la lista non viene trovata.
	 */
	@Test
	public void testRemoveShoppingListWithExistingList() throws InvalidInputException, ListNotFoundException {
		String listName = "ListaSpesa";

		listManager.addShoppingList(listName);

		// Rimozione della lista
		listManager.removeShoppingList(listName);

		// Verifica che la lista sia stata rimossa
		Exception exception = assertThrows(ListNotFoundException.class, () -> {
			listManager.getShoppingList(listName);
		});

		assertTrue(exception.getMessage().contains("lista non trovata."));
	}

	/** Verifica che venga lanciata un'eccezione ListNotFoundException quando si tenta di rimuovere una lista non esistente. */
	@Test
	public void testRemoveShoppingListWithNonExistentList() {
		String listName = "ListaInesistente";

		// Verifica che venga lanciata ListNotFoundException
		Exception exception = assertThrows(ListNotFoundException.class, () -> {
			listManager.removeShoppingList(listName);
		});

		assertTrue(exception.getMessage().contains("la lista da eliminare non esiste."));
	}

	/**
	 * Verifica che le operazioni su liste diverse non si influenzino a vicenda.
	 *
	 * @throws InvalidInputException se il nome della lista è invalido.
	 * @throws ListNotFoundException se la lista non viene trovata.
	 */
	@Test
	public void testOperationsDoNotAffectEachOther() throws InvalidInputException, ListNotFoundException {
		String listName1 = "Lista1";
		String listName2 = "Lista2";

		// Aggiunta di due liste
		listManager.addShoppingList(listName1);
		listManager.addShoppingList(listName2);

		// Rimozione di una lista
		listManager.removeShoppingList(listName1);

		// Verifica che la seconda lista esista ancora
		ShoppingList remainingList = listManager.getShoppingList(listName2);
		assertNotNull(remainingList);
		assertEquals(listName2, remainingList.getName());

		// Verifica che la lista rimossa non esista più
		Exception exception = assertThrows(ListNotFoundException.class, () -> {
			listManager.getShoppingList(listName1);
		});

		assertTrue(exception.getMessage().contains("lista non trovata."));
	}
}
