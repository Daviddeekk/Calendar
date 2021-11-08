package Kalendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class Main implements Runnable{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}
	private static final String[] DAY_NAMES = { "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday","Sunday" };
	private static final String[] MONTH_NAMES = { "January", "February", 
			"March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };
	private Color backgroundColor;
	private JComboBox<String> monthComboBox;
	
        private JLabel[][] dayLabel;
        private JButton[][] dayLabell;
        
	private JLabel titleLabel;
        private JButton titleLabell;
	private JTextField dayField;
	private JTextField yearField;
	private LocalDate calendarDate;
	public Main() {
		this.calendarDate = LocalDate.now();
	}
	@Override
	public void run() {
		JFrame frame = new JFrame("Calendar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(createTopPanel(), BorderLayout.BEFORE_FIRST_LINE);
		frame.add(createCalendarPanel(), BorderLayout.CENTER);
		frame.pack();
                frame.setSize(750, 750);
                frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		//panel.add(createDatePanel(), BorderLayout.BEFORE_FIRST_LINE);
		panel.add(createTitlePanel(), BorderLayout.AFTER_LAST_LINE);
		return panel;
	}
	
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		Font font = panel.getFont().deriveFont(40f).deriveFont(Font.BOLD);
		titleLabel = new JLabel(" ");
		titleLabel.setFont(font);
                
		
                
                
                JButton previousMonthButton = new JButton("<");
		previousMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int month = monthComboBox.getSelectedIndex();
				month--;
				if (month < 0) {
					int year = Integer.valueOf(yearField.getText()) - 1;
					yearField.setText(Integer.toString(year));
					month = 11;
				}
				monthComboBox.setSelectedIndex(month);
				updateDayLabels();
			}
		});
		panel.add(previousMonthButton);
		monthComboBox = new JComboBox<>(MONTH_NAMES);
		
		monthComboBox.setSelectedIndex(calendarDate.getMonth().ordinal());
		dayField = new JTextField(2);
		dayField.setText(Integer.toString(calendarDate.getDayOfMonth()));
		yearField = new JTextField(4);
		
		yearField.setText(Integer.toString(calendarDate.getYear()));
		JButton nextMonthButton = new JButton(">");
		nextMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int month = monthComboBox.getSelectedIndex();
				month++;
				if (month > 11) {
					int year = Integer.valueOf(yearField.getText()) + 1;
					yearField.setText(Integer.toString(year));
					month = 0;
				}
				monthComboBox.setSelectedIndex(month);
				updateDayLabels();
			}
		});
                JButton print = new JButton();
                
                print.setText("Print to pdf");
                
                panel.add(titleLabel);
                panel.add(nextMonthButton);
                panel.add(print);
                
		return panel;
	}
	private JPanel createCalendarPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		backgroundColor = panel.getBackground();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(createWeekdayLabels(), BorderLayout.BEFORE_FIRST_LINE);
		panel.add(createDayLabels(), BorderLayout.CENTER);
                
		updateDayLabels();
		return panel;
	}
	private JPanel createWeekdayLabels() {
		JPanel panel = new JPanel(new GridLayout(0, 7 , 5, 5));
                
		Font dayNamesFont = panel.getFont().deriveFont(12f);
		for (int i = 0; i < 7 ; i++) {
			JPanel dayPanel = new JPanel(new BorderLayout());
			dayPanel.setPreferredSize(new Dimension(20, 40));
			JLabel label = new JLabel(DAY_NAMES[i]);
			label.setFont(dayNamesFont);// font názvu dnù
                        
			label.setHorizontalAlignment(JLabel.CENTER);
			dayPanel.add(label, BorderLayout.CENTER);
			panel.add(dayPanel);
		}
		return panel;
	}
	private JPanel createDayLabels() {
		JPanel panel = new JPanel(new GridLayout(0, DAY_NAMES.length , 5, 5));
		dayLabell = new JButton[6][DAY_NAMES.length];
                
		Font dayFont = panel.getFont().deriveFont(48f).deriveFont(Font.BOLD); 
		for (int j = 0; j < dayLabell.length; j++) {
			for (int i = 0; i < dayLabell[j].length; i++) {
				JPanel dayPanel = new JPanel(new BorderLayout());
				//dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); //ohranièení
				dayPanel.setPreferredSize(new Dimension(120, 120)); //velikost 
				dayLabell[j][i] = new JButton(" ");
				dayLabell[j][i].setFont(dayFont);
                                dayLabell[j][i].setBackground(Color.GRAY);
				dayLabell[j][i].setHorizontalAlignment(JButton.CENTER);
				dayPanel.add(dayLabell[j][i], BorderLayout.CENTER);
				panel.add(dayPanel);
			}
		}
		return panel;
	}
	public void updateDayLabels() {
		int month = monthComboBox.getSelectedIndex();
		int day = valueOf(dayField.getText().trim());
		int year = valueOf(yearField.getText().trim());
		if (year > 0 && day > 0) {
			calendarDate = LocalDate.of(year, month + 1, day );
			LocalDate monthDate = getPreviousSunday(year, month);
			fillDays(monthDate, year, month, day);	
			String title = MONTH_NAMES[month] + " " + year;
			titleLabel.setText(title);
		} 
	}
        //vyplnìní do správných okýn
	private LocalDate getPreviousSunday(int year, int month) {
		LocalDate monthDate = LocalDate.of(year, month + 1, 1);
		int weekday = monthDate.getDayOfWeek().ordinal();
		if (weekday < 6) {
			monthDate = monthDate.minusDays((long) (weekday  ));
		}
		return monthDate;
	}
        
        //vyplnìní okýnek datumem
	private void fillDays(LocalDate monthDate, int year, int month, int day) {
		for (int j = 0; j < dayLabell.length; j++) {
			for (int i = 0; i < dayLabell[j].length; i++) {
				int calMonth = monthDate.getMonth().ordinal();
				int calYear = monthDate.getYear();
				dayLabell[j][i].getParent().setBackground(backgroundColor);
				dayLabell[j][i].setText("");
				if (year == calYear && month == calMonth) {
					int calDay = monthDate.getDayOfMonth();
					dayLabell[j][i].setText(Integer.toString(calDay));
					if (day == calDay) {
						dayLabell[j][i].getParent().setBackground(Color.YELLOW);
					} 
				}
				monthDate = monthDate.plusDays(1L);
			}
		}
	}
	public int valueOf(String text) {
		try {
			return Integer.valueOf(text);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public class CalendarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			updateDayLabels();
		}
}
}