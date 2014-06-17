package xmlConfluence;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFileChooser;

public class FileExplorerDialog {

		
	private static File datei;
	private static String inputVerzStr;
	
	public static String oeffnen() {
	    final JFileChooser chooser = new JFileChooser("Verzeichnis wählen");
	    chooser.setDialogType(JFileChooser.OPEN_DIALOG);
	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    final File file = new File("/home");
	
	    chooser.setCurrentDirectory(file);
	
	    chooser.addPropertyChangeListener(new PropertyChangeListener() {
	        public void propertyChange(PropertyChangeEvent e) {
	            if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
	                    || e.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
	                final File f = (File) e.getNewValue();
	            }
	        }
	    });
	    chooser.setVisible(true);
	    final int result = chooser.showOpenDialog(null);
	
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File inputVerzFile = chooser.getSelectedFile();
	        inputVerzStr = inputVerzFile.getPath();
	        datei = inputVerzFile;
	    }
	    chooser.setVisible(false);
		return inputVerzStr;
	}
} 
