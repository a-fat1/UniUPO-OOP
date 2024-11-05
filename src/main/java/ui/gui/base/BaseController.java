package ui.gui.base;

import javax.swing.JOptionPane;
import java.awt.Component;

public abstract class BaseController {
    protected void showError(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    protected void showMessage(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
