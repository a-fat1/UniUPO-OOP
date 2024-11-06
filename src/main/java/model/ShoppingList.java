package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.domain.ArticleNotFoundException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * `ShoppingList` contiene i metodi per gestire una singola lista della spesa.
 * Permette di aggiungere, rimuovere e cercare articoli, oltre a calcolare il totale
 * della quantità e del costo degli articoli. Include anche il metodo di ricerca
 * per trovare articoli specifici nella lista in base al prefisso o alla categoria.
 */
public class ShoppingList implements Iterable<Article> {
	/** Il nome della lista della spesa. */
	private String name;

	/** Mappa degli articoli nella lista, indicizzati per nome. */
	private Map<String, Article> articles;

	/** Chiave per ottenere la quantità totale di articoli. */
	public static final String KEY_QUANTITY = "article(s)";

	/** Chiave per ottenere il costo totale della lista. */
	public static final String KEY_COST = "totalPrice";

	/**
	 * Inizializza una lista della spesa con il nome specificato.
	 * Controlla che il nome non sia nullo o vuoto; in caso contrario, lancia un'eccezione.
	 * Se il nome è valido, viene assegnato al campo `name` della lista.
	 * Inizializza inoltre la mappa `articles` come una nuova `HashMap` vuota per contenere
	 * gli articoli associati a questa lista della spesa.
	 *
	 * @param name il nome della lista della spesa.
	 * @throws InvalidInputException se il nome è nullo o vuoto.
	 */
	public ShoppingList(String name) throws InvalidInputException {
		if (name == null || name.isEmpty()) {
			throw new InvalidInputException("nome lista non valido.");
		}
		this.name = name;
		this.articles = new HashMap<>();
	}

	/**
	 * Restituisce il nome della lista della spesa.
	 *
	 * @return Il nome della lista.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Restituisce un articolo specifico per nome.
	 *
	 * @param articleName il nome dell'articolo.
	 * @return L'articolo corrispondente al nome specificato, oppure è nullo se non esiste.
	 */
	public Article getArticleByName(String articleName) {
		return articles.get(articleName);
	}

	/**
	 * Restituisce una collezione immutabile di tutti gli articoli nella lista.
	 *
	 * @return Una collezione di articoli.
	 */
	public Collection<Article> getArticles() {
		return articles.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(articles.values());
	}

	/**
	 * Aggiunge un articolo alla lista.
	 *
	 * @param article l'articolo da aggiungere.
	 * @throws InvalidInputException se l'articolo è nullo.
	 */
	public void addArticle(Article article) throws InvalidInputException {
		if (article == null) {
			throw new InvalidInputException("articolo non valido.");
		}
		// Aggiunge l'articolo alla mappa degli articoli, utilizzando il nome come chiave
		articles.put(article.getName(), article);
	}

	/**
	 * Rimuove un articolo dalla lista in base al nome.
	 *
	 * @param articleName il nome dell'articolo da rimuovere.
	 * @throws ArticleNotFoundException se l'articolo non è presente nella lista.
	 */
	public void removeArticle(String articleName) throws ArticleNotFoundException {
		if (articles.remove(articleName) == null) {
			throw new ArticleNotFoundException("articolo non trovato.");
		}
	}

	/** Rimuove tutti gli articoli dalla lista. */
	public void clearArticles() {
		articles.clear();
	}

	/**
	 * Cerca articoli in base a una stringa specificata, che può corrispondere
	 * al nome o alla categoria a seconda del parametro specificato.
	 *
	 * @param stringToSearch la stringa da cercare; deve essere non nulla e non vuota.
	 * @param isSearchForCategory true per cercare nella categoria, false per cercare nel nome.
	 * @return Lista di articoli corrispondenti alla stringa di ricerca.
	 * @throws ArticleNotFoundException se non viene trovato nessun articolo corrispondente alla ricerca.
	 * @throws InvalidInputException se la stringa di ricerca è nulla o vuota.
	 */
	public List<Article> findArticles(String stringToSearch, boolean isSearchForCategory) throws ArticleNotFoundException, InvalidInputException {
		if (stringToSearch == null || stringToSearch.isEmpty()) {
			throw new InvalidInputException("stringa di ricerca non valida.");
		}

		List<Article> searchResult = new ArrayList<>();
		for (Article article : this) {
			// Controlla se c'è corrispondenza con il nome o la categoria dell'articolo
			boolean match = isSearchForCategory ? article.getCategory().equalsIgnoreCase(stringToSearch) : article.getName().toLowerCase().startsWith(stringToSearch.toLowerCase());
			if (match) {
				searchResult.add(article);
			}
		}

		if (searchResult.isEmpty()) {
			// Genera un messaggio di errore in base al tipo di ricerca
			String errorMessage = isSearchForCategory ? "la categoria ricercata non esiste." : "nessun articolo presente con il prefisso ricercato.";
			throw new ArticleNotFoundException(errorMessage);
		}

		return searchResult;
	}

	/**
	 * Calcola il totale della quantità e del costo degli articoli nella lista.
	 *
	 * @return Una mappa con la quantità totale e il costo totale.
	 */
	public Map<String, Object> getTotalFromList() {
		int totalQuantity = 0;
		double totalCost = 0;
		for (Article article : this) {
			totalCost += article.getCost() * article.getQuantity();
			totalQuantity += article.getQuantity();
		}

		Map<String, Object> total = new HashMap<>();
		total.put(KEY_QUANTITY, totalQuantity);
		total.put(KEY_COST, totalCost);

		return total;
	}

	/**
	 * Restituisce un iteratore per gli articoli della lista.
	 *
	 * @return Un iteratore per gli articoli.
	 */
	@Override
	public Iterator<Article> iterator() {
		return articles.values().iterator();
	}

	/**
	 * Restituisce una rappresentazione testuale (il nome) delle liste della spesa.
	 * Questo metodo viene utilizzato per stampare correttamente il nome delle liste
	 * attraverso il metodo displayItems in {@link ui.cli.base.BaseMenu}.
	 *
	 * @return Il nome della lista della spesa.
	 */
	@Override
	public String toString() {
		return name;
	}
}
