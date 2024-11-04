// package ui.gui.view.input;

// import model.Article;

// import java.awt.Frame;
// import java.awt.GridLayout;
// import java.awt.BorderLayout;

// import javax.swing.JPanel;
// import javax.swing.JLabel;
// import javax.swing.JButton;
// import javax.swing.JDialog;
// import javax.swing.JTextField;
// import javax.swing.JOptionPane;

// public class ArticleInputDialog extends JDialog {
//     private JTextField nameField;
//     private JTextField costField;
//     private JTextField quantityField;
//     private JTextField categoryField;
//     private boolean confirmed = false;

//     public ArticleInputDialog(Frame owner) {
//         super(owner, "Inserisci i dettagli dell'articolo", true);
//         initialize();
//     }

//     private void initialize() {
//         JPanel inputPanel = new JPanel(new GridLayout(0, 2));

//         nameField = new JTextField();
//         costField = new JTextField();
//         quantityField = new JTextField("1");
//         categoryField = new JTextField("Non Categorizzati");

//         inputPanel.add(new JLabel("Nome:"));
//         inputPanel.add(nameField);
//         inputPanel.add(new JLabel("Costo:"));
//         inputPanel.add(costField);
//         inputPanel.add(new JLabel("Quantità:"));
//         inputPanel.add(quantityField);
//         inputPanel.add(new JLabel("Categoria:"));
//         inputPanel.add(categoryField);

//         JButton confirmButton = new JButton("Conferma");
//         confirmButton.addActionListener(e -> {
//             if (validateInput()) {
//                 confirmed = true;
//                 dispose();
//             }
//         });

//         JButton cancelButton = new JButton("Annulla");
//         cancelButton.addActionListener(e -> dispose());

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(confirmButton);
//         buttonPanel.add(cancelButton);

//         getContentPane().add(inputPanel, BorderLayout.CENTER);
//         getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//         pack();
//         setLocationRelativeTo(getOwner());
//     }

//     private boolean validateInput() {
//         if (nameField.getText().trim().isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Il nome non può essere vuoto.");
//             return false;
//         }
//         if (!isNumeric(costField.getText().trim())) {
//             JOptionPane.showMessageDialog(this, "Il costo deve essere un numero.\n(utilizza il punto per i decimali).");
//             return false;
//         }
//         if (!isNumeric(quantityField.getText().trim())) {
//             JOptionPane.showMessageDialog(this, "La quantità deve essere un numero.");
//             return false;
//         }
//         return true;
//     }

//     private boolean isNumeric(String str) {
//         return str.matches("\\d+(\\.\\d+)?");
//     }

//     public boolean isConfirmed() {
//         return confirmed;
//     }

//     public Article getArticle() {
//         String name = nameField.getText().trim();
//         double cost = Double.parseDouble(costField.getText().trim());
//         int quantity = Integer.parseInt(quantityField.getText().trim());
//         String category = categoryField.getText().trim();
//         return new Article(name, cost, quantity, category);
//     }
// }
