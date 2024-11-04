package model.exceptions.io;

/**
 * Eccezione personalizzata per gestire errori nelle operazioni sui file.
 * Questa eccezione viene lanciata quando si verifica un problema durante
 * l'esecuzione di operazioni di input/output sui file.
 * 
 * <p>Questa classe estende {@link Exception} e fornisce due costruttori:
 * uno che accetta solo un messaggio di errore e un altro che accetta sia
 * un messaggio di errore che una causa sottostante.</p>
 * 
 * @see java.lang.Exception
 */
public class FileOperationException extends Exception {
	/**
	 * Costruisce una nuova eccezione con il messaggio specificato.
	 *
	 * @param message il messaggio specifico dell'eccezione.
	 */
	public FileOperationException(String message) {
		super(message);
	}

	/**
	 * Costruisce una nuova eccezione con il messaggio specificato e la causa.
	 *
	 * @param message il messaggio specifico dell'eccezione.
	 * @param cause la causa dell'eccezione.
	 */
	public FileOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
