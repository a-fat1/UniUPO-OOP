package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.panel.DetailPanel;
import ui.gui.view.panel.ArticlePanel;
import ui.gui.view.input.ArticleInputDialog;

import model.Article;
import model.ListManager;
import model.ShoppingList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;
import model.exceptions.domain.ArticleNotFoundException;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Map;

/**
 * Controller per la gestione degli articoli in una lista della spesa.
 * Gestisce l'aggiunta, rimozione e visualizzazione dei dettagli degli articoli.
 */
public class ArticleController extends BaseController implements ActionListener {
	/** Pannello per la visualizzazione e gestione degli articoli. */
	private ArticlePanel articlePanel;

	/** Pannello per la visualizzazione dei dettagli di un articolo selezionato. */
	private DetailPanel detailPanel;

	/** Gestore delle liste della spesa. */
	private ListManager listManager;

	/** Controller per la gestione delle categorie. */
	private CategoryController categoryController;

	/** Lista della spesa attualmente selezionata. */
	private ShoppingList currentList;

	/**
	 * Costruisce un controller per la gestione degli articoli, associato ai pannelli e manager forniti.
	 *
	 * @param articlePanel pannello per la visualizzazione degli articoli.
	 * @param detailPanel pannello per la visualizzazione dei dettagli degli articoli.
	 * @param listManager gestore delle liste della spesa.
	 * @param categoryController controller per la gestione delle categorie.
	 */
	public ArticleController(ArticlePanel articlePanel, DetailPanel detailPanel, ListManager listManager, CategoryController categoryController) {
		this.articlePanel = articlePanel;
		this.detailPanel = detailPanel;
		this.listManager = listManager;
		this.categoryController = categoryController;

		// Aggiunge i listener per gli articoli
		addListeners();
	}

	/** Aggiunge i listener necessari per la selezione e gestione degli articoli. */
	private void addListeners() {
		// Listener per aggiornare i dettagli dell'articolo selezionato nella lista
		articlePanel.getArticleJList().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				updateArticleDetails();
			}
		});

		// Listener per i pulsanti di aggiunta e rimozione degli articoli
		articlePanel.addArticleButton.addActionListener(this);
		articlePanel.removeArticleButton.addActionListener(this);
	}

	/**
	 * Gestisce gli eventi di azione per i pulsanti di aggiunta e rimozione articoli.
	 *
	 * @param e evento di azione.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == articlePanel.addArticleButton) {
			addArticle();
		} else if (source == articlePanel.removeArticleButton) {
			removeArticle();
		}
	}

	/**
	 * Aggiorna la lista degli articoli visualizzati in base alla lista della spesa selezionata.
	 *
	 * @param listName nome della lista da caricare.
	 */
	public void updateArticleList(String listName) {
		articlePanel.getArticleModel().clear();
		clearArticleDetails();
		if (listName != null) {
			try {
				// Carica la lista della spesa selezionata e aggiorna la visualizzazione degli articoli
				currentList = listManager.getShoppingList(listName);
				for (Article article : currentList) {
					articlePanel.getArticleModel().addElement(article.getName());
				}
				updateArticleInfo();
			} catch (ListNotFoundException ex) {
				// Mostra un errore se la lista non è trovata
				showError(articlePanel, "Lista mancante", "Errore: " + ex.getMessage());
				currentList = null;
				updateArticleInfo();
			}
		} else {
			currentList = null;
			updateArticleInfo();
		}
	}

	/** Aggiorna le informazioni sul totale degli articoli e il costo totale. */
	private void updateArticleInfo() {
		if (currentList != null) {
			// Recupera i totali dalla lista e aggiorna le etichette
			Map<String, Object> totals = currentList.getTotalFromList();
			int totalQuantity = (int) totals.get(ShoppingList.KEY_QUANTITY);
			double totalCost = (double) totals.get(ShoppingList.KEY_COST);
			articlePanel.getTotalArticlesLabel().setText("Totale articoli: " + totalQuantity);
			articlePanel.getTotalCostLabel().setText("Costo totale: " + String.format("%.2f", totalCost));
		} else {
			// Imposta i valori predefiniti se non è selezionata alcuna lista
			articlePanel.getTotalArticlesLabel().setText("Totale articoli: 0");
			articlePanel.getTotalCostLabel().setText("Costo totale: 0,00");
		}
	}

	/** Aggiunge un articolo alla lista corrente, richiedendo all'utente di specificarne i dettagli. */
	public void addArticle() {
		if (!checkCurrentList("a cui aggiungere l'articolo")) {
			return;
		}

		// Mostra una finestra di dialogo per l'inserimento dei dettagli dell'articolo
		ArticleInputDialog dialog = new ArticleInputDialog(null);
		dialog.setVisible(true);

		if (dialog.isConfirmed()) {
			try {
				// Crea un articolo e verifica la categoria
				Article article = dialog.getArticle();

				// Verifica se la categoria esiste utilizzando CategoryController
				if (!categoryController.getAllCategories().contains(article.getCategory())) {
					int addCategory = JOptionPane.showConfirmDialog(articlePanel, "La categoria non esiste. Vuoi aggiungerla?", "Nuova Categoria", JOptionPane.YES_NO_OPTION);
					if (addCategory == JOptionPane.YES_OPTION) {
						categoryController.addCategory(article.getCategory());
						categoryController.updateCategories(); // Aggiorna le categorie nella vista
					} else {
						article.setCategory(Article.DEFAULT_CATEGORY);
						showMessage(articlePanel, "Categoria predefinita", "L'articolo verrà salvato con la categoria predefinita.");
					}
				}
				// Aggiunge l'articolo alla lista e aggiorna la visualizzazione
				currentList.addArticle(article);
				articlePanel.getArticleModel().addElement(article.getName());
				updateAfterArticleChange("Articolo aggiunto", "Articolo aggiunto con successo.");
			} catch (InvalidInputException ex) {
				showError(articlePanel, "Errore articolo", "Errore: " + ex.getMessage());
			}
		}
	}

	/** Rimuove l'articolo selezionato dalla lista corrente. */
	public void removeArticle() {
		if (!checkCurrentList("da cui rimuovere l'articolo")) {
			return;
		}

		String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();
		if (selectedArticleName == null) {
			showMessage(articlePanel, "Nessun articolo selezionato", "Seleziona un articolo da rimuovere.");
			return;
		}

		try {
			// Rimuove l'articolo dalla lista e aggiorna la visualizzazione
			int deleteArticle = JOptionPane.showConfirmDialog(articlePanel, "Sei sicuro di voler eliminare questo articolo?", "Rimuovi articolo", JOptionPane.YES_NO_OPTION);
			if (deleteArticle == JOptionPane.YES_OPTION) {
				currentList.removeArticle(selectedArticleName);
				articlePanel.getArticleModel().removeElement(selectedArticleName);
				clearArticleDetails();
				updateAfterArticleChange("Articolo rimosso", "Articolo rimosso con successo.");
			}
		} catch (ArticleNotFoundException ex) {
			showError(articlePanel, "Errore articolo", "Errore: " + ex.getMessage());
		}
	}

	/**
	 * Controlla se la lista corrente è selezionata, altrimenti mostra un messaggio.
	 * @param action azione che l'utente sta tentando di eseguire.
	 * @return Ritorna true se la lista corrente è selezionata, false in caso contrario.
	 */
	private boolean checkCurrentList(String action) {
		if (currentList == null) {
			showMessage(articlePanel, "Nessuna lista selezionata", "Seleziona una lista " + action + ".");
			return false;
		}
		return true;
	}

	/** Pulisce il dettaglio dell'articolo nella vista. */
	private void clearArticleDetails() {
		detailPanel.getArticleDetailsTextArea().setText("");
	}

	/**
	 * Aggiorna la vista dopo un cambiamento agli articoli.
	 * @param title titolo del messaggio da mostrare.
	 * @param message messaggio da mostrare all'utente.
	 */
	private void updateAfterArticleChange(String title, String message) {
		updateArticleInfo();
		showMessage(articlePanel, title, message);
	}

	/** Aggiorna i dettagli dell'articolo selezionato nella vista. */
	private void updateArticleDetails() {
		String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();

		if (currentList != null && selectedArticleName != null) {
			Article article = currentList.getArticleByName(selectedArticleName);

			if (article != null) {
				detailPanel.getArticleDetailsTextArea().setText("Nome: " + article.getName() + "\nCosto: " + article.getCost() + "\nQuantità: " + article.getQuantity() + "\nCategoria: " + article.getCategory());
			}
		}
	}

	/** Pulisce la lista degli articoli visualizzati e i dettagli nella vista. */
	public void clearArticles() {
		articlePanel.getArticleModel().clear();
		clearArticleDetails();
		updateArticleInfo();
	}
}
