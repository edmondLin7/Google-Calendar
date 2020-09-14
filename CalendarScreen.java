package calendar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalendarScreen extends JPanel{
	private MonthNavigator monthNav;
	private DateNavigator todayButton;
	private DateNavigator rightNav;
	private DateNavigator leftNav;
	private Model model;
	private Controller controller;
	
	public CalendarScreen(Model model, Controller controller) {
		super();
		setLayout(new BorderLayout());
		setSize(400, 800);
		this.model = model;
		this.controller = controller;
		
		JPanel navBox = new JPanel();
		navBox.setLayout(new BorderLayout());
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
					controller.decreaseMonth(1);
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
					controller.increaseMonth(1);
				}
			}	
		});
		navBox.add(todayButton, BorderLayout.WEST);
		navBox.add(leftNav, BorderLayout.CENTER);
		navBox.add(rightNav, BorderLayout.EAST);
		
		CalendarView calendarView = new CalendarView(model, controller);
		model.attach(calendarView);
		
		add(navBox, BorderLayout.NORTH);
		add(calendarView, BorderLayout.SOUTH);
	}
}
