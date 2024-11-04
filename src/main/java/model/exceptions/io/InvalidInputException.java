package model.exceptions.io;

/**
 * Eccezione personalizzata che viene lanciata quando viene fornito un input non valido.
 * Questa eccezione estende la classe {@link Exception}.
 */
public class InvalidInputException extends Exception {
	/**
	 * Genera un'istanza di {@code InvalidInputException} con un messaggio specifico.
	 *
	 * @param message il messaggio specifico che descrive l'errore di input non valido.
	 */
	public InvalidInputException(String message) {
		super(message);
	}
}
