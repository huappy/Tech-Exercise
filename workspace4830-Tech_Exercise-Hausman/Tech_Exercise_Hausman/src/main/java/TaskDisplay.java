import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TaskDisplay")
public class TaskDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public TaskDisplay() {
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
			
			// Set response content type
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();								
			String title = "Your tasks:";
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + 
					"<html>" + 
					"<head><title>" + title + "</title></head>" +
					"<style>table, th, td{"
					+ "	border: 1px solid purple;"+
					"padding: 20px"
					+ "}</style>"+
					"<body bgcolor=\"#f0f0f0\">" +
					"<h2>" + title + "</h2>" +
					"<table>" +
					"<tr>" +
					"<th>ID</th>" +
					"<th>Task</th>" +
					"<th>Date Added</th>" +
					"<th>Complete By</th>"
					);
			
			
			try {
				String selectSQL = "SELECT * FROM TaskList";
				// String thisUser = "user%";
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				// preparedStatement.setString(1, thisUser);
				ResultSet rs = preparedStatement.executeQuery();
				
				//TODO: Use this as the task display. Combine this loop with the content display stuff in InserTask. Probably will have to delete the getWriter statements below
				while (rs.next()) {
					String id = rs.getString("ID");
					String task = rs.getString("TASK");
					String date = rs.getString("DATE_CREATED");
					String complete_by = rs.getString("COMPLETE_BY");
					response.getWriter().append("<tr><td>  "+ id + "  </td>");
					response.getWriter().append("<td>  " + task + "  </td>");
					response.getWriter().append("<td>  " + date + "  </td>");
					response.getWriter().append("<td>  " + complete_by + "  </td>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//TODO: add buttons and labels to links

			out.println("</table><br><br>"
					+ "<form action='DeleteTask' method='POST'"
					+ "<label for=delete>Enter the ID of the task you wish to delete: </label>"
					+ "<input type='text' name= deleteID>   "
					+ "<input type='submit' value='submit'<br><br>"
				
					+ "<a href=http://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:8080/Tech_Exercise_Hausman/InsertTask.html> Insert Task</a> <br>");
			//TODO: make update button that deletes your old taks then takes you to the insert page
			
			out.println("</body></html>");

			
	}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
