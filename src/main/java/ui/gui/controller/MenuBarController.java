package ui.gui.controller;

import ui.gui.view.bar.MenuBar;
import ui.gui.view.bar.FileChooser;
import ui.gui.base.BaseController;

import model.Article;
import model.ShoppingList;
import model.CategoryManager;
import model.InputOutputList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.io.FileOperationException;
import model.exceptions.domain.ArticleNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller per la gestione delle azioni nella barra dei menu.
 * Gestisce il salvataggio, caricamento, ricerca e rimozione degli articoli,
 * insieme all'uscita dalla GUI per ritornare a selezionare l'interfaccia grafica.
 */
public class MenuBarController extends BaseController {
	/** Barra dei menu dell'interfaccia utente. */
	private MenuBar menuBar;

	/** Finestra principale dell'applicazione.. */
	private JFrame frame;

	/** Azione da eseguire per il ritorno alla selezione dell'interfaccia. */
	private Consumer<Boolean> onReturn;

	/** Controller per la gestione delle liste della spesa. */
	private ListController listController;

	/** Manager per la gestione delle categorie. */
	private CategoryManager categoryManager;

	/** Controller per la gestione delle categorie. */
	private CategoryController categoryController;

	/** Gestore per il salvataggio e caricamento delle liste. */
	private InputOutputList inputOutputList;

	/**
	 * Costruisce un controller per la gestione della barra dei menu.
	 *
	 * @param menuBar Barra dei menu.
	 * @param frame Finestra principale dell'applicazione.
	 * @param onReturn Azione per il ritorno alla selezione dell'interfaccia.
	 * @param listController Controller per le liste della spesa.
	 * @param categoryManager Manager per le categorie.
	 * @param categoryController Controller per le categorie.
	 */
	public MenuBarController(MenuBar menuBar, JFrame frame, Consumer<Boolean> onReturn, ListController listController, CategoryManager categoryManager, CategoryController categoryController) {
		this.menuBar = menuBar;
		this.frame = frame;
		this.onReturn = onReturn;
		this.listController = listController;
		this.categoryManager = categoryManager;
		this.categoryController = categoryController;
		this.inputOutputList = new InputOutputList();

		// Aggiunge i listener agli elementi della barra dei menu
		addMenuListeners();
	}

	/** Aggiunge i listener per ciascun elemento della barra dei menu. */
	private void addMenuListeners() {
		menuBar.saveListItem.addActionListener(e -> saveList());
		menuBar.loadListItem.addActionListener(e -> loadList());
		menuBar.searchItemByName.addActionListener(e -> searchArticles(false));
		menuBar.searchItemByCategory.addActionListener(e -> searchArticles(true));
		menuBar.clearList.addActionListener(e -> removeAllArticles());
		menuBar.exitItem.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(frame, "Tutte le liste non salvate andranno perdute. Sei sicuro di voler ritornare alla selezione dell'interfaccia?", "Termina interfaccia grafica", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (onReturn != null) {
					onReturn.accept(false);
				}
				frame.dispose();
			}
		});
	}

	/** Salva la lista della spesa selezionata in un file specifico. */
	private void saveList() {
		ShoppingList selectedList = listController.getSelectedShoppingList();
		if (selectedList == null) {
			showMessage(frame, "Lista da salvare", "Seleziona una lista da salvare.");
			return;
		}

		FileChooser fileChooser = createFileChooser();

		int option = fileChooser.showSaveDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filename.toLowerCase().endsWith(".txt")) {
				filename += ".txt";
			}
			File filePath = new File(filename);

			if (filePath.exists()) {
				int confirm = JOptionPane.showConfirmDialog(frame, "Il file esiste già. Vuoi sovrascriverlo?", "Conferma sovrascrittura", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) {
					return; // L'utente ha scelto di non sovrascrivere il file
				}
			}

			try {
				inputOutputList.saveToFile(selectedList, filePath);
				showMessage(frame, "Lista salvata", "Lista salvata con successo.");
			} catch (FileOperationException ex) {
				showError(frame, "Errore salvataggio lista", "Errore: " + ex.getMessage());
			}
		}
	}

	/** Carica una lista di articoli, presa da un file, nella lista selezionata. */
	private void loadList() {
		ShoppingList selectedList = listController.getSelectedShoppingList();
		if (selectedList == null) {
			showMessage(frame, "Lista da caricare", "Seleziona una lista in cui caricare gli articoli.");
			return;
		}

		FileChooser fileChooser = createFileChooser();

		int option = fileChooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			int confirm = JOptionPane.showConfirmDialog(frame, "Il contenuto della lista selezionata verrà sovrascritto. Vuoi continuare?", "Conferma caricamento", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) {
				return; // L'utente ha scelto di non caricare il file
			}

			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			try {
				File filePath = new File(filename);
				inputOutputList.loadFromFile(selectedList, categoryManager, filePath);
				listController.updateArticleList(selectedList.getName());
				categoryController.updateCategories();
				showMessage(frame, "Lista caricata", "Lista caricata con successo.");
			} catch (FileOperationException | InvalidInputException ex) {
				showError(frame, "Errore caricamento", "Errore: " + ex.getMessage());
			}
		}
	}

	/** Rimuove tutti gli articoli dalla lista selezionata. */
	private void removeAllArticles() {
		ShoppingList selectedList = listController.getSelectedShoppingList();
		if (selectedList == null) {
			showMessage(frame, "Nessuna lista selezionata", "Seleziona una lista da cui rimuovere tutti gli articoli.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler rimuovere tutti gli articoli dalla lista selezionata?", "Rimuovi articoli", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			selectedList.clearArticles();
			listController.updateArticleList(selectedList.getName());
			showMessage(frame, "Articoli rimossi", "Tutti gli articoli sono stati rimossi dalla lista.");
		}
	}

	/**
	 * Cerca articoli nella lista selezionata basandosi su un criterio specificato (prefisso o categoria).
	 *
	 * @param byCategory flag per determinare se la ricerca è basata su categoria o prefisso.
	 */
	private void searchArticles(boolean byCategory) {
		ShoppingList selectedList = listController.getSelectedShoppingList();
		if (selectedList == null) {
			showMessage(frame, "Nessuna lista selezionata", "Seleziona una lista per effettuare la ricerca.");
			return;
		}

		String inputType = byCategory ? "categoria" : "prefisso";
		String input = JOptionPane.showInputDialog(frame, "Inserisci la " + inputType + " da cercare:");
		if (input != null) {
			try {
				List<Article> articles = selectedList.findArticles(input.trim(), byCategory);
				StringBuilder result = new StringBuilder("Articoli trovati:\n");
				for (Article article : articles) {
					result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
				}
				showMessage(frame, "Risultato ricerca", result.toString());
			} catch (InvalidInputException ex) {
				showError(frame, (byCategory ? "Categoria" : "Prefisso") + " invalido", "Errore: " + ex.getMessage());
			} catch (ArticleNotFoundException ex) {
				showMessage(frame, "Errore ricerca " + inputType, "Errore: " + ex.getMessage());
			}
		}
	}

	/**
	 * Crea un file chooser con un filtro per selezionare file di testo.
	 *
	 * @return FileChooser configurato per file di testo.
	 */
	private FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File di testo (*.txt)", "txt"));
		try {
			fileChooser.setCurrentDirectory(new File(inputOutputList.checkListsDir()));
		} catch (FileOperationException ex) {
			// Non fare nulla se non si riesce ad accedere o creare la cartella "lists"
		}
		return fileChooser;
	}
}
