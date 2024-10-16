package ui.cli;

import elaboration.InputReader;
import elaboration.ListManager;

import ui.cli.menu.ListsMenuCLI;
import ui.cli.menu.CategoriesMenuCLI;

public class MainMenuCLI {
    private ListManager manager;
    private InputReader inputReader;

    public MainMenuCLI(InputReader inputReader) {
        manager = new ListManager();
        this.inputReader = inputReader;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Menu principale ---");
            System.out.println("1. Gestisci liste esistenti");
            System.out.println("2. Gestisci lista categorie");
            System.out.println("3. Ritorna alla selezione dell'IU");
            System.out.println("4. Termina programma");
            System.out.print("\nSeleziona un'opzione: ");

            try {
                switch (inputReader.readLine()) {
                    case "1":
                        ListsMenuCLI listsCLI = new ListsMenuCLI(manager, inputReader);
                        listsCLI.start();
                        break;
                    case "2":
                        CategoriesMenuCLI categoriesCLI = new CategoriesMenuCLI(manager, inputReader);
                        categoriesCLI.start();
                        break;
                    case "3":
                        System.out.print("\nSe ritorni alla selezione dell'interfaccia utente perderai tutte le liste e gli articoli creati.\nVuoi tornare indietro? (s/n): ");
                        if (inputReader.readLine().trim().toLowerCase().equals("s")) {
                            return;
                        } else {
                            break;
                        }
                    case "4":
                        System.out.println("\nArrivederci!");
                        System.exit(0);
                    default:
                        System.out.println("\nOpzione non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("\nErrore: " + e.getMessage());
            }
        }
    }
}
