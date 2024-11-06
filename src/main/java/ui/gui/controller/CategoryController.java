package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.panel.CategoryPanel;

import model.ListManager;
import model.CategoryManager;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.CategoryNotFoundException;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Set;

/**
 * Controller per la gestione delle categorie.
 * Gestisce l'aggiunta, rimozione e visualizzazione delle categorie nell'interfaccia grafica.
 */
public class CategoryController extends BaseController implements ActionListener {
	/** Pannello per la visualizzazione e gestione delle categorie. */
	private CategoryPanel categoryPanel;

	/** Manager per la gestione delle categorie. */
	private CategoryManager categoryManager;

	/** Manager per la gestione delle liste della spesa. */
	private ListManager listManager;

	/**
	 * Costruisce un controller per la gestione delle categorie.
	 *
	 * @param categoryPanel pannello delle categorie.
	 * @param categoryManager manager delle categorie.
	 * @param listManager manager delle liste della spesa.
	 */
	public CategoryController(CategoryPanel categoryPanel, CategoryManager categoryManager, ListManager listManager) {
		this.categoryPanel = categoryPanel;
		this.categoryManager = categoryManager;
		this.listManager = listManager;

		// Inizializza le categorie e aggiunge i listener
		initializeCategories();
		addListeners();
	}

	/** Aggiunge i listener ai pulsanti di aggiunta e rimozione delle categorie. */
	private void addListeners() {
		// Listener per aggiungere una categoria
		categoryPanel.addCategoryButton.addActionListener(this);
		// Listener per rimuovere una categoria
		categoryPanel.removeCategoryButton.addActionListener(this);
	}

	/**
	 * Gestisce gli eventi di azione per i pulsanti di aggiunta e rimozione categorie.
	 *
	 * @param e evento di azione.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == categoryPanel.addCategoryButton) {
			addCategory();
		} else if (source == categoryPanel.removeCategoryButton) {
			removeCategory();
		}
	}

	/** Inizializza le categorie nel pannello delle categorie. */
	public void initializeCategories() {
		// Cancella e aggiorna il modello della lista delle categorie
		categoryPanel.getCategoryModel().clear();
		Set<String> categories = categoryManager.getCategories();
		for (String category : categories) {
			categoryPanel.getCategoryModel().addElement(category);
		}
	}

	/** Aggiorna le categorie nel pannello delle categorie. */
	public void updateCategories() {
		initializeCategories();
	}

	/** Aggiunge una nuova categoria tramite l'input dell'utente. */
	public void addCategory() {
		// Richiede il nome della categoria tramite finestra di dialogo
		String category = JOptionPane.showInputDialog(categoryPanel, "Inserisci il nome della nuova categoria:");
		if (category != null && !category.trim().isEmpty()) {
			String categoryName = category.trim();
			try {
				addCategoryToManagerAndUpdateView(categoryName);
			} catch (InvalidInputException ex) {
				showError(categoryPanel, "Errore categoria", "Errore: " + ex.getMessage());
			}
		}
	}

	/**
	 * Aggiunge una categoria specificata al manager e aggiorna la vista.
	 *
	 * @param categoryName nome della categoria da aggiungere.
	 * @throws InvalidInputException se l'input è invalido.
	 */
	public void addCategory(String categoryName) throws InvalidInputException {
		addCategoryToManagerAndUpdateView(categoryName);
	}

	/** Rimuove una categoria selezionata dall'utente. */
	public void removeCategory() {
		// Recupera la categoria selezionata nella vista
		String category = categoryPanel.getCategoryJList().getSelectedValue();
		if (category != null && !category.trim().isEmpty()) {
			String categoryName = category.trim();
			try {
				removeCategoryFromManagerAndUpdateView(categoryName);
			} catch (InvalidInputException | CategoryNotFoundException ex) {
				showError(categoryPanel, "Errore categoria", "Errore: " + ex.getMessage());
			}
		} else {
			showMessage(categoryPanel, "Nessuna categoria selezionata", "Seleziona una categoria da rimuovere.");
		}
	}

	/**
	 * Ottiene tutte le categorie gestite dal manager.
	 *
	 * @return Insieme di categorie gestite dal manager.
	 */
	public Set<String> getAllCategories() {
		return categoryManager.getCategories();
	}

	/**
	 * Aggiunge la categoria al manager e aggiorna la vista.
	 *
	 * @param categoryName nome della categoria da aggiungere.
	 * @throws InvalidInputException se l'input è invalido.
	 */
	private void addCategoryToManagerAndUpdateView(String categoryName) throws InvalidInputException {
		categoryManager.addCategory(categoryName);
		categoryPanel.getCategoryModel().addElement(categoryName);
		showMessage(categoryPanel, "Aggiunta categoria", "Categoria aggiunta con successo.");
	}

	/**
	 * Rimuove la categoria dal manager e aggiorna la vista.
	 *
	 * @param categoryName nome della categoria da rimuovere.
	 * @throws InvalidInputException se l'input è invalido.
	 * @throws CategoryNotFoundException se la categoria non esiste.
	 */
	private void removeCategoryFromManagerAndUpdateView(String categoryName) throws InvalidInputException, CategoryNotFoundException {
		categoryManager.removeCategory(categoryName);
		// Aggiorna le categorie negli articoli delle liste della spesa
		categoryManager.updateCategoryInAllLists(listManager.getShoppingLists(), categoryName);
		categoryPanel.getCategoryModel().removeElement(categoryName);
		showMessage(categoryPanel, "Categoria rimossa", "Categoria rimossa con successo.");
	}
}
