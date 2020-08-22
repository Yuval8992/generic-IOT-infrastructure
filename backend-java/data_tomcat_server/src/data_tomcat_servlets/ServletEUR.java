package data_tomcat_servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

@WebServlet("/end_users")

public class ServletEUR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
       
    public ServletEUR() throws SQLException {}
    
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = new BufferedReader(new InputStreamReader(request.getInputStream()))
				.lines().collect(Collectors.joining("\n"));
		try {
			JSONObject outerJason = new JSONObject(str);		
			JSONObject innerJason = outerJason.getJSONObject("data");
			String companyDB = outerJason.getString("company_name") + outerJason.getString("company_id");

			statement.execute("USE " + companyDB);
			
			ResultSet isEmailExist = statement.executeQuery("SELECT COUNT(*) as count FROM end_users where email = " + "'" + innerJason.getString("email") + "'");

			if (isEmailExist.next()) {
				if (isEmailExist.getInt("count") == 0) {
					statement.executeUpdate("INSERT INTO end_users (first_name, last_name, email, phone, location) VALUES ("
							+ "'" + innerJason.getString("first_name") + "'" + "," 
							+ "'" + innerJason.getString("last_name") + "'" + ","
							+ "'" + innerJason.getString("email") + "'" + ","
							+ "'" + innerJason.getString("phone") + "'" + "," 
							+ "'" + innerJason.getString("location") +  "'" 
							+ " );");			
				}
			}
				
			ResultSet getUserID = statement.executeQuery("SELECT user_id FROM end_users where email = " + "'" + innerJason.getString("email") + "'");
			String userID = null;
			if (getUserID.next()) {
				 userID = getUserID.getString("user_id");
			}
			
			statement.executeUpdate("INSERT INTO products (product_type_id, serial_number, user_id) VALUES ("
					+ "'" +  innerJason.getString("product_type_id") + "'" + "," 
					+ "'" +  innerJason.getString("serial_number") + "'" + "," 
					+ "'" +  userID + "'" 
					+ " );");
						
		} catch (JSONException | SQLException e) {response.setStatus(400);}	
	}
}
