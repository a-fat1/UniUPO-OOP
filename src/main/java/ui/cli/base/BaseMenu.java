package ui.cli.base;

import model.util.InputReader;
import model.domain.Article;
import model.domain.ListManager;

import java.util.Collection;

public abstract class BaseMenu {
    protected ListManager manager;
    protected InputReader inputReader;

    // Costruttore che riceve ListManager e InputReader
    public BaseMenu(ListManager manager, InputReader inputReader) {
        this.manager = manager;
        this.inputReader = new InputReader(System.in);
    }

    // Metodi astratti che le classi derivate devono implementare
    public abstract void start();
    public abstract void displayMenu();

    // Metodi helper con implementazione di default
    public void getPrompt(String prompt) {
        System.out.print(prompt);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.err.println("\nErrore: " + message);
    }

    public void showInvalidOption() {
        showError("opzione non valida. Riprova.");
    }

    public void showGoodbye() {
        showMessage("Arrivederci!\n");
    }

    public void getOption() {
        getPrompt("\nSeleziona un'opzione: ");
    }

	public boolean confirmQuestion(String question) {
		String answer;

        getPrompt(question + " (si/no): ");
    	answer = inputReader.getString();

		if (!inputReader.isValidAnswer(answer)) {
			showError("devi inserire 'si' o 'no' come risposta.");
            return inputReader.isValidAnswer(answer);
		} else {
            return inputReader.isPositiveAnswer(answer);
        }
	}

    public <T> void displayItems(Collection<T> items, String listDescription, boolean isArticleList) {
        showMessage("\n" + listDescription);
        if (items.isEmpty()) {
            showMessage("Nessun elemento disponibile.");
        } else {
            for (T item : items) {
                if (isArticleList && item instanceof Article) {
                    Article article = (Article) item;
                    showMessage("- " + article.getName() + " | Costo: " + article.getCost() + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
                } else {
                    showMessage("- " + item.toString());
                }
            }
        }
    }

    // TODO: da inserire in displayItems
    public void displayTotal(int totalArticlesNumber, double totalArticlesCost) {
        showMessage("\nTotale articoli: " + totalArticlesNumber);
        showMessage("Costo totale: " + totalArticlesCost);
    }
}
