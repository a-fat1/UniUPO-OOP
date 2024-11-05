package ui.gui.view.bar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
    public JMenuItem saveListItem;
    public JMenuItem loadListItem;
    public JMenuItem searchItemByName;
    public JMenuItem searchItemByCategory;
    public JMenuItem clearList;
    public JMenuItem exitItem;

    public MenuBar() {
        // Menu "File"
        JMenu fileMenu = new JMenu("File");
        saveListItem = new JMenuItem("Salva Lista");
        loadListItem = new JMenuItem("Carica Lista");
        fileMenu.add(saveListItem);
        fileMenu.add(loadListItem);

        // Menu "Ricerca"
        JMenu searchMenu = new JMenu("Ricerca");
        searchItemByName = new JMenuItem("Cerca articoli per prefisso");
        searchItemByCategory = new JMenuItem("Cerca articoli per categoria");
        searchMenu.add(searchItemByName);
        searchMenu.add(searchItemByCategory);

        // Menu "Altro"
        JMenu miscMenu = new JMenu("Altro");
        clearList = new JMenuItem("Rimuovi tutti gli articoli");
        exitItem = new JMenuItem("Ritorna alla selezione dell'interfaccia");
        miscMenu.add(clearList);
        miscMenu.add(exitItem);

        add(fileMenu);
        add(searchMenu);
        add(miscMenu);
    }
}
