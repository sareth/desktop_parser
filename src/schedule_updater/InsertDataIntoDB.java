package schedule_updater;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class InsertDataIntoDB {
	
	public static boolean testConnection(){
		
		try{
			Class.forName(GeneratorFrame.DRIVER).newInstance();
		}catch (InstantiationException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (IllegalAccessException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (ClassNotFoundException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
		try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
	            Statement statement = connection.createStatement();
				){
				
	            	statement.executeQuery("select * from rings limit 1");
	            	
			}catch(SQLException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
				return false;
			}
		
		return true;
		
	}
	public void dropTableRings(){
	try{
		Class.forName(GeneratorFrame.DRIVER).newInstance();
	}catch (InstantiationException e){
		JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
	}catch (IllegalAccessException e){
		JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
	}catch (ClassNotFoundException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
	}
	//clear table rings
	try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
            Statement statement = connection.createStatement();
			){
			
            	statement.executeUpdate("truncate table rings");
            	System.out.print("Clear table Rings \n");
		}catch(SQLException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
	}
	public void dropTableSchedule(){
		try{
			Class.forName(GeneratorFrame.DRIVER).newInstance();
		}catch (InstantiationException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (IllegalAccessException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (ClassNotFoundException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
		//clear table rings
		try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
	            Statement statement = connection.createStatement();
				){
				
	            	statement.executeUpdate("truncate table Schedule");
	            	System.out.print("Clear table Schedule \n");
			}catch(SQLException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
			}
		}
	
	public void dropTableTimetable(){
	try{
		Class.forName(GeneratorFrame.DRIVER).newInstance();
	}catch (InstantiationException e){
		JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
	}catch (IllegalAccessException e){
		JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
	}catch (ClassNotFoundException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
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
	try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
            Statement statement = connection.createStatement()){
			
            	statement.executeUpdate("drop table timetable");
            	statement.execute(createTable);
            	System.out.print("Clear table Rings \n");
		}catch(SQLException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
	}
	/** ��������� ������� Rings*/
	public void insertRings(int number, String beginTime, String endTime){
		try{
			Class.forName(GeneratorFrame.DRIVER).newInstance();
		}catch (InstantiationException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (IllegalAccessException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (ClassNotFoundException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
		
		try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, GeneratorFrame.DB_USER, GeneratorFrame.DB_PASS);
	            Statement statement = connection.createStatement()){
				   	statement.executeUpdate("insert into rings values ("+number+", '"+beginTime+"', '"+endTime+"')");
	            	System.out.print("\n This data already in base... \n");
			}catch(SQLException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
			}
	}
	/** ��������� ������� Timetable*/
	public void insertTimetable(String teacherName, int weekNumber, int dayNumber, String dayName, int lessonNumber, String classSubject, String shortClassSubject, String group, String room) throws UnsupportedEncodingException{
		Properties properties=new Properties();
        properties.setProperty("user",GeneratorFrame.DB_USER);
        properties.setProperty("password",GeneratorFrame.DB_PASS);
        /*
          ��������� ����������� � ������������� �������������� ������ �� Unicode
	  � UTF-8, ������� ������������ � ����� ������� ��� �������� ������
        */
        properties.setProperty("useUnicode","true");
        properties.setProperty("charSet","UTF-8");
		/**/
		try{
			Class.forName(GeneratorFrame.DRIVER).newInstance();
		}catch (InstantiationException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (IllegalAccessException e){
			JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}catch (ClassNotFoundException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
		}
		
		
		try(Connection connection = DriverManager.getConnection(GeneratorFrame.CONNECTION, properties);
	            Statement statement = connection.createStatement()){
				   	statement.executeUpdate("insert into timetable (Teacher,WeekNumber,DayNumber,DayName,LessonNumber,ClassSubject,ShortClassSubject,`Group`,Room) values ('"+teacherName+"', "+ weekNumber + ", "+ dayNumber + ", '"+ dayName +"', "+ lessonNumber +", '" + classSubject+ "', '" + shortClassSubject +"', '" + group +"', '" + room + "')");
	            	
				   	System.out.print("\n This data already in base... \n");
			}catch(SQLException e){
				JOptionPane
						.showMessageDialog(GeneratorFrame.frame,
								"Check, are you correctly input login, password and address of database? \n Connection error!");;
			}
	}
	
}
