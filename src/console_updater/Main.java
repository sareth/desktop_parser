package console_updater;



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



public class Main {
	static String DB_ADDRESS = "//46.61.143.135/denchik";
	static String DB_USER = "denchik";
	static String DB_PASS = "E4cfYcvZA7pLpD9z";
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final static String CONNECTION = "jdbc:mysql:"+DB_ADDRESS+"?useUnicode=true&characterEncoding=utf8";
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		ArrayList<Rings> ringslist = new ArrayList<Rings>();
		try(Connection connection = DriverManager.getConnection(CONNECTION, DB_USER, DB_PASS);
	            Statement statement = connection.createStatement()){
				// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "select * from rings";
		            		       
		      // execute the query, and get a java resultset
		      ResultSet rs = statement.executeQuery(query);
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
		}
		
		
	}
	

}
class Rings{
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
	
	/*Calendar rightNow = Calendar.getInstance();
		Date d = new Date();
		System.out.println(d);
		rightNow.setTime(d);
		rightNow.add(Calendar.HOUR, yourHours);
		d = rightNow.getTime();
		System.out.println(d);*/
	
	
	
	
	
}
