package ui.cli.menu;

import ui.cli.base.BaseMenu;

import model.ListManager;
import model.Article;
import model.CategoryManager;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.CategoryNotFoundException;

/**
 * Menu CLI per la gestione delle categorie. 
 * Visualizza il menu delle categorie, consentendo di aggiungerle e rimuoverle.
 * Estende la classe `BaseMenu`.
 */
public class CategoryMenu extends BaseMenu {
	/** Gestore delle categorie. */
	private CategoryManager categoryManager;

	/** Gestore delle liste della spesa. */
	private ListManager listManager;

	/**
	 * Inizializza il `CategoryMenu` con il gestore delle categorie e delle liste della spesa.
	 *
	 * @param categoryManager il gestore delle categorie.
	 * @param listManager il gestore delle liste della spesa.
	 */
	public CategoryMenu(CategoryManager categoryManager, ListManager listManager) {
		super();
		this.categoryManager = categoryManager;
		this.listManager = listManager;
	}

	/**
	 * Avvia il menu delle categorie, gestendo le opzioni di aggiunta e rimozione delle categorie.
	 * Continua a visualizzare il menu fino a quando l'utente non sceglie di tornare al menu principale.
	 */
	@Override
	public void start() {
		while (true) {
			displayMenu();
			try {
				switch (readString("\nSeleziona un'opzione: ")) {
					case "1":
						addCategory();
						break;
					case "2":
						removeCategory();
						break;
					case "3":
						return;
					default:
						showInvalidOption();
				}
			} catch (InvalidInputException | CategoryNotFoundException invalidOrCategoryException) {
				showError(invalidOrCategoryException.getMessage());	// Mostra un messaggio di errore per input non valido o categoria non trovata
			}
		}
	}

	/**
	 * Visualizza il menu delle categorie e le opzioni di gestione.
	 * Mostra le categorie attuali seguite dalle opzioni per aggiungere, rimuovere o tornare al menu principale.
	 */
	@Override
	public void displayMenu() {
		displayItems(categoryManager.getCategories(), "\nCategorie esistenti:", null, false);

		showMessage("\n--- Menu categorie ---");
		showMessage("1 - Aggiungi categoria");
		showMessage("2 - Rimuovi categoria");
		showMessage("3 - Torna al menu principale");
	}

	/**
	 * Aggiunge una nuova categoria.
	 * Richiede all'utente di inserire il nome della nuova categoria, quindi la aggiunge al gestore delle categorie.
	 * 
	 * @throws InvalidInputException se il nome della categoria è nullo, vuoto o già presente.
	 */
	private void addCategory() throws InvalidInputException {
		categoryManager.addCategory(readString("Inserisci il nome della nuova categoria: "));
		showMessage("\nCategoria aggiunta con successo.");
	}

	/**
	 * Rimuove una categoria esistente e aggiorna le liste della spesa.
	 * Richiede il nome della categoria da rimuovere, la rimuove e aggiorna tutti gli articoli
	 * che la possiedono impostando la categoria predefinita.
	 * 
	 * @throws InvalidInputException se il nome della categoria è nullo o vuoto.
	 * @throws CategoryNotFoundException se la categoria specificata non esiste.
	 */
	private void removeCategory() throws InvalidInputException, CategoryNotFoundException {
		String categoryName = readString("Inserisci il nome della categoria da rimuovere: ");
		categoryManager.removeCategory(categoryName);
		categoryManager.updateCategoryInAllLists(listManager.getShoppingLists(), categoryName); // Aggiorna la categoria rimossa in tutte le liste con quella predefinita
		showMessage("\nCategoria rimossa. Gli articoli che possiedono la categoria cancellata\nsono stati aggiornati con la categoria '" + Article.DEFAULT_CATEGORY + "'.");
	}
}
