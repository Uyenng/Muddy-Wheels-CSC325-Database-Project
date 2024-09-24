import java.sql.*;
import java.util.ArrayList;

public class AccountDao {
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
	
 public AccountDao()
 {
	 connectToDatabase();
 }

  /////////////////////////////////////////////////////////////
  // SELECT ALL
  /////////////////////////////////////////////////////////////
  public ArrayList<Object> selectAll()
  {
	  ArrayList<Object> result = new ArrayList<Object>();
	  
	   try {
		  //System.out.println("In selectAll()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from USERS");
		  
		  while (resultSet.next())
		  {
			  Account account = new Account(resultSet.getString("Fname"), resultSet.getString("Lname"),
																 resultSet.getDate("DOB"), resultSet.getString("Street"), resultSet.getString("City"),
                                 resultSet.getString("State"), resultSet.getString("ZIP"),
                                 resultSet.getString("Phone"), resultSet.getString("Email"), resultSet.getString("Password"));
			  result.add(account);
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

  /////////////////////////////////////////////////////////////
  // SELECT BY ID
  /////////////////////////////////////////////////////////////
  public Account selectByEmail(String email)
  {
	  Account result = null;

	   try {
		  //System.out.println("In selectByEmail()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from USERS where EMAIL='"+email+"'");
		  
		  if (resultSet.next()) 
		  {
            result = new Account(resultSet.getString("Fname"), resultSet.getString("Lname"),
																 resultSet.getDate("DOB"),
                                 resultSet.getString("Street"), resultSet.getString("City"),
                                 resultSet.getString("State"), resultSet.getString("ZIP"),
                                 resultSet.getString("Phone"), resultSet.getString("Email"),
								 resultSet.getString("Password"));
          } else {
			System.out.println("Email not found...\n");
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

	public Account selectByName(String fname, String lname)
  	{
	  Account result = null;

	   try {
		  //System.out.println("In selectByName()...");
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from USERS where fname='"+fname+"'"+" AND lname='"+lname+"'");
		  
		  if (resultSet.next()) 
		  {
            result = new Account(resultSet.getString("Fname"), resultSet.getString("Lname"),
																 resultSet.getDate("DOB"),
                                 resultSet.getString("Street"), resultSet.getString("City"),
                                 resultSet.getString("State"), resultSet.getString("ZIP"),
                                 resultSet.getString("Phone"), resultSet.getString("Email"),
								 resultSet.getString("Password"));
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
 /////////////////////////////////////////////////////////////
 // INSERT = Register a new account
 /////////////////////////////////////////////////////////////
 public void insert(Account account)
 {
	int priority = -1;
	if (isAdmin(account.fname, account.lname)) 
	{
		account.setAdmin(true);
		//System.out.print("Yay");
		priority=1;
	} else 
	{
		account.setAdmin(false);
		//System.out.print("Nay");
		priority=0;
	}

	// added this to insert by Alvin
	String phoneNumber = account.getPhone();
	if (phoneNumber.length() != 10) {
			phoneNumber = phoneNumber.substring(0, 10);
	}

	try 
	{
		//System.out.println("In insert()...");
		Statement statement = connection.createStatement();
		String sql = "insert into USERS (FNAME,LNAME,DOB,STREET,CITY,STATE,ZIP,PHONE,EMAIL,PASSWORD,ADMINISTRATOR) values ("+
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
                ""+priority+")";
		//System.out.println("insert(): "+sql);
		statement.executeUpdate(sql);
		statement.close();
	  }
	  catch (Exception e)
	 {
		 //e.printStackTrace();
     System.out.println("This email is already used. Please try a different email.\n");
     System.exit(0);
	 }
 }

 /////////////////////////////////////////////////////////////
 // UPDATE 
 /////////////////////////////////////////////////////////////
public void update(Account account)
 {
	  try {
		  //System.out.println("In update()...");
		  Statement statement = connection.createStatement();
		  String sql = "update USERS set "+
								  "FNAME = '"+account.fname.trim()+"',"+
								  "LNAME = '"+account.lname.trim()+"',"+
									"DOB = TO_DATE('" + account.dob + "', 'YYYY-MM-DD'),"+
								  "STREET= '"+account.street.trim()+"',"+
								  "CITY= '"+account.city.trim()+"',"+
								  "STATE= '"+account.state.trim()+"',"+
								  "ZIP= '"+account.ZIP.trim()+"',"+
								  "PHONE= '"+account.phone.trim()+"',"+
								  //"EMAIL= '"+account.email+"',"+
								  "PASSWORD= '"+account.password.trim()+"'"+
                  " WHERE EMAIL = '" + account.email + "'";
		  //System.out.println("update(): "+sql);
		  statement.executeUpdate(sql);
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 //e.printStackTrace();
     System.out.println("Something went wrong. Unable to update account\n");
     System.exit(0);
	 }
 }

 public void updateTicket(Account account)
 {
	  try {
		  //System.out.println("In update()...");
		  Statement statement = connection.createStatement();
		  String sql = "update USERS set "+
                  "TICKET_ID="+account.ticketID + 
                  " WHERE EMAIL = '" + account.email + "'";
		  //System.out.println("update(): "+sql);
		  statement.executeUpdate(sql);
		  statement.close();
	  }
	  catch (Exception e)
	 {
		 //e.printStackTrace();
     System.out.println("Something went wrong. Unable to update ticket\n");
     System.exit(0);
	 }
 }


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
		  //System.out.println("In deleteByEmail()...");
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
		  //System.out.println("In deleteAll()...");
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
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from USERS where EMAIL='"+email+"' AND PASSWORD='"+password+"'");
		
		if (resultSet.next()) 
		{
      result = new Account(resultSet.getString("Fname"), resultSet.getString("Lname"),
													 resultSet.getDate("DOB"), resultSet.getString("Street"), resultSet.getString("City"),
                           resultSet.getString("State"), resultSet.getString("ZIP"),
                           resultSet.getString("Phone"), resultSet.getString("Email"),
													 resultSet.getString("Password"));   

      if (isAdmin(result.fname, result.lname) )
      {
        result.setAdmin(true);
      } else {
        result.setAdmin(false);
      } 
		} else {
			System.out.println("Email and/or Password incorrect\n");
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

	//if user is Ula Nguyen, Sean Sinclair, or Alvin George then admin
	public boolean isAdmin(String fname, String lname) 
	{
		fname = trimName(fname);
		lname = trimName(lname);
		boolean isadmin = (fname.equalsIgnoreCase("Ula") && lname.equalsIgnoreCase("Nguyen")) || 
           (fname.equalsIgnoreCase("Alvin") && lname.equalsIgnoreCase("George")) ||
           (fname.equalsIgnoreCase("Sean") && lname.equalsIgnoreCase("Sinclair"));
		return isadmin;
	}

  public boolean isAdmin(String email) 
	{
    try {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select FNAME,LNAME from USERS where EMAIL='"+email+"'");

    if (resultSet.next()) 
		{
		String fname=resultSet.getString("Fname");
    String lname=resultSet.getString("Lname");
    return isAdmin(fname,lname);
    }
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
  return false;
	}

	public String trimName(String name)
	{
		String result = "";
		for(int i = 0; i < name.length(); i++)
		{
			if(Character.isLetter(name.charAt(i)))
			{
				result = result + name.substring(i, i+1);
			}
			else return result;
		}
		return result;
	}

}


