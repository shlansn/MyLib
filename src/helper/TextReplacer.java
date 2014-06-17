package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class TextReplacer implements Serializable {
	
	String regex;
	String [] parts;

	/**
	 * @param args
	 * 
	 * method that replaces specific marker within a textfile or String
	 */
	public static void main(String[] args) {
		
		
		

	}
	
	public TextReplacer() {
	
		this.regex = "%%m%%";
	}
	
	public TextReplacer(String regex) {
		
		this.regex = regex;
	}
	
	public String [] getTextRestFromFile() {
		String line = "";
		String result = "";
		this.parts = null;
		
		BufferedReader in;
		try {
			//TODO adjust path to file
			in = new BufferedReader(new InputStreamReader( new FileInputStream (new File("D:\\test.txt")),"UTF-8"));
			
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					result = result + line + "\n";
				}
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block --> problems with UTF-8
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block --> couldnt find file
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block --> couldnt close InputStream!
			e.printStackTrace();
		}
		finally {
			if (result.length() > 1) {
				parts = result.split(this.regex);
			}
			else {
				//ignore
			}
		}
		return parts;
	}
	
	public String [] getTextRestFromFile(File file) {
		String line = "";
		String result = "";
		this.parts = null;
		
		BufferedReader in;
		try {
			//TODO adjust path to file
			in = new BufferedReader(new InputStreamReader( new FileInputStream (file),"UTF-8"));
			
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					result = result + line + "\n";
				}
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block --> problems with UTF-8
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block --> couldnt find file
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block --> couldnt close InputStream!
			e.printStackTrace();
		}
		finally {
			if (result.length() > 1) {
				parts = result.split(this.regex);
			}
			else {
				//ignore
			}
		}
		return parts;
	}
	
	public String replaceWith(String string) {
		String result= "";
		
		for (int i= 0; i < parts.length; i++) {
			if (i == 0) {
				result += parts[i];
			}
			else {
				result += string + 	parts[i];
			}
		}
		return result;
	}
	
	public String getMarker() {
		return this.regex;
	}
	
	public void setMarker(String regex){
		this.regex = regex;
	}
	
	public String [] getParts() {
		return this.parts;
	}
	
	public void setParts(String[] parts) {
		this.parts = parts;
	}

}
