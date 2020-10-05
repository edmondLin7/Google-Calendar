package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * The main Controller class
 */
public class Controller {
	private Model model;
	/**
	 * Controller constructor
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	/**
	 * Increases the day by amount number of days.
	 * @param amount
	 */
	public void increaseDay(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.plusDays(amount));
	}
	/**
	 * Decreases the day by amount number of days.
	 * @param amount
	 */
	public void decreaseDay(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.minusDays(amount));
	}
	/**
	 * Increases the month by amount number of months.
	 * @param amount
	 */
	public void increaseMonth(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.plusMonths(amount));
	}
	/**
	 * Decreases the month by amount number of days.
	 * @param amount
	 */
	public void decreaseMonth(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.minusMonths(amount));
	}
	/**
	 * Increase the years by amount number of years
	 * @param amount
	 */
	public void increaseYear(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.plusYears(amount));
	}
	/**
	 * Decreases the years by amount number of years
	 * @param amount
	 */
	public void decreaseYear(int amount) {
		LocalDate currentDate = model.getCurrentDate();
		model.setCurrentDate(currentDate.minusYears(amount));
	}
	/**
	 * Sets the date on the basis of selectedDate parameter
	 * @param selectedDate
	 */
	public void setDate(LocalDate selectedDate) {
		model.setCurrentDate(selectedDate);
	}
	/**
	 * Today button functionality
	 */
	public void goToToday() {
		model.setCurrentDate(model.getToday());
	}
	/**
	 * Increases the Month Date by amount number of months.
	 * @param amount
	 */
	public void increaseViewDateMonth(int amount) {
		LocalDate viewDate  = model.getViewDate();
		model.setViewDate(viewDate.plusMonths(amount));
	}
	/**
	 * Decreases the Month view's date by amount number of months.
	 * @param amount
	 */
	public void decreaseViewDateMonth(int amount) {
		LocalDate viewDate = model.getViewDate();
		model.setViewDate(viewDate.minusMonths(amount));
	}
	/**
	 * Setting the view month depending on the user's choice
	 * that is: day, week, month, agenda
	 * day view: default
	 * @param mode
	 */
	public void setViewMode(String mode) {
		model.setViewMode(mode);
	}
	/**
	 * Add Events to the main model.
	 * @param events
	 */
	public void addEvents(ArrayList<Event> events) {
		model.addEvents(events);
		
	}
}
