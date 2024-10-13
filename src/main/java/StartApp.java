import ui.*;
import java.util.Scanner;

public class StartApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Benvenuto nell'applicazione Gestione Liste della Spesa!");
		System.out.println("Scegli l'interfaccia che desideri utilizzare:");
		System.out.println("1. Interfaccia a riga di comando (CLI)");
		System.out.println("2. Interfaccia grafica (GUI)");
		System.out.print("Inserisci il numero dell'opzione desiderata: ");

		String choice = scanner.nextLine();
		switch (choice) {
			case "1":
				ShoppingListCLI cli = new ShoppingListCLI();
				cli.start();
				break;
			case "2":
				System.out.println("Avvio dell'interfaccia grafica...");
				javax.swing.SwingUtilities.invokeLater(() -> new ShoppingListGUI());
				break;
			default:
				System.out.println("Opzione non valida. Uscita dal programma.");
				break;
		}

		scanner.close();
	}
}
