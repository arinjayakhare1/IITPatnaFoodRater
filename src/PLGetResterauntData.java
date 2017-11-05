

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class PLGetResterauntData
 */
@WebServlet("/PLGetResterauntData")
public class PLGetResterauntData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PLGetResterauntData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Declarations
		UserDefinedTypes.DBConnect vDBConnect = null;
		JSONArray vArrResterauntResults;
		String vResterauntName;
		
		
		
	    
		//Get the parameters
		vResterauntName = (String) request.getParameter("resterauntName");
		
		
		//Initialize
		vArrResterauntResults = null;
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
		System.out.println(vResterauntName);
		
		vArrResterauntResults = DataAccessLayer.DBGetResterauntResults(vDBConnect.Connect, vResterauntName);
		
		System.out.println(vArrResterauntResults);
		//Close Database Connection
		DBUtils.Disconnect(vDBConnect.Connect,
			               "PLGetDishes");
		
		//Return
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vArrResterauntResults.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
