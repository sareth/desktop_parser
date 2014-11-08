package schedule_updater;
import org.jdom2.*;
import org.jdom2.input.*;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ReadTimetable {
	public ReadTimetable(){
		
		InsertDataIntoDB db = new InsertDataIntoDB();
		
		db.dropTableTimetable();
		
		try {
			
			SAXBuilder parser = new SAXBuilder();
			
		    FileReader fr = new FileReader(GeneratorFrame.pathToTimeTable.getText());
		    
		    Document rDoc = parser.build(fr);
		    
		    //parse Root element
		    System.out.println("Root node of timetable: "+rDoc.getRootElement().getName());
		    //List child elements of root 
		    Namespace ns=rDoc.getRootElement().getNamespace();
		    
		    List<Element> classxml = rDoc.getRootElement().getChildren("teacher",ns);
		    System.out.print(classxml.size());
			    for (int i = 0; i < classxml.size(); ++i) {
			    	System.out.print(classxml.get(i).getName() + " ");
			        
			    	String teacherName = classxml.get(i).getAttributeValue("name");
			    	System.out.print(teacherName+ " \n");
			    	 	
			    	List<Element> weekxml = classxml.get(i).getChildren("week");
			    	
			    	for (int j =0; j<weekxml.size(); ++j){
			    		
			    		int weekNumber = Integer.valueOf(weekxml.get(j).getAttributeValue("number"));
			    		System.out.print(weekNumber+ " \n");
			    		
			    		
			    		List<Element> dayxml = weekxml.get(j).getChildren("day");
			    		for (int f =0; f<dayxml.size(); ++f){
			    			
			    			int dayNumber = Integer.valueOf(dayxml.get(f).getAttributeValue("number"));
			    			
			    			String dayName = dayxml.get(f).getAttributeValue("name");
			    			
			    			System.out.print(dayNumber + " " + dayName + " \n");
			    			
			    			List<Element> lessonxml = dayxml.get(f).getChildren("lesson");
			    				
			    				for (int t=0; t<lessonxml.size();++t){
			    					
			    					int lessonNumber = Integer.valueOf(lessonxml.get(t).getAttributeValue("number"));
					    			System.out.print("		lnum" + lessonNumber + " ");
					    			
					    			List<Element> groupxml = lessonxml.get(t).getChildren("group");
					    				for (int l=0;l<groupxml.size();++l){
					    					
					    					String subjectName = groupxml.get(l).getAttributeValue("subject");
					    					
					    					String shortSubjectName = groupxml.get(l).getAttributeValue("short_subject");
					    					
					    					String room = groupxml.get(l).getAttributeValue("cabinet");
					    					
					    					String classGroup = groupxml.get(l).getAttributeValue("class"); 
					    					System.out.print("sub " + subjectName + " " + shortSubjectName + " cab." + room + " gr." + classGroup);
					    					if (groupxml.size()>1&l==0) System.out.print(" \t ");
					    					
					    					int value = (100 * i) / classxml.size();
					    					NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
					    					formatter.setMinimumFractionDigits(2);
					    					double stringProgressValue = (double) 100 * i / classxml.size();
					    					GeneratorFrame.progressBar.setIndeterminate(false);
					    					GeneratorFrame.progressBar.setValue(value);
					    					GeneratorFrame.progressBar.setString(formatter.format(stringProgressValue) + "%");
					    					
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
		
	}
}
