package ui.gui.view.bar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Rappresenta una barra dei menu personalizzata per l'interfaccia utente.
 * Contiene opzioni per la gestione delle liste, la ricerca di articoli e altre azioni generali.
 */
public class MenuBar extends JMenuBar {
	/** Elemento di menu per salvare la lista. */
	public JMenuItem saveListItem;

	/** Elemento di menu per salvare la lista. */
	public JMenuItem loadListItem;

	/** Elemento di menu per cercare articoli per prefisso. */
	public JMenuItem searchItemByName;

	/** Elemento di menu per cercare articoli per categoria. */
	public JMenuItem searchItemByCategory;

	/** Elemento di menu per rimuovere tutti gli articoli dalla lista. */
	public JMenuItem clearList;

	/** Elemento di menu per ritornare alla selezione dell'interfaccia. */
	public JMenuItem exitItem;

	/** Costruisce la barra dei menu con i menu e le opzioni specificate. */
	public MenuBar() {
		// Crea il menu "File" e aggiunge le opzioni per salvare e caricare la lista
		JMenu fileMenu = new JMenu("File");
		saveListItem = new JMenuItem("Salva Lista");
		loadListItem = new JMenuItem("Carica Lista");
		fileMenu.add(saveListItem);
		fileMenu.add(loadListItem);

		// Crea il menu "Ricerca" e aggiunge le opzioni per cercare articoli per nome o categoria
		JMenu searchMenu = new JMenu("Ricerca");
		searchItemByName = new JMenuItem("Cerca articoli per prefisso");
		searchItemByCategory = new JMenuItem("Cerca articoli per categoria");
		searchMenu.add(searchItemByName);
		searchMenu.add(searchItemByCategory);

		// Crea il menu "Altro" e aggiunge le opzioni per rimuovere tutti gli articoli e uscire
		JMenu miscMenu = new JMenu("Altro");
		clearList = new JMenuItem("Rimuovi tutti gli articoli");
		exitItem = new JMenuItem("Ritorna alla selezione dell'interfaccia");
		miscMenu.add(clearList);
		miscMenu.add(exitItem);

		// Aggiunge i menu alla barra dei menu
		add(fileMenu);
		add(searchMenu);
		add(miscMenu);
	}
}
