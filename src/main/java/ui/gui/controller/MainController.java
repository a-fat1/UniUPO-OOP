package ui.gui.controller;

import model.ListManager;
import ui.gui.view.frame.MainFrame;

public class MainController {
    private MainFrame mainFrame;
    private ListManager manager;
    private Runnable onReturn;

    // private ListController listController;
    // private ArticleController articleController;
    // private CategoryController categoryController;
    // private MenuBarController menuBarController;

    public MainController(MainFrame mainFrame, ListManager manager, Runnable onReturn) {
        this.mainFrame = mainFrame;
        this.manager = manager;
        this.onReturn = onReturn;

        initializeControllers();
    }

    private void initializeControllers() {
        // listController = new ListController(mainFrame.listPanel, manager);
        // articleController = new ArticleController(mainFrame.articlePanel, mainFrame.detailPanel, manager);
        // categoryController = new CategoryController(mainFrame.categoryPanel, manager);
        // menuBarController = new MenuBarController(mainFrame.menuBar, mainFrame, onReturn);

        // listController.setArticleController(articleController);
        // articleController.setCategoryController(categoryController);
        // menuBarController.setListController(listController);
    }
}
