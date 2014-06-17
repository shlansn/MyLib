package reader;

import java.util.HashMap;

public interface XmlReader{

	//force class to be able to read a certain XML-Feed
	public String readNewData(String path);

	//parses the read XML-File and returns all entries needed within a hashmap
	public HashMap<String,String> parseXML(String content);
	
	//reads the created hashmap and returns correct html-String
	public String createHtml(HashMap<String,String> globalMap);
	
	//creates specific name out of the author and the current date
	public String createCurrentName();
	
	//writes the prepared html-String into an html-File
	//TODO check if string is needed
	public void writeHtmlFile(String content);
	
	//writes the prepared html-String into a different backup html-file
	//TODO check if string is needed
	public void backupHtml(String text,String name);
	
	
	
}
