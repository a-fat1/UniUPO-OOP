package ui.cli.menu;

import model.util.*;
import model.domain.*;
import ui.cli.base.BaseMenu;

import java.util.List;
import java.io.IOException;

public class ArticlesMenu extends BaseMenu {
    private ShoppingList list;

    public ArticlesMenu(ListManager manager, ShoppingList list) {
        super(manager, new InputReader(System.in));
        this.list = list;
    }

    @Override
    public void start() {
        while (true) {
            try {
                displayMenu();
                switch (inputReader.getString()) {
                    case "1":
                        addArticleToList();
                        break;
                    case "2":
                        removeArticleFromList();
                        break;
                    case "3":
                        searchArticlesByName();
                        break;
                    case "4":
                        searchArticlesByCategory();
                        break;
                    case "5":
                        saveListToFile();
                        break;
                    case "6":
                        loadListFromFile();
                        break;
                    case "7":
                        return;
                    default:
                        showInvalidOption();
                }
            } catch (Exception e) {
                showError(e.getMessage());
            }
        }
    }

    @Override
    public void displayMenu() {
        displayItems(list.getArticles(), "Articoli nella lista:", true);
        displayTotal(list.getTotalQuantity(), list.getTotalCost());

        showMessage("\n--- Menu lista '" + list.getName() + "' ---");
        showMessage("1 - Aggiungi articolo");
        showMessage("2 - Rimuovi articolo");
        showMessage("3 - Ricerca articoli per nome");
        showMessage("4 - Ricerca articoli per categoria");
        showMessage("5 - Salva lista su file");
        showMessage("6 - Carica lista da file");
        showMessage("7 - Torna al menu precedente");

        getOption();
    }

    private void addArticleToList() {
        Article article = getArticleDetails();

        if (!manager.getCategories().contains(article.getCategory())) {
            if (confirmQuestion("\nCategoria '" + article.getCategory() + "' non esistente. Vuoi aggiungerla?")) {
                manager.addCategory(article.getCategory());
            } else {
                article.setCategory("Non Categorizzati");
            }
        }

        list.addArticle(article);
        showMessage("Articolo aggiunto con successo.");
    }

    private Article getArticleDetails() {
        getPrompt("\nNome dell'articolo: ");
        String articleName = inputReader.getString();

        getPrompt("Costo: ");
        double cost = inputReader.getDouble();

        getPrompt("Quantità (predefinita 1): ");
        int quantity = inputReader.getInt();
        if (quantity <= 0) { quantity = 1; }

        getPrompt("Categoria (predefinita 'Non Categorizzati'): ");
        String category = inputReader.getString();
        if (category.isEmpty()) { category = "Non Categorizzati"; }

        return new Article(articleName, cost, quantity, category);
    }

    private void removeArticleFromList() {
        if (list.getArticles().isEmpty()) {
            showMessage("Non ci sono articoli da rimuovere.");
            return;
        }

        getPrompt("\nNome dell'articolo da rimuovere: ");
        list.removeArticle(inputReader.getString());
        showMessage("Articolo rimosso con successo.");
    }

    private void searchArticlesByName() {
        if (list.getArticles().isEmpty()) {
            showMessage("Non ci sono articoli da ricercare.");
            return;
        }

        getPrompt("\nInserisci il prefisso da cercare: ");
        String prefix = inputReader.getString();
        List<Article> articles = list.findArticlesByName(prefix);

        if (articles.isEmpty()) {
            showMessage("Nessun articolo trovato con il prefisso '" + prefix + "'.");
        } else {
            displayItems(articles, "Articoli con prefisso '" + prefix + "':", true);
        }
    }

    private void searchArticlesByCategory() {
        if (list.getArticles().isEmpty()) {
            showMessage("Non ci sono articoli da ricercare.");
            return;
        }

        getPrompt("\nInserisci la categoria: ");
        String category = inputReader.getString();
        if (!manager.getCategories().contains(category)) {
            showMessage("La categoria '" + category + "' non esiste.");
            return;
        }

        List<Article> articles = list.findArticlesByCategory(category);
        if (articles.isEmpty()) {
            showMessage("Nessun articolo associato alla categoria '" + category + "'.");
        } else {
            displayItems(articles, "Articoli nella categoria '" + category + "':", true);
        }
    }

    private void saveListToFile() {
        if (list.getArticles().isEmpty()) {
            showMessage("Non ci sono articoli da salvare.");
            return;
        }

        getPrompt("\nInserisci il nome del file per salvare la lista: ");
        String filename = inputReader.getString();
        try {
            list.saveToFile(filename);
            showMessage("Lista salvata con successo su file '" + filename + "'.");
        } catch (IOException e) {
            showError("Errore durante il salvataggio della lista: " + e.getMessage());
        }
    }

    private void loadListFromFile() {
        getPrompt("\nInserisci il nome del file per caricare sulla lista esistente: ");
        String filename = inputReader.getString();
        try {
            if (confirmQuestion("La lista '" + filename + "' sovrascriverà la lista attuale. Continuare?")) {
                list.loadFromFile(filename);
                showMessage("Lista caricata con successo dal file '" + filename + "'.");
            }
        } catch (IOException e) {
            showError("Errore durante il caricamento della lista: " + e.getMessage());
        }
    }
}
