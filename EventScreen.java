package calendar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EventScreen extends JPanel{
	private JPanel buttonSection;
	
	private JPanel eventView;
	private JPanel EVENT_VIEW_BY_DAY;
	private JPanel EVENT_VIEW_BY_WEEK;
	private JPanel EVENT_VIEW_BY_MONTH;
	private Model model;
	private String viewMode;
	
	
	public EventScreen(Model model, Controller controller) {
		super();
		setSize(400, 600);
		setLayout(new BorderLayout());
	
		this.model = model;
			
		// Init views by day / week / month
		EVENT_VIEW_BY_DAY = new EventViewByDay(model);
		EVENT_VIEW_BY_WEEK = new EventViewByWeek(model);
		EVENT_VIEW_BY_MONTH = new EventViewByMonth(model);
		
		model.attach((ChangeListener) EVENT_VIEW_BY_DAY);
		model.attach((ChangeListener) EVENT_VIEW_BY_WEEK);
		model.attach((ChangeListener) EVENT_VIEW_BY_MONTH);
		
		
		buttonSection = new JPanel();
		buttonSection.setLayout(new FlowLayout());
		
		JButton dayButton = new JButton("Day");
		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("DAY");		
				setEventView();
			}		
		});
		JButton weekButton = new JButton("Week");
		weekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("WEEK");
				setEventView();
			}			
		});
		JButton monthButton = new JButton("Month");
		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setViewMode("MONTH");	
				setEventView();
			}	
		});
		JButton agendaButton = new JButton("Agenda");
		
		buttonSection.add(dayButton);
		buttonSection.add(weekButton);
		buttonSection.add(monthButton);
		buttonSection.add(agendaButton);
		
		add(buttonSection, BorderLayout.NORTH);
		setEventView();
	}
	
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
		add(eventView);
		repaint();
	}
}
