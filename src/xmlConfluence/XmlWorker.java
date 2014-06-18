package xmlConfluence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class XmlWorker {
	
	//DEFINE SOME GLOBAL STRINGS
	private String patternOld = "Musterprojekt";
	private String patternNew;
	
	private String pathMuster = ""; //TODO default path for "Musterprojekt"
	private String pathNew;
	
	public XmlWorker(String pathMuster,String pathNew, String patternNew) {
		this.pathNew = pathNew;
		this.pathMuster = pathMuster;
		this.patternNew = patternNew;
	}

	public String readConfigData() {
		String line = "";
		String result = "";
		
		try {
//			BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream (new File(pathMuster)),"UTF-8"));
			RandomAccessFile in = new RandomAccessFile(pathMuster + "\\exportDescriptor.properties", "rw");
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					result = result + line + "\n";
				}
			}
			in.close();
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		} catch (IOException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		} 
		return null;
	}
	
	public String readXmlData() {
		String line = "";
		String result = "";
		
		try {
//			BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream (new File(pathMuster)),"UTF-8"));
			RandomAccessFile in = new RandomAccessFile(pathMuster + "\\entities.xml", "rw");
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					result = result + line + "\n";
				}
			}
			in.close();
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		} catch (IOException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		} 
		return null;
	}
	
	public void writeConfigData(String data) {
		File out = new File(pathNew + "\\exportDescriptor.properties");
		if(!out.exists()){
			out.mkdir();
       	}
		out.setWritable(true);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
			writer.write(data);
			writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		}catch (IOException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		}
		//TODO test if file exists, otherwise throw exception
	}
	
	public void writeXmlData(String data) {
		File out = new File(pathNew + "\\entities.xml");
		if(!out.exists()){
			out.mkdir();
       	}
		out.setWritable(true);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
			writer.write(data);
			writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		}catch (IOException e) {
			e.printStackTrace(); //TODO ERROR HANDLING
		}
		//TODO test if file exists, otherwise throw exception
	}
}
