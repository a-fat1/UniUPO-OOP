package ui.gui.view.panel;

import ui.gui.base.BasePanel;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

/**
 * Pannello per la visualizzazione e gestione delle categorie.
 * Fornisce una lista di categorie e pulsanti per aggiungere e rimuovere categorie.
 */
public class CategoryPanel extends BasePanel {
	/** Modello per la lista delle categorie. */
	private DefaultListModel<String> categoryModel;
	
	/** Lista grafica che visualizza le categorie. */
	private JList<String> categoryJList;

	/** Pulsante per aggiungere una categoria. */
	public JButton addCategoryButton;

	/** Pulsante per rimuovere una categoria. */
	public JButton removeCategoryButton;

	/** Costruisce un pannello per la gestione delle categorie. */
	public CategoryPanel() {
		super();
	}

	/** Inizializza i componenti del pannello, inclusi la lista e i pulsanti. */
	@Override
	protected void initializePanel() {
		// Imposta il layout del pannello e aggiunge un bordo con titolo
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Categorie"));

		// Crea il modello e la lista grafica per visualizzare le categorie
		categoryModel = new DefaultListModel<>();
		categoryJList = new JList<>(categoryModel);
		JScrollPane categoryScrollPane = new JScrollPane(categoryJList);

		// Inizializza i pulsanti per aggiungere e rimuovere categorie
		addCategoryButton = new JButton("Aggiungi categoria");
		removeCategoryButton = new JButton("Rimuovi categoria");

		// Crea un pannello per i pulsanti e li aggiunge
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(addCategoryButton);
		buttonPanel.add(removeCategoryButton);

		// Aggiunge la lista e il pannello dei pulsanti al pannello principale
		add(categoryScrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Ottiene il modello della lista delle categorie.
	 *
	 * @return Modello della lista delle categorie.
	 */
	public DefaultListModel<String> getCategoryModel() {
		return categoryModel;
	}

	/**
	 * Ottiene la lista grafica delle categorie.
	 *
	 * @return Lista grafica delle categorie.
	 */
	public JList<String> getCategoryJList() {
		return categoryJList;
	}
}
