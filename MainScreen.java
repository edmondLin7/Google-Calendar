package calendar;

/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * The MainScreen class that extends JFrame 
 * To organize all panels on the main screen 
 */
public class MainScreen extends JFrame{
	/**
	 * MainScreen constructor
	 * @param model
	 * @param controller
	 */
	public MainScreen(Model model, Controller controller) {
		JPanel mainPanel = new JPanel();
		CalendarScreen calendarScreen = new CalendarScreen(model, controller);
		EventScreen eventScreen = new EventScreen(model, controller);
		
		setSize(1300, 400);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		mainPanel.add(calendarScreen);
		mainPanel.add(eventScreen);
		add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
