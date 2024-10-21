package ui.cli.menu;

import model.domain.*;

import java.util.List;
import java.io.IOException;

public class ArticlesMenuCLI {
	private ListManager manager;
	private ShoppingList list;
	private InputReader inputReader;

	public ArticlesMenuCLI(ListManager manager, ShoppingList list, InputReader inputReader) {
		this.manager = manager;
		this.list = list;
		this.inputReader = inputReader;
	}

	public void start() {
		while (true) {
			displayArticles(list);

			System.out.println("\n--- Menu lista '" + list.getName() + "' ---");
			System.out.println("1. Aggiungi articolo");
			System.out.println("2. Rimuovi articolo");
			System.out.println("3. Ricerca articoli per nome");
			System.out.println("4. Ricerca articoli per categoria");
			System.out.println("5. Salva lista su file");
			System.out.println("6. Carica lista da file");
			System.out.println("7. Torna al menu precedente");
			System.out.print("\nSeleziona un'opzione: ");

			try {
				switch (inputReader.readLine()) {
					case "1":
						addArticleToList(list);
						break;
					case "2":
						removeArticleFromList(list);
						break;
					case "3":
						searchArticlesByName(list);
						break;
					case "4":
						searchArticlesByCategory(list);
						break;
					case "5":
						saveListToFile(list);
						break;
					case "6":
						loadListFromFile(list);
						break;
					case "7":
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
		String articleName = inputReader.readLine();
		System.out.print("Costo: ");
		double cost = inputReader.readDouble();
		System.out.print("Quantità (predefinita 1): ");
		int quantity = inputReader.readInt();
		quantity = (quantity <= 0) ? 1 : quantity;
		System.out.print("Categoria (predefinita 'Non Categorizzati'): ");
		String category = inputReader.readLine();
		if (category.isEmpty()) {
			category = "Non Categorizzati";
		} else {
			if (!manager.getCategories().contains(category)) {
				System.out.println("\nCategoria non esistente. Aggiungerla? (s/n)");
				if (inputReader.readLine().equalsIgnoreCase("s")) {
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
		if(list.getArticles().isEmpty()) {
			System.out.println("Non ci sono articoli da rimuovere.");
			return;
		}

		System.out.print("\nNome dell'articolo da rimuovere: ");
		list.removeArticle(inputReader.readLine());
		System.out.println("Articolo rimosso con successo.");
	}

	private void searchArticlesByName(ShoppingList list) {
		if(list.getArticles().isEmpty()) {
			System.out.println("Non ci sono articoli da ricercare.");
			return;
		}

		System.out.print("\nInserisci il prefisso da cercare: ");
		String prefix = inputReader.readLine();
		List<Article> articles = list.findArticlesByName(prefix);
		if (articles.isEmpty()) {
			System.out.println("Nessun articolo trovato con il prefisso '" + prefix + "'.");
		} else {
			System.out.println("\nArticoli con prefisso '" + prefix + "':");
			for (Article article : articles) {
				System.out.println("- " + article.getName() + " | Costo: " + article.getCost() + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
			}
		}
	}

	private void searchArticlesByCategory(ShoppingList list) {
		if(list.getArticles().isEmpty()) {
			System.out.println("Non ci sono articoli da ricercare.");
			return;
		}

		System.out.print("\nInserisci la categoria da cercare: ");
		String category = inputReader.readLine();
	
		if (!manager.getCategories().contains(category)) {
			System.out.println("La categoria '" + category + "' non esiste.");
			return;
		}
	
		List<Article> articles = list.findArticlesByCategory(category);
		if (articles.isEmpty()) {
			System.out.println("Nessun articolo trovato nella categoria '" + category + "'.");
		} else {
			System.out.println("\nArticoli nella categoria '" + category + "':");
			for (Article article : articles) {
				System.out.println("- " + article.getName() + " | Costo: " + article.getCost() + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
			}
		}
	}    

	private void saveListToFile(ShoppingList list) {
		if(list.getArticles().isEmpty()) {
			System.out.println("Non ci sono articoli da salvare.");
			return;
		}

		System.out.print("\nInserisci il nome del file per salvare la lista: ");
		String filename = inputReader.readLine();
		try {
			list.saveToFile(filename);
			System.out.println("Lista salvata con successo su file '" + filename + "'.");
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio della lista: " + e.getMessage());
		}
	}

	private void loadListFromFile(ShoppingList list) {
		System.out.print("\nInserisci il nome del file da cui caricare la lista: ");
		String filename = inputReader.readLine();
		try {
			list.loadFromFile(filename);
			System.out.println("Lista caricata con successo dal file '" + filename + "'.");
		} catch (IOException e) {
			System.out.println("Errore durante il caricamento della lista: " + e.getMessage());
		}
	}
}
