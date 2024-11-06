package ui.gui.base;

import javax.swing.JPanel;

/**
 * Classe base astratta per i pannelli dell'interfaccia grafica.
 * Fornisce un metodo per l'inizializzazione dei componenti del pannello.
 */
public abstract class BasePanel extends JPanel {
	/** Costruisce un pannello dell'interfaccia grafica. */
	public BasePanel() {
		initializePanel();
	}

	/**
	 * Inizializza i componenti del pannello.
	 * Deve essere implementato dalle sottoclassi per specificare la configurazione del pannello.
	 */
	protected abstract void initializePanel();
}
