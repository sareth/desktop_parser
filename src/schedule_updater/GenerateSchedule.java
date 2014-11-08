package schedule_updater;

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

public class GenerateSchedule {
	public static ArrayList<TimeTable> timetable = new ArrayList<TimeTable>();
	public static ArrayList<Rings> rings = new ArrayList<Rings>();

	public GenerateSchedule() throws SQLException {
		getData();
	}

	public static void getData() throws SQLException {
		GregorianCalendar c = new GregorianCalendar();
		c.set(2014, 8, 1, 0, 0, 0);
		GregorianCalendar endcal = new GregorianCalendar();
		endcal.set(2014, 12, 31, 0, 0, 0);
		Date endDate = endcal.getTime();
		// int year, int month, int date, int hour, int minute, int
		// second
		try (Connection connection = DriverManager.getConnection(
				GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
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

		try (Connection connection = DriverManager.getConnection(
				GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
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

					int BeginTimeHour = rings.get(lessonNumber - 1).getBegin()
							.getHours();
					int BeginTimeMinute = rings.get(lessonNumber - 1)
							.getBegin().getMinutes();
					int EndTimeHour = rings.get(lessonNumber - 1).getEnd()
							.getHours();
					int EndTimeMinute = rings.get(lessonNumber - 1).getEnd()
							.getMinutes();

					currentLessonCal.set(year, month, day, BeginTimeHour,
							BeginTimeMinute, 0);
					currentLessonBegins = currentLessonCal.getTime();
					currentLessonCal.set(year, month, day, EndTimeHour,
							EndTimeMinute, 0);
					currentLessonEnds = currentLessonCal.getTime();

					// add results
					if (dayOfWeek == dayNumber) {
						if (weekNumberCurrent == weekNumber) {

							TimeTable timeTableObj = new TimeTable(teacher,
									weekNumber, dayNumber, dayName,
									lessonNumber, classSubject, group, room,
									currentLessonBegins, currentLessonEnds);
							timetable.add(timeTableObj);

						}
					}
				}
				c.add(Calendar.DAY_OF_MONTH, 1);

			}
		}
	}
	public static void insertSchedule() throws SQLException{
		for (int i = 0; i <= timetable.size(); i++) {
			// System.out.print(timetable.get(i).getTeacher()+" lesson="+timetable.get(i).getLessonNumber()+" "+timetable.get(i).getDateBegins().toString()+"/"+timetable.get(i).getDateEnds().toString()+" weeknumber="+timetable.get(i).getWeekNumber()+"\n");
			GeneratorFrame.value = (100 * i) / timetable.size();
			NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
			formatter.setMinimumFractionDigits(2);
			double j = (double) 100 * i / timetable.size();

			GeneratorFrame.insertData(timetable.get(i));
			GeneratorFrame.progressBar.setValue(GeneratorFrame.value);
			GeneratorFrame.progressBar.setString(formatter.format(j) + "%");
			if (i!=timetable.size()){
				GeneratorFrame.startButton.setEnabled(false);
				GeneratorFrame.startButton.setFocusable(false);
			}
			else
			{
				GeneratorFrame.startButton.setEnabled(true);
				GeneratorFrame.startButton.setFocusable(true);
				GeneratorFrame.startButton.setText("100%");
			}
		}
	}
}
