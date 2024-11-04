package model.exceptions.domain;

/**
 * Eccezione lanciata quando una categoria non viene trovata o non esiste.
 * Estende la classe {@link Exception} e fornisce un costruttore per impostare
 * un messaggio di errore personalizzato.
 */
public class CategoryNotFoundException extends Exception {
	/**
	 * Utilizzato per creare un'istanza di {@code CategoryNotFoundException} con un messaggio specifico.
	 *
	 * @param message il messaggio che descrive i dettagli dell'errore.
	 */
	public CategoryNotFoundException(String message) {
		super(message);
	}
}
