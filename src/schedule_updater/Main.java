package schedule_updater;


import java.sql.SQLException;
import java.text.ParseException;


public class Main {

	
	public static void main(String[] args) throws ParseException, SQLException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Generator();
            }
        });	
		
	}

	
	

}


