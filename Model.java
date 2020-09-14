package calendar;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model {
	private LocalDate currentDate; // Selected date
	private LocalDate today;
	private LocalDate viewDate; // The date of the rendered calendar
	private String viewMode;
	private ArrayList<ChangeListener> listeners;
	
	public Model(LocalDate currentDate, LocalDate today) {
		listeners = new ArrayList<ChangeListener>();
		this.currentDate = currentDate;
		this.today = today;
		this.viewDate = currentDate;
		viewMode = "DAY"; // Possible values: DAY, WEEK, MONTH, AGENDA
	}
	public String getViewMode() {
		return viewMode;
	}
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	public LocalDate getToday() {
		return today;
	}
	public LocalDate getViewDate() {
		return viewDate;
	}
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
		System.out.println(viewMode);
		update();
	}
	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
		update();
	}
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
		this.viewDate = currentDate;
		update();
	}
	public void attach(ChangeListener c){
		listeners.add(c);
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
