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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * The EventViewByMonth is a panel implementing EventView class and ChangeListener
 * Outputs the events to the screen by month
 */
public class EventViewByMonth extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private JPanel eventSection;
	private LocalDate currentDate;
	private ArrayList<Event> events;
	private JFrame popup;
	private JLabel popupContent;
	private Map<JLabel, Event> eventMap;
	/**
	 * EventViewByMonth constructor 
	 * @param model
	 */
	public EventViewByMonth(Model model) {
		this.model = model;
		
		events = new ArrayList<Event> ();
		
		setSize(400, 400);
		setLayout(new BorderLayout());
		
		eventMap = new HashMap<JLabel, Event>();
		
		popup = new JFrame();
		popup.setLayout(new FlowLayout());
		popup.setResizable(false);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popupContent = new JLabel();
		popup.add(popupContent);
		
			
		eventSection = new JPanel();
		eventSection.setLayout(new GridLayout(7, 7));
		eventSection.setSize(400, 400);
		add(eventSection, BorderLayout.CENTER);
		
		refresh();
	}
	/**
	 * To get data from model class
	 */
	public void getData() {
		currentDate = model.getCurrentDate();
		LocalDate firstDateOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
		events = EventProcessor.filterEvents(model.getEvents(), firstDateOfMonth, firstDateOfMonth.plusMonths(1).minusDays(1));
	}
	/**
	 * Renders the data and loads it into the calendar application 
	 * according to the month function.
	 */
	public void render() {
		eventMap.clear();
		
		LocalDate firstDateOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
		int date = firstDateOfMonth.getDayOfWeek().getValue()%7;
		
		
		eventSection.removeAll();
		// draw day string
		String[] headers = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		for(int i = 0; i < headers.length; i++) {
			JLabel label = new CalendarDate(headers[i]);	
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER);   
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
		    eventSection.add(label);
		}
		// front padding
		LocalDate previousMonth = currentDate.minusMonths(1);
		for(int i = date - 1; i >= 0; i--) {
			JLabel label = new CalendarDate("<html>" + (previousMonth.getMonth().maxLength() - i) + "</html>");
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.TOP); 
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
		    label.setForeground(Color.gray);
		    eventSection.add(label);
		}
		// draw dates
		for(int i = 1; i <= currentDate.getMonth().maxLength(); i++) {
			JPanel outerPanel = new JPanel();
			outerPanel.setLayout(new BorderLayout());
			outerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					
			JLabel label = new CalendarDate("<html>" + i + "</html>");
			label.setPreferredSize(new Dimension(150,20));
			label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			label.setHorizontalAlignment(JLabel.CENTER);  		    

		    outerPanel.add(label, BorderLayout.NORTH);
		   	    
		    LocalDate tmp = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), i);
			ArrayList<Event> dateEvents = new ArrayList<Event>();
			dateEvents = EventProcessor.filterEvents(model.getEvents(), tmp, tmp);
			
			if (dateEvents.size() > 0) {	
				JPanel container = new JPanel();
				container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
			    JScrollPane scrollablePanel = new JScrollPane(container, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			    scrollablePanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
			    outerPanel.add(scrollablePanel, BorderLayout.CENTER);
			    
				for (int j = 0; j < dateEvents.size(); j++) {
					Event ev = dateEvents.get(j);
					
					JPanel panel = new JPanel();			
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					if (j % 2 == 1)
						panel.setBackground(new Color(243, 243, 243));
					else
						panel.setBackground(new Color(192, 219, 243));
					panel.setOpaque(true);
					
					String eventName = ev.getName();
					JLabel eventPill = new JLabel("<html>" + (eventName.length() > 20 ? eventName.substring(0, 17) + "..." : eventName) + "</html>");
					eventPill.setHorizontalAlignment(JLabel.LEFT);
					eventPill.setAlignmentX(Component.LEFT_ALIGNMENT);

					eventMap.put(eventPill, ev);
					
					eventPill.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {
							Event event = eventMap.get((JLabel)e.getSource());
							String content = "<html><strong>" + event.getName() + "</strong><br><span>" + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(event.getDate()) + "</span><br><span>" + event.getStartTime() + ":00 - " + event.getEndTime() + ":00</span></html>";
							popupContent.setText(content);
							popup.repaint();
							popup.pack();
							popup.setLocation(e.getXOnScreen(), e.getYOnScreen());
							popup.setVisible(true);
						}

						@Override
						public void mousePressed(MouseEvent e) {}

						@Override
						public void mouseReleased(MouseEvent e) {}

						@Override
						public void mouseEntered(MouseEvent e) {}

						@Override
						public void mouseExited(MouseEvent e) {}
						
					});
					
					panel.add(eventPill);
					container.add(panel);
				}
			}
		    
		    eventSection.add(outerPanel);
		}
		// rear padding
		int rearPaddingCount = 49 - currentDate.getMonth().maxLength() - headers.length - date;
		for(int i = 0; i < rearPaddingCount; i++) {
			JLabel label = new CalendarDate("<html>" + (i + 1) + "</html>");
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.TOP); 
		    label.setForeground(Color.gray);
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
			eventSection.add(label);
			
		}
		revalidate();
		repaint();
	}
	/**
	 *  To refresh the screen of the application after every user action
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
