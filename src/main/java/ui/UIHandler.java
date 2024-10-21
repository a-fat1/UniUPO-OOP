package ui;

import ui.cli.MainMenuCLI;
import ui.gui.MainGUI;

import javax.swing.SwingUtilities;

import model.domain.InputReader;

public class UIHandler {
    private InputReader inputReader;

    public UIHandler() {
        inputReader = new InputReader(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nBenvenuto nel GestoreSpesa!");
            System.out.println("\nScegli l'interfaccia utente che desideri utilizzare:");
            System.out.println("1. Interfaccia a riga di comando (CLI)");
            System.out.println("2. Interfaccia grafica (GUI)");
            System.out.println("3. Termina programma");
            System.out.print("\nInserisci il numero dell'opzione desiderata: ");

            String choice = inputReader.readLine();
            switch (choice) {
                case "1":
                    startCLI();
                    break;
                case "2":
                    startGUI();
                    break;
                case "3":
                    System.out.println("Arrivederci!\n");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
        }
    }

    private void startCLI() {
        MainMenuCLI mainMenuCLI = new MainMenuCLI(inputReader);
        mainMenuCLI.start();
    }

    private void startGUI() {
        System.out.println("\nAvvio dell'interfaccia grafica...");
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
                System.out.println("Il thread è stato interrotto.");
            }
        }
        System.out.println("Interfaccia grafica terminata.");
    }
}
