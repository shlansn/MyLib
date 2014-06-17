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

public class XmlSpiegelReader implements XmlReader{
	
	
	static String path = "D:\\spiegel-online.xml";
//	static File file = new File("D:\\golem.xml");
	static HashMap<String,String> globalMap = new HashMap<String,String>();
	static String title;
	static String link;
	static String publ;    //-> pubDate in xml
	static String up;      //-> not used in xml
	static String author;  //-> not used in xml
	static String sum;     //-> description in xml 
	static int globalCounter = 0;
	
//	public static void main(String[] args) {
//		String text = readNewData(path);
//		XmlSpiegelReader.parseXML(text);
//		String string = createHtml();
//		writeHtmlFile(string);
//		backupHtml(string,createCurrentName());
//	}
	
	public String readNewData(String path) {
		String line = "";
		String result = "";
		
		try {
			URL url = new URL("http://www.spiegel.de/schlagzeilen/index.rss");
		    URLConnection urlConnection = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader( urlConnection.getInputStream(),"ISO-8859-1"));
//			BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream (new File("D:\\spiegel-online.xml")),"ISO-8859-1"));
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
							"\t\t\t<div id=\"title\" style=\"font-style:bold\"" + i + "><h4>" + globalMap.get("title-spiegel-"+i) + "</h4></div>\n" +
							"\t\t\t<div id=\"link\">" + globalMap.get("link-spiegel-"+i) + "</div>\n" +
							"\t\t\t<div id=\"publ\">" + globalMap.get("publ-spiegel-"+i) + "</div>\n" +
							"\t\t\t<div id=\"up\">" + globalMap.get("up-spiegel-"+i) + "</div>\n" +
							"\t\t\t<div id=\"author\">" + globalMap.get("author-spiegel-"+i) + "</div>\n" +
							"\t\t\t<div id=\"sum\">" + globalMap.get("sum-spiegel-"+i) + "</div>\n" +
							"\t\t</section>\n";
		}
		HtmlString +="\t</body>\n" +
					"</html>\n";
		return HtmlString;
		
		
	}
	
	public HashMap<String,String> parseXML(String content) {
		String full = content;
		String [] parts = full.split("<item>");
		
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
			String [] troll2 = parts[x].split("</link>");
			int pos2 = troll2[0].indexOf("<link>");
			link = troll2[0].substring(pos2+6, troll2[0].length());
			//------------------------GET PUBLISHED---------------------------------------------
			String [] troll3 = parts[x].split("</pubDate>");
			int pos3 = troll3[0].indexOf("<pubDate>");
			publ = troll3[0].substring(pos3+9, troll3[0].length());
			String testb = prepareDateFormat(publ);
			publ = testb;
			//------------------------GET UPDATED---------------------------------------------
			// NOT USED -> set to pubDate
			up = publ;
			//------------------------GET AUTHOR---------------------------------------------
			// NOT USED -> set to "Spiegel online"
			author = "n.A. - Spiegel Online";
			//------------------------GET SUMMARY---------------------------------------------
			String [] troll6 = parts[x].split("</description>");
			int pos6 = troll6[0].indexOf("<description>");
			sum = troll6[0].substring(pos6+13, troll6[0].length());
			if (sum.contains("\"")) {
				sum = sum.replaceAll("\"", "");
			}
			if (sum.contains("\'")) {
				sum = sum.replaceAll("\'", "");
			}
			
			
			//check if every single tag exists, otherwise ignore entry because it will cut the string wrong
			if ((parts[x].contains("description")) && parts[x].contains("title") && parts[x].contains("link") &&
				 parts[x].contains("pubDate") && full.contains("item")) {
				//PUT VALS INTO MAP
				globalMap.put("title-spiegel-" + x, title);
				globalMap.put("link-spiegel-" + x, link);
				globalMap.put("publ-spiegel-" + x, publ);
				globalMap.put("up-spiegel-" + x, up);
				globalMap.put("author-spiegel-" + x, author);
				globalMap.put("sum-spiegel-" + x, sum);
				globalCounter = globalMap.size()/6;
			}
		}
		return globalMap;
	}
	
	public void writeHtmlFile(String html) {
		//TODO change path
		File out = new File("D:\\spiegel.html");
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
		String name = date.toStringUs() + "_spiegel";
		return name;
	}
	
	private static String prepareDateFormat(String datumstring) {
		//TODO build complex method to create sortable date

		String [] buf = datumstring.split(" ");
		//TODO lookup switch syntax and build method to recognize month
//		Wed, 27 Nov 2013 11:13:00 +0100
		String year = "";
		String month = "";
		String day = "";
		String hour = "";
		String minute = "";
		String second = "";
		
		//check day
		day = buf[1];
		
		//check month
		switch (buf[2]) {
			//TODO check if "Jan" is correct
			case "Jan": month = "01";
					break;
			//TODO check if shortcut for month is correct		
			case "Feb": month = "02";
					break;
			//TODO check if shortcut for month is correct		
			case "Mar": month = "03";
					break;
			//TODO check if shortcut for month is correct		
			case "Apr": month = "04";
					break;
			//TODO check if shortcut for month is correct		
			case "May": month = "05";
					break;
			//TODO check if shortcut for month is correct		
			case "Jun": month = "06";
					break;
			//TODO check if shortcut for month is correct		
			case "Jul": month = "07";
					break;
			//TODO check if shortcut for month is correct		
			case "Aug": month = "08";
					break;
			//TODO check if shortcut for month is correct		
			case "Sep": month = "09";
					break;
			//TODO check if shortcut for month is correct		
			case "Oct": month = "10";
					break;		
			case "Nov": month = "11";
					break;
			//TODO check if shortcut for month is correct		
			case "Dec": month = "12";
					break;
			default:	month = "01";
			//TODO create log-entry 
		}
		//check year
		year = buf[3];
		
		//check time
		String [] time = buf[4].split(":");
		hour = time[0];
		minute = time[1];
		second = time[2];
		
		return year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second;
	}
}
