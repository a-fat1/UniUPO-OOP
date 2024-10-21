package ui.cli.menu;

import model.domain.InputReader;
import model.domain.ListManager;
import model.domain.ShoppingList;

public class ListsMenuCLI {
	private ListManager manager;
	private InputReader inputReader;

	public ListsMenuCLI(ListManager manager, InputReader inputReader) {
		this.manager = manager;
		this.inputReader = inputReader;
	}

	public void start() {
		while (true) {
			displayLists();

			System.out.println("\n--- Menu liste ---");
			System.out.println("1. Crea nuova lista");
			System.out.println("2. Rimuovi lista");
			System.out.println("3. Visualizza/Modifica una lista");
			System.out.println("4. Torna al menu principale");
			System.out.print("\nSeleziona un'opzione: ");

			switch (inputReader.readLine()) {
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
					System.out.println("Opzione non valida. Riprova.");
			}
		}
	}

	private void displayLists() {
		System.out.println("\nListe esistenti:");
		if (manager.getShoppingLists().isEmpty()) {
			System.out.println("Nessuna lista disponibile.");
		} else {
			for (String listName : manager.getShoppingLists().keySet()) {
				System.out.println("- " + listName);
			}
		}
	}

	private void createNewList() {
		System.out.print("\nInserisci il nome della nuova lista: ");
		manager.addShoppingList(inputReader.readLine());
		System.out.println("Lista creata con successo.");
	}

	private void removeList() {
		if (manager.getShoppingLists().isEmpty()) {
			System.out.println("\nNon ci sono liste da rimuovere.");
			return;
		}

		System.out.print("\nInserisci il nome della lista da rimuovere: ");
		try {
			manager.removeShoppingList(inputReader.readLine());
			System.out.println("Lista rimossa con successo.");
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	private void manageList() {
		if (manager.getShoppingLists().isEmpty()) {
			System.out.println("\nNon ci sono liste disponibili.\nCreane una nuova prima di gestirla.");
			return;
		}

		System.out.print("\nInserisci il nome della lista da gestire: ");
		String listName = inputReader.readLine();

		if (!manager.getShoppingLists().containsKey(listName)) {
			System.out.println("Lista non trovata.");
			return;
		}

		ShoppingList list = manager.getShoppingList(listName);
		ArticlesMenuCLI articlesListCLI = new ArticlesMenuCLI(manager, list, inputReader);
		articlesListCLI.start();
	}
}
