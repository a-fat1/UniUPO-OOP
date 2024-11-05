package ui.gui;

import ui.gui.view.frame.MainFrame;

import java.util.function.Consumer;

import ui.gui.controller.MainController;

public class MainGUI {
    public MainGUI(Consumer<Boolean> onReturn) {
        // Crea la vista
        MainFrame mainFrame = new MainFrame();

        // Crea il controller principale
        new MainController(mainFrame, onReturn);

        // Aggiunge un WindowListener per notificare quando la finestra viene chiusa
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (onReturn != null) {
                    onReturn.accept(true);
                }
                mainFrame.dispose();
            }
        });

        mainFrame.setVisible(true);
    }
}
