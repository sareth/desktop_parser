package console_updater;

/*import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main{
	final static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args){
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://46.61.143.135/denchik?useUnicode=true&characterEncoding=utf8", "denchik", "E4cfYcvZA7pLpD9z");
		selectAllLessons();
		Base.close();
	}
	@SuppressWarnings("unchecked")
	private static void selectAllLessons() {
        System.out.println("Java8 start>>>");
        timetable.findAll().stream().forEach(null);;
        System.out.println("Java8 end<<<");
        logger.info("Employees list: " + timetable.findAll());
    }
}*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class Main {
	static String DB_ADDRESS = "//46.61.143.135/denchik";
	static String DB_USER = "denchik";
	static String DB_PASS = "E4cfYcvZA7pLpD9z";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String CONNECTION = "jdbc:mysql:"+DB_ADDRESS+"?useUnicode=true&characterEncoding=utf8";
	
	public static void main(String[] args) throws ParseException, SQLException {
		// TODO Auto-generated method stub
		try{
			Class.forName(DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		
		ArrayList<TimeTable> timetable = new ArrayList<TimeTable>();
		GregorianCalendar c = new GregorianCalendar();
		c.set(2014, 8, 1, 0, 0, 0);
		//int year, int month, int date, int hour, int minute, int second
		try(Connection connection = DriverManager.getConnection(CONNECTION, DB_USER, DB_PASS);
	            Statement statement = connection.createStatement()){
				// our SQL SELECT query. 
				// if you only need a few columns, specify them by name instead of using "*"
		      String query = "select * from timetable";
		      ResultSet rs = statement.executeQuery(query);
		      
		      
		      for (int i=0;i<=3;i++){
		    	  //String datetime = String.valueOf(i);
		    	  Date datetime = c.getTime();
		    	  
		    	  rs.first();
		    	  while (rs.next())
			      {
			        
		    		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);  
		    		int day = c.get(Calendar.DAY_OF_MONTH);
		    		int year = c.get(Calendar.YEAR);
		    		int month = c.get(Calendar.MONTH);
		    		int weekNumberCurrent = c.get(Calendar.WEEK_OF_MONTH);
		    		if(weekNumberCurrent%2==0){
		    			weekNumberCurrent=2;
		    		}else
		    		{
		    			weekNumberCurrent=1;
		    		}
		    		
		    		Date currentLessonBegins = null;
		    		Date currentLessonEnds = null;
		    		
		    		String teacher = rs.getString("Teacher");
			       	int weekNumber = rs.getInt("WeekNumber");
			    	int dayNumber=rs.getInt("DayNumber");
			    	String dayName=rs.getString("DayName");
			    	int lessonNumber=rs.getInt("LessonNumber");
			    	String classSubject=rs.getString("ClassSubject");
			    	String group=rs.getString("Group");
			    	String room=rs.getString("Room");
			    	
			    	GregorianCalendar currentLessonCal = new GregorianCalendar();
			    	currentLessonCal = c;
			    	
			    	if (lessonNumber==1){
			    		currentLessonCal.set(year, month, day, 8, 0, 0);
			    		currentLessonBegins = currentLessonCal.getTime();
			    		currentLessonCal.add(Calendar.MINUTE, 90);
			    		currentLessonEnds = currentLessonCal.getTime();
			    	}
			    	
			        // add results
			        if(dayOfWeek==dayNumber){
			        	if(weekNumberCurrent==weekNumber){
				        	if(lessonNumber==1){
				        		
				        		TimeTable timeTableObj = new TimeTable(teacher, weekNumber, dayNumber, dayName, lessonNumber, classSubject, group, room, currentLessonBegins, currentLessonEnds);
				        		timetable.add(timeTableObj);
				        	}
			        	}
			        }
			      }
		    	  c.add(Calendar.DAY_OF_MONTH, 1);
		    	  
		      }
		      for(int i=0;i<timetable.size();i++){
		    	  System.out.print(timetable.get(i).getTeacher()+" "+timetable.get(i).getDateBegins().toString()+"/"+timetable.get(i).getDateEnds().toString()+" "+timetable.get(i).getWeekNumber()+"\n");
		      }
		      
		}
		
		System.out.println(c.getTime());
		int month1 = c.get(Calendar.MONTH);
		System.out.println(month1+1);
		c.add(Calendar.MONTH, 3);
		month1 = c.get(Calendar.MONTH);
		System.out.println(month1+1);
		c.set(2014, 8, 1, 0, 0, 0);
		System.out.println(c.getTime());
		
	}
	

}
/*class Rings{
	public Rings(int n, Date bt, Date et){
		this.beginTime = bt;
		this.endTime = et;
		this.Number = n;
	}
	
	int Number;
	Date beginTime;
	Date endTime;
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public Date getBeginTime() throws ParseException {
		String beginHour = String.valueOf(beginTime.getHours());
		String beginMinute = String.valueOf(beginTime.getMinutes()-1);
		String beginDate = beginHour+":"+beginMinute;
		Date dateBegin = new SimpleDateFormat("hh:mm").parse(beginDate);
		
		return dateBegin;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
		
	}
	public Date getEndTime() throws ParseException {
		String endHour = String.valueOf(endTime.getHours());
		String endMinute = String.valueOf(endTime.getMinutes()-1);
		String endDate = endHour+":"+endMinute;
		Date dateEnd = new SimpleDateFormat("hh:mm").parse(endDate);
		return dateEnd;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	Calendar rightNow = Calendar.getInstance();
		Date d = new Date();
		System.out.println(d);
		rightNow.setTime(d);
		rightNow.add(Calendar.HOUR, yourHours);
		d = rightNow.getTime();
		System.out.println(d);

}
*/


/*
 * 
 * // execute the query, and get a java resultset
		      
		      DateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
		      // iterate through the java resultset
		      
		      
		      while (rs.next())
		      {
		        int number = rs.getInt("number");
		        Date beginTime = rs.getTime("begin_time");
		        Date endTime = rs.getTime("end_time");
		        
		        // add results
		        Rings ringsObj = new Rings(number, beginTime, endTime);
		        ringslist.add(ringsObj);
		      }
		      	
	            	
			}catch(SQLException e){
				e.printStackTrace();
			}
		
		for(int i=0;i<ringslist.size();i++){
			System.out.print(ringslist.get(i).getNumber()+"\n");
			System.out.print(ringslist.get(i).getBeginTime()+"\n");
			System.out.print(ringslist.get(i).getEndTime()+"\n");
			Date endTimeD = ringslist.get(i).getEndTime();
			long longEndTime = endTimeD.getTime();
			Date d = new Date();
			
			d.setTime(longEndTime);
			System.out.println(d);
			
			Calendar rightNow = Calendar.getInstance();
			Date nowDate = rightNow.getTime();
			System.out.println(nowDate);
			int nowDateWithoutTime = nowDate.getDay();
			System.out.println(nowDateWithoutTime);
			d.setDate(nowDateWithoutTime);
			d.setTime(longEndTime);
			System.out.println(d);
			/*Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(d);
			rightNow.add(Calendar.HOUR, endTime);
			d = rightNow.getTime();
			System.out.println(d);*/
