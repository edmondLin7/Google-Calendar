package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * The main Model class to get, set stage of variable and update them
 */
public class Model {
	private LocalDate currentDate; // Selected date
	private LocalDate today;
	private LocalDate viewDate; // The date of the rendered calendar
	private String viewMode;
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Event> events;
	/**
	 * Model constructor
	 * @param currentDate
	 * @param today
	 */
	public Model(LocalDate currentDate, LocalDate today) {
		listeners = new ArrayList<ChangeListener>();
		events = new ArrayList<Event>();
		this.currentDate = currentDate;
		this.today = today;
		this.viewDate = currentDate;
		viewMode = "DAY"; // Possible values: DAY, WEEK, MONTH, AGENDA
	}
	/**
	 * To get the view mode
	 * @return the view mode
	 */
	public String getViewMode() {
		return viewMode;
	}
	/**
	 * To get the currentDate
	 * @return the current date 
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	/**
	 * To get today date
	 * @return today 
	 */
	public LocalDate getToday() {
		return today;
	}
	/**
	 * To get the view date
	 * @return the view date that is selected 
	 */
	public LocalDate getViewDate() {
		return viewDate;
	}
	/**
	 * To get the list of scheduled events 
	 * @return a list of events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	/**
	 * To set the view mode and update it simultaneously
	 * @param viewMode viewMode that users want to see
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
		System.out.println(viewMode);
		update();
	}
	/**
	 * To set the viewDate and updates it
	 * @param viewDate
	 */
	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
		update();
	}
	/**
	 * To set the currentDate and updates it
	 * @param currentDate
	 */
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
		this.viewDate = currentDate;
		update();
	}
	/**
	 * To attach a ChangeListener
	 * @param c
	 */
	public void attach(ChangeListener c){
		listeners.add(c);
    }
	/**
	 * To add events to the list of events already scheduled
	 * @param newEvents
	 */
	public void addEvents(ArrayList<Event> newEvents) {
		for (int i = 0; i < newEvents.size(); i++) {
			boolean overlapped = false;
			Event newEvent = newEvents.get(i);
			for (int j = 0; j < events.size(); j++) {
				if (events.get(j).isOverlapped(newEvent)) {
					overlapped = true;
					break;
				}
			}
			
			if (!overlapped) {
				events.add(newEvent);
			}
		}
		update();
	}
	/**
	 * To check if the events are overlapped
	 * @param newEvent
	 * @return true or false
	 */
	public boolean hasOverlapped(Event newEvent) {
		boolean overlapped = false;
		for (int j = 0; j < events.size(); j++) {
			if (events.get(j).isOverlapped(newEvent)) {
				overlapped = true;
				break;
			}
		}
		return overlapped;
	}
	
   /**
    * Notify views of the changes
   */
	public void update(){
		for (ChangeListener l : listeners){
			l.stateChanged(new ChangeEvent(this));
	    }
	}
}
