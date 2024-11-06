package ui.gui.controller;

import ui.gui.view.frame.MainFrame;

import model.ListManager;
import model.CategoryManager;

import java.util.function.Consumer;

/**
 * Controller principale dell'applicazione.
 * Gestisce l'inizializzazione dei manager e dei controller associati alla vista principale.
 */
public class MainController {
	/** Riferimento alla finestra principale dell'interfaccia utente. */
	private MainFrame mainFrame;

	/** Manager per la gestione delle liste. */
	private ListManager listManager;

	/** Manager per la gestione delle categorie. */
	private CategoryManager categoryManager;

	/** Azione da eseguire al ritorno alla schermata principale. */
	private Consumer<Boolean> onReturn;

	/** Controller per la gestione delle liste. */
	private ListController listController;

	/** Controller per la gestione degli articoli. */
	private ArticleController articleController;

	/** Controller per la gestione delle categorie. */
	private CategoryController categoryController;

	/**
	 * Costruisce il controller principale e inizializza i manager e i controller.
	 *
	 * @param mainFrame finestra principale dell'interfaccia utente.
	 * @param onReturn azione da eseguire al ritorno sulla schermata principale.
	 */
	public MainController(MainFrame mainFrame, Consumer<Boolean> onReturn) {
		this.mainFrame = mainFrame;
		this.onReturn = onReturn;

		// Inizializzazione dei manager per liste e categorie
		this.listManager = new ListManager();
		this.categoryManager = new CategoryManager();

		// Inizializza i controller specifici
		initializeControllers();
	}

	/** Inizializza i controller per le diverse parti dell'interfaccia. */
	private void initializeControllers() {
		// Inizializzazione del controller per le categorie con i parametri necessari
		categoryController = new CategoryController(mainFrame.categoryPanel, categoryManager, listManager);

		// Inizializzazione del controller per le liste con la vista e il manager delle liste
		listController = new ListController(mainFrame.listPanel, listManager);

		// Inizializzazione del controller per gli articoli con vista, manager e controller categorie
		articleController = new ArticleController(mainFrame.articlePanel, mainFrame.detailPanel, listManager, categoryController);

		// Inizializzazione del controller per la barra dei menu
		new MenuBarController(mainFrame.menuBar, mainFrame, onReturn, listController, categoryManager, categoryController);

		// Collegamento del ListController con l'ArticleController per sincronizzare le operazioni sugli articoli
		listController.setArticleController(articleController);
	}
}
