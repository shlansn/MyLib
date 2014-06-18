package xmlConfluence;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Canvas;

public class XmlWindow extends Shell {
	
	private String pathForFile;
	private String pathForLog;
	private FileExplorerDialog fEx = new FileExplorerDialog();
	private XmlWorker xmlWorker = new XmlWorker("X:\\xml\\TEMP", "X:\\xml\\TEMP", "XXX");
	private ZipWorker zipWorker = new ZipWorker();
	
	private Text textPathFile;
	private Text textProjectName;
	private Text textProjectShort;
	private Text textPathLog;

	/**
	 * open dialog
	 * @param args
	 */
	public void run() {
		try {
			Display display = Display.getDefault();
			XmlWindow shell = new XmlWindow(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public XmlWindow(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(null);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(10, 26, 472, 23);
		lblNewLabel.setText("Bitte geben Sie den Datei-Pfad an, an dem der neue Projektbereich generiert werden soll: ");
		
		textPathFile = new Text(this, SWT.BORDER);
		textPathFile.setBounds(52, 55, 267, 23);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathForFile = fEx.oeffnen();
				textPathFile.setText(pathForFile);
			}
		});
		btnNewButton.setBounds(352, 55, 130, 25);
		btnNewButton.setText("Durchsuchen...");
		
		Label lblBitteWhlenSie = new Label(this, SWT.NONE);
		lblBitteWhlenSie.setText("Bitte w\u00E4hlen Sie den Namen des neuen Projektbereichs:");
		lblBitteWhlenSie.setBounds(10, 106, 307, 23);
		
		textProjectName = new Text(this, SWT.BORDER);
		textProjectName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textProjectName.getText().equals("%%Projektname%%")) {
					textProjectName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textProjectName.getText().equals("")) {
					textProjectName.setText("%%Projektname%%");
				}
			}
		});
		
		textProjectName.setToolTipText("Name des Bereiches bspw. KfW-APP oder NordLB");
		textProjectName.setText("%%Projektname%%");
		textProjectName.setBounds(352, 103, 130, 23);
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Bitte w\u00E4hlen Sie den Namen des neuen Projektkürzel:");
		label.setBounds(10, 138, 307, 23);
		
		textProjectShort = new Text(this, SWT.BORDER);
		textProjectShort.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textProjectShort.getText().equals("%%Projektk\u00FCrzel%%")) {
					textProjectShort.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textProjectShort.getText().equals("")) {
					textProjectShort.setText("%%Projektk\u00FCrzel%%");
				}
			}
		});
		textProjectShort.setToolTipText("K\u00FCrzel f\u00FCr das Projekt bspw. KfW oder NLB");
		textProjectShort.setText("%%Projektk\u00FCrzel%%");
		textProjectShort.setBounds(352, 135, 130, 23);
		
		Group group = new Group(this, SWT.NONE);
		group.setBounds(129, 205, 361, 82);
		
		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathForLog = fEx.oeffnen();
				textPathLog.setText(pathForLog);
			}
		});
		button.setEnabled(false);
		button.setText("Durchsuchen...");
		button.setBounds(221, 49, 130, 25);
		
		final Label lblPfadZurLogdatei = new Label(group, SWT.NONE);
		lblPfadZurLogdatei.setText("Pfad zur Logdatei:");
		lblPfadZurLogdatei.setBounds(10, 22, 113, 23);
		lblPfadZurLogdatei.setEnabled(false);
		
		final Button btnCheckButton = new Button(this, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (btnCheckButton.getSelection()) {
					//wenn angehackt
					textPathLog.setEditable(true);
					button.setEnabled(true);
					lblPfadZurLogdatei.setEnabled(true);
				}
				else {
					// wenn nicht
					textPathLog.setEditable(false);
					button.setEnabled(false);
					lblPfadZurLogdatei.setEnabled(false);
				}
			}
		});
		btnCheckButton.setBounds(12, 226, 111, 16);
		btnCheckButton.setText("generiere Logfile");
		

		
		textPathLog = new Text(group, SWT.BORDER);
		textPathLog.setEditable(false);
		textPathLog.setBounds(10, 51, 205, 23);
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO start action
				LoadScreen l = new LoadScreen();
				l.run();
				
				//first unzip it TODO fix me
				zipWorker.unZipIt("X:\\xml\\musterprojekt.zip", "X:\\xml\\TEMP");

				//config file
				String data = xmlWorker.readConfigData();
				String buf = data.replaceAll("Musterprojekt", "XXX");
				xmlWorker.writeConfigData(buf);
				//xml file
				data = xmlWorker.readXmlData();
				buf = data.replaceAll("Musterprojekt", "XXX");
				xmlWorker.writeXmlData(buf);
				
				//zip it TODO fix me
				zipWorker.zipIt("X:\\xml\\New.zip");
				l.close();
				
			}
		});
		btnOk.setBounds(370, 317, 75, 25);
		btnOk.setText("OK");
		
		Button btnAbbrechen = new Button(this, SWT.NONE);
		btnAbbrechen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO just close properly
			}
		});
		btnAbbrechen.setText("Abbrechen");
		btnAbbrechen.setBounds(462, 317, 75, 25);

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Neuen Bereich aus Musterprojekt importieren");
		setSize(576, 391);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
