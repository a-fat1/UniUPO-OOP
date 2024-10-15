package ui.cli.menu;

import elaboration.*;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ListMenuCLI {
    private ListManager manager;
    private Scanner scanner;
    private ShoppingList list;

    public ListMenuCLI(ListManager manager, Scanner scanner, ShoppingList list) {
        this.manager = manager;
        this.scanner = scanner;
        this.list = list;
    }

    public void start() {
        String listName = list.getName();

        while (true) {
            displayArticles(list);

            System.out.println("\n--- Gestore lista '" + listName + "' ---");
            System.out.println("1. Aggiungi articolo");
            System.out.println("2. Rimuovi articolo");
            System.out.println("3. Cerca articoli per prefisso");
            System.out.println("4. Salva lista su file");
            System.out.println("5. Carica lista da file");
            System.out.println("6. Torna al menu precedente");
            System.out.print("\nSeleziona un'opzione: ");

            try {
                switch (scanner.nextLine()) {
                    case "1":
                        addArticleToList(list);
                        break;
                    case "2":
                        removeArticleFromList(list);
                        break;
                    case "3":
                        searchArticlesByPrefix(list);
                        break;
                    case "4":
                        saveListToFile(list);
                        break;
                    case "5":
                        loadListFromFile(list);
                        break;
                    case "6":
                        return;
                    default:
                        System.out.println("Opzione non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private void displayArticles(ShoppingList list) {
        System.out.println("\nArticoli nella lista:");
        if (list.getArticles().isEmpty()) {
            System.out.println("La lista è vuota.");
        } else {
            for (Article article : list) {
                System.out.println("- " + article.getName() + " | Costo: " + article.getCost() + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
            }
        }
        
        System.out.println("\nTotale articoli: " + list.getTotalQuantity());
        System.out.println("Costo totale: " + list.getTotalCost());
    }

	private void addArticleToList(ShoppingList list) {
        System.out.print("\nNome dell'articolo: ");
        String articleName = scanner.nextLine();
        System.out.print("Costo: ");
        double cost = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantità (predefinita 1): ");
        String quantityInput = scanner.nextLine();
        int quantity = quantityInput.isEmpty() ? 1 : Integer.parseInt(quantityInput);
        System.out.print("Categoria (predefinita 'Non Categorizzati'): ");
        String category = scanner.nextLine();
        if (category.isEmpty()) {
            category = "Non Categorizzati";
        } else {
            if (!manager.getCategories().contains(category)) {
                System.out.println("\nCategoria non esistente. Aggiungerla? (s/n)");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    manager.addCategory(category);
                } else {
                    category = "Non Categorizzati";
                }
            }
        }

        Article article = new Article(articleName, cost, quantity, category);
        list.addArticle(article);
        System.out.println("\nArticolo aggiunto con successo.");
    }

    private void removeArticleFromList(ShoppingList list) {
        System.out.print("\nNome dell'articolo da rimuovere: ");
        list.removeArticle(scanner.nextLine());
        System.out.println("Articolo rimosso con successo.");
    }

    private void searchArticlesByPrefix(ShoppingList list) {
        System.out.print("\nInserisci il prefisso da cercare: ");
        String prefix = scanner.nextLine();
        List<Article> articles = list.findArticlesByPrefix(prefix);
        if (articles.isEmpty()) {
            System.out.println("Nessun articolo trovato con il prefisso '" + prefix + "'.");
        } else {
            System.out.println("\nArticoli con prefisso '" + prefix + "':");
            for (Article article : articles) {
                System.out.println("- " + article.getName() + " | Costo: " + article.getCost() + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
            }
        }
    }

    private void saveListToFile(ShoppingList list) {
        System.out.print("\nInserisci il nome del file per salvare la lista: ");
        String filename = scanner.nextLine();
        try {
            list.saveToFile(filename);
            System.out.println("Lista salvata con successo su file '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della lista: " + e.getMessage());
        }
    }

    private void loadListFromFile(ShoppingList list) {
        System.out.print("\nInserisci il nome del file da cui caricare la lista: ");
        String filename = scanner.nextLine();
        try {
            list.loadFromFile(filename);
            System.out.println("Lista caricata con successo dal file '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento della lista: " + e.getMessage());
        }
    }
}
