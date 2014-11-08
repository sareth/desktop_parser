package schedule_updater;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class GeneratorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	static String DB_ADDRESS = "//localhost/Schedule";
	static String DB_USER = "root";
	static String DB_PASS = "fyukbz17";
	final static String DRIVER = "com.mysql.jdbc.Driver";
	final static String CONNECTION = "jdbc:mysql:" + DB_ADDRESS
			+ "?useUnicode=true&characterEncoding=utf8";

	public static int value = 0;
	public final static JProgressBar progressBar = new JProgressBar();
	static JButton startButton = new JButton("Start");
	static private int BOR = 10;
	static JLabel labelStatus = new JLabel("Select");
	static Checkbox ringsadd = new Checkbox("Add rings in database");
	static Checkbox timetableadd = new Checkbox("Add schedule in database");
	static Checkbox scheduleadd = new Checkbox("Generate schedule");
	static JTextField dbaddress = new JTextField();
	static JTextField dbuser = new JTextField();
	static JPasswordField dbpass = new JPasswordField();
	public final static JFrame frame = new JFrame("Schedules generator");
	static JTextField pathToRings = new JTextField();
	static JTextField pathToTimeTable = new JTextField();
	public GeneratorFrame() {
		

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(BOR, BOR, BOR, BOR));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(labelStatus);

		panel.add(Box.createVerticalGlue());
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		int minimum = progressBar.getMinimum();
		if (value < minimum) {
			value = minimum;
		}
		// progressBar.setIndeterminate(true);
		progressBar.setValue(value);

		panel.add(progressBar);

		panel.add(Box.createHorizontalGlue());
		JPanel allPanel = new JPanel();
		allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalGlue());
		allPanel.add(ringsadd);
		panel.add(Box.createVerticalGlue());
		allPanel.add(timetableadd);
		panel.add(Box.createVerticalGlue());
		allPanel.add(scheduleadd);
		allPanel.add(Box.createHorizontalGlue());
		allPanel.add(new JLabel("Database address:"));
		allPanel.add(dbaddress);
		dbaddress.setText(DB_ADDRESS);
		allPanel.add(new JLabel("Database user:"));
		allPanel.add(dbuser);
		dbuser.setText(DB_USER);
		allPanel.add(new JLabel("Database password:"));
		dbpass.setEchoChar('*');
		allPanel.add(dbpass);
		dbpass.setText(DB_PASS);
		allPanel.add(new JLabel("Path to rings.xml"));
		allPanel.add(pathToRings);
		JButton buttonRings = new JButton("Find");
        buttonRings.setAlignmentX(CENTER_ALIGNMENT);
        allPanel.add(buttonRings);
        buttonRings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();             
                int ret = fileopen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    pathToRings.setText(file.getPath());
                }
            }
        });
        
        allPanel.add(new JLabel("Path to timetable.xml"));
        allPanel.add(pathToTimeTable);
        JButton buttonTime = new JButton("Find");
		buttonTime.setAlignmentX(CENTER_ALIGNMENT);
		allPanel.add(buttonTime);
		buttonTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();             
                int ret = fileopen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    pathToTimeTable.setText(file.getPath());
                }
            }
        });
        
		panel.add(allPanel);
		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		frame.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowListener() {

					public void windowActivated(WindowEvent event) {

					}

					public void windowClosed(WindowEvent event) {

					}

					public void windowClosing(WindowEvent event) {
						Object[] options = { "Yes", "No!!" };
						int n = JOptionPane.showOptionDialog(event.getWindow(),
								"Would you like do this?", "Shure?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
						if (n == 0) {
							event.getWindow().setVisible(false);
							System.exit(0);
						}
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				if ((dbuser.getText().isEmpty() == false)
						& (dbpass.getPassword().toString().isEmpty() == false)
						& (dbaddress.getText().isEmpty() == false)) {
					DB_USER = dbuser.getText();
					DB_PASS = new String(dbpass.getPassword());
					DB_ADDRESS = dbaddress.getText();
					if (InsertDataIntoDB.testConnection() == true) {
						startButton.setEnabled(false);
						startButton.setFocusable(false);
						progressBar.setString("Preparing data...");
						progressBar.setIndeterminate(true);
						StatusLineTimer slt = new StatusLineTimer();
						slt.start();
					}
					else{
						JOptionPane
						.showMessageDialog(frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");
					}
				} else {
					JOptionPane
							.showMessageDialog(frame,
									"Check, are you correctly input login, password and address of database?\n Some fields are clear");
				}

			}

		});
		buttonsPanel.add(startButton);
		panel.add(buttonsPanel);

		panel.add(Box.createVerticalGlue());
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		frame.setPreferredSize(new Dimension(300, 450));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void insertData(TimeTable item) throws SQLException {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException e) {
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		} catch (IllegalAccessException e) {
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		} catch (ClassNotFoundException e) {
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
		try (Connection connection = DriverManager.getConnection(CONNECTION,
				DB_USER, DB_PASS);
				Statement statement = connection.createStatement()) {

			String query = "insert into Schedule (Teacher,WeekNumber,DayNumber,DayName,LessonNumber,ClassSubject,`Group`,Room,LessonBeginTime,LessonEndTime) values"
					+ "( " + "'"
					+ item.getTeacher()
					+ "', "
					+ ""
					+ item.getWeekNumber()
					+ ", "
					+ ""
					+ item.getDayNumber()
					+ ", "
					+ "'"
					+ item.getDayName()
					+ "', "
					+ ""
					+ item.getLessonNumber()
					+ ", "
					+ "'"
					+ item.getClassSubject()
					+ "', "
					+ "'"
					+ item.getGroup()
					+ "', "
					+ "'"
					+ item.getRoom()
					+ "', "
					+ "'"
					+ convertJavaDateToSqlDate(item.getDateBegins())
					+ "', "
					+ "'"
					+ convertJavaDateToSqlDate(item.getDateEnds())
					+ "' "
					+ " )";
			statement.executeUpdate(query);

		}
	}

	public static java.sql.Timestamp convertJavaDateToSqlDate(
			java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	class StatusLineTimer extends Thread {
		private Thread gettime;

		public void start() {
			gettime = new Thread(this);

			gettime.start();

		}

		@SuppressWarnings({ "static-access", "deprecation" })
		public void run() {

			{
				try {
					Class.forName(DRIVER).newInstance();
				} catch (InstantiationException e) {
					JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
				} catch (IllegalAccessException e) {
					JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
				} catch (ClassNotFoundException e) {
					JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
				}

				// ������ ���� rings.xml

				if (ringsadd.getState() == true) {
					progressBar.setString("Reading XML rings");
					labelStatus.setText("Stage 1 - Reading XML rings");
					new ReadRings();
				}
				// ������ ���� data.xml
				if (timetableadd.getState() == true) {
					progressBar.setString("Reading XML timetable");
					labelStatus.setText("Stage 2 - Reading XML timetable");
					new ReadTimetable();
				}
				if (scheduleadd.getState() == true) {
					try {
						new GenerateSchedule();
						progressBar
								.setString("Adding data into table Schedule");
						labelStatus
								.setText("Stage 3 - adding data in schedule");
						progressBar.setIndeterminate(false);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
					}
				}
				progressBar.setString("Completed!");
				progressBar.setIndeterminate(false);
				progressBar.setValue(100);
				labelStatus.setText("Completed!");
				//
				startButton.setFocusable(true);
				startButton.setEnabled(true);
				gettime.stop();

				/*
				 * System.out.println(c.getTime()); int month1 =
				 * c.get(Calendar.MONTH); System.out.println(month1+1);
				 * c.add(Calendar.MONTH, 3); month1 = c.get(Calendar.MONTH);
				 * System.out.println(month1+1); c.set(2014, 8, 1, 0, 0, 0);
				 * System.out.println(c.getTime());
				 */

			}
		}
	}
}
