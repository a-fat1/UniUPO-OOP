import ui.cli.MainMenuCLI;
import ui.ShoppingListGUI;
import elaboration.InputReader;

public class StartApp {
	public static void main(String[] args) {
		InputReader inputReader = new InputReader(System.in);

		System.out.println("\nBenvenuto in ShoppingManager!");
		while (true) {
			System.out.println("\nScegli l'interfaccia utente che desideri utilizzare:");
			System.out.println("1. Interfaccia a riga di comando (CLI)");
			System.out.println("2. Interfaccia grafica (GUI)");
			System.out.print("\nInserisci il numero dell'opzione desiderata: ");

			switch (inputReader.readLine()) {
				case "1":
					MainMenuCLI mainMenuCLI = new MainMenuCLI(inputReader);
					mainMenuCLI.start();
					break;
				case "2":
					System.out.println("\nAvvio dell'interfaccia grafica...");
					javax.swing.SwingUtilities.invokeLater(() -> new ShoppingListGUI());
					return;
					// break;
				default:
					System.out.println("Opzione non valida. Riprova.");
					break;
			}
		}
	}
}
