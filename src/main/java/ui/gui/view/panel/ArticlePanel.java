package ui.gui.view.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class ArticlePanel extends BasePanel {
    private DefaultListModel<String> articleModel;
    private JList<String> articleJList;
    private JLabel totalArticlesLabel;
    private JLabel totalCostLabel;
    public JButton addArticleButton;
    public JButton removeArticleButton;

    public ArticlePanel() {
        super();
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Articoli"));

        articleModel = new DefaultListModel<>();
        articleJList = new JList<>(articleModel);
        JScrollPane articleScrollPane = new JScrollPane(articleJList);

        totalArticlesLabel = new JLabel("Totale articoli: 0");
        totalCostLabel = new JLabel("Costo totale: 0.0");

        // JPanel info articoli
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(totalArticlesLabel);
        infoPanel.add(totalCostLabel);

        // Pulsanti per aggiungere e rimuovere articoli
        addArticleButton = new JButton("Aggiungi Articolo");
        removeArticleButton = new JButton("Rimuovi Articolo");

        // JPanel per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addArticleButton);
        buttonPanel.add(removeArticleButton);

        add(infoPanel, BorderLayout.NORTH);
        add(articleScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultListModel<String> getArticleModel() {
        return articleModel;
    }

    public JList<String> getArticleJList() {
        return articleJList;
    }

    public JLabel getTotalArticlesLabel() {
        return totalArticlesLabel;
    }

    public JLabel getTotalCostLabel() {
        return totalCostLabel;
    }
}
