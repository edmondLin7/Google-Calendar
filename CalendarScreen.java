package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * The CalendarScreen class JPanel
 */
public class CalendarScreen extends JPanel{
	private MonthNavigator monthNav;
	private DateNavigator todayButton;
	private DateNavigator rightNav;
	private DateNavigator leftNav;
	private Model model;
	private Controller controller;
	private JFrame popup;
	private JTextField newEventName;
	private JFormattedTextField newEventStartTime;
	private JFormattedTextField newEventEndTime;
	private UtilDateModel dateModel;
	private JButton createButton;
	private JLabel dialogMessage;
	
	/**
	 * CalendarScreen constructor
	 * @param model
	 * @param controller
	 */
	public CalendarScreen(Model model, Controller controller) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(400, 800);
		this.model = model;
		this.controller = controller;
		
		JPanel navBox = new JPanel();
		navBox.setLayout(new FlowLayout());
		todayButton = new DateNavigator("Today");
		todayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.goToToday();
			}	
		});
		
		leftNav = new DateNavigator("<"); 
		leftNav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mode = model.getViewMode();
				if(mode.compareTo("DAY") == 0) {
					controller.decreaseDay(1);
				}
				else if(mode.compareTo("WEEK") == 0) {
					controller.decreaseDay(7);
				}
				else if(mode.compareTo("MONTH") == 0) {
					controller.decreaseMonth(1);
				}
				else if(mode.compareTo("AGENDA") == 0) {
					// Do nothing
				}
			}	
		});
		rightNav = new DateNavigator(">");
		rightNav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mode = model.getViewMode();
				if(mode.compareTo("DAY") == 0) {
					controller.increaseDay(1);
				}
				else if(mode.compareTo("WEEK") == 0) {
					controller.increaseDay(7);
				}
				else if(mode.compareTo("MONTH") == 0) {
					controller.increaseMonth(1);
				}
				else if(mode.compareTo("AGENDA") == 0) {
					// Do nothing
				}
			}	
		});
		navBox.add(todayButton);
		navBox.add(leftNav);
		navBox.add(rightNav);
		
		CalendarView calendarView = new CalendarView(model, controller);
		model.attach(calendarView);
		
		createButton = new JButton("Create");
		createButton.setBackground(Color.red);
		createButton.setOpaque(true);
		createButton.setBorderPainted(false);
		createButton.setFont(new Font(createButton.getFont().getName(),createButton.getFont().getStyle(),17));
		createButton.setForeground(Color.white);
		
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.repaint();
				popup.pack();
				popup.setVisible(true);	
			}
			
		});
		
		add(navBox, BorderLayout.NORTH);
		add(createButton);
		add(calendarView, BorderLayout.CENTER);
		
		
		popup = new JFrame();
		popup.setLayout(new FlowLayout());
		popup.setResizable(false);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Name: "));
		newEventName = new JTextField();
		newEventName.setPreferredSize(new Dimension(200, 30));
		namePanel.add(newEventName);
		container.add(namePanel);
		
		dateModel = new UtilDateModel();
	    Properties dateProperties = new Properties();
	    dateProperties.put("text.today", "Today");
	    dateProperties.put("text.month", "Month");
	    dateProperties.put("text.year", "Year");    
	    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	    
	    JPanel dPanel = new JPanel();
	    dPanel.add(new JLabel("Date: "));
	    dPanel.add(datePicker);
	    
	    container.add(dPanel);
	    
	    
	    JPanel timePanel = new JPanel();
	    timePanel.add(new JLabel("Time: "));
	    
	    NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(23);
	    formatter.setAllowsInvalid(false);
	    
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    newEventStartTime = new JFormattedTextField(formatter);
	    newEventStartTime.setPreferredSize(new Dimension(30, 30));
	    newEventEndTime = new JFormattedTextField(formatter);
	    newEventEndTime.setPreferredSize(new Dimension(30, 30));
	    
	    timePanel.add(newEventStartTime);
	    timePanel.add(new JLabel(" - "));
	    timePanel.add(newEventEndTime);
	    
	    container.add(timePanel);
	    
	    dialogMessage = new JLabel(" ");
	    dialogMessage.setForeground(Color.red);
	    container.add(dialogMessage);
	    
	    
	    JButton dialogButton = new JButton("Create");
	    
	    dialogButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = newEventName.getText();
					Integer startTime = Integer.parseInt(newEventStartTime.getValue().toString());
					Integer endTime = Integer.parseInt(newEventEndTime.getValue().toString());
					LocalDate date = dateModel.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					
					if (name.trim().compareTo("") == 0) {
						dialogMessage.setText("Invalid Name");
					}
					else {
						Event event = new Event(name, date, startTime, endTime);
						if (!model.hasOverlapped(event)) {
							ArrayList<Event> aTmp = new ArrayList<Event>();
							aTmp.add(event);
							controller.addEvents(aTmp);
											
							newEventName.setText("");
							newEventStartTime.setText("");
							newEventEndTime.setText("");
							dialogMessage.setText(" ");
							popup.setVisible(false);
						}
						else {
							dialogMessage.setText("Overlapped Date / time");
						}
					}
				}
				catch(Exception ex) {
					dialogMessage.setText("Invalid Data");
				}
			}
	    });
	    container.add(dialogButton);
	    
	    popup.add(container);
	}
	/**
	 * Date formatter for date picker
	 */
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
