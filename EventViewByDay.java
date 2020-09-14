package calendar;

import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EventViewByDay extends JPanel implements EventView, ChangeListener {
	private Model model;
	private Controller controller;
	private JLabel header;
	private LocalDate currentDate;
	private ArrayList<Event> events;
	
	public EventViewByDay(Model model) {
		this.model = model;
		
		header = new JLabel();
		events = new ArrayList<Event> ();
		
		setSize(400, 400);
		setLayout(new FlowLayout());
		add(header);
		refresh();
	}
	
	public void getData() {
		currentDate = model.getCurrentDate();
		
	}
	
	public void render() {
		header.setText("Day View: " + currentDate.getDayOfMonth());	
		revalidate();
		repaint();
	}
	
	public void refresh() {
		getData();
		render();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		refresh();
	}
}
