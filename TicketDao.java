import java.sql.*;
import java.util.ArrayList;

public class TicketDao {
  // ORACLE JDBC Driver
  String driverName    = "oracle.jdbc.driver.OracleDriver";

  // Connection to specific ORACLE database
  ////////////////////////////////////////////////
  // Put YOUR userid here -----------------------v
  ////////////////////////////////////////////////
  String connectionURL = "jdbc:oracle:thin:pnguyen2/csrocks55@csc325.cjjvanphib99.us-west-2.rds.amazonaws.com:1521:ORCL";
  Driver driver;
  Connection connection;

  public void setDriverName(String value)
  {
    driverName = value;
  }

  public String getDriverName()
  {
    return driverName;
  }

  public void setConnectionURL(String value)
  {
    connectionURL = value;
  }

  public String getConnectionURL()
  {
    return connectionURL;
  }

 /////////////////////////////////////////////////////////////
 // CONNECT TO DATABASE
 /////////////////////////////////////////////////////////////
 private void connectToDatabase()
 {
	 try {
		 //System.out.println("Trying to connect to database getting driver..."); 
		 driver = (java.sql.Driver) Class.forName(driverName).newInstance();
		 
		 //System.out.println("Driver loaded.  Connecting to database...");
		 connection = DriverManager.getConnection(connectionURL);
		 //System.out.println("Connection successful!");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

  /////////////////////////////////////////////////////////////
  // DISCONNECT FROM DATABASE.
  /////////////////////////////////////////////////////////////
  public void disconnectFromDatabase()
  {
	  try {
		  //System.out.println("Trying to disconnect from database...");
		  connection.close();
		  //System.out.println("Disconnection successful...");
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
  }

 // Finalize method disconnects on garbage collection
 public void finalize()
 {
	  disconnectFromDatabase();
 }
	
 public TicketDao()
 {
	 connectToDatabase();
 }

  /////////////////////////////////////////////////////////////
  // SELECT
  /////////////////////////////////////////////////////////////
  public ArrayList<Object> selectByFlightID(int ID)
  {
	  ArrayList<Object> result = new ArrayList<Object>();
	  
	   try {
		  //System.out.println("In selectByFlightID()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from TICKET where flightID="+ID);
		  
		  while (resultSet.next())
		  {
			  Ticket ticket = new Ticket(resultSet.getInt("ticketID"), resultSet.getInt("flightID"), 
                                    resultSet.getString("classType"), resultSet.getDouble("fare")
																	);
			  result.add(ticket);
		  }
		  
		  resultSet.close();
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }

	  return result;
  }
  
	public Ticket selectByTicketID(int ID)
  {
	  Ticket result = null;

	   try {
		  //System.out.println("In selectByID()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from TICKET where ticketID="+ID);
		  //System.out.println("select * from FLIGHT where flightNo="+ID);
		  if (resultSet.next()) 
		  {
      	result = new Ticket(resultSet.getInt("ticketID"), resultSet.getInt("flightID"), 
                            resultSet.getString("classType"), resultSet.getDouble("fare")
														);
      }
		  
		  resultSet.close();
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 
	  return result;
  }

  public void displayTicketFlightSummary(int ticketId) 
  {
	  try {
		  //System.out.println("In displayTicketFlightSummary()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select flightNo, airline, departure, departureTimestamp, destination, arrivalTimestamp, ticketID, classType, fare from TICKET, FLIGHT where ticketID="+ticketId+" AND flightNo=flightID");
		  //System.out.println("select * from FLIGHT where flightNo="+ID);
		  if (resultSet.next()) 
		  { 
        System.out.println("Flight ID: " + resultSet.getInt("flightNo"));
        System.out.println("Airline: " + resultSet.getString("airline"));
        System.out.println("Departure: " + resultSet.getString("departure"));
        System.out.println("Departure Timestamp: " + resultSet.getTime("departureTimestamp"));
        System.out.println("Destination: " + resultSet.getString("destination"));
        System.out.println("Arrival Timestamp: " + resultSet.getString("arrivalTimestamp"));
        System.out.println("Ticket ID: " + resultSet.getInt("ticketID"));
        System.out.println("Class Type: " + resultSet.getString("classType"));
        System.out.println("Fare: " + resultSet.getDouble("fare") + "\n");
      }
      else {System.out.println("Empty\n");}
		  
		  resultSet.close();
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
  }

  public Ticket getReservations(String userEmail)
  {
      Ticket reservation = null;
	   try {
		  //System.out.println("In displayTicketFlightSummary()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select flightNo, airline, departure, departureTimestamp, destination, arrivalTimestamp, ticketID, classType, fare from TICKET, FLIGHT,USERS where USERS.Email='"+userEmail+"' AND flightNo=flightID AND TICKETID=TICKET_ID");
		  //System.out.println("select * from FLIGHT where flightNo="+ID);
		  if (resultSet.next()) 
		  { 
        reservation = new Ticket(resultSet.getInt("ticketID"), resultSet.getInt("flightNo"),resultSet.getString("airline"),
                                 resultSet.getString("departure"),resultSet.getTimestamp("departureTimestamp"),
                                 resultSet.getString("destination"),resultSet.getTimestamp("arrivalTimestamp"),
                                 resultSet.getString("classType"),
                                 resultSet.getDouble("fare"));
        
      }
		  
		  resultSet.close();
		  statement.close();
	  }
	  catch (Exception e)
	 {
    System.out.println("Empty\n");
		 e.printStackTrace();
	 }
   return reservation;
  }

  /*
 /////////////////////////////////////////////////////////////
 // INSERT = Register a new account
 /////////////////////////////////////////////////////////////
 public void insert(Account account)
 {
	int priority = -1; 
	if (isAdmin(account.fname, account.lname))
		priority = 1; //user is admin
	else priority = 0;

	try 
	{
		System.out.println("In insert()...");
		Statement statement = connection.createStatement();
		String sql = "insert into USERS values ("+
								"'"+account.fname+"',"+
								"'"+account.lname+"',"+
								"TO_DATE('"+account.dob+"', 'YYYY-MM-DD'),"+
								"'"+account.street+"',"+
								"'"+account.city+"',"+
								"'"+account.state+"',"+
								"'"+account.ZIP+"',"+
								"'"+account.phone+"',"+
								"'"+account.email+"',"+
								"'"+account.password+"'," +
                ""+String.valueOf(priority)+")";
		System.out.println("insert(): "+sql);
		statement.executeUpdate(sql);
		statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

 /////////////////////////////////////////////////////////////
 // UPDATE - new passenger - capacity-1
 /////////////////////////////////////////////////////////////
public void update(Account account)
 {
	  try {
		  System.out.println("In update()...");
		  Statement statement = connection.createStatement();
		  String sql = "update USERS set "+
								  "FNAME = '"+account.fname+"',"+
								  "LNAME = '"+account.lname+"',"+
									"DOB = TO_DATE('" + account.dob + "', 'YYYY-MM-DD'),"+
								  "STREET= '"+account.street+"',"+
								  "CITY= '"+account.city+"',"+
								  "STATE= '"+account.state+"',"+
								  "ZIP= '"+account.ZIP+"',"+
								  "PHONE= '"+account.phone+"',"+
								  "EMAIL= '"+account.email+"',"+
								  "PASSWORD= '"+account.password+"'";
		  //System.out.println("update(): "+sql);
		  statement.executeUpdate(sql);
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

/*
/////////////////////////////////////////////////////////////
// DELETE don't provoke error even if data doesn't exist in table
/////////////////////////////////////////////////////////////
 public void delete(String email, String password)
 {
  //System.out.println("delete from USERS where EMAIL='"+email+"' AND PASSWORD='"+password+"'"); //debug
	 try {
		  System.out.println("In delete()...");
		  Statement statement = connection.createStatement();
		  statement.executeUpdate("delete from USERS where EMAIL='"+email+"' AND PASSWORD='"+password+"'");
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

 public void deleteByEmail(String email)
 {
	 try {
		  System.out.println("In deleteByEmail()...");
		  Statement statement = connection.createStatement();
		  statement.executeUpdate("delete from USERS where EMAIL='"+email+"'");
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

 public void deleteAll()
 {
	 try {
		  System.out.println("In deleteAll()...");
		  Statement statement = connection.createStatement();
		  statement.executeUpdate("delete from USERS");
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 }

  /////////////////////////////////////////////////////////////
  // SELECT BY EMAIL & PASSWORD = Log In
  /////////////////////////////////////////////////////////////
  public Account logIn(String email, String password)
  {
	Account result = null;
	
	try {
		System.out.println("In logIn()...");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from USERS where EMAIL='"+email+"' AND PASSWORD='"+password+"'");
		
		if (resultSet.next()) 
		{
      result = new Account(resultSet.getString("Fname"), resultSet.getString("Lname"),
													 resultSet.getDate("DOB"), resultSet.getString("Street"), resultSet.getString("City"),
                           resultSet.getString("State"), resultSet.getString("ZIP"),
                           resultSet.getString("Phone"), resultSet.getString("Email"),
													 resultSet.getString("Password"));    
		} else {
			System.out.println("Email and/or Password incorrect");
		}
		  
		resultSet.close();
		statement.close();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
 
	return result;
  }

	//if user is Ula Nguyen, Sean Sinclair, or Alvin George then admin, for now
	public boolean isAdmin(String fname, String lname) 
	{
		return (fname.equalsIgnoreCase("Ula") && lname.equalsIgnoreCase("Nguyen")) || 
           (fname.equalsIgnoreCase("Alvin") && lname.equalsIgnoreCase("George")) ||
           (fname.equalsIgnoreCase("Sean") && lname.equalsIgnoreCase("Sinclair"));
	}
  */
}


