package ui.cli.menu;

import elaboration.ListManager;
import java.util.Scanner;

public class CategoriesMenuCLI {
    private ListManager manager;
    private Scanner scanner;

    public CategoriesMenuCLI(ListManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            displayCategories();

            System.out.println("\n--- Menu categorie ---");
            System.out.println("1. Aggiungi categoria");
            System.out.println("2. Rimuovi categoria");
            System.out.println("3. Torna al menu principale");
            System.out.print("\nSeleziona un'opzione: ");

            switch (scanner.nextLine()) {
                case "1":
                    addCategory();
                    break;
                case "2":
                    removeCategory();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private void displayCategories() {
        System.out.println("\nCategorie esistenti:");
        if (manager.getCategories().isEmpty()) {
            System.out.println("Nessuna categoria disponibile.");
        } else {
            for (String category : manager.getCategories()) {
                System.out.println("- " + category);
            }
        }
    }

    private void addCategory() {
        System.out.print("\nInserisci il nome della nuova categoria: ");
        manager.addCategory(scanner.nextLine());
        System.out.println("Categoria aggiunta con successo.");
    }

    private void removeCategory() {
        System.out.print("\nInserisci il nome della categoria da rimuovere: ");
        manager.removeCategory(scanner.nextLine());
        System.out.println("Categoria rimossa. Gli articoli sono stati aggiornati a 'Non Categorizzati'.");
    }
}
