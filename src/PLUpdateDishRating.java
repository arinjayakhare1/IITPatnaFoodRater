

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PLUpdateDishRating
 */
@WebServlet("/PLUpdateDishRating")
public class PLUpdateDishRating extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PLUpdateDishRating() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Declarations
		Connection vDBConnect = null;
		String vdishName;
		String vResterauntName;
		String vNewRating;
		
		//Get the parameters
		vdishName = (String) request.getParameter("dishName");
		vResterauntName = (String) request.getParameter("resterauntName");
		vNewRating = (String) request.getParameter("newRating");
		
		
		try 
		{
			String vURL;
			String vUserName;
			String vPassword;
			
			vUserName = "root";
			vPassword = "root";
			
			//Connection parameters
			vURL="jdbc:mysql://127.0.0.1:3306/iitpatnafoodrater";
			
			
			//JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connect to the database
			vDBConnect = DriverManager.getConnection(vURL, vUserName, vPassword);	
		       
		}
		catch (ClassNotFoundException e) 
		{
		e.printStackTrace();
		}
		catch (SQLException e) 
		{
		e.printStackTrace();
		}
		
		System.out.println(vDBConnect);
		System.out.println(vdishName + vResterauntName + vNewRating); 
		
		DataAccessLayer.DBUpdateDishRating(vDBConnect, vdishName, vResterauntName, vNewRating);
		
		try {
			vDBConnect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Return
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Updated the rating");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
