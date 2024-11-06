package ui.gui.view.panel;

import ui.gui.base.BasePanel;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

/**
 * Pannello per la visualizzazione e gestione degli articoli.
 * Fornisce una lista di articoli, informazioni sul totale e pulsanti per aggiungere e rimuovere articoli.
 */
public class ArticlePanel extends BasePanel {
	/** Modello per la lista degli articoli. */
	private DefaultListModel<String> articleModel;

	/** Lista grafica che visualizza gli articoli. */
	private JList<String> articleJList;

	/** Etichetta per il numero totale di articoli. */
	private JLabel totalArticlesLabel;

	/** Etichetta per il costo totale degli articoli. */
	private JLabel totalCostLabel;

	/** Pulsante per aggiungere un articolo. */
	public JButton addArticleButton;

	/** Pulsante per rimuovere un articolo. */
	public JButton removeArticleButton;

	/** Crea un pannello per la gestione degli articoli. */
	public ArticlePanel() {
		super();
	}

	/** Inizializza i componenti del pannello, inclusi la lista, le etichette e i pulsanti. */
	@Override
	protected void initializePanel() {
		// Imposta il layout del pannello e aggiunge un bordo con titolo
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Articoli"));

		// Crea il modello e la lista grafica per visualizzare gli articoli
		articleModel = new DefaultListModel<>();
		articleJList = new JList<>(articleModel);
		JScrollPane articleScrollPane = new JScrollPane(articleJList);

		// Inizializza le etichette per mostrare il totale degli articoli e il costo totale
		totalArticlesLabel = new JLabel("Totale articoli: 0");
		totalCostLabel = new JLabel("Costo totale: 0,00");

		// Crea un pannello informativo e aggiunge le etichette del totale
		JPanel infoPanel = new JPanel(new GridLayout(2, 1));
		infoPanel.add(totalArticlesLabel);
		infoPanel.add(totalCostLabel);

		// Inizializza i pulsanti per aggiungere e rimuovere articoli
		addArticleButton = new JButton("Aggiungi articolo");
		removeArticleButton = new JButton("Rimuovi articolo");

		// Crea un pannello per i pulsanti e li aggiunge
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(addArticleButton);
		buttonPanel.add(removeArticleButton);

		// Aggiunge i pannelli informativi, la lista e il pannello dei pulsanti al pannello principale
		add(infoPanel, BorderLayout.NORTH);
		add(articleScrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Ottiene il modello della lista degli articoli.
	 *
	 * @return Modello della lista degli articoli.
	 */
	public DefaultListModel<String> getArticleModel() {
		return articleModel;
	}

	/**
	 * Ottiene la lista grafica degli articoli.
	 *
	 * @return Lista grafica degli articoli.
	 */
	public JList<String> getArticleJList() {
		return articleJList;
	}

	/**
	 * Ottiene l'etichetta per il numero totale di articoli.
	 *
	 * @return Etichetta per il numero totale di articoli.
	 */
	public JLabel getTotalArticlesLabel() {
		return totalArticlesLabel;
	}

	/**
	 * Ottiene l'etichetta per il costo totale degli articoli.
	 *
	 * @return Etichetta per il costo totale degli articoli.
	 */
	public JLabel getTotalCostLabel() {
		return totalCostLabel;
	}
}
