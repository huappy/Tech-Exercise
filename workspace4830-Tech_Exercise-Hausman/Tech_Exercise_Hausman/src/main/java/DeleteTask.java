import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DeleteTask")
public class DeleteTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public DeleteTask() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
			
			//Set connection and attempt to create table
			TaskDatabaseConnection.getConnection(getServletContext());
			connection = TaskDatabaseConnection.connection;
			Statement state;
			try {
				state = connection.createStatement();
			
			String del = request.getParameter("deleteID");
			String deleteSQL = "DELETE FROM TaskList where id="+del;
			state.executeUpdate(deleteSQL);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();	
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + 
			"<html>" + 
			"<head><title> Successfully Deleted!</title></head>\n" +
			"<body bgcolor=\"#f0f0f0\">\n" +
			"<h2 align=\"center\">Successfully Deleted!</h2><br><br>"
			+ "<h2 align=\"center\"><a href=http://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:8080/Tech_Exercise_Hausman/TaskDisplay>Return to Task List</a> "
			+ "</h2><br>"); 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}

