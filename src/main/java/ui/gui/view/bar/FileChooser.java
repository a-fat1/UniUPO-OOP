package ui.gui.view.bar;

import javax.swing.UIManager;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/** Personalizza il selettore di file con testi localizzati e configurazioni specifiche. */
public class FileChooser extends JFileChooser {
	/** Costruisce il selettore di file personalizzato e applica le configurazioni dei testi. */
	public FileChooser() {
		UIManager.put("FileChooser.saveDialogTitleText", "Salva");
		UIManager.put("FileChooser.openDialogTitleText", "Apri");
		UIManager.put("FileChooser.cancelButtonText", "Annulla");
		UIManager.put("FileChooser.cancelButtonToolTipText", "Annulla");
		UIManager.put("FileChooser.openButtonText", "Apri");
		UIManager.put("FileChooser.openButtonToolTipText", "Apri il file selezionato");
		UIManager.put("FileChooser.saveButtonText", "Salva");
		UIManager.put("FileChooser.saveButtonToolTipText", "Salva il file");
		UIManager.put("FileChooser.lookInLabelText", "Cerca in");
		UIManager.put("FileChooser.saveInLabelText", "Salva in");
		UIManager.put("FileChooser.fileNameLabelText", "Nome file");
		UIManager.put("FileChooser.folderNameLabelText", "Nome cartella");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo di file");
		UIManager.put("FileChooser.upFolderToolTipText", "Livello superiore");
		UIManager.put("FileChooser.upFolderAccessibleName", "Livello superiore");
		UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
		UIManager.put("FileChooser.homeFolderAccessibleName", "Desktop");
		UIManager.put("FileChooser.newFolderToolTipText", "Nuova cartella");
		UIManager.put("FileChooser.newFolderAccessibleName", "Nuova cartella");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Elenco");
		UIManager.put("FileChooser.listViewButtonAccessibleName", "Elenco");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Dettagli");
		UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Dettagli");
		UIManager.put("FileChooser.fileNameHeaderText", "Nome");
		UIManager.put("FileChooser.fileSizeHeaderText", "Dimensione");
		UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
		UIManager.put("FileChooser.fileDateHeaderText", "Data modifica");
		UIManager.put("FileChooser.fileAttrHeaderText", "Attributi");
		UIManager.put("FileChooser.acceptAllFileFilterText", "Tutti i file");
		UIManager.put("FileChooser.fileNameExtension", "Estensione");

		// Aggiorna l'interfaccia utente per riflettere le modifiche alle configurazioni
		SwingUtilities.updateComponentTreeUI(this);
	}
}
