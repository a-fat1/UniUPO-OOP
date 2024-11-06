package ui.gui.view.panel;

import ui.gui.base.BasePanel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

/**
 * Pannello per la visualizzazione e gestione delle liste della spesa.
 * Fornisce una lista di liste della spesa e pulsanti per aggiungere e rimuovere liste.
 */
public class ListPanel extends BasePanel {
	/** Modello per la lista delle liste della spesa. */
	private DefaultListModel<String> listModel;

	/** Lista grafica che visualizza le liste della spesa. */
	private JList<String> shoppingListJList;

	/** Pulsante per aggiungere una lista della spesa. */
	public JButton addListButton;

	/** Pulsante per rimuovere una lista della spesa. */
	public JButton removeListButton;

	/** Costruisce un pannello per la gestione delle liste della spesa. */
	public ListPanel() {
		super();
	}

	/** Inizializza i componenti del pannello, inclusa la lista e i pulsanti. */
	@Override
	protected void initializePanel() {
		// Imposta il layout del pannello e aggiunge un bordo con titolo
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Liste della spesa"));

		// Crea il modello e la lista grafica per visualizzare le liste della spesa
		listModel = new DefaultListModel<>();
		shoppingListJList = new JList<>(listModel);
		JScrollPane listScrollPane = new JScrollPane(shoppingListJList);

		// Inizializza i pulsanti per aggiungere e rimuovere liste della spesa
		addListButton = new JButton("Aggiungi lista");
		removeListButton = new JButton("Rimuovi lista");

		// Crea un pannello per i pulsanti e li aggiunge
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(addListButton);
		buttonPanel.add(removeListButton);

		// Aggiunge la lista e il pannello dei pulsanti al pannello principale
		add(listScrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Ottiene il modello della lista delle liste della spesa.
	 *
	 * @return Modello della lista per le liste della spesa.
	 */
	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	/**
	 * Ottiene la lista grafica delle liste della spesa.
	 *
	 * @return Lista grafica delle liste della spesa.
	 */
	public JList<String> getShoppingListJList() {
		return shoppingListJList;
	}
}
