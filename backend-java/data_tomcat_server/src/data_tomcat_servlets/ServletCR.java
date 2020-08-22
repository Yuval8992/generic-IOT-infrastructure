package data_tomcat_servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/companies")

public class ServletCR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
       
    public ServletCR() throws SQLException {}
    
    @Override
    public void init() throws ServletException {
    	try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost", "root", "");       
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("got doGet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request instanceof HttpServletRequest) {
			 String url = (request).getRequestURL().toString();
			 String queryString = (request).getQueryString();
			}
		String str = new BufferedReader(new InputStreamReader(request.getInputStream()))
				.lines().collect(Collectors.joining("\n"));
		System.out.println("got do post");
			try {
				JSONObject outerJason = new JSONObject(str);
				String companyDB = outerJason.getString("company_name") + outerJason.getString("company_id");
				statement.execute("CREATE DATABASE " + companyDB);
				statement.execute("USE " + companyDB);
				
				statement.execute("CREATE TABLE products "
						+ "(product_instance_id INT NOT NULL AUTO_INCREMENT, "
						+ "product_type_id VARCHAR(40), "
						+ "serial_number VARCHAR(40), "
						+ "user_id VARCHAR(40), "
						+ "PRIMARY KEY (product_instance_id));");
				
				statement.execute("CREATE TABLE end_users "
						+ "(user_id INT NOT NULL AUTO_INCREMENT, "
						+ "first_name VARCHAR(40), "
						+ "last_name VARCHAR(40), "
						+ "email VARCHAR(40), "
						+ "phone VARCHAR(40), "
						+ "location VARCHAR(40), "
						+ "PRIMARY KEY (user_id));");
				
			} catch (JSONException | SQLException e) {response.setStatus(400);
			System.out.println("got catch");}	
	}
}

