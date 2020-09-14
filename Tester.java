package calendar;

import java.time.LocalDate;

import javax.swing.JFrame;

public class Tester {
	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		
		Model model = new Model(today, today);
		Controller controller = new Controller(model);
		
		MainScreen screen = new MainScreen(model, controller);	
	}
}
