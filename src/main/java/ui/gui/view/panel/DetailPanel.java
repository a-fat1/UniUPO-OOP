package ui.gui.view.panel;

import ui.gui.base.BasePanel;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

public class DetailPanel extends BasePanel {
    private JTextArea articleDetailsTextArea;

    public DetailPanel() {
        super();
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Dettagli articolo"));

        articleDetailsTextArea = new JTextArea();
        articleDetailsTextArea.setEditable(false);
        JScrollPane detailScrollPane = new JScrollPane(articleDetailsTextArea);

        add(detailScrollPane, BorderLayout.CENTER);
    }

    public JTextArea getArticleDetailsTextArea() {
        return articleDetailsTextArea;
    }
}
