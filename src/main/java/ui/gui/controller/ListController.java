package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.panel.ListPanel;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ListNotFoundException;

import javax.swing.JOptionPane;

import model.ListManager;
import model.ShoppingList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListController extends BaseController implements ActionListener {
    private ListPanel listPanel;
    private ListManager manager;
    private ArticleController articleController;

    public ListController(ListPanel listPanel, ListManager manager) {
        this.listPanel = listPanel;
        this.manager = manager;

        addListeners();
        initializeLists();
    }

    public void setArticleController(ArticleController articleController) {
        this.articleController = articleController;
    }

    private void addListeners() {
        listPanel.getShoppingListJList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedList = listPanel.getShoppingListJList().getSelectedValue();
                articleController.updateArticleList(selectedList);
            }
        });

        // Listener per i bottoni
        listPanel.addListButton.addActionListener(this);
        listPanel.removeListButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == listPanel.addListButton) {
            addShoppingList();
        } else if (source == listPanel.removeListButton) {
            removeShoppingList();
        }
    }

    public void addShoppingList() {
        String listName = JOptionPane.showInputDialog(listPanel, "Inserisci il nome della nuova lista:");
        if (listName != null) {
            try {
                manager.addShoppingList(listName.trim());
                listPanel.getListModel().addElement(listName.trim());
                showMessage(listPanel, "Lista creata", "Lista creata con successo.");
            } catch (InvalidInputException ex) {
                showError(listPanel, "Errore creazione lista", "Errore: " + ex.getMessage());
            }
        }
    }

    public void removeShoppingList() {
        String selectedList = listPanel.getShoppingListJList().getSelectedValue();
        if (selectedList != null) {
            try {
                manager.removeShoppingList(selectedList);
                listPanel.getListModel().removeElement(selectedList);
                articleController.clearArticles();
                showMessage(listPanel, "Lista rimossa", "Lista rimossa con successo.");
            } catch (ListNotFoundException ex) {
                showError(listPanel, "Errore di rimozione", "Errore: " + ex.getMessage());
            }
        } else {
            showMessage(listPanel, "Nessuna lista selezionata", "Seleziona una lista da rimuovere.");
        }
    }

    public ShoppingList getSelectedShoppingList() {
        String selectedListName = listPanel.getShoppingListJList().getSelectedValue();
        if (selectedListName != null) {
            try {
                return manager.getShoppingList(selectedListName);
            } catch (ListNotFoundException ex) {
                showError(listPanel, "Errore lista", "Errore: " + ex.getMessage());
            }
        }
        return null;
    }

    public void updateArticleList(String listName) {
        articleController.updateArticleList(listName);
    }

    public void initializeLists() {
        listPanel.getListModel().clear();
        for (ShoppingList shoppingList : manager.getShoppingLists()) {
            listPanel.getListModel().addElement(shoppingList.getName());
        }
    }
}
