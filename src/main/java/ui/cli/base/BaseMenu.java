package ui.cli.base;

import model.Article;

import java.util.Scanner;
import java.util.Collection;

/**
 * Classe astratta che fornisce funzionalità di base per la gestione di menu CLI.
 * Gestisce l'input da console e fornisce metodi di utilità per mostrare messaggi,
 * gestire errori e confermare azioni dell'utente.
 */
public abstract class BaseMenu {
	/** Scanner per la lettura dell'input da console. */
	protected Scanner scanner;

	/**
	 * Inizializza lo scanner per l'input da console.
	 */
	public BaseMenu() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Metodo astratto che avvia il menu.
	 * Deve essere implementato dalle classi derivate.
	 */
	public abstract void start();

	/**
	 * Metodo astratto che visualizza le opzioni del menu.
	 * Deve essere implementato dalle classi derivate.
	 */
	public abstract void displayMenu();

	/** Chiude lo scanner, da richiamare alla fine del programma. */
	public void closeScanner() {
		if (scanner != null) {
			scanner.close();
		}
	}

	/**
	 * Legge una stringa dall'input dopo aver mostrato un messaggio di prompt.
	 *
	 * @param prompt il messaggio da mostrare prima della lettura dell'input.
	 * @return La stringa letta dall'utente, senza spazi iniziali e finali.
	 */
	protected String readString(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();	// trim() rimuove gli spazi superflui dall'input
	}

	/**
	 * Legge un numero intero dall'input gestendo gli errori di formattazione.
	 *
	 * @param prompt il messaggio da mostrare per la richiesta di input
	 * @return Il numero intero inserito dall'utente
	 */
	protected int readInt(String prompt) {
		while (true) {
			try {
				return Integer.parseInt(readString(prompt));
			} catch (NumberFormatException invalidNumberException) {
				prompt = "\nValore intero invalido.\nPer favore, inserisci un numero intero: ";
			}
		}
	}

	/**
	 * Legge un numero decimale dall'input gestendo gli errori di formattazione.
	 *
	 * @param prompt il messaggio da mostrare per la richiesta di input
	 * @return Il numero decimale inserito dall'utente
	 */
	protected double readDouble(String prompt) {
		while (true) {
			try {
				return Double.parseDouble(readString(prompt));
			} catch (NumberFormatException invalidNumberException) {
				prompt = "\nValore decimale invalido.\nPer favore, inserisci un numero decimale: ";
			}
		}
	}

	/**
	 * Mostra un messaggio sulla console.
	 *
	 * @param message il messaggio da mostrare.
	 */
	protected void showMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Mostra un messaggio di errore sulla console.
	 *
	 * @param message il messaggio di errore da mostrare.
	 */
	protected void showError(String message) {
		System.err.println("\nErrore: " + message);
	}

	/** Mostra un messaggio per un'opzione non valida. */
	protected void showInvalidOption() {
		showMessage("Opzione non valida. Riprova.");
	}

	/** Chiude lo scanner e termina l'esecuzione del programma. */
	protected void exitProgram() {
		showMessage("\nArrivederci!\n");
		closeScanner();
		System.exit(0);
	}

	/**
	 * Chiede all'utente di confermare una domanda con "si" o "no".
	 *
	 * @param question la domanda da mostrare all'utente.
	 * @return Ritorna true se l'utente conferma con una risposta positiva, false in caso contrario.
	 */
	protected boolean confirmQuestion(String question) {
		String answer;
		answer = readString(question + " (si/no): ");

		do {
			if (!isValidAnswer(answer)) {
				answer = readString("\nDevi inserire 'si' o 'no' per confermare.\nPer favore, inserisci di nuovo la risposta: ");
			}
		} while (!isValidAnswer(answer));

		return isPositiveAnswer(answer);	// Restituisce true se la risposta è positiva
	}

	/**
	 * Verifica se la risposta dell'utente è valida (si o no).
	 *
	 * @param answer la risposta inserita dall'utente.
	 * @return Restituisce true se la risposta è "si" o "no", false in caso contrario.
	 */
	private boolean isValidAnswer(String answer) {
		return answer.equalsIgnoreCase("si") || answer.equalsIgnoreCase("sì") || answer.equalsIgnoreCase("no");
	}

	/**
	 * Verifica se la risposta dell'utente è positiva.
	 *
	 * @param answer la risposta inserita dall'utente.
	 * @return Ritorna true se la risposta è "si" o "sì", false in caso contrario.
	 */
	private boolean isPositiveAnswer(String answer) {
		return answer.equalsIgnoreCase("si") || answer.equalsIgnoreCase("sì");
	}

	/**
	 * Mostra una lista di elementi sulla console.
	 *
	 * @param <T> il tipo di elemento contenuto nella collezione.
	 * @param items la collezione di elementi da mostrare.
	 * @param listDescription la descrizione della lista.
	 * @param errorList il messaggio di errore da mostrare se la lista è vuota.
	 * @param isArticleList true se la lista contiene oggetti di tipo `Article`, false altrimenti.
	 */
	protected <T> void displayItems(Collection<T> items, String listDescription, String errorList, boolean isArticleList) {
		showMessage(listDescription);
		if (items.isEmpty()) {
			if(errorList != null) {
				showMessage(errorList);
			}
		} else {
			for (T item : items) {
				if (isArticleList && item instanceof Article) {
					Article article = (Article) item;
					showMessage("- " + article.getName() + " | Costo: " + String.format("%.2f", article.getCost()) + " | Quantità: " + article.getQuantity() + " | Categoria: " + article.getCategory());
				} else {
					showMessage("- " + item.toString());
				}
			}
		}
	}

	/**
	 * Controlla se una collezione è vuota e mostra un messaggio se non contiene elementi.
	 *
	 * @param collection la collezione da verificare.
	 * @param noElementsMessage il messaggio da mostrare se la collezione è vuota.
	 * @return Restituisce true se la collezione è vuota, false in caso contrario.
	 */
	protected boolean isCollectionEmpty(Collection<?> collection, String noElementsMessage) {
		if (collection.isEmpty()) {
			showMessage(noElementsMessage);
			return true;
		} else {
			return false;
		}
	}
}
