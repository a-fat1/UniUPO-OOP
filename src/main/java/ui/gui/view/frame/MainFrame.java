package ui.gui.view.frame;

import ui.gui.view.panel.*;
import ui.gui.view.bar.MenuBar;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Finestra principale dell'applicazione, contenente tutti i pannelli di visualizzazione e il menu. */
public class MainFrame extends JFrame {
	/** Barra dei menu per le opzioni principali dell'applicazione. */
	public MenuBar menuBar;

	/** Pannello che visualizza la lista degli articoli. */
	public ListPanel listPanel;

	/** Pannello per la gestione degli articoli. */
	public ArticlePanel articlePanel;

	/** Pannello che mostra i dettagli di un articolo selezionato. */
	public DetailPanel detailPanel;
	
	/** Pannello per la gestione delle categorie degli articoli. */
	public CategoryPanel categoryPanel;

	/** Costruisce la finestra principale con le dimensioni specificate, la barra dei menu e il pannello principale. */
	public MainFrame() {
		super("GestoreSpesa");
		// Imposta la dimensione della finestra
		setSize(1000, 600);
		// Impedisce la chiusura diretta della finestra senza azioni definite
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Crea e imposta la barra dei menu
		menuBar = new MenuBar();
		setJMenuBar(menuBar);

		// Crea e aggiunge il pannello principale
		createMainPanel();
	}

	/** Crea il pannello principale con layout e pannelli specifici. */
	private void createMainPanel() {
		// Inizializza i vari pannelli per la visualizzazione dei dati
		listPanel = new ListPanel();
		articlePanel = new ArticlePanel();
		detailPanel = new DetailPanel();
		categoryPanel = new CategoryPanel();

		// Configura il layout principale usando GridBagLayout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Configura e aggiunge il pannello delle liste
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.2;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(listPanel, gbc);

		// Configura e aggiunge il pannello degli articoli
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.6;
		gbc.weighty = 1.0;
		mainPanel.add(articlePanel, gbc);

		// Configura e aggiunge il pannello dei dettagli
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.2;
		gbc.weighty = 0.4;
		mainPanel.add(detailPanel, gbc);

		// Configura e aggiunge il pannello delle categorie
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.2;
		gbc.weighty = 0.6;
		mainPanel.add(categoryPanel, gbc);

		// Aggiunge il pannello principale al contenuto della finestra
		getContentPane().add(mainPanel);
	}
}
