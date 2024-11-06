package ui.gui.view.panel;

import ui.gui.base.BasePanel;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;

/**
 * Pannello per la visualizzazione dei dettagli di un articolo.
 * Fornisce un'area di testo per mostrare le informazioni relative a un articolo selezionato.
 */
public class DetailPanel extends BasePanel {
	/** Area di testo che visualizza i dettagli dell'articolo. */
	private JTextArea articleDetailsTextArea;

	/** Costruisce un pannello per la visualizzazione dei dettagli dell'articolo. */
	public DetailPanel() {
		super();
	}

	/** Inizializza i componenti del pannello, inclusa l'area di testo per i dettagli. */
	@Override
	protected void initializePanel() {
		// Imposta il layout del pannello e aggiunge un bordo con titolo
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Dettagli articolo"));

		// Crea l'area di testo per visualizzare i dettagli dell'articolo e la rende non modificabile
		articleDetailsTextArea = new JTextArea();
		articleDetailsTextArea.setEditable(false);
		JScrollPane detailScrollPane = new JScrollPane(articleDetailsTextArea);

		// Aggiunge l'area di testo con scorrimento al pannello principale
		add(detailScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Ottiene l'area di testo per i dettagli dell'articolo.
	 *
	 * @return Area di testo per i dettagli dell'articolo.
	 */
	public JTextArea getArticleDetailsTextArea() {
		return articleDetailsTextArea;
	}
}
