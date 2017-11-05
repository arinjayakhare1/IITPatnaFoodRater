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
	
	
	
	
	
//	public static JSONArray DBGetTableNames(Connection pConnect)
//	{
//		//Declarations
//        UserDefinedTypes.DBResult vDBResult = null;
//        JSONArray vArrTableNames;
//        JSONObject vObj;
//        String vSPStr;
//        int vCount1;
//
//        //Initialize
//        vArrTableNames = null;
//        vSPStr = null;
//
//        //Frame query string
//        vSPStr = "select * from dbc.tables where databasename = 'FUZZYLOGIX' order by 2;";
//         
//      //Execute stored procedure
//        try 
//        {
//	        vDBResult = DBUtils.ExecSQL(pConnect, 
//	        		                    vSPStr,
//	        		                    UserDefinedTypes.udQueryTypes.SELECTStmnt,
//	        		                    0);
//        }
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//        
//        try 
//        {
//	        //Instantiate the object
//	        vArrTableNames = new JSONArray();
//
//	        //Populate the array
//	        for (vCount1 = 1; vCount1 <= vDBResult.NumOfRecs; vCount1++)
//	        {	
//	        	vObj = new JSONObject();
//	        	vObj.put("TableName", vDBResult.ArrRecSet[vCount1][2].toString());
//	            vArrTableNames.put(vObj);
//	        };
//        }
//        catch (JSONException e)
//        {
//			e.printStackTrace();
//		}
//        
//        return (vArrTableNames);
//	}
//	
//	public static JSONArray DBGetLinRegrAnalysisID( Connection pConnect,
//													String vTableName,
//													String vRegressionNote)
//	{
//		//Declarations
//		UserDefinedTypes.DBResult vDBResult = null;
//		JSONArray vArrLinRegrAnalysisID;
//		JSONObject vObj;
//		String vSPStr;
//		
//		
//		//Initialize
//		vArrLinRegrAnalysisID = null;
//		vSPStr = null;
//		
//		//Frame query string
//		vSPStr = "CALL FLLinRegrWR ('"+ vTableName +"'," +
//									"'ObsID'" + "," +
//									"'VarID'" +"," +
//									"'Num_Val'" +	"," +
//									"'"+ vRegressionNote + "'"	+");";
//		
//		//Execute stored procedure
//		try 
//		{
//			vDBResult = DBUtils.ExecSQL(pConnect, 
//										vSPStr,
//										UserDefinedTypes.udQueryTypes.SELECTStmnt,
//										0);
//		}
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//		
//		try 
//		{
//			//Instantiate the object
//			vArrLinRegrAnalysisID = new JSONArray();
//			
//			//Populate the array
//			vObj = new JSONObject();
//			vObj.put("AnalysisID", vDBResult.ArrRecSet[1][2].toString());
//			vArrLinRegrAnalysisID.put(vObj);	
//		}    
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//		
//		return (vArrLinRegrAnalysisID);
//	}	
//	
//	
//	
//	public static JSONArray DBGetLinRegrStats(Connection pConnect,
//		    								  String vAnalysisID)
//	{		
//		//Declarations
//        UserDefinedTypes.DBResult vDBResult = null;
//        JSONArray vArrLinRegrStats;
//        JSONObject vObj;
//        String vSPStr;
//       
//
//        //Initialize
//        vArrLinRegrStats = null;
//        vSPStr = null;
//
//        //Frame query string
//        vSPStr =  "CALL  FLLinRegrWRStats('" + vAnalysisID + "')";;
//         
//        //Execute stored procedure
//        try 
//        {
//	        vDBResult = DBUtils.ExecSQL(pConnect, 
//	        		                    vSPStr,
//	        		                    UserDefinedTypes.udQueryTypes.SELECTStmnt,
//	        		                    0);
//        }
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//        
//        try 
//        {
//	        //Instantiate the object
//        	vArrLinRegrStats = new JSONArray();
//
//	        //Populate the array
//	        vObj = new JSONObject();
//	        vObj.put("MULTIPLER", vDBResult.ArrRecSet[1][3].toString());
//	        vObj.put("RSQUARED", vDBResult.ArrRecSet[1][4].toString());
//	        vObj.put("ADJRSQUARED", vDBResult.ArrRecSet[1][5].toString());
//	        vObj.put("STDERR", vDBResult.ArrRecSet[1][6].toString());
//	        vObj.put("NUMOFOBS", vDBResult.ArrRecSet[1][7].toString());
//	        vObj.put("RESIDUALAUTOCOREL", vDBResult.ArrRecSet[1][18].toString());
//	        vObj.put("DWSTAT", vDBResult.ArrRecSet[1][19].toString());
//	        vObj.put("BPSTAT", vDBResult.ArrRecSet[1][20].toString());
//	        vObj.put("SIGBPSTAT", vDBResult.ArrRecSet[1][21].toString());
//	        vArrLinRegrStats.put(vObj);	        
//        }
//        catch (JSONException e)
//        {
//			e.printStackTrace();
//		}
//        
//        return (vArrLinRegrStats);
//		
//	}
//	
//	public static JSONArray DBGetLinRegrCoeffs(Connection pConnect,
//			  								  String vAnalysisID)
//	{
//		//Declarations
//        UserDefinedTypes.DBResult vDBResult = null;
//        JSONArray vArrLinRegrCoeffs;
//        JSONObject vObj;
//        String vSPStr;
//        int vCount1;
//
//        //Initialize
//        vArrLinRegrCoeffs = null;
//        vSPStr = null;
//
//        //Frame query string
//        vSPStr = "CALL  FLLinRegrWRCoeffs('" + vAnalysisID + "')";;
//         
//        //Execute stored procedure
//        try 
//        {
//	        vDBResult = DBUtils.ExecSQL(pConnect, 
//	        		                    vSPStr,
//	        		                    UserDefinedTypes.udQueryTypes.SELECTStmnt,
//	        		                    0);
//        }
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//        
//        try 
//        {
//	        //Instantiate the object
//        	vArrLinRegrCoeffs = new JSONArray();
//
//	        //Populate the array
//        	for (vCount1 = 1; vCount1 <= vDBResult.NumOfRecs; vCount1++)
//	        {	
//	        	vObj = new JSONObject();
//	        	if(vDBResult.ArrRecSet[vCount1][3].toString()!="0")
//	        	{	
//	        		vObj.put("COEFFID", vDBResult.ArrRecSet[vCount1][3].toString());
//	        		vObj.put("COEFFVALUE", vDBResult.ArrRecSet[vCount1][4].toString());
//	        		vObj.put("STDERR", vDBResult.ArrRecSet[vCount1][5].toString());
//	        		vObj.put("TSTAT", vDBResult.ArrRecSet[vCount1][6].toString());
//	        		vObj.put("PVALUE", vDBResult.ArrRecSet[vCount1][7].toString());
//	        		vArrLinRegrCoeffs.put(vObj);
//	        	}
//	        	
//	        };
//        }
//        catch (JSONException e)
//        {
//			e.printStackTrace();
//		}
//        
//        return (vArrLinRegrCoeffs);
//	}
//	
//
//	
//	public static JSONArray DBGetLogRegrAnalysisID( Connection pConnect,
//													String vTableName,
//													String vNumberOfIterations,
//													String vFalsePosNegThreshold,
//													String vRegressionNote)
//	{
//		//Declarations
//        UserDefinedTypes.DBResult vDBResult = null;
//        JSONArray vArrLogRegrAnalysisID;
//        JSONObject vObj;
//        String vSPStr;
//        
//
//        //Initialize
//        vArrLogRegrAnalysisID = null;
//        vSPStr = null;
//
//        //Frame query string
//        vSPStr = "CALL FLLogRegrWR  ('" + vTableName + "', "+
//									 "'ObsID',"+
//									 "'VarID',"+
//									 "'Num_Val',"+
//									 vNumberOfIterations + ","+
//									 vFalsePosNegThreshold + ","+
//									 "'" + vRegressionNote + "');";;
//       
//		//Execute stored procedure
//        try 
//        {
//	        vDBResult = DBUtils.ExecSQL(pConnect, 
//	        		                    vSPStr,
//	        		                    UserDefinedTypes.udQueryTypes.SELECTStmnt,
//	        		                    0);
//        }
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//        
//        try 
//        {
//	        //Instantiate the object
//        	vArrLogRegrAnalysisID = new JSONArray();
//
//	        //Populate the array
//            vObj = new JSONObject();
//	        vObj.put("AnalysisID", vDBResult.ArrRecSet[1][2].toString());
//	        vArrLogRegrAnalysisID.put(vObj);	
//	        
//	    }
//        catch (JSONException e)
//        {
//			e.printStackTrace();
//		}
//        
//        return (vArrLogRegrAnalysisID);
//	}
//	
//	public static JSONArray DBGetLogRegrCoeffs(Connection pConnect,
//			  								   String vAnalysisID)
//	{
//		//Declarations
//		UserDefinedTypes.DBResult vDBResult = null;
//		JSONArray vArrLogRegrCoeffs;
//		JSONObject vObj;
//		String vSPStr;
//		int vCount1;
//		
//		//Initialize
//		vArrLogRegrCoeffs = null;
//		vSPStr = null;
//		
//		//Frame query string
//		vSPStr = "CALL  FLLogRegrWRCoeffs('" + vAnalysisID + "')";;
//         
//		//Execute stored procedure
//		try 
//		{
//			vDBResult = DBUtils.ExecSQL(pConnect, 
//										vSPStr,
//										UserDefinedTypes.udQueryTypes.SELECTStmnt,
//										0);
//		}
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//		
//		try 
//		{
//			//Instantiate the object
//			vArrLogRegrCoeffs = new JSONArray();
//			
//			//Populate the array
//			for (vCount1 = 1; vCount1 <= vDBResult.NumOfRecs; vCount1++)
//			{	
//				vObj = new JSONObject();
//				if(vDBResult.ArrRecSet[vCount1][3].toString()!="0")
//				{	
//					vObj.put("COEFFID", vDBResult.ArrRecSet[vCount1][3].toString());
//					vObj.put("COEFFVALUE", vDBResult.ArrRecSet[vCount1][4].toString());
//					vObj.put("STDERR", vDBResult.ArrRecSet[vCount1][5].toString());
//					vObj.put("CHISQ", vDBResult.ArrRecSet[vCount1][6].toString());
//					vObj.put("PVALUE", vDBResult.ArrRecSet[vCount1][7].toString());
//					vArrLogRegrCoeffs.put(vObj);
//				}
//		
//			};
//			
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//		
//		return (vArrLogRegrCoeffs);
//	}
//	
//	public static JSONArray DBGetLogRegrStats(Connection pConnect,
//			  								  String vAnalysisID)
//	{
//		//Declarations
//        UserDefinedTypes.DBResult vDBResult = null;
//        JSONArray vArrLogRegrStats;
//        JSONObject vObj;
//        String vSPStr;
//       
//
//        //Initialize
//        vArrLogRegrStats = null;
//        vSPStr = null;
//
//        //Frame query string
//        vSPStr =  "CALL  FLLogRegrWRStats('" + vAnalysisID + "')";
//
//        //Execute stored procedure
//        try 
//        {
//	        vDBResult = DBUtils.ExecSQL(pConnect, 
//	        		                    vSPStr,
//	        		                    UserDefinedTypes.udQueryTypes.SELECTStmnt,
//	        		                    0);
//        }
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//        
//        try 
//        {
//	        //Instantiate the object
//        	vArrLogRegrStats = new JSONArray();
//
//	        //Populate the array
//	        vObj = new JSONObject();
//	        vObj.put("NUMOFVARS", vDBResult.ArrRecSet[1][3].toString());
//	        vObj.put("ITERATIONS", vDBResult.ArrRecSet[1][4].toString());
//	        vObj.put("CONCORDANT", vDBResult.ArrRecSet[1][5].toString());
//	        vObj.put("DISCORDANT", vDBResult.ArrRecSet[1][6].toString());
//	        vObj.put("TIED", vDBResult.ArrRecSet[1][7].toString());
//	        vObj.put("TOTALPAIRS", vDBResult.ArrRecSet[1][8].toString());
//	        vObj.put("GINICOEFF", vDBResult.ArrRecSet[1][9].toString());
//	        vObj.put("CSTATISTIC", vDBResult.ArrRecSet[1][10].toString());
//	        vObj.put("GAMMA", vDBResult.ArrRecSet[1][11].toString());
//	        vObj.put("HIGHESTPVALUE", vDBResult.ArrRecSet[1][12].toString());
//	        vObj.put("EVENTS", vDBResult.ArrRecSet[1][13].toString());
//	        vObj.put("NONEVENTS", vDBResult.ArrRecSet[1][14].toString());
//	        vObj.put("NUMOFOBS", vDBResult.ArrRecSet[1][15].toString());
//	        vObj.put("FALSEPOSITIVE", vDBResult.ArrRecSet[1][16].toString());
//	        vObj.put("FALSENEGATIVE", vDBResult.ArrRecSet[1][17].toString());
//	        vArrLogRegrStats.put(vObj);	        
//        }
//        catch (JSONException e)
//        {
//			e.printStackTrace();
//		}
//        
//        return (vArrLogRegrStats);
//
//	}
}
