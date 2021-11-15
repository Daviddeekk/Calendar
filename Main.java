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
	private static final String[] MONTH_NAMES = { "January  ", "February ", 
			"March    ", "April    ", "May      ", "June     ", "July     ",
			"August   ", "September", "October  ", "November ", "December  " };
	private Color backgroundColor;
	private JComboBox<String> monthComboBox;
	
        private JLabel[][] dayLabel;
        private JButton[][] dayLabell;
        
	private JLabel titleLabel;
        private JButton titleLabell;
	private JTextField dayField;
	private JTextField yearField;
	private LocalDate calendarDate;
        public String monthh;
        public int denn;
        
	public Main() {
		this.calendarDate = LocalDate.now();
                
                
	}
	@Override
	public void run() {
                Poznamky poznamky = new Poznamky();
		JFrame frame = new JFrame("Calendar");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(createTitlePanel(), BorderLayout.NORTH);
		frame.add(createCalendarPanel(), BorderLayout.CENTER);
		//frame.pack();
                frame.setSize(750, 750);
                frame.setResizable(false);
		//frame.setLocationByPlatform(true);
		frame.setVisible(true);
               
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
                                //èíslo mìsíce
				if (month > 11) {
					int year = Integer.valueOf(yearField.getText()) + 1;
					yearField.setText(Integer.toString(year));
					month = 0;
				}
				monthComboBox.setSelectedIndex(month);
				updateDayLabels();
                                
                                
			}
                        
		});
                        
                JButton print = new JButton("Print to pdf");
                
                print.setPreferredSize(new Dimension(150,40));
                
                
                
                 panel.add(previousMonthButton);
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
			dayPanel.setPreferredSize(new Dimension(20, 50));
			JLabel label = new JLabel(DAY_NAMES[i]);
			label.setFont(dayNamesFont);// font názvu dn?
                        
			label.setHorizontalAlignment(JLabel.CENTER);
			dayPanel.add(label, BorderLayout.CENTER);
			panel.add(dayPanel);
		}
		return panel;
	}
        //vytvo?ení okýnek
	private JPanel createDayLabels() {
		JPanel panel = new JPanel(new GridLayout(0, DAY_NAMES.length , 5, 5));
		dayLabell = new JButton[6][DAY_NAMES.length];
		Font dayFont = panel.getFont().deriveFont(48f).deriveFont(Font.BOLD); 
		for (int j = 0; j < dayLabell.length; j++) {
			for (int i = 0; i < dayLabell[j].length; i++) {
				JPanel dayPanel = new JPanel(new BorderLayout());
				//dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); //ohrani?ení
				dayPanel.setPreferredSize(new Dimension(50, 50)); //velikost 
				dayLabell[j][i] = new JButton(" ");
				dayLabell[j][i].setFont(dayFont);
                                dayLabell[j][i].setBackground(Color.LIGHT_GRAY);
                                dayLabell[j][i].setForeground(backgroundColor.GRAY);
				dayLabell[j][i].setHorizontalAlignment(JButton.CENTER);
				
                                
                                dayLabell[j][i].addActionListener(new ActionListener() {
                                @Override
                                  public void actionPerformed(ActionEvent e) {
                                    
                                      JButton buttonn = (JButton) e.getSource(); 
                                      String den = buttonn.getText();
                                      
                                  
                                 int year = valueOf(yearField.getText().trim());     
                                 monthh = (String) monthComboBox.getSelectedItem();
                                 int montt = monthComboBox.getSelectedIndex();
                                
                                 denn =Integer.parseInt(den); //int den
                               //String dayy = dayLabell[j][i].get();
                                
                                 
                               
                                System.out.println(denn);
                                // System.out.println(dayy);
                                System.out.println(monthh );
                                System.out.println(year);
                                Poznamky poznamky = new Poznamky();
                                poznamky.setVisible(true);
                                
                                }    
                                });  
                                dayPanel.add(dayLabell[j][i], BorderLayout.CENTER);
				panel.add(dayPanel);
			}
		}
                
		return panel;
	}
        //aktualizace datumu po p?epnutí
	public void updateDayLabels() {
		int month = monthComboBox.getSelectedIndex();
		int day = valueOf(dayField.getText().trim());
                
		int year = valueOf(yearField.getText().trim());
		if (year > 0 && day > 0) {
			calendarDate = LocalDate.of(year, month + 1, day );
                       //String xy =  calendarDate.toString();
                       //System.out.println(xy);
			LocalDate monthDate = getPreviousSunday(year, month);
			fillDays(monthDate, year, month, day);	
			String title = MONTH_NAMES[month] + " " + year;
			titleLabel.setText(title);
                      
		} 
	}
        //vypln?ní do správných okýnek
	private LocalDate getPreviousSunday(int year, int month) {
		LocalDate monthDate = LocalDate.of(year, month + 1, 1);
		int weekday = monthDate.getDayOfWeek().ordinal();
		if (weekday < 6) {
			monthDate = monthDate.minusDays((long) (weekday  ));
		}
		return monthDate;
	}
        
        //vypln?ní okýnek datumem
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