package xmlConfluence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.eclipse.swt.widgets.Display;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

//		RandomAccessFile rF = new RandomAccessFile("X:\\xml\\TEMP\\exportDescriptor.properties", "rw");
//		while (rF.readLine() != null) {
//			System.out.println(rF.readLine());
//		}
		
		XmlWindow neu = new XmlWindow(Display.getDefault());
		neu.run();
//		
//		XmlWorker worker = new XmlWorker("X:\\xml\\xmltest.xml", "X:\\xml\\xmltest2.xml", "PETER");
//		String buf = worker.readNewData();
//		System.out.println(buf);
//		worker.writeNewData(buf);
//		
//		ZipWorker zip = new ZipWorker();
//		zip.unZipIt("X:\\xml\\musterprojekt.zip", "X:\\xml\\Neuer Ordner");
//		zip.zipIt("X:\\xml\\New.zip");
		
	}

}
