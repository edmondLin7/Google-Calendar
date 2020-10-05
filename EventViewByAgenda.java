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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
/**
 * The EventViewByAgenda is a panel implementing EventView class and ChangeListener
 */
public class EventViewByAgenda extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private JPanel eventSection;
	private ArrayList<Event> events;
	/**
	 * EventViewByAgenda constructor
	 * @param model
	 */
	public EventViewByAgenda(Model model) {
		this.model = model;
		
		events = new ArrayList<Event> ();
		
		setLayout(new BorderLayout());
		
		eventSection = new JPanel();
		eventSection.setPreferredSize(new Dimension(300, 100));
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	    eventSection.setBorder(border);
	    
	    JPanel dialogBox = new JPanel();
	    dialogBox.setLayout(new FlowLayout());
	    
	    JLabel fromDateLabel = new JLabel("From: ");
	    JLabel toDateLabel = new JLabel("To: ");
	    JButton searchButton = new JButton("Search");

	   	    
	    UtilDateModel fromDateModel = new UtilDateModel();
	    Properties fromDateProperties = new Properties();
	    fromDateProperties.put("text.today", "Today");
	    fromDateProperties.put("text.month", "Month");
	    fromDateProperties.put("text.year", "Year"); 
	    JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromDateModel, fromDateProperties);
	    JDatePickerImpl fromDatePicker = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
	    
	    
	    UtilDateModel toDateModel = new UtilDateModel();
	    Properties toDateProperties = new Properties();
	    toDateProperties.put("text.today", "Today");
	    toDateProperties.put("text.month", "Month");
	    toDateProperties.put("text.year", "Year");    
	    JDatePanelImpl toDatePanel = new JDatePanelImpl(toDateModel, toDateProperties);
	    JDatePickerImpl toDatePicker = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());
	     
	    
	    dialogBox.add(fromDateLabel);
	    dialogBox.add(fromDatePicker);
	    dialogBox.add(toDateLabel);
	    dialogBox.add(toDatePicker);
	    dialogBox.add(searchButton);
	    
	    searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate fromDate = fromDateModel.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate toDate = toDateModel.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				events = EventProcessor.filterEvents(model.getEvents(), fromDate, toDate);
				render();
			}
	    });
	    
	    add(dialogBox, BorderLayout.NORTH);
	    add(eventSection, BorderLayout.CENTER);
	}
	/**
	 * To render the events appeared within the chosen period and load it to the application
	 */
	public void render() {
		eventSection.removeAll();
		
		JPanel panel = new JPanel();
		JScrollPane scrollablePanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	   
		scrollablePanel.setPreferredSize(new Dimension(780, 270));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < events.size(); i++) {
			Event ev = events.get(i);
			
			JPanel item = new JPanel();
			item.setMaximumSize(new Dimension(780, 70));
			item.setLayout(new FlowLayout(FlowLayout.LEFT));
			item.setAlignmentX(Component.LEFT_ALIGNMENT);
			item.setOpaque(true);
			
			if (i % 2 == 0) {
				item.setBackground(new Color(243, 243, 243));
			}
			else {
				item.setBackground(new Color(192, 219, 243));
			}
			
			JLabel name = new JLabel("<html><strong>" + ev.getName() + "</strong><br><span>" + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(ev.getDate()) + "</span><br><span>" + ev.getStartTime() + ":00 - " + ev.getEndTime() + ":00</span></html>");
			item.add(name);
			panel.add(item);
		}
		
		eventSection.add(scrollablePanel);
		revalidate();
		repaint();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
//		refresh();
	}
	/**
	 * Date formatter for date picker
	 */
	// Date formatter for date picker
	private class DateLabelFormatter extends AbstractFormatter {
	    private String datePattern = "MM-dd-yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }
	}
}
