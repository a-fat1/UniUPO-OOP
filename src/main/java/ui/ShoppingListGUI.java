package ui;

import elaboration.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.List;
import java.io.IOException;

public class ShoppingListGUI {
	private ListManager manager;
	private JFrame frame;
	private DefaultListModel<String> listModel;
	private JList<String> shoppingListJList;
	private DefaultListModel<String> articleModel;
	private JList<String> articleJList;
	private JTextArea articleDetailsTextArea;
	private JLabel totalArticlesLabel;
	private JLabel totalCostLabel;

	public ShoppingListGUI() {
		manager = new ListManager();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		frame = new JFrame("Gestione Liste della Spesa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem saveListItem = new JMenuItem("Salva Lista");
		JMenuItem loadListItem = new JMenuItem("Carica Lista");
		fileMenu.add(saveListItem);
		fileMenu.add(loadListItem);

		JMenu actionMenu = new JMenu("Ricerca");
		JMenuItem searchItem = new JMenuItem("Cerca Articoli per Prefisso");
		actionMenu.add(searchItem);

		JMenu categoryMenu = new JMenu("Categorie");
		JMenuItem addCategoryItem = new JMenuItem("Aggiungi Categoria");
		JMenuItem removeCategoryItem = new JMenuItem("Rimuovi Categoria");
		categoryMenu.add(addCategoryItem);
		categoryMenu.add(removeCategoryItem);

		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		menuBar.add(categoryMenu);

		frame.setJMenuBar(menuBar);

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(BorderFactory.createTitledBorder("Liste della Spesa"));

		listModel = new DefaultListModel<>();
		shoppingListJList = new JList<>(listModel);
		JScrollPane listScrollPane = new JScrollPane(shoppingListJList);

		JButton addListButton = new JButton("Aggiungi Lista");
		JButton removeListButton = new JButton("Rimuovi Lista");

		JPanel listButtonPanel = new JPanel();
		listButtonPanel.add(addListButton);
		listButtonPanel.add(removeListButton);

		listPanel.add(listScrollPane, BorderLayout.CENTER);
		listPanel.add(listButtonPanel, BorderLayout.SOUTH);

		JPanel articlePanel = new JPanel(new BorderLayout());
		articlePanel.setBorder(BorderFactory.createTitledBorder("Articoli"));

		articleModel = new DefaultListModel<>();
		articleJList = new JList<>(articleModel);
		JScrollPane articleScrollPane = new JScrollPane(articleJList);

		JButton addArticleButton = new JButton("Aggiungi Articolo");
		JButton removeArticleButton = new JButton("Rimuovi Articolo");

		JPanel articleButtonPanel = new JPanel();
		articleButtonPanel.add(addArticleButton);
		articleButtonPanel.add(removeArticleButton);

		JPanel articleInfoPanel = new JPanel(new BorderLayout());
		totalArticlesLabel = new JLabel("Totale articoli: 0");
		totalCostLabel = new JLabel("Costo totale: 0.0");
		articleInfoPanel.add(totalArticlesLabel, BorderLayout.NORTH);
		articleInfoPanel.add(totalCostLabel, BorderLayout.SOUTH);

		articlePanel.add(articleScrollPane, BorderLayout.CENTER);
		articlePanel.add(articleButtonPanel, BorderLayout.SOUTH);
		articlePanel.add(articleInfoPanel, BorderLayout.NORTH);

		JPanel detailPanel = new JPanel(new BorderLayout());
		detailPanel.setBorder(BorderFactory.createTitledBorder("Dettagli Articolo"));

		articleDetailsTextArea = new JTextArea();
		articleDetailsTextArea.setEditable(false);
		JScrollPane detailScrollPane = new JScrollPane(articleDetailsTextArea);

		detailPanel.add(detailScrollPane, BorderLayout.CENTER);

		mainPanel.add(listPanel, BorderLayout.WEST);
		mainPanel.add(articlePanel, BorderLayout.CENTER);
		mainPanel.add(detailPanel, BorderLayout.EAST);

		frame.getContentPane().add(mainPanel);

		addListButton.addActionListener(e -> addShoppingList());
		removeListButton.addActionListener(e -> removeShoppingList());
		shoppingListJList.addListSelectionListener(e -> updateArticleList());
		addArticleButton.addActionListener(e -> addArticle());
		removeArticleButton.addActionListener(e -> removeArticle());
		articleJList.addListSelectionListener(e -> updateArticleDetails());
		saveListItem.addActionListener(e -> saveList());
		loadListItem.addActionListener(e -> loadList());
		searchItem.addActionListener(e -> searchArticlesByPrefix());
		addCategoryItem.addActionListener(e -> addCategory());
		removeCategoryItem.addActionListener(e -> removeCategory());

		frame.setVisible(true);
	}

	private void addCategory() {
		String category = JOptionPane.showInputDialog(frame, "Inserisci il nome della nuova categoria:");
		if (category != null && !category.trim().isEmpty()) {
			try {
				manager.addCategory(category.trim());
				JOptionPane.showMessageDialog(frame, "Categoria aggiunta con successo.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		}
	}

	private void removeCategory() {
		String category = JOptionPane.showInputDialog(frame, "Inserisci il nome della categoria da rimuovere:");
		if (category != null && !category.trim().isEmpty()) {
			try {
				manager.removeCategory(category.trim());
				updateArticleList();
				JOptionPane.showMessageDialog(frame, "Categoria rimossa con successo.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		}
	}

	private void addShoppingList() {
		String listName = JOptionPane.showInputDialog(frame, "Inserisci il nome della nuova lista:");
		if (listName != null && !listName.trim().isEmpty()) {
			try {
				manager.addShoppingList(listName.trim());
				listModel.addElement(listName.trim());
				JOptionPane.showMessageDialog(frame, "Lista creata con successo.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		}
	}

	private void removeShoppingList() {
		String selectedList = shoppingListJList.getSelectedValue();
		if (selectedList != null) {
			try {
				manager.removeShoppingList(selectedList);
				listModel.removeElement(selectedList);
				articleModel.clear();
				articleDetailsTextArea.setText("");
				totalArticlesLabel.setText("Totale articoli: 0");
				totalCostLabel.setText("Costo totale: 0.0");
				JOptionPane.showMessageDialog(frame, "Lista rimossa con successo.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Seleziona una lista da rimuovere.");
		}
	}

	private void updateArticleList() {
		String selectedList = shoppingListJList.getSelectedValue();
		articleModel.clear();
		articleDetailsTextArea.setText("");
		if (selectedList != null) {
			try {
				ShoppingList list = manager.getShoppingList(selectedList);
				for (Article article : list) {
					articleModel.addElement(article.getName());
				}

				totalArticlesLabel.setText("Totale articoli: " + list.getTotalQuantity());
				totalCostLabel.setText("Costo totale: " + list.getTotalCost());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		} else {
			totalArticlesLabel.setText("Totale articoli: 0");
			totalCostLabel.setText("Costo totale: 0.0");
		}
	}

	private void addArticle() {
		String selectedListName = shoppingListJList.getSelectedValue();
		if (selectedListName == null) {
			JOptionPane.showMessageDialog(frame, "Seleziona una lista a cui aggiungere l'articolo.");
			return;
		}

		ShoppingList list = manager.getShoppingList(selectedListName);

		JPanel inputPanel = new JPanel(new GridLayout(0, 2));
		JTextField nameField = new JTextField();
		JTextField costField = new JTextField();
		JTextField quantityField = new JTextField("1");
		JTextField categoryField = new JTextField("Non Categorizzati");

		inputPanel.add(new JLabel("Nome:"));
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("Costo:"));
		inputPanel.add(costField);
		inputPanel.add(new JLabel("Quantità:"));
		inputPanel.add(quantityField);
		inputPanel.add(new JLabel("Categoria:"));
		inputPanel.add(categoryField);

		int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Inserisci i dettagli dell'articolo", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				String name = nameField.getText().trim();
				double cost = Double.parseDouble(costField.getText().trim());
				int quantity = Integer.parseInt(quantityField.getText().trim());
				String category = categoryField.getText().trim();

				if (!manager.getCategories().contains(category)) {
					int addCategory = JOptionPane.showConfirmDialog(frame, "La categoria non esiste. Vuoi aggiungerla?", "Nuova Categoria", JOptionPane.YES_NO_OPTION);
					if (addCategory == JOptionPane.YES_OPTION) {
						manager.addCategory(category);
					} else {
						category = "Non Categorizzati";
					}
				}

				Article article = new Article(name, cost, quantity, category);
				list.addArticle(article);
				articleModel.addElement(article.getName());
				updateArticleList();
				JOptionPane.showMessageDialog(frame, "Articolo aggiunto con successo.");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(frame, "Errore nel formato dei numeri.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
			}
		}
	}

	private void removeArticle() {
		String selectedListName = shoppingListJList.getSelectedValue();
		String selectedArticleName = articleJList.getSelectedValue();

		if (selectedListName == null || selectedArticleName == null) {
			JOptionPane.showMessageDialog(frame, "Seleziona un articolo da rimuovere.");
			return;
		}

		ShoppingList list = manager.getShoppingList(selectedListName);
		try {
			list.removeArticle(selectedArticleName);
			articleModel.removeElement(selectedArticleName);
			articleDetailsTextArea.setText("");
			updateArticleList();
			JOptionPane.showMessageDialog(frame, "Articolo rimosso con successo.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage());
		}
	}

	private void updateArticleDetails() {
		String selectedListName = shoppingListJList.getSelectedValue();
		String selectedArticleName = articleJList.getSelectedValue();

		if (selectedListName != null && selectedArticleName != null) {
			ShoppingList list = manager.getShoppingList(selectedListName);
			Article article = null;
			for (Article a : list) {
				if (a.getName().equals(selectedArticleName)) {
					article = a;
					break;
				}
			}

			if (article != null) {
				articleDetailsTextArea.setText("Nome: " + article.getName() + "\nCosto: " + article.getCost() + "\nQuantità: " + article.getQuantity() + "\nCategoria: " + article.getCategory());
			}
		}
	}

	private void saveList() {
		String selectedListName = shoppingListJList.getSelectedValue();
		if (selectedListName == null) {
			JOptionPane.showMessageDialog(frame, "Seleziona una lista da salvare.");
			return;
		}
		ShoppingList list = manager.getShoppingList(selectedListName);

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text file (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(true);

		int option = fileChooser.showSaveDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filename.toLowerCase().endsWith(".txt")) {
				filename += ".txt";
			}
			try {
				list.saveToFile(filename);
				JOptionPane.showMessageDialog(frame, "Lista salvata con successo.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Errore durante il salvataggio: " + e.getMessage());
			}
		}
	}

	private void loadList() {
		String selectedListName = shoppingListJList.getSelectedValue();
		if (selectedListName == null) {
			JOptionPane.showMessageDialog(frame, "Seleziona una lista in cui caricare gli articoli.");
			return;
		}
		ShoppingList list = manager.getShoppingList(selectedListName);

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(true);

		int option = fileChooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			try {
				list.loadFromFile(filename);
				updateArticleList();
				JOptionPane.showMessageDialog(frame, "Lista caricata con successo.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Errore durante il caricamento: " + e.getMessage());
			}
		}
	}

	private void searchArticlesByPrefix() {
		String selectedListName = shoppingListJList.getSelectedValue();
		if (selectedListName == null) {
			JOptionPane.showMessageDialog(frame, "Seleziona una lista per effettuare la ricerca.");
			return;
		}
		ShoppingList list = manager.getShoppingList(selectedListName);

		String prefix = JOptionPane.showInputDialog(frame, "Inserisci il prefisso da cercare:");
		if (prefix != null && !prefix.trim().isEmpty()) {
			List<Article> articles = list.findArticlesByName(prefix.trim());
			if (articles.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Nessun articolo trovato con il prefisso '" + prefix + "'.");
			} else {
				StringBuilder result = new StringBuilder("Articoli trovati:\n");
				for (Article article : articles) {
					result.append("- ").append(article.getName()).append(" | Costo: ").append(article.getCost()).append(" | Quantità: ").append(article.getQuantity()).append(" | Categoria: ").append(article.getCategory()).append("\n");
				}
				JOptionPane.showMessageDialog(frame, result.toString());
			}
		}
	}
}
