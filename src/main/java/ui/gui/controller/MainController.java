package ui.gui.controller;

import model.ListManager;

import java.util.function.Consumer;

import model.CategoryManager;
import ui.gui.view.frame.MainFrame;

public class MainController {
    private MainFrame mainFrame;
    private ListManager listManager;
    private CategoryManager categoryManager;
    private Consumer<Boolean> onReturn;

    private ListController listController;
    private ArticleController articleController;
    private CategoryController categoryController;

    public MainController(MainFrame mainFrame, Consumer<Boolean> onReturn) {
        this.mainFrame = mainFrame;
        this.onReturn = onReturn;

        // Inizializzazione dei manager
        this.listManager = new ListManager();
        this.categoryManager = new CategoryManager();

        initializeControllers();
    }

    private void initializeControllers() {
        // Inizializzazione dei controller con i parametri aggiornati
        categoryController = new CategoryController(mainFrame.categoryPanel, categoryManager, listManager);
        listController = new ListController(mainFrame.listPanel, listManager);
        articleController = new ArticleController(mainFrame.articlePanel, mainFrame.detailPanel, listManager, categoryController);
        new MenuBarController(mainFrame.menuBar, mainFrame, onReturn, listController, categoryManager, categoryController);

        // Collegamento dei controller
        listController.setArticleController(articleController);
        // Non è più necessario il setCategoryController, poiché il CategoryController viene passato direttamente all'ArticleController nel costruttore
    }
}
