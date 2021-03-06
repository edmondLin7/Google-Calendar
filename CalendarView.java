package calendar;
/**
 * Project Solution
 * @author Team TripleByte
 * @version 1.0
 * @copyright TripleByte Team-Project-CS151-SJSU 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * CalendarView class that is a panel implementing the ChangeListener
 * Making the individual dates click-able and navigable
 */
public class CalendarView extends JPanel implements ChangeListener{
	private JPanel navigationSection;
	private JPanel calendarSection;	
	private LocalDate currentDate;
	private LocalDate viewDate;
	private LocalDate today;
	private JLabel text;
	private Model model;
	private Controller controller;
	
	private MonthNavigator rightNav;
	private MonthNavigator leftNav;
	/**
	 * CalendarView constructor
	 * @param model
	 * @param controller
	 */
	public CalendarView(Model model, Controller controller) {
		super();
		setLayout(new BorderLayout());
		setSize(400, 400);
		
		this.model = model;
		this.controller = controller;
		
		navigationSection = new JPanel();
		setLayout(new BorderLayout());
		text = new JLabel();
		
		JPanel navBox = new JPanel();
		navBox.setLayout(new BorderLayout());
		leftNav = new MonthNavigator("<"); 
		leftNav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.decreaseViewDateMonth(1);
			}	
		});
		rightNav = new MonthNavigator(">");
		rightNav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.increaseViewDateMonth(1);
			}	
		});
		navBox.add(leftNav, BorderLayout.WEST);
		navBox.add(rightNav, BorderLayout.EAST);
		
		navigationSection.add(text, BorderLayout.WEST);
		navigationSection.add(navBox, BorderLayout.EAST);
		
		
		calendarSection = new JPanel();
		calendarSection.setLayout(new GridLayout(7, 7));
		
		
		add(navigationSection, BorderLayout.NORTH);
		add(calendarSection, BorderLayout.CENTER);
		
		
		// render is recalled many times when month or day changed
		refresh();
	}
	/**
	 * Rendering the visual part of the calendar
	 * Gets and loads the data simultaneously 
	 */
	public void render() {
		LocalDate firstDateOfMonth = LocalDate.of(viewDate.getYear(), viewDate.getMonthValue(), 1);
		int date = firstDateOfMonth.getDayOfWeek().getValue()%7;
		// Empty string + integer then java will try to cast integer into string
		text.setText("" + viewDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + viewDate.getYear());
		// clear calendarSection before adding updating the content, otherwise it keeps adding 
		// to the current calendar
		calendarSection.removeAll();
		// draw day string
		String[] headers = {"S", "M", "T", "W", "T", "F", "S"};
		for(int i = 0; i < headers.length; i++) {
			JLabel label = new CalendarDate(headers[i]);
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER);   
		    calendarSection.add(label);
		}
		// front padding
		LocalDate previousMonth = viewDate.minusMonths(1);
		for(int i = date - 1; i >= 0; i--) {
			JLabel label = new CalendarDate("" + (previousMonth.getMonth().maxLength() - i));
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER); 
		    label.setForeground(Color.gray);
		    
		    label.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					String value = l.getText();
					LocalDate selectedDate = LocalDate.of(viewDate.getYear(), viewDate.minusMonths(1).getMonth().getValue(), Integer.parseInt(value));
					controller.setDate(selectedDate);
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					Border border = BorderFactory.createLineBorder(new Color(192, 219, 243), 2);
			    	l.setBorder(border);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
			    	l.setBorder(null);
				}
		    });
		    
			calendarSection.add(label);
		}
		// draw dates
		for(int i = 1; i <= viewDate.getMonth().maxLength(); i++) {
			JLabel label = new CalendarDate("" + i);
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER);   
		    if(
	    		i == today.getDayOfMonth() && 
				viewDate.getMonth().getValue() == today.getMonth().getValue() &&
				viewDate.getYear() == today.getYear()
		    ) {
		    	label.setBackground(new Color(0x61A0D7));
		    	label.setOpaque(true);
		    	
		    }
		    else if(
	    		i == currentDate.getDayOfMonth() &&
				viewDate.getMonth().getValue() == currentDate.getMonth().getValue() &&
				viewDate.getYear() == currentDate.getYear()
	    		
    		) {
		    	label.setBackground(new Color(192, 219, 243));
		    	label.setOpaque(true);
		    	
		    }
		    
		    label.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					String value = l.getText();
					LocalDate selectedDate = LocalDate.of(viewDate.getYear(), viewDate.getMonth().getValue(), Integer.parseInt(value));
					controller.setDate(selectedDate);
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					Border border = BorderFactory.createLineBorder(new Color(192, 219, 243), 2);
			    	l.setBorder(border);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
			    	l.setBorder(null);
				}
		    });
		    
		    calendarSection.add(label);
		}
		// rear padding
		int rearPaddingCount = 49 - viewDate.getMonth().maxLength() - headers.length - date;
		for(int i = 0; i < rearPaddingCount; i++) {
			JLabel label = new CalendarDate(""+ (i+1));
			label.setHorizontalAlignment(JLabel.CENTER);
		    label.setVerticalAlignment(JLabel.CENTER); 
		    label.setForeground(Color.gray);
		    
		    label.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					String value = l.getText();
					LocalDate selectedDate = LocalDate.of(viewDate.getYear(), viewDate.plusMonths(1).getMonth().getValue(), Integer.parseInt(value));
					controller.setDate(selectedDate);
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
					Border border = BorderFactory.createLineBorder(new Color(192, 219, 243), 2);
			    	l.setBorder(border);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					JLabel l = (JLabel)e.getSource();
			    	l.setBorder(null);
				}
		    });
		    
			calendarSection.add(label);
			
		}
		revalidate();
		repaint();
	}
	/**
	 * getData method
	 * Gets the data from the model class
	 */
	private void getData() {
		currentDate = model.getCurrentDate();
		today = model.getToday();
		viewDate = model.getViewDate();
	}
	/**
	 * Refreshes the navigation button's functionality
	 * Loading the data simultaneously
	 */
	private void refresh() {
		// Lock buttons
		rightNav.setEnabled(false);
		leftNav.setEnabled(false);
		
		getData();
		render();
		// Unlock buttons
		rightNav.setEnabled(true);
		leftNav.setEnabled(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		refresh();
	}
}
