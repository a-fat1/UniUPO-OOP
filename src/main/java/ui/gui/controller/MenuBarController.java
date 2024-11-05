package ui.gui.controller;

import ui.gui.view.bar.MenuBar;
import ui.gui.view.bar.FileChooser;
import ui.gui.base.BaseController;

import model.Article;
import model.ShoppingList;
import model.CategoryManager;
import model.InputOutputList;

import model.exceptions.io.InvalidInputException;
import model.exceptions.io.FileOperationException;
import model.exceptions.domain.ArticleNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class MenuBarController extends BaseController {
    private MenuBar menuBar;
    private JFrame frame;
    private Consumer<Boolean> onReturn;
    private ListController listController;
    private CategoryManager categoryManager;
    private CategoryController categoryController;
    private InputOutputList inputOutputList;

    public MenuBarController(MenuBar menuBar, JFrame frame, Consumer<Boolean> onReturn, ListController listController, CategoryManager categoryManager, CategoryController categoryController) {
        this.menuBar = menuBar;
        this.frame = frame;
        this.onReturn = onReturn;
        this.listController = listController;
        this.categoryManager = categoryManager;
        this.categoryController = categoryController;
        this.inputOutputList = new InputOutputList();

        addMenuListeners();
    }
        
    private void addMenuListeners() {
        menuBar.saveListItem.addActionListener(e -> saveList());
        menuBar.loadListItem.addActionListener(e -> loadList());
        menuBar.searchItemByName.addActionListener(e -> searchArticlesByPrefix());
        menuBar.searchItemByCategory.addActionListener(e -> searchArticlesByCategory());
        menuBar.clearList.addActionListener(e -> removeAllArticles());
        menuBar.exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Tutte le liste non salvata andranno perdute. Sei sicuro di voler ritornare alla selezione dell'interfaccia?", "Termina interfaccia grafica", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (onReturn != null) {
                    onReturn.accept(false);
                }
                frame.dispose();
            }
        });
    }
    // TODO: io in questi due metodi devo mettere un showConfirmDialog per chiedere conferma all'utente dopo il fileChooser
    private void saveList() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Lista da salvare", "Seleziona una lista da salvare.");
            return;
        }
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File di testo (*.txt)", "txt"));
    
        try {
            // Assicura che la directory "lists" esista e la imposta come directory predefinita del file chooser
            fileChooser.setCurrentDirectory(new File(inputOutputList.checkListsDir()));
        } catch (FileOperationException e) {
            // Non fare nulla se non si riesce ad accedere o creare la cartella "lists"
            // Il file chooser aprirà la directory predefinita del sistema
        }
    
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename += ".txt";
            }
            File filePath = new File(filename);

            if (filePath.exists()) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Il file esiste già. Vuoi sovrascriverlo?", "Conferma sovrascrittura", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return; // L'utente ha scelto di non sovrascrivere il file
                }
            }

            try {
                inputOutputList.saveToFile(selectedList, filePath);
                showMessage(frame, "Lista salvata", "Lista salvata con successo.");
            } catch (FileOperationException e) {
                showError(frame, "Errore salvataggio lista", "Errore durante il salvataggio: " + e.getMessage());
            }
        }
    }

    private void loadList() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Lista da caricare", "Seleziona una lista in cui caricare gli articoli.");
            return;
        }
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File di testo (*.txt)", "txt"));
    
        try {
            // Assicura che la directory "lists" esista e la imposta come directory predefinita del file chooser
            fileChooser.setCurrentDirectory(new File(inputOutputList.checkListsDir()));
        } catch (FileOperationException e) {
            // Non fare nulla se non si riesce ad accedere o creare la cartella "lists"
            // Il file chooser aprirà la directory predefinita del sistema
        }
    
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Il contenuto della lista selezionata verrà sovrascritto. Vuoi continuare?", "Conferma caricamento", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // L'utente ha scelto di non caricare il file
            }

            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                File filePath = new File(filename);
                inputOutputList.loadFromFile(selectedList, categoryManager, filePath);
                listController.updateArticleList(selectedList.getName());
                categoryController.updateCategories();
                showMessage(frame, "Lista caricata", "Lista caricata con successo.");
            } catch (FileOperationException | InvalidInputException ex) {
                showError(frame, "Errore caricamento", "Errore: " + ex.getMessage());
            }
        }
    }

    private void removeAllArticles() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Nessuna lista selezionata", "Seleziona una lista da cui rimuovere tutti gli articoli.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler rimuovere tutti gli articoli dalla lista selezionata?", "Rimuovi articoli", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            selectedList.clearArticles();
            listController.updateArticleList(selectedList.getName());
            showMessage(frame, "Articoli rimossi", "Tutti gli articoli sono stati rimossi dalla lista.");
        }
    }

    private void searchArticlesByPrefix() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Nessuna lista selezionata", "Seleziona una lista per effettuare la ricerca.");
            return;
        }

        String prefix = JOptionPane.showInputDialog(frame, "Inserisci il prefisso da cercare:");
        if (prefix != null) {
            try {
                List<Article> articles = selectedList.findArticles(prefix.trim(), false);
                StringBuilder result = new StringBuilder("Articoli trovati:\n");
                for (Article article : articles) {
                    result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
                }
                showMessage(frame, "Risultato ricerca", result.toString());
            } catch (InvalidInputException e) {
                showError(frame, "Prefisso invalido", "Errore: " + e.getMessage());
            } catch (ArticleNotFoundException e) {
                showMessage(frame, "Errore ricerca", "Nessun articolo trovato con il prefisso '" + prefix + "'.");
            }
            
        }
    }

    private void searchArticlesByCategory() {
        ShoppingList selectedList = listController.getSelectedShoppingList();
        if (selectedList == null) {
            showMessage(frame, "Nessuna lista selezionata", "Seleziona una lista per effettuare la ricerca.");
            return;
        }

        String category = JOptionPane.showInputDialog(frame, "Inserisci la categoria da cercare:");
        if (category != null) {
            try {
                List<Article> articles = selectedList.findArticles(category.trim(), true);
                StringBuilder result = new StringBuilder("Articoli trovati:\n");
                for (Article article : articles) {
                    result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
                }
                showMessage(frame, "Risultato ricerca", result.toString());
            } catch (InvalidInputException e) {
                showError(frame, "Categoria invalida", "Errore: " + e.getMessage());
            } catch (ArticleNotFoundException e) {
                showMessage(frame, "Errore ricerca", "Nessun articolo trovato nella categoria '" + category + "'.");
            }
        }
    }
}
