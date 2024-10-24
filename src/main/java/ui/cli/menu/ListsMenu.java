package ui.cli.menu;

import model.domain.ListManager;
import ui.cli.base.BaseMenu;
import model.util.InputReader;

public class ListsMenu extends BaseMenu {

    public ListsMenu(ListManager manager) {
        super(manager, new InputReader(System.in));
    }

    @Override
    public void start() {
        while (true) {
            try {
                displayMenu();
                switch (inputReader.getString()) {
                    case "1":
                        createNewList();
                        break;
                    case "2":
                        removeList();
                        break;
                    case "3":
                        manageList();
                        break;
                    case "4":
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
        displayItems(manager.getShoppingLists().keySet(), "Liste esistenti:", false);

        showMessage("\n--- Menu liste ---");
        showMessage("1 - Crea nuova lista");
        showMessage("2 - Rimuovi lista");
        showMessage("3 - Visualizza/Modifica una lista");
        showMessage("4 - Torna al menu principale");

        getOption();
    }

    private void createNewList() {
        getPrompt("\nInserisci il nome della nuova lista: ");
        manager.addShoppingList(inputReader.getString());
        showMessage("Lista creata con successo.");
    }

    private void removeList() {
        if (manager.getShoppingLists().isEmpty()) {
            showMessage("Non ci sono liste da rimuovere.");
            return;
        }

        getPrompt("\nInserisci il nome della lista da rimuovere: ");
        try {
            manager.removeShoppingList(inputReader.getString());
            showMessage("Lista rimossa con successo.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void manageList() {
        if (manager.getShoppingLists().isEmpty()) {
            showMessage("Non ci sono liste disponibili.\nCreane una nuova prima di gestirla.");
            return;
        }

        getPrompt("\nInserisci il nome della lista da gestire: ");
        String listName = inputReader.getString();

        if (!manager.getShoppingLists().containsKey(listName)) {
            showMessage("Lista non trovata.");
            return;
        } else {
            ArticlesMenu articlesController = new ArticlesMenu(manager, manager.getShoppingList(listName));
            articlesController.start();
        }
    }
}
