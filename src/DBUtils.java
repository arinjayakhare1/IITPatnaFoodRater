import java.sql.SQLException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;

public class DBUtils {
		
	//Method for establishing connection to the database
	public static UserDefinedTypes.DBConnect ConnectToDB(String pFunctionName)
            				                 throws ClassNotFoundException, 
                                             SQLException 
	{
	
		//Declarations
		UserDefinedTypes.DBConnect vConnect = null;
		String vURL;
		InputStream vStream;
		Properties vProp;
		String vUserName;
		String vPassword;
		
		//Initialize
        vConnect = new UserDefinedTypes.DBConnect();
		vUserName = "root";
		vPassword = "root";
		
		//Connection parameters
		vURL="jdbc:mysql://127.0.0.1:3306/iitpatnafoodrater";
		
//		//Obtain user name and password from the resource file
//		vStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Resources/Credentials");
//		vProp = new Properties();
//		try 
//		{
//			vProp.load(vStream);
//			vUserName = vProp.getProperty("Username");
//		    vPassword = vProp.getProperty("Password");
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		
		try 
		{

			//JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connect to the database
			vConnect.Connect = DriverManager.getConnection(vURL, vUserName, vPassword);
            //System.out.println(pFunctionName + " - Opened connection");
			
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		//Return the connection
		return vConnect;
		
	}

	//Method for closing connection to the database
	public static void Disconnect(Connection pConnect,
			                      String pFunctionName) 
	{

		//Close the connection
		try 
		{
            if (pConnect.isClosed() == false)
            {
            	pConnect.close();
            	//System.out.println(pFunctionName + " - Closed connection");
            }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

    public static UserDefinedTypes.DBResult ExecSQL(Connection pConnect,
                                                    String pQueryStr,
                                                    UserDefinedTypes.udQueryTypes pQueryType,
                                                    int pReqdNumOfRecs)
	                                                throws SQLException
    {
	
        //Declarations
    	UserDefinedTypes.DBResult vDBResult = null;
    	CallableStatement vStmnt;
        ResultSet vRecSet;
        int vRecCount;
        int vCount1;
        int vCount2;

        //Initialize
        vDBResult = new UserDefinedTypes.DBResult();
        vStmnt = null;
        vRecSet = null;
        
        //For select statements, use a record set. For other statements execute the query
        try 
        {
	        if (pQueryType == UserDefinedTypes.udQueryTypes.SELECTStmnt) 
	        {
	
	            //Execute the query
	        	vStmnt = pConnect.prepareCall(pQueryStr,
	        			                      ResultSet.TYPE_SCROLL_INSENSITIVE,
    			                              ResultSet.CONCUR_READ_ONLY);
	        	vRecSet = vStmnt.executeQuery();

	            //Count the number of records.
	            vRecSet.last();
	            vRecCount = vRecSet.getRow();

	            //Populate the array with query results.
	            if (vRecCount > 0) 
	            {
	                if (pReqdNumOfRecs == 0)
	                    vDBResult.NumOfRecs = vRecCount;
	                else
	                	vDBResult.NumOfRecs = pReqdNumOfRecs;
	                
	                //Allocate memory
	                vDBResult.ArrRecSet = new Object [vDBResult.NumOfRecs + 1][vRecSet.getMetaData().getColumnCount() + 1];
	                
	                vRecSet.first();
	                for (vCount1 = 1; vCount1 <= vDBResult.NumOfRecs; vCount1++)
	                {
	                    for (vCount2 = 1; vCount2 <= vRecSet.getMetaData().getColumnCount(); vCount2++)
	                    	vDBResult.ArrRecSet[vCount1][vCount2] = vRecSet.getObject(vCount2);
	                    
	                    vRecSet.next();
	                }
	            }
	            else
	            	vDBResult.NumOfRecs = 0;
	            
	            //Close the result set
	            if (pConnect.isClosed() == false)
	            	vRecSet.close();
	            
	        }
	        else
	        {
	        	//Execute the query
	        	vStmnt = pConnect.prepareCall(pQueryStr);
	        	vRecSet = vStmnt.executeQuery();
	        }
        }
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

        return(vDBResult);
	}
}
