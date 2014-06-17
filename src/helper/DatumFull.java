package helper;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatumFull implements Serializable {

	
	private String day; // --> dd (Bsp. 07)
	private String month;// --> mm (Bsp. 09)
	private String year; // --> yyyy (Bsp 1996)
	
	private String hours;
	private String minutes;
	private String seconds;
	
	// default constructor to create Date with current time
	public DatumFull() {
		long unitime = System.currentTimeMillis();	
		Date currentTime = new Date(unitime);
		
		//day
		SimpleDateFormat formatterDay = new SimpleDateFormat("dd");//"yyyy.MM.dd - HH:mm:ss");
		this.day = formatterDay.format(currentTime);
		//month 
		SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");//"yyyy.MM.dd - HH:mm:ss");
		this.month = formatterMonth.format(currentTime);
		//year
		SimpleDateFormat formatterYear = new SimpleDateFormat( "yyyy");//"yyyy.MM.dd - HH:mm:ss");
		this.year = formatterYear.format(currentTime);
		
		//hours
		SimpleDateFormat formatterHours = new SimpleDateFormat("HH");//"yyyy.MM.dd - HH:mm:ss");
		this.hours = formatterHours.format(currentTime);
		//minutes 
		SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");//"yyyy.MM.dd - HH:mm:ss");
		this.minutes = formatterMinutes.format(currentTime);
		//seconds
		SimpleDateFormat formatterSeconds = new SimpleDateFormat( "ss");//"yyyy.MM.dd - HH:mm:ss");
		this.seconds = formatterSeconds.format(currentTime);
	}
	
	// constructor to create specific date
	public DatumFull(String Tag, String Monat, String Jahr, String Stunde, String Minute, String Sekunde) throws Exception {
		
		try {
			int tag = Integer.parseInt(Tag);
			int monat = Integer.parseInt(Monat);
			int jahr = Integer.parseInt(Jahr);
			int stunde = Integer.parseInt(Stunde);
			int minute = Integer.parseInt(Minute);
			int sekunde = Integer.parseInt(Sekunde);
			
			if (tag < 1 || tag > 31 || monat < 1 || monat > 12 || jahr < 0 || jahr > 9999 || stunde > 23 || stunde < 0 || minute < 0 || minute > 59 || sekunde < 0 || sekunde > 59) throw new Exception();
			
			this.day = Tag;
			this.month = Monat;
			this.year = Jahr;
			this.hours = Stunde;
			this.minutes = Minute;
			this.seconds = Sekunde;
			
			
		}
		catch (Exception ex) {
			throw new Exception("\nEs ist ein Fehler aufgetreten, �berpr�fen Sie die �bergebenen Werte!" +
					"\nBitte halten Sie sich an folgendes Schema:\n" +
					"Tag --> DD (1-31) \n" +
					"Monat --> MM (1-12) \n" +
					"Jahr --> YYYY (0-9999) \n" +
					"\n" +
					"Stunde --> hh (0-23) \n" +
					"Minute --> mm (0-59) \n" +
					"Sekunde --> ss (0-59)");
		}
	}
	
	public DatumFull(String Tag, String Monat, String Jahr) throws Exception {
		
		try {
			int tag = Integer.parseInt(Tag);
			int monat = Integer.parseInt(Monat);
			int jahr = Integer.parseInt(Jahr);
			int stunde = 12;
			int minute = 12;
			int sekunde = 12;
			
			if (tag < 1 || tag > 31 || monat < 1 || monat > 12 || jahr < 0 || jahr > 9999 || stunde > 23 || stunde < 0 || minute < 0 || minute > 59 || sekunde < 0 || sekunde > 59) throw new Exception();
			
			this.day = Tag;
			this.month = Monat;
			this.year = Jahr;
			this.hours = new String("12");
			this.minutes = new String("12");
			this.seconds = new String("12");
			
			
		}
		catch (Exception ex) {
			throw new Exception("\nEs ist ein Fehler aufgetreten, �berpr�fen Sie die �bergebenen Werte!" +
					"\nBitte halten Sie sich an folgendes Schema:\n" +
					"Tag --> DD (1-31) \n" +
					"Monat --> MM (1-12) \n" +
					"Jahr --> YYYY (0-9999) \n" +
					"\n" +
					"Stunde --> hh (0-23) \n" +
					"Minute --> mm (0-59) \n" +
					"Sekunde --> ss (0-59)");
		}
	}
	
	// check if date is within time interval
	public static boolean isInTime(DatumFull datum, DatumFull datumMin, DatumFull datumMax) {
		boolean flag = false;
		boolean flag2 = false;
		
		if (Long.parseLong(datum.getYear()) > Long.parseLong(datumMin.getYear())) {
			flag = true;
		}
		else { if (Long.parseLong(datum.getYear()) == Long.parseLong(datumMin.getYear())) {
			
					if (Long.parseLong(datum.getMonth()) > Long.parseLong(datumMin.getMonth())) {
						flag = true;
					}
					else { if (Long.parseLong(datum.getMonth()) == Long.parseLong(datumMin.getMonth())) {
						
								if (Long.parseLong(datum.getDay()) > Long.parseLong(datumMin.getDay())) {
					
									flag = true;
								}
								else	{if (Long.parseLong(datum.getDay()) == Long.parseLong(datumMin.getDay())) {
											flag = true;
											}
											else return false;
								}
							}
					}
				}
		}
		if (Long.parseLong(datum.getYear()) < Long.parseLong(datumMax.getYear())) {
			flag2 = true;
		}
		else { if (Long.parseLong(datum.getYear()) == Long.parseLong(datumMax.getYear())) {
			
					if (Long.parseLong(datum.getMonth()) < Long.parseLong(datumMax.getMonth())) {
						flag2 = true;
					}
					else { if (Long.parseLong(datum.getMonth()) == Long.parseLong(datumMax.getMonth())) {
						
								if (Long.parseLong(datum.getDay()) < Long.parseLong(datumMax.getDay())) {
					
									flag2 = true;
								}
								else	{if (Long.parseLong(datum.getDay()) == Long.parseLong(datumMax.getDay())) {
											flag2 = true;
											}
											else return false;
								}
							}
					}
				}
		}
		if (!flag) return false;
		if (!flag2) return false;
		return true;
		
	}
	
	public DatumFull createSortableDateFromUsStyle(String datumstring, String trennzeichen) {
		DatumFull buf = null;
		String [] puffer = datumstring.split(trennzeichen);
		//if anything went wrong just return null
		if (puffer.length < 0) { 
			return null;
		}
		else {
			buf.year = puffer[0];
			buf.month = puffer[1];
			buf.day = puffer[2];
			buf.hours = puffer[3];
			buf.minutes = puffer[4];
			buf.seconds = puffer[5];
		}
		return buf;
	}
	
	public String toString() {
		return (this.day + "." + this.month + "." + this.year + "-" + this.hours + ":" + this.minutes + ":" + this.seconds);
	}
	
	public String toStringUs() {
		return (this.year + this.month + this.day + this.hours + this.minutes + this.seconds);
	}
	
	public void setDay(String day) {
		this.day = day;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getHours() {
		return hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public String getSeconds() {
		return seconds;
	}
}
