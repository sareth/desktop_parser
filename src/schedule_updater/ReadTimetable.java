package schedule_updater;
import org.jdom2.*;
import org.jdom2.input.*;

import java.io.*;
import java.util.List;


public class ReadTimetable {
	public ReadTimetable(){
		//создаем новый объект для записи в базу
		InsertDataIntoDB db = new InsertDataIntoDB();
		//чистим таблицу в базе для записи новых данных
		db.dropTableTimetable();
		
		try {
			//создаем парсер
			SAXBuilder parser = new SAXBuilder();
			//получаем файлик
			//TODO:читать адрес файлика из конфига или сделать диалог выбора файла
		    FileReader fr = new FileReader("data.xml");
		    //получаем док
		    Document rDoc = parser.build(fr);
		    //parse Root element
		    System.out.println("Root node of timetable: "+rDoc.getRootElement().getName());
		    //List child elements of root 
		    Namespace ns=rDoc.getRootElement().getNamespace();
		    //сохраняем в лист все классы
		    List<Element> classxml = rDoc.getRootElement().getChildren("teacher",ns);
		    System.out.print(classxml.size());
			    for (int i = 0; i < classxml.size(); ++i) {
			    	System.out.print(classxml.get(i).getName() + " ");
			        //Получаем имя препода из атрибутов класса
			    	String teacherName = classxml.get(i).getAttributeValue("name");
			    	System.out.print(teacherName+ " \n");
			    	//сохраняем в лист все недели week из текущего класса	    	
			    	List<Element> weekxml = classxml.get(i).getChildren("week");
			    	
			    	for (int j =0; j<weekxml.size(); ++j){
			    		//получаем из атрибутов недели
			    		int weekNumber = Integer.valueOf(weekxml.get(j).getAttributeValue("number"));
			    		System.out.print(weekNumber+ " \n");
			    		
			    		//сохраняем все дни из ноды недель
			    		List<Element> dayxml = weekxml.get(j).getChildren("day");
			    		for (int f =0; f<dayxml.size(); ++f){
			    			//получаем номер дня
			    			int dayNumber = Integer.valueOf(dayxml.get(f).getAttributeValue("number"));
			    			//получаем номер недели
			    			String dayName = dayxml.get(f).getAttributeValue("name");
			    			
			    			System.out.print(dayNumber + " " + dayName + " \n");
			    			//сохраняем в лист все лессоны из дня
			    			List<Element> lessonxml = dayxml.get(f).getChildren("lesson");
			    				
			    				for (int t=0; t<lessonxml.size();++t){
			    					//получаем номер лессона из атрибутов
			    					int lessonNumber = Integer.valueOf(lessonxml.get(t).getAttributeValue("number"));
					    			System.out.print("		lnum" + lessonNumber + " ");
					    			//сохраняем в лист все записи о занятиях (как долго мы сюда шли) из лессона
					    			List<Element> groupxml = lessonxml.get(t).getChildren("group");
					    				for (int l=0;l<groupxml.size();++l){
					    					//получаем из атрибутов имя предмета
					    					String subjectName = groupxml.get(l).getAttributeValue("subject");
					    					//получаем из атрибутов сокр. имя предмета
					    					String shortSubjectName = groupxml.get(l).getAttributeValue("short_subject");
					    					//получаем из атрибутов кабинет
					    					String room = groupxml.get(l).getAttributeValue("cabinet");
					    					//получаем из атрибутов группу
					    					String classGroup = groupxml.get(l).getAttributeValue("class"); 
					    					System.out.print("sub " + subjectName + " " + shortSubjectName + " cab." + room + " gr." + classGroup);
					    					if (groupxml.size()>1&l==0) System.out.print(" \t ");
					    					//влупливаем все в базу
					    					db.insertTimetable(teacherName, weekNumber, dayNumber, dayName, lessonNumber, subjectName, shortSubjectName, classGroup, room);
					    					
					    				}
					    				
					    			System.out.print("\n");	
					    			groupxml.clear();
			    				}
			    				
			    				
			    		}
			    	}
			        
			    }    
		    }
		catch (Exception ex) {
			System.out.println(ex.getMessage());
	    }
	db.dropTableSchedule();	
	}
}
