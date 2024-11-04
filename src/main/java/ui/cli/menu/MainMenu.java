package ui.cli.menu;

import ui.cli.base.BaseMenu;

import model.ListManager;
import model.CategoryManager;

/**
 * Menu principale dell'applicazione CLI. Permette all'utente di navigare tra le opzioni
 * di gestione delle liste e delle categorie, oltre a fornire un'opzione per terminare il programma.
 * Estende la classe `BaseMenu`.
 */
public class MainMenu extends BaseMenu {
	/** Gestore delle liste della spesa. */
	private ListManager listManager;

	/** Gestore delle categorie. */
	private CategoryManager categoryManager;

	/** Inizializza il menu principale creando istanze di `ListManager` e `CategoryManager`. */
	public MainMenu() {
		super();
		this.listManager = new ListManager();
		this.categoryManager = new CategoryManager();
	}

	/**
	 * Avvia il menu principale, permettendo all'utente di scegliere tra le opzioni di gestione
	 * delle liste, delle categorie o di terminare il programma. Continua a visualizzare il menu
	 * fino a quando l'utente non sceglie di uscire o tornare indietro.
	 */
	@Override
	public void start() {
		while (true) {
			displayMenu();
			switch (readString("\nSeleziona un'opzione valida: ")) {
				case "1":
					ListMenu listMenu = new ListMenu(categoryManager, listManager);
					listMenu.start();
					break;
				case "2":
					CategoryMenu categoryMenu = new CategoryMenu(categoryManager, listManager);
					categoryMenu.start();
					break;
				case "3":
					// Chiede conferma prima di tornare indietro e perdere le modifiche non salvate
					if (confirmQuestion("\nSe ritorni alla selezione dell'interfaccia utente\nperderai tutte le liste e gli articoli creati.\n\nVuoi tornare indietro?")) {
						return;
					}
					break;
				case "4":
					showGoodbye();
					break;
				default:
					showInvalidOption();
			}
		}
	}

	/**
	 * Visualizza le opzioni del menu principale, che comprendono i comandi per gestire le liste,
	 * le categorie, per tornare alla selezione dell'interfaccia utente e terminare il programma.
	 */
	@Override
	public void displayMenu() {
		showMessage("\n--- Menu principale ---");
		showMessage("1 - Gestisci liste esistenti");
		showMessage("2 - Gestisci lista categorie");
		showMessage("3 - Ritorna alla selezione dell'IU");
		showMessage("4 - Termina programma");
	}
}
