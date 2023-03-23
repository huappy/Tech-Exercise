import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

public class TaskDatabaseConnection{
	//initializes connection to none
	static Connection connection = null;
	// defines the set of methods the servlet uses to make its connection 
	static ServletContext servletContext;
	
	static void getConnection() {
		//Retrieves the connection
		System.out.println("-----------Connecting to MySQL JDBC----------------");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found...");
			e.printStackTrace();
			return;
		}
		//If JDBC Driver is found then we reinitialize the connection then try to make the connection
		System.out.println("MySQL JDBC Driver Found!");
		
		connection = null;
		try {
			//try to connect
			UtilTaskProp.loadProperty(servletContext);
			connection = DriverManager.getConnection(getURL(), getUserName(),getPassword());
		}
		catch (Exception e){
			//if it can't connect
			System.out.println("Connection Failed! Check output console");
		}
		//if the connection is made, tell the user
		if(connection != null) {
			System.out.println("Database connected!");
		}
		else {
			System.out.println("Failed to make connection!");
		}
	}
	
	//retrieves URL from property file
	static String getURL() {
		String url = UtilTaskProp.getProp("url");
		System.out.println("[DBG] URL: "+ url);
		return url;
	}
	
	//retrieves Username from property file
	static String getUserName() {
		String user = UtilTaskProp.getProp("user");
		System.out.println("[DBG] User: " + user);
		return user;
	}
	
	//retrieves Password from property file
	static String getPassword() {
		String password = UtilTaskProp.getProp("password");
		System.out.println("[DBG] password:" + password);
		return password;
	}
	
	public static void getConnection(ServletContext context) {
		servletContext = context;
		getConnection();
	}
	
	
}