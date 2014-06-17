package reader;

import java.util.HashMap;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String [] args) {
		
		XmlHandler handler = new XmlHandler();
		HashMap<String, String> mapAll = new HashMap<>();
		
		//do golem stuff
		//TODO fix path
		String golemText = handler.getxGolem().readNewData(null); 
		HashMap<String,String> golemMap = handler.getxGolem().parseXML(golemText);
		String golemHtml = handler.getxGolem().createHtml(golemMap);
		handler.getxGolem().writeHtmlFile(golemHtml);
		handler.getxGolem().backupHtml(golemHtml, handler.getxGolem().createCurrentName());
		
		mapAll.putAll(golemMap);
		String a = new Integer(mapAll.size()/6).toString();
		
		//do heise stuff
		//TODO fix path
		String heiseText = handler.getxHeise().readNewData(null); 
		HashMap<String,String> heiseMap = handler.getxHeise().parseXML(heiseText);
		String heiseHtml = handler.getxHeise().createHtml(heiseMap);
		handler.getxHeise().writeHtmlFile(heiseHtml);
		handler.getxHeise().backupHtml(heiseHtml, handler.getxHeise().createCurrentName());
		
		mapAll.putAll(heiseMap);
		String b = new Integer(mapAll.size()/6).toString();
		
		//do spiegel stuff
		//TODO fix path
		String spiegelText = handler.getxSpiegel().readNewData(null); 
		HashMap<String,String> spiegelMap = handler.getxSpiegel().parseXML(spiegelText);
		String spiegelHtml = handler.getxSpiegel().createHtml(spiegelMap);
		handler.getxSpiegel().writeHtmlFile(spiegelHtml);
		handler.getxSpiegel().backupHtml(spiegelHtml, handler.getxSpiegel().createCurrentName());
		
		mapAll.putAll(spiegelMap);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(mapAll.size()/6);
//		System.out.println("------y> \n" + mapAll.toString());
//		System.out.println("------y> \n" + golemMap.toString());
		
	}

}
