package calendar;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class MainScreen extends JFrame{
	public MainScreen(Model model, Controller controller) {
		CalendarScreen calendarScreen = new CalendarScreen(model, controller);
		EventScreen eventScreen = new EventScreen(model, controller);
		
		setSize(800, 800);
		setLayout(new FlowLayout());
		
		add(calendarScreen);
		add(eventScreen);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
