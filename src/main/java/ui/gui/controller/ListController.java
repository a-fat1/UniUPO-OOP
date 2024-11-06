package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.panel.ListPanel;

import model.ListManager;
import model.ShoppingList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller per la gestione delle liste della spesa.
 * Gestisce l'aggiunta, rimozione e selezione delle liste della spesa nell'interfaccia grafica.
 */
public class ListController extends BaseController implements ActionListener {
	/** Pannello per la visualizzazione e gestione delle liste della spesa. */
	private ListPanel listPanel;

	/** Manager per la gestione delle liste della spesa. */
	private ListManager manager;

	/** Controller per la gestione degli articoli associati a una lista della spesa. */
	private ArticleController articleController;

	/**
	 * Costruisce un controller per la gestione delle liste della spesa.
	 *
	 * @param listPanel pannello delle liste della spesa.
	 * @param manager manager delle liste della spesa.
	 */
	public ListController(ListPanel listPanel, ListManager manager) {
		this.listPanel = listPanel;
		this.manager = manager;

		addListeners();
		initializeLists();
	}

	/**
	 * Imposta il controller per la gestione degli articoli.
	 *
	 * @param articleController Controller degli articoli.
	 */
	public void setArticleController(ArticleController articleController) {
		this.articleController = articleController;
	}

	/** Aggiunge i listener ai pulsanti di aggiunta e rimozione delle liste. */
	private void addListeners() {
		// Listener per la selezione di una lista
		listPanel.getShoppingListJList().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				String selectedList = getSelectedListName();
				articleController.updateArticleList(selectedList);
			}
		});

		// Listener per i pulsanti di aggiunta e rimozione delle liste
		listPanel.addListButton.addActionListener(this);
		listPanel.removeListButton.addActionListener(this);
	}

	/**
	 * Gestisce gli eventi di azione per i pulsanti di aggiunta e rimozione liste.
	 *
	 * @param e evento di azione.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == listPanel.addListButton) {
			addShoppingList();
		} else if (source == listPanel.removeListButton) {
			removeShoppingList();
		}
	}

	/** Aggiunge una nuova lista della spesa tramite l'input dell'utente. */
	public void addShoppingList() {
		String listName = getTrimmedInput("Inserisci il nome della nuova lista:");
		if (listName != null) {
			try {
				addListToManagerAndUpdateView(listName);
			} catch (InvalidInputException ex) {
				showError(listPanel, "Errore creazione lista", "Errore: " + ex.getMessage());
			}
		}
	}

	/** Rimuove la lista della spesa selezionata. */
	public void removeShoppingList() {
		String selectedList = getSelectedListName();
		if (selectedList != null) {
			try {
				int removeList = JOptionPane.showConfirmDialog(listPanel, "Sei sicuro di voler rimuovere la lista selezionata?", "Conferma rimozione", JOptionPane.YES_NO_OPTION);
				if (removeList == JOptionPane.YES_OPTION) {
					removeListFromManagerAndUpdateView(selectedList);
				}
			} catch (ListNotFoundException ex) {
				showError(listPanel, "Errore di rimozione", "Errore: " + ex.getMessage());
			}
		} else {
			showMessage(listPanel, "Nessuna lista selezionata", "Seleziona una lista da rimuovere.");
		}
	}

	/**
	 * Restituisce la lista della spesa selezionata.
	 *
	 * @return Lista della spesa selezionata, null se nessuna lista è selezionata.
	 */
	public ShoppingList getSelectedShoppingList() {
		String selectedListName = getSelectedListName();
		if (selectedListName != null) {
			try {
				return manager.getShoppingList(selectedListName);
			} catch (ListNotFoundException ex) {
				showError(listPanel, "Errore lista", "Errore: " + ex.getMessage());
			}
		}
		return null;
	}

	/**
	 * Aggiorna la lista degli articoli visualizzati in base alla lista della spesa selezionata.
	 *
	 * @param listName nome della lista da visualizzare.
	 */
	public void updateArticleList(String listName) {
		articleController.updateArticleList(listName);
	}

	/** Inizializza la lista delle varie liste della spesa. */
	public void initializeLists() {
		listPanel.getListModel().clear();
		for (ShoppingList shoppingList : manager.getShoppingLists()) {
			listPanel.getListModel().addElement(shoppingList.getName());
		}
	}

	/** Ottiene il nome della lista selezionata. */
	private String getSelectedListName() {
		return listPanel.getShoppingListJList().getSelectedValue();
	}

	/**
	 * Richiede un input all'utente e lo restituisce trimmato.
	 *
	 * @param message messaggio da mostrare nella finestra di input.
	 * @return Input trimmato dell'utente, o null se l'utente annulla o inserisce una stringa vuota.
	 */
	private String getTrimmedInput(String message) {
		String input = JOptionPane.showInputDialog(listPanel, message);
		if (input != null) {
			input = input.trim();
			if (!input.isEmpty()) {
				return input;
			}
		}
		return null;
	}

	/**
	 * Aggiunge la lista al manager e aggiorna la vista.
	 *
	 * @param listName nome della lista da aggiungere.
	 * @throws InvalidInputException se l'input è invalido.
	 */
	private void addListToManagerAndUpdateView(String listName) throws InvalidInputException {
		manager.addShoppingList(listName);
		listPanel.getListModel().addElement(listName);
		showMessage(listPanel, "Lista creata", "Lista creata con successo.");
	}

	/**
	 * Rimuove la lista dal manager e aggiorna la vista.
	 *
	 * @param listName nome della lista da rimuovere.
	 * @throws ListNotFoundException se la lista non viene trovata.
	 */
	private void removeListFromManagerAndUpdateView(String listName) throws ListNotFoundException {
		manager.removeShoppingList(listName);
		listPanel.getListModel().removeElement(listName);
		articleController.clearArticles();
		showMessage(listPanel, "Lista rimossa", "Lista rimossa con successo.");
	}
}
