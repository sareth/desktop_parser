package schedule_updater;

import java.util.Date;

public class TimeTable {
	
	private String teacher;
	private int weekNumber;
	private int dayNumber;
	private String dayName;
	private int lessonNumber;
	private String classSubject;
	private String group;
	private String room;
	private Date dateBegins;
	private Date dateEnds;
	
	/**
	 * @param teacher
	 * @param weekNumber
	 * @param dayNumber
	 * @param dayName
	 * @param lessonNumber
	 * @param classSubject
	 * @param group
	 * @param room
	 * @param datetime
	 */
	public TimeTable(String teacher, int weekNumber, int dayNumber,
			String dayName, int lessonNumber, String classSubject,
			String group, String room, Date dateBegins, Date dateEnds) {
		super();
		this.teacher = teacher;
		this.weekNumber = weekNumber;
		this.dayNumber = dayNumber;
		this.dayName = dayName;
		this.lessonNumber = lessonNumber;
		this.classSubject = classSubject;
		this.group = group;
		this.room = room;
		this.dateBegins = dateBegins;
		this.dateEnds = dateEnds;
	}
	
	/**
	 * @return the dateBegins
	 */
	public Date getDateBegins() {
		return dateBegins;
	}

	/**
	 * @return the dateEnds
	 */
	public Date getDateEnds() {
		return dateEnds;
	}

	/**
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}
	/**
	 * @return the weekNumber
	 */
	public int getWeekNumber() {
		return weekNumber;
	}
	/**
	 * @return the dayNumber
	 */
	public int getDayNumber() {
		return dayNumber;
	}
	/**
	 * @return the dayName
	 */
	public String getDayName() {
		return dayName;
	}
	/**
	 * @return the lessonNumber
	 */
	public int getLessonNumber() {
		return lessonNumber;
	}
	/**
	 * @return the classSubject
	 */
	public String getClassSubject() {
		return classSubject;
	}
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}
	
	
	
	
	
	
	
}