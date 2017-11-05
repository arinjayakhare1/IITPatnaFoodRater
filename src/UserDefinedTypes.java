import java.sql.Connection;

public class UserDefinedTypes 
{
	//Query types
	public enum udQueryTypes
	{
		SELECTStmnt,
		INSERTStmnt,
		UPDATEStmnt,
		DELETEStmnt,
		EXECUTEStmnt
	};

	//Search criteria
	public enum udSearchCriteria
	{
		BeginsWith,
		EndsWith,
		ExactMatch,
		Contains;
	};

	//DB Connection
	public static class DBConnect
	{
		public Connection Connect; 	//Database connection
	}
	
	//DB Result
	public static class DBResult
	{
		public int NumOfRecs; 	//Number of records
		Object[][] ArrRecSet;	//Record set returned
		
		public DBResult()
		{
			this.NumOfRecs = 0;
		};
	}
}
