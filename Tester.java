package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.time.LocalDate;

import javax.swing.JFrame;
/**
 * The Tester class that has the main method 
 * Main execution class.
 */
public class Tester {
	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		
		Model model = new Model(today, today);
		Controller controller = new Controller(model);
		
		MainScreen screen = new MainScreen(model, controller);	
	}
}
