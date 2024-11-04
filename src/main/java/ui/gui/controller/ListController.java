// package ui.gui.controller;

// import ui.gui.base.BaseController;
// import ui.gui.view.panel.ListPanel;

// import javax.swing.JOptionPane;

// import model.ListManager;
// import model.ShoppingList;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class ListController extends BaseController implements ActionListener {
//     private ListPanel listPanel;
//     private ListManager manager;
//     private ArticleController articleController;

//     public ListController(ListPanel listPanel, ListManager manager) {
//         this.listPanel = listPanel;
//         this.manager = manager;

//         addListeners();
//     }

//     public void setArticleController(ArticleController articleController) {
//         this.articleController = articleController;
//     }

//     private void addListeners() {
//         listPanel.getShoppingListJList().addListSelectionListener(e -> {
//             if (!e.getValueIsAdjusting()) {
//                 String selectedList = listPanel.getShoppingListJList().getSelectedValue();
//                 articleController.updateArticleList(selectedList);
//             }
//         });

//         // Listener per i bottoni
//         listPanel.addListButton.addActionListener(this);
//         listPanel.removeListButton.addActionListener(this);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         Object source = e.getSource();

//         if (source == listPanel.addListButton) {
//             addShoppingList();
//         } else if (source == listPanel.removeListButton) {
//             removeShoppingList();
//         }
//     }

//     public void addShoppingList() {
//         String listName = JOptionPane.showInputDialog(listPanel, "Inserisci il nome della nuova lista:");
//         if (listName != null && !listName.trim().isEmpty()) {
//             try {
//                 manager.addShoppingList(listName.trim());
//                 listPanel.getListModel().addElement(listName.trim());
//                 showMessage(listPanel, "Lista creata con successo.");
//             } catch (Exception ex) {
//                 showError(listPanel, "Errore: " + ex.getMessage());
//             }
//         }
//     }

//     public void removeShoppingList() {
//         String selectedList = listPanel.getShoppingListJList().getSelectedValue();
//         if (selectedList != null) {
//             try {
//                 manager.removeShoppingList(selectedList);
//                 listPanel.getListModel().removeElement(selectedList);
//                 articleController.clearArticles();
//                 showMessage(listPanel, "Lista rimossa con successo.");
//             } catch (Exception ex) {
//                 showError(listPanel, "Errore: " + ex.getMessage());
//             }
//         } else {
//             showMessage(listPanel, "Seleziona una lista da rimuovere.");
//         }
//     }

//     public ShoppingList getSelectedShoppingList() {
//         String selectedListName = listPanel.getShoppingListJList().getSelectedValue();
//         if (selectedListName != null) {
//             return manager.getShoppingList(selectedListName);
//         }
//         return null;
//     }

//     public void updateArticleList(String listName) {
//         articleController.updateArticleList(listName);
//     }
// }
