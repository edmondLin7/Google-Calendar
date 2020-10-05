package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.time.LocalDate;
/**
 * The main Event class 
 */
public class Event {
	private String name;
	private LocalDate date;
	private int startTime;
	private int endTime;
	/**
	 * The Event constructor 
	 * @param name
	 * @param date
	 * @param startTime
	 * @param endTime
	 */
	public Event(String name, LocalDate date, int startTime, int endTime) {
		this.name = name;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	/**
	 * To get name of the event
	 * @return name of event
	 */
	public String getName() {
		return name;
	}
	/**
	 * To get date of the event
	 * @return date of the event
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * To get start time of the event
	 * @return start time
	 */
	public int getStartTime() {
		return startTime;
	}
	/**
	 * To get end time of the event
	 * @return end time
	 */
	public int getEndTime() {
		return endTime;
	}
	/**
	 * To check if the two events are overlapped
	 * @param event
	 * @return true or false
	 */
	public boolean isOverlapped(Event event) {
		return date.isEqual(event.getDate()) && ( (startTime <= event.getEndTime() && startTime >= event.getStartTime()) || (endTime <= event.getEndTime() && endTime >= event.getStartTime()));
	}
}
