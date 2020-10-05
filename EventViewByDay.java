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
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * The EventViewByDay is a panel implementing EventView class and ChangeListener
 * Outputs the events to the screen for a chosen day
 */
public class EventViewByDay extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private JPanel eventSection;
	private LocalDate currentDate;
	private ArrayList<Event> events;
	private JLabel header;
	/**
	 * EventViewByDay constructor
	 * @param model
	 */
	public EventViewByDay(Model model) {
		this.model = model;
		
		events = new ArrayList<Event> ();
		setLayout(new BorderLayout());
		header = new JLabel();
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		headerPanel.add(header);
		add(headerPanel, BorderLayout.NORTH);	
		
		eventSection = new JPanel();
		eventSection.setPreferredSize(new Dimension(300, 300));
		add(eventSection, BorderLayout.CENTER);
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    eventSection.setBorder(border);	    
		refresh();
	}
	/**
	 * To get data from model class
	 */
	public void getData() {
		currentDate = model.getCurrentDate();
		events = model.getEvents();
		events = EventProcessor.filterEvents(events, currentDate, currentDate);
	}
	/**
	 * Renders the data and loads it into the calendar application 
	 * according to the day function
	 */
	public void render() {
		String[] abbreviations = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		header.setText("<html><weak>" + abbreviations[currentDate.getDayOfWeek().getValue() % 7] + " " + currentDate.getDayOfMonth() + "</weak></html>");   
		
		eventSection.removeAll();
		
		JPanel panel = new JPanel();
		JScrollPane scrollablePanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	   
		scrollablePanel.setPreferredSize(new Dimension(760, 280));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < events.size(); i++) {
			Event ev = events.get(i);
			
			JPanel item = new JPanel();
			item.setMaximumSize(new Dimension(780, 50));
			item.setLayout(new FlowLayout(FlowLayout.LEFT));
			item.setAlignmentX(Component.LEFT_ALIGNMENT);
			item.setOpaque(true);
			
			if (i % 2 == 0) {
				item.setBackground(new Color(243, 243, 243));
			}
			else {
				item.setBackground(new Color(192, 219, 243));
			}
			
			JLabel name = new JLabel("<html><strong>" + ev.getName() + "</strong><br><span>" + ev.getStartTime() + ":00 - " + ev.getEndTime() + ":00</span></html>");
			item.add(name);
			panel.add(item);
		}
		
		eventSection.add(scrollablePanel);
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
