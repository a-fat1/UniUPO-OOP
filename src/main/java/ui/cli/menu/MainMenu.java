package ui.cli.menu;

import model.util.*;
import model.domain.ListManager;
import ui.cli.base.BaseMenu;

public class MainMenu extends BaseMenu {

    public MainMenu() {
        super(new ListManager(), new InputReader(System.in));
    }

    @Override
    public void start() {
        while (true) {
            try {
                displayMenu();
                switch (inputReader.getString()) {
                    case "1":
                        ListsMenu listsController = new ListsMenu(manager);
                        listsController.start();
                        break;
                    case "2":
                        CategoriesMenu categoriesController = new CategoriesMenu(manager);
                        categoriesController.start();
                        break;
                    case "3":
                        if (confirmQuestion("\nSe ritorni alla selezione dell'interfaccia utente\nperderai tutte le liste e gli articoli creati.\n\nVuoi tornare indietro?")) {
                            return;
                        }
                        break;
                    case "4":
                        showGoodbye();
                        System.exit(0);
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
        showMessage("\n--- Menu principale ---");
        showMessage("1 - Gestisci liste esistenti");
        showMessage("2 - Gestisci lista categorie");
        showMessage("3 - Ritorna alla selezione dell'IU");
        showMessage("4 - Termina programma");
        
        getOption();
    }
}
