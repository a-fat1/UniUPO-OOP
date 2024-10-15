package ui.cli;

import elaboration.ListManager;
import ui.cli.menu.ListsMenuCLI;
import ui.cli.menu.CategoriesMenuCLI;

import java.util.Scanner;

public class MainMenuCLI {
    private ListManager manager;
    private Scanner scanner;

    public MainMenuCLI() {
        manager = new ListManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Menu principale ---");
            System.out.println("1. Gestisci liste esistenti");
            System.out.println("2. Gestisci lista categorie");
            System.out.println("3. Esci");
            System.out.print("Seleziona un'opzione: ");

            try {
                switch (scanner.nextLine()) {
                    case "1":
                        ListsMenuCLI listsCLI = new ListsMenuCLI(manager, scanner);
                        listsCLI.start();
                        break;
                    case "2":
                        CategoriesMenuCLI categoriesCLI = new CategoriesMenuCLI(manager, scanner);
                        categoriesCLI.start();
                        break;
                    case "3":
                        System.out.println("\nArrivederci!");
                        return;
                    default:
                        System.out.println("\nOpzione non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("\nErrore: " + e.getMessage());
            }
        }
    }
}
