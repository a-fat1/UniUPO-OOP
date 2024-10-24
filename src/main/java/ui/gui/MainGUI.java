package ui.gui;

import model.domain.ListManager;
import ui.gui.controller.MainController;
import ui.gui.view.frame.MainFrame;

public class MainGUI {
    public MainGUI(Runnable onReturn) {
        // Crea il modello
        ListManager manager = new ListManager();

        // Crea la vista
        MainFrame mainFrame = new MainFrame();

        // Crea il controller principale
        new MainController(mainFrame, manager, onReturn);

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
