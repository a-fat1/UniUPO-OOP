package ui.gui.controller;

import javax.swing.JOptionPane;
import java.awt.Component;

public abstract class BaseController {
    protected void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    protected void showMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Messaggio", JOptionPane.INFORMATION_MESSAGE);
    }

    // protected void showWarning(Component parent, String message) {
    //     JOptionPane.showMessageDialog(parent, message, "Avviso", JOptionPane.WARNING_MESSAGE);
    // }
}
