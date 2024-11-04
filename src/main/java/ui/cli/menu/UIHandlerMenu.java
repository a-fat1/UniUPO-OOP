package ui.cli.menu;

import ui.gui.MainGUI;
import ui.cli.base.BaseMenu;

import javax.swing.SwingUtilities;

/**
 * Gestisce la selezione dell'interfaccia utente, permettendo di scegliere
 * tra l'interfaccia a riga di comando (CLI) e l'interfaccia grafica (GUI).
 * Estende la classe `BaseMenu`.
 */
public class UIHandlerMenu extends BaseMenu {
	/** Inizializza `UIHandlerMenu`. */
	public UIHandlerMenu() {
		super();
	}

	/**
	 * Avvia il menu di selezione dell'interfaccia utente, consentendo
	 * all'utente di scegliere tra l'interfaccia CLI, l'interfaccia GUI o l'uscita.
	 * Continua a visualizzare il menu fino a quando l'utente non sceglie di terminare il programma.
	 */
	@Override
	public void start() {
		showMessage("\nBenvenuto nel GestoreSpesa!");
		while (true) {
			displayMenu();
			switch (readString("\nSeleziona un'interfaccia: ")) {
				case "1":
					startCLI();
					break;
				case "2":
					// Avvia l'interfaccia grafica (GUI) con gestione dell'interruzione del thread
					try { startGUI(); }
					catch (InterruptedException interruptException) {
						Thread.currentThread().interrupt();
						showMessage("Il thread è stato interrotto.");
					}
					break;
				case "3":
					showGoodbye();
					break;
				default:
					showInvalidOption();
					break;
			}
		}
	}

	/**
	 * Visualizza il menu di selezione dell'interfaccia utente.
	 * Include le opzioni per avviare l'interfaccia CLI, l'interfaccia GUI o terminare il programma.
	 */
	@Override
	public void displayMenu() {
		showMessage("\nScegli l'interfaccia utente che desideri utilizzare:");
		showMessage("1 - Interfaccia a riga di comando (CLI)");
		showMessage("2 - Interfaccia grafica (GUI)");
		showMessage("3 - Termina programma");
	}

	/**
	 * Avvia l'interfaccia a riga di comando (CLI).
	 * Crea un'istanza di `MainMenu` e ne avvia il metodo `start`.
	 */
	private void startCLI() {
		MainMenu mainController = new MainMenu();
		mainController.start();
	}

	/**
	 * Avvia l'interfaccia grafica (GUI) e gestisce la sincronizzazione per attendere
	 * la chiusura della GUI prima di tornare al menu principale.
	 * 
	 * @throws InterruptedException se il thread viene interrotto durante l'attesa.
	 */
	private void startGUI() throws InterruptedException{
		showMessage("\nPer terminare il programma, chiudi la finestra dell'interfaccia grafica.");
		showMessage("Per tornare al terminale, clicca su Esci --> Ritorna alla selezione dell'interfaccia.");

		showMessage("\nAvvio dell'interfaccia grafica...");
		final Object lock = new Object();

		// Sincronizzazione per attendere la chiusura della GUI prima di proseguire
		synchronized (lock) {
			SwingUtilities.invokeLater(() -> {
				new MainGUI(() -> {
					// Notifica al thread principale che la GUI è stata chiusa
					synchronized (lock) {
						lock.notify();
					}
				});
			});
			// Attende che la GUI sia chiusa
			lock.wait();
		}

		showMessage("\nInterfaccia grafica terminata.\nRitorno al menu dell'interfaccia...");
	}
}
