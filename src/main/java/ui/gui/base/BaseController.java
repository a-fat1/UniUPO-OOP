package ui.gui.base;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Classe base astratta per i controller dell'interfaccia grafica.
 * Fornisce metodi di utilità per visualizzare messaggi di errore e informazioni.
 */
public abstract class BaseController {
	/**
	 * Mostra un messaggio di errore in una finestra di dialogo.
	 *
	 * @param parent componente genitore per la finestra di dialogo.
	 * @param title  titolo della finestra di dialogo.
	 * @param message messaggio di errore da visualizzare.
	 */
	protected void showError(Component parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Mostra un messaggio informativo in una finestra di dialogo.
	 *
	 * @param parent componente genitore per la finestra di dialogo.
	 * @param title  titolo della finestra di dialogo.
	 * @param message messaggio informativo da visualizzare.
	 */
	protected void showMessage(Component parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
