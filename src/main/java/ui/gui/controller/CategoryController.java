// package ui.gui.controller;

// import ui.gui.base.BaseController;
// import ui.gui.view.panel.CategoryPanel;
// import model.ListManager;

// import javax.swing.JOptionPane;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class CategoryController extends BaseController implements ActionListener {
//     private CategoryPanel categoryPanel;
//     private ListManager manager;

//     public CategoryController(CategoryPanel categoryPanel, ListManager manager) {
//         this.categoryPanel = categoryPanel;
//         this.manager = manager;

//         initializeCategories();
//         addListeners();
//     }

//     private void addListeners() {
//         categoryPanel.addCategoryButton.addActionListener(this);
//         categoryPanel.removeCategoryButton.addActionListener(this);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         Object source = e.getSource();

//         if (source == categoryPanel.addCategoryButton) {
//             addCategory();
//         } else if (source == categoryPanel.removeCategoryButton) {
//             removeCategory();
//         }
//     }

//     public void initializeCategories() {
//         categoryPanel.getCategoryModel().clear();
//         for (String category : manager.getCategories()) {
//             categoryPanel.getCategoryModel().addElement(category);
//         }
//     }

//     public void updateCategories() {
//         initializeCategories();
//     }

//     public void addCategory() {
//         String category = JOptionPane.showInputDialog(categoryPanel, "Inserisci il nome della nuova categoria:");
//         if (category != null && !category.trim().isEmpty()) {
//             try {
//                 manager.addCategory(category.trim());
//                 categoryPanel.getCategoryModel().addElement(category.trim());
//                 showMessage(categoryPanel, "Categoria aggiunta con successo.");
//             } catch (Exception ex) {
//                 showError(categoryPanel, "Errore: " + ex.getMessage());
//             }
//         }
//     }

//     public void removeCategory() {
//         String category = categoryPanel.getCategoryJList().getSelectedValue();
//         if (category != null && !category.trim().isEmpty()) {
//             try {
//                 manager.removeCategory(category.trim());
//                 categoryPanel.getCategoryModel().removeElement(category);
//                 showMessage(categoryPanel, "Categoria rimossa con successo.");
//             } catch (Exception ex) {
//                 showError(categoryPanel, "Errore: " + ex.getMessage());
//             }
//         } else {
//             showMessage(categoryPanel, "Seleziona una categoria da rimuovere.");
//         }
//     }
// }
