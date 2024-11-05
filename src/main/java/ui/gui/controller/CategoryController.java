package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.panel.CategoryPanel;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.CategoryNotFoundException;

import model.ListManager;
import model.CategoryManager;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Set;

public class CategoryController extends BaseController implements ActionListener {
    private CategoryPanel categoryPanel;
    private CategoryManager categoryManager;
    private ListManager listManager;

    public CategoryController(CategoryPanel categoryPanel, CategoryManager categoryManager, ListManager listManager) {
        this.categoryPanel = categoryPanel;
        this.categoryManager = categoryManager;
        this.listManager = listManager;

        initializeCategories();
        addListeners();
    }

    private void addListeners() {
        categoryPanel.addCategoryButton.addActionListener(this);
        categoryPanel.removeCategoryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == categoryPanel.addCategoryButton) {
            addCategory();
        } else if (source == categoryPanel.removeCategoryButton) {
            removeCategory();
        }
    }

    public void initializeCategories() {
        categoryPanel.getCategoryModel().clear();
        Set<String> categories = categoryManager.getCategories();
        for (String category : categories) {
            categoryPanel.getCategoryModel().addElement(category);
        }
    }

    public void updateCategories() {
        initializeCategories();
    }

    // Metodo esistente per aggiungere una categoria tramite input utente
    public void addCategory() {
        String category = JOptionPane.showInputDialog(categoryPanel, "Inserisci il nome della nuova categoria:");
        if (category != null && !category.trim().isEmpty()) {
            try {
                categoryManager.addCategory(category.trim());
                categoryPanel.getCategoryModel().addElement(category.trim());
                showMessage(categoryPanel, "Aggiunta categoria", "Categoria aggiunta con successo.");
            } catch (InvalidInputException ex) {
                showError(categoryPanel, "Errore categoria", "Errore: " + ex.getMessage());
            }
        }
    }

    public void addCategory(String categoryName) throws InvalidInputException {
        categoryManager.addCategory(categoryName);
        categoryPanel.getCategoryModel().addElement(categoryName);
        showMessage(categoryPanel, "Aggiunta categoria", "Categoria aggiunta con successo.");
    }

    public void removeCategory() {
        String category = categoryPanel.getCategoryJList().getSelectedValue();
        if (category != null && !category.trim().isEmpty()) {
            try {
                categoryManager.removeCategory(category.trim());
                // Aggiorna le categorie negli articoli delle liste della spesa
                categoryManager.updateCategoryInAllLists(listManager.getShoppingLists(), category.trim());
                categoryPanel.getCategoryModel().removeElement(category);
                showMessage(categoryPanel, "Categoria rimossa", "Categoria rimossa con successo.");
            } catch (InvalidInputException | CategoryNotFoundException ex) {
                showError(categoryPanel, "Errore categoria", "Errore: " + ex.getMessage());
            }
        } else {
            showMessage(categoryPanel, "Nessuna categoria selezionata", "Seleziona una categoria da rimuovere.");
        }
    }

    public Set<String> getAllCategories() {
        return categoryManager.getCategories();
    }
}
