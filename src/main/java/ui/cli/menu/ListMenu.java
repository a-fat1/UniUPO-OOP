package ui.cli.menu;

import ui.cli.base.BaseMenu;

import model.ListManager;
import model.CategoryManager;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;

/**
 * Menu CLI per la gestione delle liste.
 * Permette di creare, rimuovere e gestire le liste della spesa.
 * Estende la classe `BaseMenu`.
 */
public class ListMenu extends BaseMenu {
	/** Gestore delle categorie. */
	private CategoryManager categoryManager;

	/** Gestore delle liste della spesa. */
	private ListManager listManager;

	/**
	 * Inizializza il `ListMenu` con il gestore delle categorie e delle liste.
	 *
	 * @param categoryManager il gestore delle categorie.
	 * @param listManager il gestore delle liste della spesa.
	 */
	public ListMenu(CategoryManager categoryManager, ListManager listManager) {
		super();
		this.categoryManager = categoryManager;
		this.listManager = listManager;
	}

	/**
	 * Avvia il menu delle liste, consentendo all'utente di selezionare tra le opzioni di creazione,
	 * rimozione e gestione di una lista. Continua a visualizzare il menu fino a quando l'utente non sceglie di tornare al menu principale.
	 */
	@Override
	public void start() {
		while (true) {
			displayMenu();
			try {
				switch (readString("\nSeleziona un'operazione: ")) {
					case "1":
						createNewList();
						break;
					case "2":
						removeList();
						break;
					case "3":
						manageList();
						break;
					case "4":
						return;
					default:
						showInvalidOption();
				}
			} catch (InvalidInputException | ListNotFoundException invalidOrListException) {
				showError(invalidOrListException.getMessage());	// Gestisce errori di input non valido o lista non trovata
			}
		}
	}

	/**
	 * Visualizza il menu delle liste e le opzioni di gestione.
	 * Mostra le liste esistenti e le opzioni per crearne, rimuoverne o gestirne una specifica.
	 */
	@Override
	public void displayMenu() {
		displayItems(listManager.getShoppingLists(), "\nListe esistenti:", "Nessuna lista presente.", false);

		showMessage("\n--- Menu liste ---");
		showMessage("1 - Crea nuova lista");
		showMessage("2 - Rimuovi lista");
		showMessage("3 - Visualizza/Modifica una lista");
		showMessage("4 - Torna al menu principale");
	}

	/**
	 * Crea una nuova lista della spesa.
	 * Chiede all'utente di inserire il nome della nuova lista
	 * e la aggiunge al gestore delle liste.
	 *
	 * @throws InvalidInputException se il nome della lista è nullo, vuoto o già esistente.
	 */
	private void createNewList() throws InvalidInputException {
		listManager.addShoppingList(readString("Inserisci il nome della nuova lista: "));
		showMessage("\nLista creata con successo.");
	}

	/**
	 * Rimuove una lista esistente.
	 * Controlla se ci sono liste esistenti e, in caso affermativo,
	 * chiede all'utente di specificare il nome della lista da rimuovere.
	 *
	 * @throws ListNotFoundException se la lista specificata non esiste.
	 */
	private void removeList() throws ListNotFoundException {
		// Verifica che ci siano liste disponibili per la rimozione
		if (!isCollectionEmpty(listManager.getShoppingLists(), "Non ci sono liste da rimuovere.")) {
			listManager.removeShoppingList(readString("Inserisci il nome della lista da rimuovere: "));
			showMessage("\nLista rimossa con successo.");
		}
	}

	/**
	 * Gestisce una lista esistente.
	 * Verifica che ci siano liste disponibili e, in caso affermativo, chiede all'utente di
	 * specificare il nome della lista da gestire e avvia il menu di gestione della lista.
	 *
	 * @throws InvalidInputException se l'input è nullo o non valido.
	 * @throws ListNotFoundException se la lista specificata non esiste.
	 */
	private void manageList() throws InvalidInputException, ListNotFoundException {
		// Verifica che ci siano liste disponibili per la gestione
		if (!isCollectionEmpty(listManager.getShoppingLists(), "\nNon ci sono liste disponibili.\nCreane una nuova prima di gestirla.")) {
			String listName = readString("Inserisci il nome della lista da gestire: ");
			// Inizializza e avvia il menu di gestione della lista specificata
			ShoppingMenu shoppingMenu = new ShoppingMenu(categoryManager, listManager.getShoppingList(listName));
			shoppingMenu.start();
		}
	}
}
