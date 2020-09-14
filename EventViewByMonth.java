package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EventViewByMonth extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private JLabel header;
	private JPanel eventSection;
	private LocalDate currentDate;
	private ArrayList<Event> events;
	
	public EventViewByMonth(Model model) {
		this.model = model;
		
		header = new JLabel();
		events = new ArrayList<Event> ();
		
		setSize(400, 400);
		setLayout(new BorderLayout());
		
		
		
		add(header, BorderLayout.NORTH);
			
		eventSection = new JPanel();
		eventSection.setLayout(new GridLayout(7, 7));
		eventSection.setSize(400, 400);
		add(eventSection, BorderLayout.CENTER);
		
		refresh();
	}
	
	public void getData() {
		currentDate = model.getCurrentDate();
		
	}
	
	public void render() {
		header.setText("" + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentDate.getYear());
		
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
			JLabel label = new CalendarDate("<html>&nbsp;&nbsp;&nbsp;&nbsp;" + (previousMonth.getMonth().maxLength() - i) + "&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br></html>");
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER); 
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
		    label.setForeground(Color.gray);
		    eventSection.add(label);
		}
		// draw dates
		for(int i = 1; i <= currentDate.getMonth().maxLength(); i++) {
			JLabel label = new CalendarDate("<html>&nbsp;&nbsp;&nbsp;&nbsp;" + i + "&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br></html>");
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER);   
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
		    eventSection.add(label);
		}
		// rear padding
		int rearPaddingCount = 49 - currentDate.getMonth().maxLength() - headers.length - date;
		for(int i = 0; i < rearPaddingCount; i++) {
			JLabel label = new CalendarDate("<html>&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + "&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br></html>");
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER); 
		    label.setForeground(Color.gray);
		    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    	label.setBorder(border);
			eventSection.add(label);
			
		}
		revalidate();
		repaint();
	}
	
	public void refresh() {
		getData();
		render();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		refresh();
	}
}
