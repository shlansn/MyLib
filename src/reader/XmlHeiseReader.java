package reader;
import helper.DatumFull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class XmlHeiseReader implements XmlReader{
	
	
	//TODO change to heise and test if connection available or backup needed
	static String path = "http://www.spiegel.de/schlagzeilen/index.rss";
//	static File file = new File("D:\\golem.xml");
	static HashMap<String,String> globalMap = new HashMap<String,String>();
	static String title;
	static String link;
	static String publ;
	//TODO decide if needed
	static String up;
	static String author;
	static String sum;
	static int globalCounter = 0;
	
//	public static void main(String[] args) {
//		String text = readNewData(path);
//		XmlHeiseReader.parseXML(text);
//		String string = createHtml();
//		writeHtmlFile(string);
//		backupHtml(string,createCurrentName());
//	}
	
	public String readNewData(String path) {
		String line = "";
		String result = "";
		
		try {
			URL url = new URL("http://www.heise.de/newsticker/heise-top-atom.xml");
		    URLConnection urlConnection = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader( urlConnection.getInputStream(),"UTF-8"));
//		    BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream (new File("D:\\heise-top-atom.xml")),"UTF-8"));
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					result = result + line + "\n";
				}
			}
			in.close();
			//System.out.println(result);
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("Datei konnte nicht gefunden werden! \n");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public String createHtml(HashMap<String,String> globalMap) {
		String HtmlString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
							"<html> \n" +
							"\t<header> \n" +
							"\t\t<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" /> \n" +
							"\t</header> \n" +
							"\t<body> \n";
		for (int i = 1; i <= globalCounter; i++) {
			HtmlString +=   "\t\t<section id=\"entry\">\n" +
							"\t\t\t<div id=\"title\" style=\"font-style:bold\"" + i + "><h4>" + globalMap.get("title-heise-"+i) + "</h4></div>\n" +
							"\t\t\t<div id=\"link\">" + globalMap.get("link-heise-"+i) + "</div>\n" +
							"\t\t\t<div id=\"publ\">" + globalMap.get("publ-heise-"+i) + "</div>\n" +
							"\t\t\t<div id=\"up\">" + globalMap.get("up-heise-"+i) + "</div>\n" +
							"\t\t\t<div id=\"author\">" + globalMap.get("author-heise-"+i) + "</div>\n" +
							"\t\t\t<div id=\"sum\">" + globalMap.get("sum-heise-"+i) + "</div>\n" +
							"\t\t</section>\n";
		}
		HtmlString +="\t</body>\n" +
					"</html>\n";
		return HtmlString;
		
		
	}
	
	public HashMap<String,String> parseXML(String content) {
		String full = content;
		String [] parts = full.split("<entry>");
		
		int count = parts.length;
		
		for (int x= 1; x < count; x++){
			//--------------------------GET TITLE-----------------------------------------
			String [] detail = parts[x].split("</title>");
			int pos = detail[0].indexOf(">");
			title = detail[0].substring(pos+1, detail[0].length());
			if (title.contains("\"")) {
				title = title.replaceAll("\"", "");
			}
			if (title.contains("\'")) {
				title = title.replaceAll("\'", "");
			}
			//------------------------GET LINK---------------------------------------------
			String [] troll2 = parts[x].split(" />");
			int pos2 = troll2[0].indexOf("href=\"");
			link = troll2[0].substring(pos2+6, troll2[0].length());
			if (link.contains("\"")) {
				link.replaceAll("\"", "");
			}
			if (link.contains("\'")) {
				link.replaceAll("\'", "");
			}
			//------------------------GET PUBLISHED---------------------------------------------
			String [] troll3 = parts[x].split("</published>");
			int pos3 = troll3[0].indexOf("<published>");
			publ = troll3[0].substring(pos3+11, troll3[0].length());
			String testb = prepareDateFormat(publ);
			publ = testb;
			//------------------------GET UPDATED---------------------------------------------
			String [] troll4 = parts[x].split("</updated>");
			int pos4 = troll4[0].indexOf("<updated>");
			up = troll4[0].substring(pos4+9, troll4[0].length());
			String testa = prepareDateFormat(up);
			up = testa;
			//------------------------GET AUTHOR---------------------------------------------
			//NOT USED SO SET "Heise Online"
			author = "n.A. - Heise Online";
			//------------------------GET SUMMARY---------------------------------------------
			String [] troll6 = parts[x].split("</summary>");
			int pos6 = troll6[0].indexOf("<summary type=\"html\">");
			sum = troll6[0].substring(pos6+21, troll6[0].length());
			String [] buff = sum.split("href=");
			sum = buff[0].substring(0, buff[0].length()-8);
			if (sum.contains("\"")) {
				sum.replaceAll("\"", "");
			}
			if (sum.contains("\'")) {
				sum.replaceAll("\'", "");
			}
			
			
			//check if every single tag exists, otherwise ignore entry because it will cut the string wrong
			if ((parts[x].contains("summary")) && parts[x].contains("title") && parts[x].contains("href=") &&
				 parts[x].contains("published") && parts[x].contains("updated") && full.contains("entry")) {
				//PUT VALS INTO MAP
				globalMap.put("title-heise-" + x, title);
				globalMap.put("link-heise-" + x, link);
				globalMap.put("publ-heise-" + x, publ);
				globalMap.put("up-heise-" + x, up);
				globalMap.put("author-heise-" + x, author);
				globalMap.put("sum-heise-" + x, sum);
				globalCounter = globalMap.size()/6;
			}
		}
		return globalMap;
	}
	
	public void writeHtmlFile(String html) {
		//TODO change path
		File out = new File("D:\\heise.html");
		try {
			//TODO change so that UTF-8 is written correctly
			BufferedWriter in = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out),"UTF-8"));
			//OutputStreamWriter in = new OutputStreamWriter(new FileOutputStream(out),"UTF-8");
			in.write(html);
			in.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block --> Problems with UTF-8
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block --> file not existing!
			e.printStackTrace();
		} catch (IOException e) {
			// TODO ERRORHANDLING huge ERROR -> stream not closed properly
			e.printStackTrace();
		}
	}
	
	public void backupHtml(String xml, String name) {
	
		File out = new File("D:\\backup\\" + name + ".html");
		try {
			//TODO change so that UTF-8 is written correctly
			BufferedWriter in = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out),"UTF-8"));
			//OutputStreamWriter in = new OutputStreamWriter(new FileOutputStream(out),"UTF-8");
			in.write(xml);
			in.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block --> Problems with UTF-8
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block --> file not existing!
			e.printStackTrace();
		} catch (IOException e) {
			// TODO ERRORHANDLING huge ERROR -> stream not closed properly
			e.printStackTrace();
		}		
	}
	
	public String createCurrentName() {
		DatumFull date = new DatumFull();
		String name = date.toStringUs() + "_heise";
		return name;
	}
	
	private static String prepareDateFormat(String datumstring) {
		if (datumstring.length() > 0) {
			String buf = datumstring.substring(0,19);
			buf = buf.replaceAll("T", "-");
			buf = buf.replaceAll(":", "-");
			return buf;
		}
		
		return null;
	}
}
