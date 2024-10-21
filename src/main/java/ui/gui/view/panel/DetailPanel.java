package ui.gui.view.panel;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

public class DetailPanel extends BasePanel {
    private JTextArea articleDetailsTextArea;

    public DetailPanel() {
        super();
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Dettagli Articolo"));

        articleDetailsTextArea = new JTextArea();
        articleDetailsTextArea.setEditable(false);
        JScrollPane detailScrollPane = new JScrollPane(articleDetailsTextArea);

        add(detailScrollPane, BorderLayout.CENTER);
    }

    public JTextArea getArticleDetailsTextArea() {
        return articleDetailsTextArea;
    }
}
