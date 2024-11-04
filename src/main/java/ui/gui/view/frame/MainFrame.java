package ui.gui.view.frame;

import ui.gui.view.panel.*;
import ui.gui.view.bar.MenuBar;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    public MenuBar menuBar;
    public ListPanel listPanel;
    public ArticlePanel articlePanel;
    public DetailPanel detailPanel;
    public CategoryPanel categoryPanel;

    public MainFrame() {
        super("GestoreSpesa");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        createMainPanel();

        setVisible(true);
    }

    private void createMainPanel() {
        listPanel = new ListPanel();
        articlePanel = new ArticlePanel();
        detailPanel = new DetailPanel();
        categoryPanel = new CategoryPanel();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Pannello delle liste
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(listPanel, gbc);

        // Pannello degli articoli
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        mainPanel.add(articlePanel, gbc);

        // Pannello dei dettagli
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.4;
        mainPanel.add(detailPanel, gbc);

        // Pannello delle categorie
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.6;
        mainPanel.add(categoryPanel, gbc);

        getContentPane().add(mainPanel);
    }
}
