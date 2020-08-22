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

@WebServlet("/products")

public class ServletPR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
       
    public ServletPR() throws SQLException {}
    
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
			
			statement.execute("CREATE TABLE " 
					+ "product_" + innerJason.getString("product_type_id") + "_updates "
					+ "(update_id INT NOT NULL AUTO_INCREMENT, "
					+ "serial_number VARCHAR(40), "
					+ "backend_time VARCHAR(40), "
					+ "user_time VARCHAR(40), "
					+ "data_field1 VARCHAR(300), "
					+ "data_field2 VARCHAR(300), "
					+ "data_field3 VARCHAR(300), "
					+ "PRIMARY KEY (update_id));");
			
		} catch (JSONException | SQLException e) {response.setStatus(400);}	
	}
}
