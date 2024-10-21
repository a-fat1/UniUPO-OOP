package ui.cli.menu;

import model.domain.InputReader;
import model.domain.ListManager;

public class CategoriesMenuCLI {
	private ListManager manager;
	private InputReader inputReader;

	public CategoriesMenuCLI(ListManager manager, InputReader inputReader) {
		this.manager = manager;
		this.inputReader = inputReader;
	}

	public void start() {
		while (true) {
			displayCategories();

			System.out.println("\n--- Menu categorie ---");
			System.out.println("1. Aggiungi categoria");
			System.out.println("2. Rimuovi categoria");
			System.out.println("3. Torna al menu principale");
			System.out.print("\nSeleziona un'opzione: ");
			try {
				switch (inputReader.readLine()) {
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
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
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
		manager.addCategory(inputReader.readLine());
		System.out.println("Categoria aggiunta con successo.");
	}

	private void removeCategory() {
		if (manager.getCategories().isEmpty()) {
			System.out.println("Nessuna categoria disponibile.");
			return;
		}
		System.out.print("\nInserisci il nome della categoria da rimuovere: ");
		manager.removeCategory(inputReader.readLine());
		System.out.println("Categoria rimossa. Gli articoli sono stati aggiornati a 'Non Categorizzati'.");
	}
}
