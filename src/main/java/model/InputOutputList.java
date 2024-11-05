package model;

import model.exceptions.io.InvalidInputException;
import model.exceptions.io.FileOperationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * `InputOutputList` gestisce le operazioni di input e output
 * per salvare e caricare liste della spesa da file.
 */
public class InputOutputList {
	/** Il nome della directory dove vengono salvate le liste. */
	private static final String DEFAULT_DIR = "lists";

	/**
	 * Salva una lista della spesa in un file specificato.
	 * Il file viene sovrascritto se è già presente.
	 * 
	 * @param shoppingList la lista della spesa da salvare.
	 * @param filePath il percorso del file in cui salvare la lista.
	 * @throws FileOperationException se si verifica un errore durante il salvataggio.
	 */
	public void saveToFile(ShoppingList shoppingList, File filePath) throws FileOperationException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Scrive ogni articolo della lista nel file in formato CSV
			for (Article article : shoppingList) {
				writer.write(article.getName() + "," + article.getCost() + "," + article.getQuantity() + "," + article.getCategory());
				writer.newLine();
			}
		} catch (IOException exceptionIO) {
			throw new FileOperationException("il salvataggio della lista non è andato a buon fine.", exceptionIO);
		}
	}

	/**
	 * Carica una lista della spesa da un file e aggiorna le categorie.
	 * Sostituisce gli articoli nella lista con quelli presenti nel file.
	 *
	 * @param shoppingList la lista della spesa da caricare.
	 * @param categoryManager il gestore delle categorie per aggiornare eventuali categorie mancanti.
	 * @param filePath il percorso del file da cui caricare la lista.
	 * @throws FileOperationException se si verifica un errore durante il caricamento.
	 * @throws InvalidInputException se il file contiene dati non validi.
	 */
	public void loadFromFile(ShoppingList shoppingList, CategoryManager categoryManager, File filePath) throws FileOperationException, InvalidInputException {
		if (!filePath.exists()) {
			throw new FileOperationException("il file specificato non esiste.");
		}

		// Svuota la lista prima di caricare i nuovi articoli
		shoppingList.clearArticles();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line, name, category;
			int quantity, lineNumber = 1;
			double cost;
			
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					// Verifica che ogni linea (divisa attraverso il carattere ',') contenga esattamente 4 elementi
					String[] parts = line.split(",");
					if (parts.length != 4) {
						throw new FileOperationException("presenza di un articolo invalido all'interno del file (riga " + lineNumber +").");
					}

					name = parts[0];
					try {
						// Converte il costo e la quantità in numeri, se i caratteri non sono numerici viene lanciata un'eccezione
						cost = Double.parseDouble(parts[1]);
						quantity = Integer.parseInt(parts[2]);
						lineNumber++;
					} catch (NumberFormatException e) {
						throw new InvalidInputException("valori non validi per costo/quantità sull'articolo '" + name + "' (riga " + lineNumber + ").");
					}
					category = parts[3];

					// Aggiunge la categoria se non esiste nel CategoryManager
					shoppingList.addArticle(new Article(name, cost, quantity, category));
					if(!categoryManager.getCategories().contains(category)) {
						categoryManager.addCategory(category);
					}
				}
			}
		} catch (IOException exceptionIO) {
			throw new FileOperationException("caricamento lista della spesa fallito.", exceptionIO);
		}
	}

	/**
	 * Verifica l'esistenza della directory per le liste della spesa e la crea se non esiste.
	 *
	 * @return il percorso della directory.
	 * @throws FileOperationException se la directory non può essere creata o se esiste ma non è una cartella.
	 */
	public String checkListsDir() throws FileOperationException {
		File directory = new File(DEFAULT_DIR);
		if (!directory.exists()) {
			if (!directory.mkdir()) {
				throw new FileOperationException("impossibile creare la cartella '" + DEFAULT_DIR + "'.");
			}
		} else if (!directory.isDirectory()) {
			throw new FileOperationException("'" + DEFAULT_DIR + "'' esiste ma non è una cartella.");
		}

		return DEFAULT_DIR + "/";
	}
}
