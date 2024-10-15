import ui.cli.MainMenuCLI;
import ui.ShoppingListGUI;

import java.util.Scanner;

public class StartApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nBenvenuto nell'applicazione Gestione Liste della Spesa!");
		System.out.println("\nScegli l'interfaccia che desideri utilizzare:");
		System.out.println("1. Interfaccia a riga di comando (CLI)");
		System.out.println("2. Interfaccia grafica (GUI)");
		System.out.print("\nInserisci il numero dell'opzione desiderata: ");

		switch (scanner.nextLine()) {
			case "1":
				MainMenuCLI mainMenuCLI = new MainMenuCLI();
				mainMenuCLI.start();
				break;
			case "2":
				System.out.println("\nAvvio dell'interfaccia grafica...");
				javax.swing.SwingUtilities.invokeLater(() -> new ShoppingListGUI());
				break;
			default:
				System.out.println("Opzione non valida. Uscita dal programma.");
				break;
		}

		scanner.close();
	}
}
