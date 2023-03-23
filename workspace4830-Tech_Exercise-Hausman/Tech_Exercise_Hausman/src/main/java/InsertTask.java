

/**
* @file InsertTask
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/InsertTask")
public class InsertTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public InsertTask() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			//Set connection and attempt to create table
			TaskDatabaseConnection.getConnection(getServletContext());
			connection = TaskDatabaseConnection.connection;
			Statement state = connection.createStatement();
			String createTableSQL = "CREATE TABLE TaskList " +
								"(id INTEGER not NULL AUTO_INCREMENT, " +
								"TASK VARCHAR(500) not NULL, " +
								"DATE_CREATED VARCHAR(50) not NULL, " +
								"COMPLETE_BY VARCHAR(50) not NULL, " +
								"PRIMARY KEY ( id ))";
			state.executeUpdate(createTableSQL);
		} catch (SQLException e) {
			System.out.print("Table already exists.\n");		}
		
		//Retrieve information from the task form
		
		String task = request.getParameter("task");
		String date = request.getParameter("date");
		String complete_by = request.getParameter("complete_by");
				
		//define SQL operation 
		String insertSql = " INSERT INTO TaskList (id, TASK, DATE_CREATED, COMPLETE_BY) values (default, ?, ?, ?)";
		if(task != "" && date != "" && complete_by != "") {
		try {
			
			//pairs data to be inserted
			PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
			preparedStatement.setString(1, task);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, complete_by);
			preparedStatement.execute();
			System.out.print("Task Stored!\n");
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();								
		String title = "Task Added!";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + 
				"<html>" + 
				"<head><title>" + title + "</title></head>\n" +
				"<body bgcolor=\"#f0f0f0\">\n" +
				"<h2 align=\"center\">" + title + "</h2>\n" +
				"<ul>\n" +
				
            "  <li><b>Task</b>: " + task + "\n" + //
            "  <li><b>Date Added:</b>: " + date + "\n" + //
            "  <li><b>Complete By</b>: " + complete_by + "\n" +
				
				
			"</ul>\n");
		
		//TODO: add buttons and labels to links
		out.println("<a href=http://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:8080/Tech_Exercise_Hausman/TaskDisplay>Show Task List</a> <br>");
		//TODO: make update button that deletes your old taks then takes you to the insert page
		
		out.println("</body></html>");}
		else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();								
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + 
					"<html>" + 
					"<head><title>ERROR</title></head>\n" +
					"<h2 align=\"center\">Error, no task entered</h2><br><br>"
							+ "<h3 align=\"center\"><a href=http://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:8080/Tech_Exercise_Hausman/InsertTask.html>Return to Insert</a><br><br>"
							+ "<h3 align=\"center\"><a href=http://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:8080/Tech_Exercise_Hausman/TaskDisplay>Show Task List</a>"
							+ "</h3><br>");
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}