package schedule_updater;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class InsertDataIntoDB {
	
	/** убивает таблицу Rings*/
	public void dropTableRings(){
	try{
		Class.forName(Generator.DRIVER).newInstance();
	}catch (InstantiationException e){
		e.printStackTrace();
	}catch (IllegalAccessException e){
		e.printStackTrace();
	}catch (ClassNotFoundException e){
			e.printStackTrace();
	}
	//clear table rings
	try(Connection connection = DriverManager.getConnection(Generator.CONNECTION, Generator.DB_USER, Generator.DB_PASS);
            Statement statement = connection.createStatement();
			){
			
            	statement.executeUpdate("truncate table rings");
            	System.out.print("Clear table Rings \n");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void dropTableSchedule(){
		try{
			Class.forName(Generator.DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		//clear table rings
		try(Connection connection = DriverManager.getConnection(Generator.CONNECTION, Generator.DB_USER, Generator.DB_PASS);
	            Statement statement = connection.createStatement();
				){
				
	            	statement.executeUpdate("truncate table Schedule");
	            	System.out.print("Clear table Schedule \n");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	/** убивает таблицу TableTimetable*/
	public void dropTableTimetable(){
	try{
		Class.forName(Generator.DRIVER).newInstance();
	}catch (InstantiationException e){
		e.printStackTrace();
	}catch (IllegalAccessException e){
		e.printStackTrace();
	}catch (ClassNotFoundException e){
			e.printStackTrace();
	}
	//clear table rings
	String createTable = "CREATE TABLE `timetable` ( " +
			"`Teacher` VARCHAR(200) NULL DEFAULT NULL," +
			"`WeekNumber` INT(11) NULL DEFAULT NULL," +
			"`DayNumber` INT(11) NULL DEFAULT NULL," +
			"`DayName` VARCHAR(50) NULL DEFAULT NULL," +
			"`LessonNumber` INT(11) NULL DEFAULT NULL," +
			"`ClassSubject` VARCHAR(500) NULL DEFAULT NULL," +
			"`ShortClassSubject` VARCHAR(50) NULL DEFAULT NULL," +
			"`Group` VARCHAR(50) NULL DEFAULT NULL," +
			"`Room` VARCHAR(50) NULL DEFAULT NULL" +
		")"+
		"COLLATE='utf8_general_ci'"+
		"ENGINE=InnoDB;";
	try(Connection connection = DriverManager.getConnection(Generator.CONNECTION, Generator.DB_USER, Generator.DB_PASS);
            Statement statement = connection.createStatement()){
			
            	statement.executeUpdate("drop table timetable");
            	statement.execute(createTable);
            	System.out.print("Clear table Rings \n");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/** заполняет таблицу Rings*/
	public void insertRings(int number, String beginTime, String endTime){
		try{
			Class.forName(Generator.DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		
		try(Connection connection = DriverManager.getConnection(Generator.CONNECTION, Generator.DB_USER, Generator.DB_PASS);
	            Statement statement = connection.createStatement()){
				   	statement.executeUpdate("insert into rings values ("+number+", '"+beginTime+"', '"+endTime+"')");
	            	System.out.print("\n This data already in base... \n");
			}catch(SQLException e){
				e.printStackTrace();
			}
	}
	/** заполняет таблицу Timetable*/
	public void insertTimetable(String teacherName, int weekNumber, int dayNumber, String dayName, int lessonNumber, String classSubject, String shortClassSubject, String group, String room) throws UnsupportedEncodingException{
		Properties properties=new Properties();
        properties.setProperty("user",Generator.DB_USER);
        properties.setProperty("password",Generator.DB_PASS);
        /*
          настройки указывающие о необходимости конвертировать данные из Unicode
	  в UTF-8, который используется в нашей таблице для хранения данных
        */
        properties.setProperty("useUnicode","true");
        properties.setProperty("charSet","UTF-8");
		/**/
		try{
			Class.forName(Generator.DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		
		
		try(Connection connection = DriverManager.getConnection(Generator.CONNECTION, properties);
	            Statement statement = connection.createStatement()){
				   	statement.executeUpdate("insert into timetable (Teacher,WeekNumber,DayNumber,DayName,LessonNumber,ClassSubject,ShortClassSubject,`Group`,Room) values ('"+teacherName+"', "+ weekNumber + ", "+ dayNumber + ", '"+ dayName +"', "+ lessonNumber +", '" + classSubject+ "', '" + shortClassSubject +"', '" + group +"', '" + room + "')");
	            	
				   	System.out.print("\n This data already in base... \n");
			}catch(SQLException e){
				e.printStackTrace();
			}
	}
	
}
