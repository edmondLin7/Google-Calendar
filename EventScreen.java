package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * the EventScreen, that is a JLabel which displays event list
 */
public class EventScreen extends JPanel implements ChangeListener{
	private JPanel buttonSection;
	
	private JPanel eventView;
	private JPanel EVENT_VIEW_BY_DAY;
	private JPanel EVENT_VIEW_BY_WEEK;
	private JPanel EVENT_VIEW_BY_MONTH;
	private JPanel EVENT_VIEW_BY_AGENDA;
	private Model model;
	private String viewMode;
	private JLabel header;
	
	/**
	 * EventScreen constructor 
	 * @param model
	 * @param controller
	 */
	public EventScreen(Model model, Controller controller) {
		super();
		setSize(400, 600);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));	
	
		this.model = model;
			
		// Init views by day / week / month
		EVENT_VIEW_BY_DAY = new EventViewByDay(model);
		EVENT_VIEW_BY_WEEK = new EventViewByWeek(model);
		EVENT_VIEW_BY_MONTH = new EventViewByMonth(model);
		EVENT_VIEW_BY_AGENDA = new EventViewByAgenda(model);
		
		model.attach((ChangeListener) EVENT_VIEW_BY_DAY);
		model.attach((ChangeListener) EVENT_VIEW_BY_WEEK);
		model.attach((ChangeListener) EVENT_VIEW_BY_MONTH);
		model.attach((ChangeListener) EVENT_VIEW_BY_AGENDA);
		model.attach(this);
		
		
		buttonSection = new JPanel();
		buttonSection.setLayout(new FlowLayout());
		
		header = new JLabel();
		
		JButton dayButton = new JButton("Day");
		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("DAY");	
				drawHeader();
				setEventView();
			}		
		});
		JButton weekButton = new JButton("Week");
		weekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("WEEK");
				drawHeader();
				setEventView();
			}			
		});
		JButton monthButton = new JButton("Month");
		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("MONTH");	
				drawHeader();
				setEventView();
			}	
		});
		JButton agendaButton = new JButton("Agenda");
		agendaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("AGENDA");	
				setEventView();
			}		
		});
		JButton fromFileButton = new JButton("From File");
		fromFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filePicker = new JFileChooser();
				int result = filePicker.showOpenDialog(filePicker);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = filePicker.getSelectedFile();
				    ArrayList<Event> events = EventProcessor.readFile(selectedFile.getAbsolutePath());
				    controller.addEvents(events);
				}
			}		
		});
		
		buttonSection.add(header);
		buttonSection.add(dayButton);
		buttonSection.add(weekButton);
		buttonSection.add(monthButton);
		buttonSection.add(agendaButton);
		buttonSection.add(fromFileButton);
		
		add(buttonSection, BorderLayout.NORTH);
		setEventView();
		drawHeader();
	}
	/**
	 * Sets the view of the EventList
	 */
	private void setEventView() {
		viewMode = model.getViewMode();
		
		if (eventView != null)
			remove(eventView);
		
		if (viewMode.compareTo("DAY") == 0) {
			eventView = EVENT_VIEW_BY_DAY;
		}
		else if (viewMode.compareTo("WEEK") == 0) {
			eventView = EVENT_VIEW_BY_WEEK;
		}
		else if (viewMode.compareTo("MONTH") == 0) {
			eventView = EVENT_VIEW_BY_MONTH;
		}
		else if (viewMode.compareTo("AGENDA") == 0) {
			eventView = EVENT_VIEW_BY_AGENDA;
		}
		add(eventView);
		repaint();
	}
	/**
	 * Displays the different view's Header
	 * that is: Day, Week, Month, Agenda
	 */
	private void drawHeader() {
		String viewMode = model.getViewMode();
		LocalDate currentDate = model.getCurrentDate();
		
		if(viewMode.compareTo("DAY") == 0){
			header.setText("" + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentDate.getDayOfMonth() + "," + " " + currentDate.getYear());
		}
		if(viewMode.compareTo("WEEK") == 0){
			if (weekHasPreviousMonth(currentDate)) {
				header.setText("" + currentDate.minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " - " + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + currentDate.getYear());
			}
			else if (weekHasNextMonth(currentDate)) {
				header.setText("" + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " - " + currentDate.plusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) +  ", " + currentDate.getYear());
			}
			else {
				header.setText("" + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + " " + currentDate.getYear());
			}
		}
		if(viewMode.compareTo("MONTH") == 0){
			header.setText("" + currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentDate.getYear());
		}
	}
	/**
	 * Tracks and checks for the weeks with two month together
	 * Checks that in previous week
	 * @param currentDate
	 * @return true or false
	 */
	private boolean weekHasPreviousMonth(LocalDate currentDate) {
		boolean hasPreviousMonth = false;
		int currentDateIdx = currentDate.getDayOfWeek().getValue() % 7;
	
		LocalDate tmp = currentDate;
		for (int i = currentDateIdx - 1; i >= 0; i--) {
			if (!tmp.getMonth().equals(tmp.minusDays(1).getMonth())) {
				hasPreviousMonth = true;
				break;
			}
			tmp = tmp.minusDays(1);
		}
		
		return hasPreviousMonth;
	}
	/**
	 * Tracks and checks for the weeks with two month together
	 * Checks that in next week
	 * @param currentDate
	 * @return true or false
	 */
	private boolean weekHasNextMonth(LocalDate currentDate) {
		boolean hasNextMonth = false;
		int currentDateIdx = currentDate.getDayOfWeek().getValue() % 7;

		LocalDate tmp = currentDate;
		for (int i = currentDateIdx + 1; i < 7; i++) {
			if (!tmp.getMonth().equals(tmp.plusDays(1).getMonth())) {
				hasNextMonth = true;
				break;
			}
			tmp = tmp.minusDays(1);
		}
		
		return hasNextMonth;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		drawHeader();		
	}
}
