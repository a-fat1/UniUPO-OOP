// package ui.gui.controller;

// import model.Article;
// import model.ListManager;
// import model.ShoppingList;
// import ui.gui.base.BaseController;
// import ui.gui.view.input.ArticleInputDialog;
// import ui.gui.view.panel.ArticlePanel;
// import ui.gui.view.panel.DetailPanel;

// import javax.swing.JOptionPane;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class ArticleController extends BaseController implements ActionListener {
//     private ArticlePanel articlePanel;
//     private DetailPanel detailPanel;
//     private ListManager manager;
//     private CategoryController categoryController;
//     private ShoppingList currentList;

//     public ArticleController(ArticlePanel articlePanel, DetailPanel detailPanel, ListManager manager) {
//         this.articlePanel = articlePanel;
//         this.detailPanel = detailPanel;
//         this.manager = manager;

//         addListeners();
//     }

//     public void setCategoryController(CategoryController categoryController) {
//         this.categoryController = categoryController;
//     }

//     private void addListeners() {
//         articlePanel.getArticleJList().addListSelectionListener(e -> {
//             if (!e.getValueIsAdjusting()) {
//                 updateArticleDetails();
//             }
//         });

//         // Listener per i bottoni
//         articlePanel.addArticleButton.addActionListener(this);
//         articlePanel.removeArticleButton.addActionListener(this);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         Object source = e.getSource();

//         if (source == articlePanel.addArticleButton) {
//             addArticle();
//         } else if (source == articlePanel.removeArticleButton) {
//             removeArticle();
//         }
//     }

//     public void updateArticleList(String listName) {
//         articlePanel.getArticleModel().clear();
//         detailPanel.getArticleDetailsTextArea().setText("");
//         if (listName != null) {
//             currentList = manager.getShoppingList(listName);
//             for (Article article : currentList) {
//                 articlePanel.getArticleModel().addElement(article.getName());
//             }
//             updateArticleInfo();
//         } else {
//             currentList = null;
//             updateArticleInfo();
//         }
//     }

//     private void updateArticleInfo() {
//         if (currentList != null) {
//             articlePanel.getTotalArticlesLabel().setText("Totale articoli: " + currentList.getTotalQuantity());
//             articlePanel.getTotalCostLabel().setText("Costo totale: " + String.format("%.2f", currentList.getTotalCost()));
//         } else {
//             articlePanel.getTotalArticlesLabel().setText("Totale articoli: 0");
//             articlePanel.getTotalCostLabel().setText("Costo totale: 0,00");
//         }
//     }

//     public void addArticle() {
//         if (currentList == null) {
//             showMessage(articlePanel, "Seleziona una lista a cui aggiungere l'articolo.");
//             return;
//         }

//         ArticleInputDialog dialog = new ArticleInputDialog(null);
//         dialog.setVisible(true);

//         if (dialog.isConfirmed()) {
//             Article article = dialog.getArticle();
//             try {
//                 if (!manager.getCategories().contains(article.getCategory())) {
//                     int addCategory = JOptionPane.showConfirmDialog(articlePanel, "La categoria non esiste. Vuoi aggiungerla?", "Nuova Categoria", JOptionPane.YES_NO_OPTION);
//                     if (addCategory == JOptionPane.YES_OPTION) {
//                         manager.addCategory(article.getCategory());
//                         categoryController.updateCategories();
//                     } else {
//                         article.setCategory("Non Categorizzati");
//                     }
//                 }
//                 currentList.addArticle(article);
//                 articlePanel.getArticleModel().addElement(article.getName());
//                 updateArticleInfo();
//                 showMessage(articlePanel, "Articolo aggiunto con successo.");
//             } catch (Exception ex) {
//                 showError(articlePanel, "Errore: " + ex.getMessage());
//             }
//         }
//     }

//     public void removeArticle() {
//         if (currentList == null) {
//             showMessage(articlePanel, "Seleziona una lista da cui rimuovere l'articolo.");
//             return;
//         }

//         String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();
//         if (selectedArticleName == null) {
//             showMessage(articlePanel, "Seleziona un articolo da rimuovere.");
//             return;
//         }

//         try {
//             currentList.removeArticle(selectedArticleName);
//             articlePanel.getArticleModel().removeElement(selectedArticleName);
//             detailPanel.getArticleDetailsTextArea().setText("");
//             updateArticleInfo();
//             showMessage(articlePanel, "Articolo rimosso con successo.");
//         } catch (Exception ex) {
//             showError(articlePanel, "Errore: " + ex.getMessage());
//         }
//     }

//     private void updateArticleDetails() {
//         String selectedArticleName = articlePanel.getArticleJList().getSelectedValue();

//         if (currentList != null && selectedArticleName != null) {
//             Article article = currentList.getArticleByName(selectedArticleName);

//             if (article != null) {
//                 detailPanel.getArticleDetailsTextArea().setText("Nome: " + article.getName() + "\nCosto: " + article.getCost() + "\nQuantità: " + article.getQuantity() + "\nCategoria: " + article.getCategory());
//             }
//         }
//     }

//     public void clearArticles() {
//         articlePanel.getArticleModel().clear();
//         detailPanel.getArticleDetailsTextArea().setText("");
//         updateArticleInfo();
//     }
// }
