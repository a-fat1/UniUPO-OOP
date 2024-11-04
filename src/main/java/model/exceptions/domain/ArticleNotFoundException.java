package model.exceptions.domain;

/**
 * Eccezione che viene lanciata quando un articolo non viene trovato o non esiste.
 * Estende la classe {@link Exception} e fornisce un costruttore per impostare
 * un messaggio di errore personalizzato.
 */
public class ArticleNotFoundException extends Exception {
	/**
	 * Costruisce un'istanza di {@code ArticleNotFoundException} con un messaggio specifico.
	 *
	 * @param message il messaggio che descrive i dettagli dell'errore.
	 */
	public ArticleNotFoundException(String message) {
		super(message);
	}
}
