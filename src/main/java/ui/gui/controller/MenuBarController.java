package ui.gui.controller;

import ui.gui.base.BaseController;
import ui.gui.view.bar.FileChooser;
import ui.gui.view.bar.MenuBar;
import model.domain.Article;
import model.domain.ShoppingList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.util.List;
import java.io.IOException;

public class MenuBarController extends BaseController {
    private MenuBar menuBar;
    private JFrame frame;
    private Runnable onReturn;
    private ListController listController;

    public MenuBarController(MenuBar menuBar, JFrame frame, Runnable onReturn) {
        this.menuBar = menuBar;
        this.frame = frame;
        this.onReturn = onReturn;

        addMenuListeners();
    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    private void addMenuListeners() {
        menuBar.saveListItem.addActionListener(e -> saveList());
        menuBar.loadListItem.addActionListener(e -> loadList());
        menuBar.searchItemByName.addActionListener(e -> searchArticlesByPrefix());
        menuBar.searchItemByCategory.addActionListener(e -> searchArticlesByCategory());
        menuBar.exitItem.addActionListener(e -> {
            frame.dispose();
            if (onReturn != null) {
                onReturn.run();
            }
        });
    }

    private void saveList() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Seleziona una lista da salvare.");
            return;
        }

        JFileChooser fileChooser = new FileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File di testo (*.txt)", "txt"));

        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename += ".txt";
            }
            try {
                selectedList.saveToFile(filename);
                showMessage(frame, "Lista salvata con successo.");
            } catch (IOException e) {
                showError(frame, "Errore durante il salvataggio: " + e.getMessage());
            }
        }
    }

    private void loadList() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Seleziona una lista in cui caricare gli articoli.");
            return;
        }

        JFileChooser fileChooser = new FileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File di testo (*.txt)", "txt"));

        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                selectedList.loadFromFile(filename);
                listController.updateArticleList(selectedList.getName());
                showMessage(frame, "Lista caricata con successo.");
            } catch (IOException e) {
                showError(frame, "Errore durante il caricamento: " + e.getMessage());
            }
        }
    }

    private void searchArticlesByPrefix() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Seleziona una lista per effettuare la ricerca.");
            return;
        }

        String prefix = JOptionPane.showInputDialog(frame, "Inserisci il prefisso da cercare:");
        if (prefix != null && !prefix.trim().isEmpty()) {
            List<Article> articles = selectedList.findArticlesByName(prefix.trim());
            if (articles.isEmpty()) {
                showMessage(frame, "Nessun articolo trovato con il prefisso '" + prefix + "'.");
            } else {
                StringBuilder result = new StringBuilder("Articoli trovati:\n");
                for (Article article : articles) {
                    result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
                }
                showMessage(frame, result.toString());
            }
        }
    }

    private void searchArticlesByCategory() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Seleziona una lista per effettuare la ricerca.");
            return;
        }

        String category = JOptionPane.showInputDialog(frame, "Inserisci la categoria da cercare:");
        if (category != null && !category.trim().isEmpty()) {
            List<Article> articles = selectedList.findArticlesByCategory(category.trim());
            if (articles.isEmpty()) {
                showMessage(frame, "Nessun articolo trovato nella categoria '" + category + "'.");
            } else {
                StringBuilder result = new StringBuilder("Articoli trovati:\n");
                for (Article article : articles) {
                    result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
                }
                showMessage(frame, result.toString());
            }
        }
    }
}
