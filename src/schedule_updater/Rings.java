package schedule_updater;

import java.util.Date;

public class Rings {
	private int lessonNumber;
	private Date begin;
	private Date end;
	
	public int getLessonNumber() {
		return lessonNumber;
	}
	public Date getBegin() {
		return begin;
	}
	public Date getEnd() {
		return end;
	}
	public Rings(int lessonNumber, Date begin, Date end) {
		
		this.lessonNumber = lessonNumber;
		this.begin = begin;
		this.end = end;
	}
	
	
	
	
}
