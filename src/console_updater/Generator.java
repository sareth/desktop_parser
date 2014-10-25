package console_updater;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Generator extends JFrame {

	private static final long serialVersionUID = 1L;
	static String DB_ADDRESS = "//46.61.143.135/denchik";
	static String DB_USER = "denchik";
	static String DB_PASS = "E4cfYcvZA7pLpD9z";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String CONNECTION = "jdbc:mysql:" + DB_ADDRESS + "?useUnicode=true&characterEncoding=utf8";
	public static ArrayList<TimeTable> timetable = new ArrayList<TimeTable>();
	public static ArrayList<Rings> rings = new ArrayList<Rings>();
	public static int value = 0;
	public final static JProgressBar progressBar = new JProgressBar();
	static JButton startButton = new JButton("Start");
	static private int BOR = 10;

	public Generator() {
		final JFrame frame = new JFrame("Schedules generator");
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(BOR, BOR, BOR, BOR));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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
		panel.add(Box.createVerticalGlue());

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
		                Object[] options = { "Да", "Нет!" };
		                int n = JOptionPane
		                        .showOptionDialog(event.getWindow(), "Программа не закончила обработку данных, вы уверены, что хотите выйти?",
		                                "Подтверждение", JOptionPane.YES_NO_OPTION,
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
				startButton.setEnabled(false);
				startButton.setFocusable(false);
				progressBar.setString("Preparing data...");
				try {
					insertingData();
					StatusLineTimer slt = new StatusLineTimer();
					slt.start();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});
		buttonsPanel.add(startButton);
		panel.add(buttonsPanel);

		panel.add(Box.createVerticalGlue());
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		frame.setPreferredSize(new Dimension(260, 225));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void insertingData() throws SQLException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Class.forName(DRIVER).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				GregorianCalendar c = new GregorianCalendar();
				c.set(2014, 8, 1, 0, 0, 0);
				GregorianCalendar endcal = new GregorianCalendar();
				endcal.set(2014, 12, 31, 0, 0, 0);
				Date endDate = endcal.getTime();
				// int year, int month, int date, int hour, int minute, int
				// second
				try (Connection connection = DriverManager.getConnection(CONNECTION, DB_USER, DB_PASS);
						Statement statement = connection.createStatement()) {
					String queryRings = "select * from rings";
					ResultSet rsRings = statement.executeQuery(queryRings);

					while (rsRings.next()) {
						int lnumber = rsRings.getInt("number");
						Date beginTime = rsRings.getTime("begin_time");
						Date endTime = rsRings.getTime("end_time");

						// add results
						Rings ringsObj = new Rings(lnumber, beginTime, endTime);
						rings.add(ringsObj);

					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try (Connection connection = DriverManager.getConnection(CONNECTION, DB_USER, DB_PASS);
						Statement statement = connection.createStatement()) {
					// our SQL SELECT query.
					// if you only need a few columns, specify them by name
					// instead of
					// using "*"
					String query = "select * from timetable";
					ResultSet rsTimeTable = statement.executeQuery(query);

					// while (c.before(endDate)) {

					for (int i = 0; i <= 122; i++) {
						// String datetime = String.valueOf(i);
						Date datetime = c.getTime();

						rsTimeTable.first();
						while (rsTimeTable.next()) {

							int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
							int day = c.get(Calendar.DAY_OF_MONTH);
							int year = c.get(Calendar.YEAR);
							int month = c.get(Calendar.MONTH);
							int weekNumberCurrent = c.get(Calendar.WEEK_OF_MONTH);
							if (weekNumberCurrent % 2 == 0) {
								weekNumberCurrent = 2;
							} else {
								weekNumberCurrent = 1;
							}

							Date currentLessonBegins = null;
							Date currentLessonEnds = null;

							String teacher = rsTimeTable.getString("Teacher");
							int weekNumber = rsTimeTable.getInt("WeekNumber");
							int dayNumber = rsTimeTable.getInt("DayNumber");
							String dayName = rsTimeTable.getString("DayName");
							int lessonNumber = rsTimeTable.getInt("LessonNumber");
							String classSubject = rsTimeTable.getString("ClassSubject");
							String group = rsTimeTable.getString("Group");
							String room = rsTimeTable.getString("Room");

							GregorianCalendar currentLessonCal = new GregorianCalendar();
							currentLessonCal = c;

							int BeginTimeHour = rings.get(lessonNumber - 1).getBegin().getHours();
							int BeginTimeMinute = rings.get(lessonNumber - 1).getBegin().getMinutes();
							int EndTimeHour = rings.get(lessonNumber - 1).getEnd().getHours();
							int EndTimeMinute = rings.get(lessonNumber - 1).getEnd().getMinutes();

							currentLessonCal.set(year, month, day, BeginTimeHour, BeginTimeMinute, 0);
							currentLessonBegins = currentLessonCal.getTime();
							currentLessonCal.set(year, month, day, EndTimeHour, EndTimeMinute, 0);
							currentLessonEnds = currentLessonCal.getTime();

							// add results
							if (dayOfWeek == dayNumber) {
								if (weekNumberCurrent == weekNumber) {

									TimeTable timeTableObj = new TimeTable(teacher, weekNumber, dayNumber, dayName,
											lessonNumber, classSubject, group, room, currentLessonBegins,
											currentLessonEnds);
									timetable.add(timeTableObj);

								}
							}
						}
						c.add(Calendar.DAY_OF_MONTH, 1);

					}

					for (int i = 0; i <= timetable.size(); i++) {
						// System.out.print(timetable.get(i).getTeacher()+" lesson="+timetable.get(i).getLessonNumber()+" "+timetable.get(i).getDateBegins().toString()+"/"+timetable.get(i).getDateEnds().toString()+" weeknumber="+timetable.get(i).getWeekNumber()+"\n");
						value = (100 * i) / timetable.size();
						NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
						formatter.setMinimumFractionDigits(2);
						double j = (double) 100 * i / timetable.size();

						insertData(timetable.get(i));
						progressBar.setValue(value);
						progressBar.setString(formatter.format(j) + "%");
						if (i!=timetable.size()){
							startButton.setEnabled(false);
							startButton.setFocusable(false);
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * System.out.println(c.getTime()); int month1 =
				 * c.get(Calendar.MONTH); System.out.println(month1+1);
				 * c.add(Calendar.MONTH, 3); month1 = c.get(Calendar.MONTH);
				 * System.out.println(month1+1); c.set(2014, 8, 1, 0, 0, 0);
				 * System.out.println(c.getTime());
				 */

			}
		}).start();
	}

	public static void insertData(TimeTable item) throws SQLException {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(CONNECTION, DB_USER, DB_PASS);
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
					+ convertJavaDateToSqlDate(item.getDateEnds()) + "' " + " )";
			statement.executeUpdate(query);

		}
	}

	public static java.sql.Timestamp convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	class StatusLineTimer extends Thread {
		private Thread gettime;

		public void start() {
			gettime = new Thread(this);
			
			gettime.start();
			
		}

		public void run() {
			while (true) {
				try {
					gettime.sleep(1000);
				} catch (InterruptedException ie) {
					continue;
				}
				if (value > progressBar.getMaximum()) {
					progressBar.setValue(progressBar.getMaximum());
				} else {
					progressBar.setValue(value);
					if (value==timetable.size()){
						gettime.stop();
					}
					
				}

			}
		}
	}
}
