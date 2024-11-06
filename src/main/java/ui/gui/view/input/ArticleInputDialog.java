package ui.gui.view.input;

import model.Article;
import model.exceptions.io.InvalidInputException;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

/**
 * Finestra di dialogo per l'inserimento dei dettagli di un articolo.
 * Consente all'utente di specificare nome, costo, quantità e categoria dell'articolo.
 */
public class ArticleInputDialog extends JDialog {
	/** Campo di testo per il nome dell'articolo. */
	private JTextField nameField;

	/** Campo di testo per il costo dell'articolo. */
	private JTextField costField;

	/** Campo di testo per la quantità dell'articolo. */
	private JTextField quantityField;

	/** Campo di testo per la categoria dell'articolo. */
	private JTextField categoryField;

	/** Indica se l'input è stato confermato. */
	private boolean confirmed = false;

	/**
	 * Costruisce una finestra di dialogo per l'inserimento dei dettagli dell'articolo.
	 *
	 * @param owner finestra proprietaria della finestra di dialogo.
	 */
	public ArticleInputDialog(Frame owner) {
		super(owner, "Inserisci i dettagli dell'articolo", true);
		initialize();
	}

	/** Inizializza i componenti della finestra di dialogo. */
	private void initialize() {
		// Crea un pannello per i campi di input e imposta il layout
		JPanel inputPanel = new JPanel(new GridLayout(0, 2));

		// Inizializza i campi di testo per i dettagli dell'articolo
		nameField = new JTextField();
		costField = new JTextField();
		quantityField = new JTextField("1");
		categoryField = new JTextField(Article.DEFAULT_CATEGORY);

		// Aggiunge etichette e campi di input al pannello
		inputPanel.add(new JLabel("Nome:"));
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("Costo:"));
		inputPanel.add(costField);
		inputPanel.add(new JLabel("Quantità:"));
		inputPanel.add(quantityField);
		inputPanel.add(new JLabel("Categoria:"));
		inputPanel.add(categoryField);

		// Crea il pulsante di conferma con listener per validare e confermare l'input
		JButton confirmButton = new JButton("Conferma");
		confirmButton.addActionListener(e -> {
			// Convalida l'input e chiude la finestra se valido
			if (validateInput()) {
				confirmed = true;
				dispose();
			}
		});

		// Crea il pulsante di annullamento con listener per chiudere la finestra
		JButton cancelButton = new JButton("Annulla");
		cancelButton.addActionListener(e -> dispose());

		// Crea un pannello per i pulsanti e imposta il layout
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		// Aggiunge il pannello di input e quello dei pulsanti alla finestra di dialogo
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
	}

	/**
	 * Valida l'input inserito dall'utente.
	 *
	 * @return Ritorna true se l'input è valido, altrimenti false.
	 */
	private boolean validateInput() {
		// Verifica che il campo nome non sia vuoto
		if (nameField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Il nome non può essere vuoto.");
			return false;
		}
		// Verifica che il campo costo contenga un numero valido
		if (!isNumeric(costField.getText().trim())) {
			JOptionPane.showMessageDialog(this, "Il costo deve essere un numero.\n(utilizza il punto per i decimali).");
			return false;
		}
		// Verifica che il campo quantità contenga un numero intero valido
		try {
			Integer.parseInt(quantityField.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "La quantità deve essere un numero intero.");
			return false;
		}		
		return true;
	}

	/**
	 * Controlla se una stringa rappresenta un valore numerico.
	 *
	 * @param str Stringa da verificare.
	 * @return Restituisce true se la stringa è numerica, false in caso contrario.
	 */
	private boolean isNumeric(String str) {
		return str.matches("\\d+(\\.\\d+)?");
	}

	/**
	 * Verifica se l'input dell'utente è stato confermato.
	 *
	 * @return Ritorna true se l'input è stato confermato, false in caso contrario.
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Ottiene un oggetto Article in base all'input dell'utente.
	 *
	 * @return Istanza di 'Article' con i dettagli specificati dall'utente.
	 * @throws InvalidInputException se l'input è invalido.
	 */
	public Article getArticle() throws InvalidInputException {
		String name = nameField.getText().trim();
		double cost = Double.parseDouble(costField.getText().trim());
		int quantity = Integer.parseInt(quantityField.getText().trim());
		String category = categoryField.getText().trim();
		return new Article(name, cost, quantity, category);
	}
}
