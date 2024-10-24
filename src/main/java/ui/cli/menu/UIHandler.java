package ui.cli.menu;

import javax.swing.SwingUtilities;
import ui.cli.base.BaseMenu;

import model.util.*;
import ui.gui.MainGUI;

public class UIHandler extends BaseMenu {

    public UIHandler() {
        super(null, new InputReader(System.in));
    }

    @Override
    public void start() {
        showMessage("\nBenvenuto nel GestoreSpesa!");
        while (true) {
            displayMenu();
            switch (inputReader.getString()) {
                case "1":
                    startCLI();
                    break;
                case "2":
                    startGUI();
                    break;
                case "3":
                    showGoodbye();
                    return;
                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    @Override
    public void displayMenu() {
        showMessage("\nScegli l'interfaccia utente che desideri utilizzare:");
        showMessage("1 - Interfaccia a riga di comando (CLI)");
        showMessage("2 - Interfaccia grafica (GUI)");
        showMessage("3 - Termina programma");

        getOption();
    }

    private void startCLI() {
        MainMenu mainController = new MainMenu();
        mainController.start();
    }

    private void startGUI() {
        showMessage("\nAvvio dell'interfaccia grafica...");
        final Object lock = new Object();
        synchronized (lock) {
            SwingUtilities.invokeLater(() -> {
                new MainGUI(() -> {
                    synchronized (lock) {
                        lock.notify();
                    }
                });
            });
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                showError("il thread è stato interrotto.");
            }
        }

        showMessage("\nInterfaccia grafica terminata.\nRitorno al menu dell'interfaccia...");
    }
}
