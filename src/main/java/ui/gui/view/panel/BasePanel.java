package ui.gui.view.panel;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
    public BasePanel() {
        initializePanel();
    }

    protected abstract void initializePanel();
}

