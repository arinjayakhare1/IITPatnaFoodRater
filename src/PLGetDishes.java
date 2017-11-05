

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class PLGetDishes
 */
@WebServlet("/PLGetDishes")
public class PLGetDishes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PLGetDishes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Declarations
		UserDefinedTypes.DBConnect vDBConnect = null;
		JSONArray vArrDishesResults;
		String vdishName;
		String vminCost;
		String vmaxCost;
		String vminRating;
		String vmaxRating;
		String vtype;
		String vURL;
		String vUserName;
		String vPassword;
		
		
	    
		//Get the parameters
		vdishName = (String) request.getParameter("dishName");
		vminCost = (String) request.getParameter("minCost");
		vmaxCost = (String) request.getParameter("maxCost");
		vminRating = (String) request.getParameter("minRating");
		vmaxRating = (String) request.getParameter("maxRating");
		vtype = (String) request.getParameter("type");
		
		//Initialize
		vArrDishesResults = null;
		//Call subroutine to open connection to the database
		try 
		{
			
		    vDBConnect = DBUtils.ConnectToDB("PLGetDishes");
		    		
		       
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
		System.out.println(vdishName + vminCost + vmaxCost + vminRating + vmaxRating + vtype);
		
		vArrDishesResults = DataAccessLayer.DBGetDishesResults(vDBConnect.Connect, vdishName, vminCost, vmaxCost, vminRating, vmaxRating, vtype);
		
		System.out.println(vArrDishesResults);
		//Close Database Connection
		DBUtils.Disconnect(vDBConnect.Connect,
			               "PLGetDishes");
		
		//Return
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vArrDishesResults.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
