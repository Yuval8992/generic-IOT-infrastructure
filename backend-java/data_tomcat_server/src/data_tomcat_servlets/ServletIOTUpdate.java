package data_tomcat_servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/updates")

public class ServletIOTUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
       
    public ServletIOTUpdate() throws SQLException {}
    
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

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String backend_time = dateFormat.format(date);

		try {
			JSONObject outerJason = new JSONObject(str);
			JSONObject innerJason = outerJason.getJSONObject("data");

			String companyDB = outerJason.getString("company_name") + outerJason.getString("company_id");
			statement.execute("USE " + companyDB);

			statement.executeUpdate("INSERT INTO " + "product_" + innerJason.getString("product_type_id") + "_updates" +
			" (serial_number, backend_time, user_time, data_field1, data_field2, data_field3) VALUES ("
					+ "'" + innerJason.getString("serial_number") + "'" + "," 
					+ "'" + backend_time +  "'" + "," 
					+ "'" + innerJason.getString("user_time") + "'" + "," 
					+ "'" + innerJason.getString("data_field1") + "'" + "," 
					+ "'" + innerJason.getString("data_field2") + "'" + ","
					+ "'" + innerJason.getString("data_field3") + "'" 
					+  " );");

		} catch (JSONException | SQLException e) {response.setStatus(400);}	
	}
}
