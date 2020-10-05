package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * The EventProcessor class to read events from file, then filter, and sort them
 */
public class EventProcessor {
	/**
	 * To decode the dates of week into integers
	 * @param input
	 * @return the code for each date
	 */
	public static int decodeRawDate(String input) {
		switch(input) {
			case "S":
				return 0;
			case "M":
				return 1;
			case "T":
				return 2;
			case "W":
				return 3;
			case "H":
				return 4;
			case "F":
				return 5;
			case "A":
				return 6;
			default:
				return -1;
		}
	}
	/**
	 * To check if the event in the list is added
	 * @param array
	 * @param value
	 * @return true or false 
	 */
	public static boolean contain(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value)
				return true;
		}
		return false;
	}
	/**
	 * To return an arrayList of all the events read from the file
	 * @param path
	 * @return a list of events
	 */
	public static ArrayList<Event> readFile(String path){
		ArrayList<Event> result = new ArrayList<Event>();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			while(line != null) {
				String[] parts = line.split(";");
				LocalDate start = LocalDate.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 1);
				LocalDate end  = LocalDate.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[3]), 1).plusMonths(1).minusDays(1);
				
				LocalDate tmp = start;
				
				String[] dates = parts[4].split("");
				int[] dateDigits = new int[dates.length];
				
				for(int i = 0; i < dates.length; i++) {
					dateDigits[i] = decodeRawDate(dates[i]);
				}
				
				while(tmp.isBefore(end)) {	
					Event event = new Event(parts[0], tmp, Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
					if (contain(dateDigits, event.getDate().getDayOfWeek().getValue())){	
						result.add(event);
					}
					tmp = tmp.plusDays(1);
				}
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * To filter and return a list of sorted events based on dates
	 * @param allEvents
	 * @param start
	 * @param end
	 * @return a list of sorted events by time in a day
	 */
	public static ArrayList<Event> filterEvents(ArrayList<Event> allEvents, LocalDate start, LocalDate end){
		ArrayList<Event> result = new ArrayList<Event>();
		
		if (start != null) {
			for (int i = 0; i < allEvents.size(); i++) {
				Event currentEvent = allEvents.get(i);
				LocalDate eventDate = currentEvent.getDate();
				if (
					(eventDate.isEqual(start) || eventDate.isAfter(start)) &&
					(eventDate.isEqual(end) || eventDate.isBefore(end))
				) {
					result.add(currentEvent);
				}
			}
		}
		else {
			result.addAll(allEvents);
		}
		
		result.sort(new Comparator<Event>() {
			@Override
			public int compare(Event ev1, Event ev2) {
				
				int ev1_startTime = ev1.getStartTime();
				int ev2_startTime = ev2.getStartTime();
				
				if (ev1.getDate().isEqual(ev2.getDate())) {
					return ev1_startTime == ev2_startTime ? 0 : (ev1_startTime > ev2_startTime ? 1 : -1);
				}
				else if (ev1.getDate().isAfter(ev2.getDate())){
					return 1;
				}
				else {
					return -1;
				}
			}
		});
		
		return result;
	}
}
