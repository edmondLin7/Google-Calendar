package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * The EventViewByWeek is a panel implementing EventView class and ChangeListener
 * Outputs the events to the screen by week
 */
public class EventViewByWeek extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private LocalDate currentDate;
	private ArrayList<Event> events;
	private JPanel eventSection;
	private String[] weekdays = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
	/**
	 * EventViewByWeek constructor
	 * @param model
	 */
	public EventViewByWeek(Model model) {
		this.model = model;
		events = new ArrayList<Event> ();
		
		setSize(400, 400);
		setLayout(new BorderLayout());
		
				
		refresh();
	}
	/**
	 * Returns the arrays after arranging the week
	 * @return
	 */
	private int[] computeWeekDayArray() {
		int[] result = new int[7];
		int currentDateIdx = currentDate.getDayOfWeek().getValue() % 7;
		result[currentDateIdx] = currentDate.getDayOfMonth();
		
		LocalDate tmp = currentDate;
		for (int i = currentDateIdx + 1; i < result.length; i++) {
			tmp = tmp.plusDays(1);
			result[i] = tmp.getDayOfMonth();
		}
		
		tmp = currentDate;
		for (int i = currentDateIdx - 1; i >= 0; i--) {
			tmp = tmp.minusDays(1);
			result[i] = tmp.getDayOfMonth();
		}
		
		return result;
	}
	/**
	 * To get data from model class
	 */
	public void getData() {
		currentDate = model.getCurrentDate();
		events = model.getEvents();
		System.out.println("\n\nevents");
		for(Event e : events) {
			System.out.println(e.toString());
		}
		events = EventProcessor.filterEvents(events, null, null);
		
	}
	/**
	 * Renders the data and loads it into the calendar application 
	 * according to the week function
	 */
	public void render() {	
		if (eventSection != null) {
			remove(eventSection);
		}
		
		eventSection = new JPanel();
		eventSection.setLayout(new GridLayout(1, 7));
		eventSection.setPreferredSize(new Dimension(300, 300));
		add(eventSection, BorderLayout.CENTER);
	    
	    String[] headers = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	    int[] currentWeekDays = computeWeekDayArray();
	    
	    for(int j = 0; j < 7; j++) {
	    	JPanel dayColumn = new JPanel();
	    	dayColumn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    	dayColumn.setLayout(new BoxLayout(dayColumn, BoxLayout.Y_AXIS));
	    	
	    	JLabel label = new JLabel("<html>" + headers[j] + "</html>", SwingConstants.CENTER);
	    	label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    	
	    	JLabel dateLabel = new JLabel("<html>" + currentWeekDays[j] + "</html>", SwingConstants.CENTER);
	    	dateLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    	
	    	JPanel headerPanel = new JPanel();
	    	headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
	    	headerPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
	    	
	    	headerPanel.add(label);
	    	headerPanel.add(dateLabel);
	    	dayColumn.add(headerPanel);
		  
		    JPanel eventPanel = new JPanel();
		    eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		    
		  	dayColumn.add(eventPanel);
		    
		    for (int i = 0; i < events.size(); i++) {
				Event ev = events.get(i);
				JPanel item = new JPanel();
				item.setMaximumSize(new Dimension(780, 40));
				item.setLayout(new FlowLayout());
				
				if (eventPanel.getComponentCount() % 2 == 0) {
					item.setBackground(new Color(243, 243, 243));
				}
				else {
					item.setBackground(new Color(192, 219, 243));
				}
				
				JLabel name = new JLabel("<html><strong>" + ev.getName() + "</strong><br><span>" + ev.getStartTime() + ":00 - " + ev.getEndTime() + ":00</span></html>", SwingConstants.LEFT);
				
				if(currentWeekDays[j] == ev.getDate().getDayOfMonth() && ev.getDate().getDayOfWeek().toString().equalsIgnoreCase(weekdays[j])) { 	
					item.add(name);
					eventPanel.add(item);
				}	
			}   
		    eventSection.add(dayColumn);
		}
		
		
		revalidate();
		repaint();
	}
	/**
	 * To refresh the screen of the application after every user action
	 */
	public void refresh() {
		getData();
		render();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		refresh();
	}
}