package ui;

import elaboration.*;

import java.util.*;
import java.io.IOException;

public class ShoppingListCLI {
	private ListManager manager;
	private Scanner scanner;

	public ShoppingListCLI() {
		manager = new ListManager();
		scanner = new Scanner(System.in);
	}

	public void start() {
		while (true) {
			System.out.println("\n--- Gestione Liste della Spesa ---");
			System.out.println("1. Crea nuova lista");
			System.out.println("2. Gestisci lista esistente");
			System.out.println("3. Aggiungi categoria");
			System.out.println("4. Rimuovi categoria");
			System.out.println("5. Esci");
			System.out.print("Seleziona un'opzione: ");

			String choice = scanner.nextLine();
			try {
				switch (choice) {
					case "1":
						createNewList();
						break;
					case "2":
						manageExistingList();
						break;
					case "3":
						addCategory();
						break;
					case "4":
						removeCategory();
						break;
					case "5":
						System.out.println("Arrivederci!");
						return;
					default:
						System.out.println("Opzione non valida. Riprova.");
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
	}

	private void createNewList() {
		System.out.print("Inserisci il nome della nuova lista: ");
		String listName = scanner.nextLine();
		manager.addShoppingList(listName);
		System.out.println("Lista creata con successo.");
	}

	private void manageExistingList() {
		if (manager.getShoppingLists().isEmpty()) {
			System.out.println("Non ci sono liste disponibili. Creane una nuova prima di gestirla.");
			return;
		}

		System.out.println("Liste disponibili:");
		for (String listName : manager.getShoppingLists().keySet()) {
			System.out.println("- " + listName);
		}
		System.out.print("Inserisci il nome della lista da gestire: ");
		String listName = scanner.nextLine();

		if (!manager.getShoppingLists().containsKey(listName)) {
			System.out.println("Lista non trovata.");
			return;
		}

		ShoppingList list = manager.getShoppingList(listName);

		while (true) {
			displayArticles(list);

			System.out.println("\n--- Gestione Lista '" + listName + "' ---");
			System.out.println("1. Aggiungi articolo");
			System.out.println("2. Rimuovi articolo");
			System.out.println("3. Cerca articoli per prefisso");
			System.out.println("4. Salva lista su file");
			System.out.println("5. Carica lista da file");
			System.out.println("6. Torna al menu principale");
			System.out.print("Seleziona un'opzione: ");

			String choice = scanner.nextLine();
			try {
				switch (choice) {
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

	private void addCategory() {
		System.out.print("Inserisci il nome della nuova categoria: ");
		String category = scanner.nextLine();
		manager.addCategory(category);
		System.out.println("Categoria aggiunta con successo.");
	}

	private void removeCategory() {
		System.out.print("Inserisci il nome della categoria da rimuovere: ");
		String category = scanner.nextLine();
		manager.removeCategory(category);
		System.out.println("Categoria rimossa. Gli articoli sono stati aggiornati a 'Non Categorizzati'.");
	}

	private void addArticleToList(ShoppingList list) {
		System.out.print("Nome dell'articolo: ");
		String articleName = scanner.nextLine();
		System.out.print("Costo: ");
		double cost = Double.parseDouble(scanner.nextLine());
		System.out.print("Quantità (predefinita 1): ");
		String qtyInput = scanner.nextLine();
		int quantity = qtyInput.isEmpty() ? 1 : Integer.parseInt(qtyInput);
		System.out.print("Categoria (predefinita 'Non Categorizzati'): ");
		String category = scanner.nextLine();
		if (category.isEmpty()) {
			category = "Non Categorizzati";
		} else {
			if (!manager.getCategories().contains(category)) {
				System.out.println("Categoria non esistente. Aggiungerla? (s/n)");
				String addCategory = scanner.nextLine();
				if (addCategory.equalsIgnoreCase("s")) {
					manager.addCategory(category);
				} else {
					category = "Non Categorizzati";
				}
			}
		}

		Article article = new Article(articleName, cost, quantity, category);
		list.addArticle(article);
		System.out.println("Articolo aggiunto con successo.");
	}

	private void removeArticleFromList(ShoppingList list) {
		System.out.print("Nome dell'articolo da rimuovere: ");
		String articleName = scanner.nextLine();
		list.removeArticle(articleName);
		System.out.println("Articolo rimosso con successo.");
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
		System.out.println("Totale articoli: " + list.getTotalQuantity());
		System.out.println("Costo totale: " + list.getTotalCost());
	}

	private void searchArticlesByPrefix(ShoppingList list) {
		System.out.print("Inserisci il prefisso da cercare: ");
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
		System.out.print("Inserisci il nome del file per salvare la lista: ");
		String filename = scanner.nextLine();
		try {
			list.saveToFile(filename);
			System.out.println("Lista salvata con successo su file '" + filename + "'.");
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio della lista: " + e.getMessage());
		}
	}

	private void loadListFromFile(ShoppingList list) {
		System.out.print("Inserisci il nome del file da cui caricare la lista: ");
		String filename = scanner.nextLine();
		try {
			list.loadFromFile(filename);
			System.out.println("Lista caricata con successo dal file '" + filename + "'.");
		} catch (IOException e) {
			System.out.println("Errore durante il caricamento della lista: " + e.getMessage());
		}
	}
}
