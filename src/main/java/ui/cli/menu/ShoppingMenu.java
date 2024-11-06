package ui.cli.menu;

import ui.cli.base.BaseMenu;

import model.Article;
import model.ShoppingList;
import model.CategoryManager;
import model.InputOutputList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.io.FileOperationException;
import model.exceptions.domain.ArticleNotFoundException;

import java.util.Map;
import java.util.List;

import java.io.File;

/**
 * Menu CLI per la gestione di una lista della spesa.
 * Permette di aggiungere, rimuovere, cercare articoli,
 * svuotare la lista e gestire l'import/export su file.
 * Estende la classe `BaseMenu`.
 */
public class ShoppingMenu extends BaseMenu {
	/** Gestore delle categorie. */
	private CategoryManager categoryManager;

	/** Lista della spesa da gestire. */
	private ShoppingList shoppingList;

	/** Gestore di input/output per salvare e caricare liste da file. */
	private InputOutputList inputOutputList;

	/**
	 * Inizializza il `ShoppingMenu` con il gestore delle categorie e una lista della spesa.
	 *
	 * @param categoryManager il gestore delle categorie.
	 * @param shoppingList la lista della spesa da gestire.
	 */
	public ShoppingMenu(CategoryManager categoryManager, ShoppingList shoppingList) {
		super();
		this.categoryManager = categoryManager;
		this.shoppingList = shoppingList;
		this.inputOutputList = new InputOutputList();
	}

	/**
	 * Avvia il menu per la gestione della lista della spesa, permettendo di selezionare le opzioni
	 * di aggiunta, rimozione, ricerca, svuotamento e salvataggio/caricamento su file.
	 */
	@Override
	public void start() {
		while (true) {
			displayMenu();
			try {
				switch (readString("\nInserisci l'opzione da eseguire: ")) {
					case "1":
						addArticleToList();
						break;
					case "2":
						removeArticleFromList();
						break;
					case "3":
						// Cerca articoli nella lista per nome
						searchArticlesInList(false);
						break;
					case "4":
						// Cerca articoli nella lista per categoria
						searchArticlesInList(true);
						break;
					case "5":
						clearList();
						break;
					case "6":
						saveListToFile();
						break;
					case "7":
						loadListFromFile();
						break;
					case "8":
						return;
					default:
						showInvalidOption();
				}
			} catch (InvalidInputException | ArticleNotFoundException | FileOperationException shoppingListException) {
				showError(shoppingListException.getMessage());
			}
		}
	}

	/**
	 * Visualizza il menu per la gestione della lista e le opzioni disponibili.
	 * Mostra gli articoli presenti e il costo totale della lista.
	 */
	@Override
	public void displayMenu() {
		displayItems(shoppingList.getArticles(), "\nArticoli nella lista:", "Nessun articolo presente nella lista.", true);
		printTotalFromList();

		showMessage("\n--- Menu lista '" + shoppingList.getName() + "' ---");
		showMessage("1 - Aggiungi articolo");
		showMessage("2 - Rimuovi articolo");
		showMessage("3 - Ricerca articoli per nome");
		showMessage("4 - Ricerca articoli per categoria");
		showMessage("5 - Svuota lista");
		showMessage("6 - Salva lista su file");
		showMessage("7 - Carica lista da file");
		showMessage("8 - Torna al menu precedente");
	}

	/** Stampa il totale degli articoli e il costo totale della lista. */
	private void printTotalFromList() {
		Map<String, Object> total = shoppingList.getTotalFromList();
		int totalQuantity = (int) total.get(ShoppingList.KEY_QUANTITY);
		double totalCost = (double) total.get(ShoppingList.KEY_COST);

		showMessage("\nTotale articoli: " + totalQuantity);
		showMessage("Costo totale: " + String.format("%.2f", totalCost));
	}

	/**
	 * Aggiunge un articolo alla lista della spesa.
	 * Richiede dettagli dell'articolo e verifica la presenza della categoria,
	 * chiedendo all'utente se desidera aggiungere categorie non esistenti.
	 *
	 * @throws InvalidInputException se i dettagli dell'articolo sono non validi.
	 */
	private void addArticleToList() throws InvalidInputException {
		Article article = getArticleDetails();
		if (!categoryManager.getCategories().contains(article.getCategory())) {
			// Conferma per aggiungere una nuova categoria, se essa non è presente
			if (confirmQuestion("Categoria '" + article.getCategory() + "' non presente. Vuoi aggiungerla?")) {
				categoryManager.addCategory(article.getCategory());
			} else {
				// Imposta la categoria di default in caso di rifiuto
				showMessage("\nArticolo impostato con la categoria di default '" + Article.DEFAULT_CATEGORY + "''.");
				article.setCategory(Article.DEFAULT_CATEGORY);
			}
		}
		shoppingList.addArticle(article);
		showMessage("\nArticolo aggiunto con successo.");
	}

	/**
	 * Ottiene i dettagli dell'articolo dall'utente.
	 *
	 * @return Un nuovo oggetto `Article` basato sui dettagli forniti dall'utente.
	 * @throws InvalidInputException se i dettagli forniti non sono validi.
	 */
	private Article getArticleDetails() throws InvalidInputException {
		String articleName = readString("\nNome dell'articolo: ");
		double cost = readDouble("Costo (usare '.' per i decimali): ");
		int quantity = readInt("Quantità (default 1 con numero invalido): ");

		// Mostra le categorie esistenti per la scelta
		displayItems(categoryManager.getCategories(), "\nLista categorie:", null, false);
		String category = readString("\nCategoria (predefinita '" + Article.DEFAULT_CATEGORY + "'): ");

		return new Article(articleName, cost, quantity, category);
	}

	/**
	 * Rimuove un articolo dalla lista della spesa.
	 *
	 * @throws ArticleNotFoundException se l'articolo non esiste nella lista.
	 */
	private void removeArticleFromList() throws ArticleNotFoundException {
		if (!isCollectionEmpty(shoppingList.getArticles(), "Non ci sono articoli da rimuovere.")) {
			shoppingList.removeArticle(readString("Nome dell'articolo da rimuovere: "));
			showMessage("\nArticolo rimosso con successo.");
		}
	}

	/**
	 * Cerca articoli nella lista in base al nome o alla categoria.
	 *
	 * @param searchForCategory true per cercare per categoria, false per cercare per nome.
	 * @throws ArticleNotFoundException se non ci sono articoli corrispondenti alla ricerca.
	 */
	private void searchArticlesInList(boolean searchForCategory) throws ArticleNotFoundException, InvalidInputException {
		if (!isCollectionEmpty(shoppingList.getArticles(), "Non ci sono articoli da ricercare.")) {
			List<Article> articles = shoppingList.findArticles(searchForCategory ? readString("Inserisci la categoria: ") : readString("Inserisci il prefisso da cercare: "), searchForCategory);
			displayItems(articles, "\nRisultato ricerca:", null, true);
		}
	}

	/** Svuota tutti gli articoli dalla lista della spesa dopo aver richiesto conferma. */
	private void clearList() {
		if (confirmQuestion("\nLa lista verrà svuotata.\nQuesta azione è irreversibile. Procedere?")) {
			shoppingList.clearArticles();
			showMessage("\nLista svuotata.");
		}
	}

	/**
	 * Salva la lista della spesa su file.
	 *
	 * @throws FileOperationException se si verifica un errore durante il salvataggio su file.
	 */
	private void saveListToFile() throws FileOperationException {
		if (!isCollectionEmpty(shoppingList.getArticles(), "Non ci sono articoli da salvare.")) {
			String listFile = defaultFile("\nPremendo solamente invio la lista verrà salvata con il nome '" + shoppingList.getName() + ".txt'.\nInserisci il nome del file (compresa l'estensione, es .txt) per salvarla: ");
			if (confirmQuestion("\nLa lista verrà salvata sul file '" + listFile + "'.\nSe il file è già presente esso verrà sovrascritto. Continuare?")) {
				inputOutputList.saveToFile(shoppingList, new File(inputOutputList.checkListsDir() + listFile));
				showMessage("\nLista salvata con successo sul file '" + listFile + "'.");
			}
		}
	}

	/**
	 * Carica una lista della spesa da file, sovrascrivendo quella attuale.
	 *
	 * @throws FileOperationException se si verifica un errore durante il caricamento da file.
	 * @throws InvalidInputException se i dati nel file non sono validi.
	 */
	private void loadListFromFile() throws FileOperationException, InvalidInputException {
		showMessage("\nGli articoli presenti nel file devono avere il formato 'nome,costo,quantità,categoria' per essere caricati correttamente.");
		
		String listFile = defaultFile("\nPremendo solamente invio la lista verrà caricata dal file '" + shoppingList.getName() + ".txt' (se è presente).\nInserisci il nome del file (compresa l'estensione, es .txt) per caricarla: ");
		if (confirmQuestion("\nLa lista del file '" + listFile + "' sovrascriverà quella attuale.\nContinuare?")) {
			inputOutputList.loadFromFile(shoppingList, categoryManager, new File(inputOutputList.checkListsDir() + listFile));
			showMessage("\nTutti gli articoli (e le categorie)\nsono stati importati con successo dal file '" + listFile + "'.");
		}
	}

	/**
	 * Restituisce il nome del file di default per il salvataggio o caricamento della lista.
	 *
	 * @param messageIO il messaggio da mostrare per richiedere il nome del file.
	 * @return Il nome del file da utilizzare per il salvataggio/caricamento.
	 */
	private String defaultFile(String messageIO) {
		String listFile = readString(messageIO);
		return listFile.isEmpty() ? shoppingList.getName() + ".txt" : listFile;
	}
}
