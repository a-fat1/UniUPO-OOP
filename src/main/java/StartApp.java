import ui.cli.menu.UIHandlerMenu;

/**
 * Classe principale per l'avvio dell'applicazione.
 * `StartApp` contiene il metodo `main`, che rappresenta il punto iniziale
 * dell'applicazione, e si occupa di avviare il menu `UIHandlerMenu`.
 */
public class StartApp {
	/**
	 * Metodo principale per l'avvio dell'applicazione.
	 * 
	 * @param args gli argomenti della linea di comando (non utilizzati).
	 */
	public static void main(String[] args) {
		UIHandlerMenu uIHandlerMenu = new UIHandlerMenu();
		uIHandlerMenu.start();
	}
}
