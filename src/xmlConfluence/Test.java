package xmlConfluence;

import org.eclipse.swt.widgets.Display;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		XmlWindow neu = new XmlWindow(Display.getDefault());
		neu.main(null);
		
		XmlWorker worker = new XmlWorker("X:\\xml\\xmltest.xml", "X:\\xml\\xmltest2.xml", "PETER");
		String buf = worker.readNewData();
		System.out.println(buf);
		worker.writeNewData(buf);
		
		ZipWorker zip = new ZipWorker(null, null);
		zip.zipIt(null);
		zip.unZipIt(null, null);
	}

}
