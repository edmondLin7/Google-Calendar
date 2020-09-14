package calendar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JFrame{
	private MonthNavigator monthNav;
	private DateNavigator todayButton;
	private DateNavigator rightNav;
	private DateNavigator leftNav;
	private Model model;
	private Controller controller;
	
	public Screen(Model model, Controller controller) {
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
				controller.decreaseDay(7);
			}	
		});
		rightNav = new DateNavigator(">");
		rightNav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.increaseDay(7);
			}	
		});
		navBox.add(todayButton, BorderLayout.WEST);
		navBox.add(leftNav, BorderLayout.CENTER);
		navBox.add(rightNav, BorderLayout.EAST);
		
		CalendarView calendarView = new CalendarView(model, controller);
		model.attach(calendarView);
		
		add(navBox, BorderLayout.NORTH);
		add(calendarView, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
}
