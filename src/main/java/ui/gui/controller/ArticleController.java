package ui.gui.controller;

import model.Article;
import model.ListManager;
import model.ShoppingList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;
import model.exceptions.domain.ArticleNotFoundException;

import ui.gui.base.BaseController;
import ui.gui.view.input.ArticleInputDialog;
import ui.gui.view.panel.ArticlePanel;
import ui.gui.view.panel.DetailPanel;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Map;

public class ArticleController extends BaseController implements ActionListener {
    private ArticlePanel articlePanel;
    private DetailPanel detailPanel;
    private ListManager listManager;
    private CategoryController categoryController;
    private ShoppingList currentList;

    public ArticleController(ArticlePanel articlePanel, DetailPanel detailPanel, ListManager listManager, CategoryController categoryController) {
        this.articlePanel = articlePanel;
        this.detailPanel = detailPanel;
        this.listManager = listManager;
        this.categoryController = categoryController;

        addListeners();
    }

    private void addListeners() {
        articlePanel.getArticleJList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateArticleDetails();
            }
        });

        // Listener per i bottoni
        articlePanel.addArticleButton.addActionListener(this);
        articlePanel.removeArticleButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == articlePanel.addArticleButton) {
            addArticle();
        } else if (source == articlePanel.removeArticleButton) {
            removeArticle();
        }
    }

    public void updateArticleList(String listName) {
        articlePanel.getArticleModel().clear();
        detailPanel.getArticleDetailsTextArea().setText("");
        if (listName != null) {
            try {
                currentList = listManager.getShoppingList(listName);
                for (Article article : currentList) {
                    articlePanel.getArticleModel().addElement(article.getName());
                }
                updateArticleInfo();
            } catch (ListNotFoundException ex) {
                showError(articlePanel, "Lista mancante", "Errore: " + ex.getMessage());
                currentList = null;
                updateArticleInfo();
            }
        } else {
            currentList = null;
            updateArticleInfo();
        }
    }

    private void updateArticleInfo() {
        if (currentList != null) {
            Map<String, Object> totals = currentList.getTotalFromList();
            int totalQuantity = (int) totals.get(ShoppingList.KEY_QUANTITY);
            double totalCost = (double) totals.get(ShoppingList.KEY_COST);
            articlePanel.getTotalArticlesLabel().setText("Totale articoli: " + totalQuantity);
            articlePanel.getTotalCostLabel().setText("Costo totale: " + String.format("%.2f", totalCost));
        } else {
            articlePanel.getTotalArticlesLabel().setText("Totale articoli: 0");
            articlePanel.getTotalCostLabel().setText("Costo totale: 0,00");
        }
    }

    public void addArticle() {
        if (currentList == null) {
            showMessage(articlePanel, "Nessuna lista selezionata", "Seleziona una lista a cui aggiungere l'articolo.");
            return;
        }
    
        ArticleInputDialog dialog = new ArticleInputDialog(null);
        dialog.setVisible(true);
    
        if (dialog.isConfirmed()) {
            try {
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
    
                currentList.addArticle(article);
                articlePanel.getArticleModel().addElement(article.getName());
                updateArticleInfo();
                showMessage(articlePanel, "Articolo aggiunto", "Articolo aggiunto con successo.");
            } catch (InvalidInputException ex) {
                showError(articlePanel, "Errore articolo", "Errore: " + ex.getMessage());
            }
        }
    }    

    public void removeArticle() {
        if (currentList == null) {
            showMessage(articlePanel, "Nessuna lista selezionata", "Seleziona una lista da cui rimuovere l'articolo.");
            return;
        }

        String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();
        if (selectedArticleName == null) {
            showMessage(articlePanel, "Nessun articolo selezionato", "Seleziona un articolo da rimuovere.");
            return;
        }

        try {
            currentList.removeArticle(selectedArticleName);
            articlePanel.getArticleModel().removeElement(selectedArticleName);
            detailPanel.getArticleDetailsTextArea().setText("");
            updateArticleInfo();
            showMessage(articlePanel, "Articolo rimosso", "Articolo rimosso con successo.");
        } catch (ArticleNotFoundException ex) {
            showError(articlePanel, "Errore articolo", "Errore: " + ex.getMessage());
        }
    }

    private void updateArticleDetails() {
        String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();

        if (currentList != null && selectedArticleName != null) {
            Article article = currentList.getArticleByName(selectedArticleName);

            if (article != null) {
                detailPanel.getArticleDetailsTextArea().setText("Nome: " + article.getName() + "\nCosto: " + article.getCost() + "\nQuantità: " + article.getQuantity() + "\nCategoria: " + article.getCategory());
            }
        }
    }

    public void clearArticles() {
        articlePanel.getArticleModel().clear();
        detailPanel.getArticleDetailsTextArea().setText("");
        updateArticleInfo();
    }
}
