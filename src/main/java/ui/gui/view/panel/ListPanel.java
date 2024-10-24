package ui.gui.view.panel;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import ui.gui.base.BasePanel;

import javax.swing.BorderFactory;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class ListPanel extends BasePanel {
    private DefaultListModel<String> listModel;
    private JList<String> shoppingListJList;
    public JButton addListButton;
    public JButton removeListButton;

    public ListPanel() {
        super();
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Liste della Spesa"));

        listModel = new DefaultListModel<>();
        shoppingListJList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(shoppingListJList);

        // Pulsanti per aggiungere e rimuovere liste
        addListButton = new JButton("Aggiungi Lista");
        removeListButton = new JButton("Rimuovi Lista");

        // JPanel per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addListButton);
        buttonPanel.add(removeListButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public JList<String> getShoppingListJList() {
        return shoppingListJList;
    }
}
