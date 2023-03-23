import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TaskServlet
 */
@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       static String url = "jdbc:mysql://ec2-3-136-157-109.us-east-2.compute.amazonaws.com:3306/tech_exercise?useSSL=false";
       static String user = "Hap";
       static String password = "CJoy410";
       static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println("Connecting to MySQL...");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No MYSQL JDBC Driver Found");
			e.printStackTrace();
			return;
		}
		response.getWriter().println("Driver found");
		connection = null;
		
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Connection Failure");
			e.printStackTrace();
		}
		
		if (connection != null) {
			response.getWriter().println("Connection Succeeded!");
		} else {
			System.out.println("Connection Failure");
		}
		
		try {
			String selectSQL = "SELECT * FROM TaskList";
			// String thisUser = "user%";
			response.getWriter().println(selectSQL + "<br>");
			response.getWriter().println("-------------------------------------------------<br>");
			PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			// preparedStatement.setString(1, thisUser);
			ResultSet rs = preparedStatement.executeQuery();
			
			//TODO: Use this as the task display. Combine this loop with the content display stuff in InserTask. Probably will have to delete the getWriter statements below
			while (rs.next()) {
				String id = rs.getString("ID");
				String task = rs.getString("TASK");
				String date = rs.getString("DATE_CREATED");
				String complete_by = rs.getString("COMPLETE_BY");
				response.getWriter().append(id + ", ");
				response.getWriter().append("TASK: " + task + ", ");
				response.getWriter().append("DATE: " + date + ", ");
				response.getWriter().append("COMPLETE BY: " + complete_by + ".<br>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//TODO: Remember to add the links to the other pages as well

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
