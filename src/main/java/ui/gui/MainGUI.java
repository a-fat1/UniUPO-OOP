package ui.gui;

import ui.gui.view.frame.MainFrame;
import ui.gui.controller.MainController;

import java.util.function.Consumer;

/** Gestisce l'interfaccia grafica principale dell'applicazione. */
public class MainGUI {
	/**
	 * Costruisce la GUI principale e gestisce il comportamento di chiusura della finestra.
	 *
	 * @param onReturn azione da eseguire al momento della chiusura della finestra. Accetta un Boolean che indica l'intenzione di ritornare alla schermata principale.
	 */
	public MainGUI(Consumer<Boolean> onReturn) {
		// Crea la vista principale
		MainFrame mainFrame = new MainFrame();

		// Crea il controller principale associato alla vista
		new MainController(mainFrame, onReturn);

		// Aggiunge un listener per gestire l'evento di chiusura della finestra
		mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			/**
			 * Invocato quando la finestra sta per essere chiusa.
			 *
			 * @param windowEvent evento di chiusura della finestra.
			 */
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// Esegue l'azione di ritorno alla schermata principale
				if (onReturn != null) {
					onReturn.accept(true);
				}
				// Chiude e rilascia le risorse associate alla finestra principale
				mainFrame.dispose();
			}
		});

		// Rende visibile la finestra principale
		mainFrame.setVisible(true);
	}
}
