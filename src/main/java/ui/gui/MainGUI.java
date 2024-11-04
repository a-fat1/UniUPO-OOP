package ui.gui;

import ui.gui.view.frame.MainFrame;
import ui.gui.controller.MainController;

import model.ListManager;

public class MainGUI {
    public MainGUI(Runnable onReturn) {
        // Crea il modello
        ListManager listManager = new ListManager();

        // Crea la vista
        MainFrame mainFrame = new MainFrame();

        // Crea il controller principale
        new MainController(mainFrame, listManager, onReturn);

        // Aggiunge un WindowListener per notificare quando la finestra viene chiusa
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (onReturn != null) {
                    onReturn.run();
                }
            }
        });
    }
}
