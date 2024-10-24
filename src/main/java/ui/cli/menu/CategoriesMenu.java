package ui.cli.menu;

import model.domain.ListManager;
import model.util.InputReader;
import ui.cli.base.BaseMenu;

public class CategoriesMenu extends BaseMenu {

    public CategoriesMenu(ListManager manager) {
        super(manager, new InputReader(System.in));
    }

    @Override
    public void start() {
        while (true) {
            try {
                displayMenu();
                switch (inputReader.getString()) {
                    case "1":
                        addCategory();
                        break;
                    case "2":
                        removeCategory();
                        break;
                    case "3":
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
        displayItems(manager.getCategories(), "Categorie esistenti:", false);

        showMessage("\n--- Menu categorie ---");
        showMessage("1 - Aggiungi categoria");
        showMessage("2 - Rimuovi categoria");
        showMessage("3 - Torna al menu principale");

        getOption();
    }

    private void addCategory() {
        getPrompt("\nInserisci il nome della nuova categoria: ");
        manager.addCategory(inputReader.getString());
        showMessage("Categoria aggiunta con successo.");
    }

    private void removeCategory() {
        if (manager.getCategories().isEmpty()) {
            showMessage("Nessuna categoria disponibile.");
            return;
        }

        getPrompt("\nInserisci il nome della categoria da rimuovere: ");
        manager.removeCategory(inputReader.getString());
        showMessage("Categoria rimossa. Gli articoli sono stati aggiornati a 'Non Categorizzati'.");
    }
}
