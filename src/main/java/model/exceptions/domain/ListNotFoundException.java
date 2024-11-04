package model.exceptions.domain;

/**
 * Eccezione che viene lanciata quando una lista non viene trovata o non esiste.
 * Estende la classe {@link Exception} e fornisce un costruttore per impostare
 * un messaggio di errore personalizzato.
 */
public class ListNotFoundException extends Exception {
	/**
	 * Crea un'istanza di {@code ListNotFoundException} con un messaggio specifico.
	 *
	 * @param message il messaggio che descrive i dettagli dell'errore.
	 */
	public ListNotFoundException(String message) {
		super(message);
	}
}
