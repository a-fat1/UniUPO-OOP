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

public class CategoryPanel extends BasePanel {
    private DefaultListModel<String> categoryModel;
    private JList<String> categoryJList;
    public JButton addCategoryButton;
    public JButton removeCategoryButton;

    public CategoryPanel() {
        super();
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Categorie"));

        categoryModel = new DefaultListModel<>();
        categoryJList = new JList<>(categoryModel);
        JScrollPane categoryScrollPane = new JScrollPane(categoryJList);

        // Pulstanti per aggiungere e rimuovere categorie
        addCategoryButton = new JButton("Aggiungi Categoria");
        removeCategoryButton = new JButton("Rimuovi Categoria");

        // JPanel per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addCategoryButton);
        buttonPanel.add(removeCategoryButton);

        add(categoryScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultListModel<String> getCategoryModel() {
        return categoryModel;
    }

    public JList<String> getCategoryJList() {
        return categoryJList;
    }
}
