import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class DataAccessLayer
{
	
	public static JSONArray DBGetResterauntResults(Connection pConnect,
			   									   String vResterauntName )
	{
		//Declarations
		ResultSet vDBResult = null;
		JSONArray vArrResterauntesults;
		JSONObject vObj;
		String vSPStr;
	  
			
		//Initialise
		vArrResterauntesults = null;
		vSPStr = null;
		
		vSPStr = "Select * from food_rating inner join resteraunt_rating on food_rating.resteraunt_name = resteraunt_rating.resteraunt_name where food_rating.resteraunt_name like '%"+ vResterauntName + "%';";
				
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			vDBResult = pst.executeQuery();
			//Instantiate the object
			vArrResterauntesults = new JSONArray();

	        while(vDBResult.next())
	        {
	        	vObj = new JSONObject();
	        	vObj.put("dish_name", vDBResult.getString("dish_name"));
	        	vObj.put("resteraunt_name", vDBResult.getString("resteraunt_name"));
	        	vObj.put("dish_rating", vDBResult.getString("dish_rating"));
	        	vObj.put("resteraunt_rating", vDBResult.getString("resteraunt_rating"));
	        	vObj.put("cost", vDBResult.getString("cost"));
	        	vObj.put("type", vDBResult.getString("type"));
	        	vObj.put("numberOfPeopleRatingDish", vDBResult.getString("food_rating.numberOfRatings"));
	        	vObj.put("numberOfPeopleRatingResteraunt", vDBResult.getString("resteraunt_rating.numberOfRatings"));
	        	vArrResterauntesults.put(vObj);
	        }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}  
		  
		  
		return vArrResterauntesults;
		
	}
	
	public static JSONArray DBGetDishesResults(Connection pConnect,
											   String vdishName ,
											   String vminCost ,
											   String vmaxCost ,
											   String vminRating ,
											   String vmaxRating ,
											   String vtype)
	{
		
		//Declarations
		ResultSet vDBResult = null;
		JSONArray vArrDishesResults;
		JSONObject vObj;
		String vSPStr;
	  
			
		//Initialise
		vArrDishesResults = null;
		vSPStr = null;
		
		//Frame query string
		vSPStr = "Select * from food_rating inner join resteraunt_rating on food_rating.resteraunt_name = resteraunt_rating.resteraunt_name where dish_name like '%" + vdishName + "%' and cost >= "  + vminCost + " and cost <= " + vmaxCost + " and dish_rating >= " + vminRating + " and dish_rating <= " + vmaxRating;
		if(!vtype.equals("all"))
		{
			vSPStr += " and (type = '" +  vtype + "' or type = 'all')";
		}
		vSPStr += ";";
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			vDBResult = pst.executeQuery();
			//Instantiate the object
	        vArrDishesResults = new JSONArray();

	        while(vDBResult.next())
	        {
	        	vObj = new JSONObject();
	        	vObj.put("dish_name", vDBResult.getString("dish_name"));
	        	vObj.put("resteraunt_name", vDBResult.getString("resteraunt_name"));
	        	vObj.put("dish_rating", vDBResult.getString("dish_rating"));
	        	vObj.put("resteraunt_rating", vDBResult.getString("resteraunt_rating"));
	        	vObj.put("cost", vDBResult.getString("cost"));
	        	vObj.put("type", vDBResult.getString("type"));
	        	vObj.put("numberOfPeopleRatingDish", vDBResult.getString("food_rating.numberOfRatings"));
	        	vObj.put("numberOfPeopleRatingResteraunt", vDBResult.getString("resteraunt_rating.numberOfRatings"));
	            vArrDishesResults.put(vObj);
	        }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}  
		  
		  
		return vArrDishesResults;
		
	}
	
	public static void DBUpdateDishRating(Connection pConnect,
										  String vdishName,
										  String vRestaurantName,
										  String vNewRating)
	{
		String vSPStr;
		vSPStr = "update food_rating set dish_rating = ((dish_rating * numberOfRatings) + " + vNewRating + " ) / (numberOfRatings + 1) where dish_name = '" + vdishName + "' and resteraunt_name = '" + vRestaurantName + "';";
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			pst.executeUpdate();
			pst.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
		
		vSPStr = "update food_rating set numberOfRatings = numberOfRatings + 1 where dish_name = '" + vdishName + "' and resteraunt_name = '" + vRestaurantName + "';";
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			pst.executeUpdate();
			pst.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
	}
	
	public static void DBUpdateResterauntRating(Connection pConnect,
										  String vRestaurantName,
										  String vNewRating)
	{
		String vSPStr;
		vSPStr = "update resteraunt_rating set resteraunt_rating = ((resteraunt_rating * numberOfRatings) + " + vNewRating + " ) / (numberOfRatings + 1) where resteraunt_name = '" + vRestaurantName + "';";
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			pst.executeUpdate();
			pst.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
		
		vSPStr = "update resteraunt_rating set numberOfRatings = numberOfRatings + 1 where resteraunt_name = '" + vRestaurantName + "';";
		System.out.println(vSPStr);
		//Execute stored procedure
		try 
		{
			java.sql.PreparedStatement pst = pConnect.prepareStatement(vSPStr);
			pst.executeUpdate();
			pst.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
	}
	

}
