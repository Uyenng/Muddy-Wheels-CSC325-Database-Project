import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CarRentalDao {
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

	public CarRentalDao()
	{
		connectToDatabase();
	}
 /////////////////////////////////////////////////////////////
 // CONNECT TO DATABASE
 /////////////////////////////////////////////////////////////
 private void connectToDatabase()
 {
	 try { 
		 driver = (java.sql.Driver) Class.forName(driverName).newInstance();
		 connection = DriverManager.getConnection(connectionURL);
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
			if(connection != null){
				connection.close();
			}
	  }
	  catch (SQLException e)
	 {
		 e.printStackTrace();
	 }
  }

 // Finalize method disconnects on garbage collection
 public void finalize()
 {
	  disconnectFromDatabase();
 }

  /////////////////////////////////////////////////////////////
  // GET ALL Rentals
  /////////////////////////////////////////////////////////////
  public ArrayList<CarRental> getAllAvailableCars(int destinationLocationId)
  {
		ArrayList<CarRental> availableCars = new ArrayList<>();
	  
	  try {
			String query = "select carId, carName, carModel, mpg, fuelType, numSeats, carType, numDoors, rentRatePerDay, isAvailable, destinationLocationId from CARS where isAvailable = 'Y' and destinationLocationId = ?"; // '?' placeholder
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, destinationLocationId);
		  ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				CarRental car = new CarRental();
				car.setCarId(resultSet.getInt("carId"));
				car.setCarName(resultSet.getString("carName"));
				car.setCarModel(resultSet.getString("carModel"));
				car.setMPG(resultSet.getInt("mpg"));
				car.setFuelType(resultSet.getString("fuelType"));
				car.setNumSeats(resultSet.getInt("numSeats"));
				car.setCarType(resultSet.getString("carType"));
				car.setNumDoors(resultSet.getInt("numDoors"));
				car.setRentRatePerDay(resultSet.getDouble("rentRatePerDay"));
				car.setIsAvailable(resultSet.getBoolean("isAvailable"));
				car.setDestinationLocation(resultSet.getInt("destinationLocationId"));
				availableCars.add(car);
			}

			resultSet.close();
			preparedStatement.close();
	  }
	  catch (SQLException e)
	 {
		e.printStackTrace();
	 }
	 return availableCars;
  }
	
	/////////////////////////////////////////////////////////
 // GET CAR DETAILS
 /////////////////////////////////////////////////////////////
 public CarRental getCarByName(String carName)
 {
	CarRental car = null;
	try 
	{
		String query = "select * from CARS where carName = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, carName);
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			car = new CarRental();
			car.setCarId(resultSet.getInt("carId"));
			car.setCarName(resultSet.getString("carName"));
			car.setCarModel(resultSet.getString("carModel"));
			car.setMPG(resultSet.getInt("mpg"));
			car.setFuelType(resultSet.getString("fuelType"));
			car.setNumSeats(resultSet.getInt("numSeats"));
			car.setCarType(resultSet.getString("carType"));
			car.setNumDoors(resultSet.getInt("numDoors"));
			car.setRentRatePerDay(resultSet.getDouble("rentRatePerDay"));
			car.setIsAvailable(resultSet.getBoolean("isAvailable"));
			car.setDestinationLocation(resultSet.getInt("destinationLocationId"));
		}
		resultSet.close();
		preparedStatement.close();
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
	return car;
 }


	public void bookCar(String carName, String userEmail, String driverLicenseNumber, String cardNumber, String cvv, String expiryDate, String nameOnCard) {
		try {

			int carId = getCarIdByName(carName);

			// check if the user has already booked a car
			if(isUserBookedCar(userEmail))
			{
				System.out.println("Sorry, you have already booked a car. You can only book one car at a time.");
				return;
			}

			// Update the car availability in the database
			PreparedStatement preparedStatement = connection.prepareStatement("update CARS set isAvailable = 'N' where carId = ?");
			preparedStatement.setInt(1, carId);
			preparedStatement.executeUpdate();

			int bookingId = generateUniqueBookingId();
			// Insert the booking information into the BOOKINGS table
			preparedStatement = connection.prepareStatement("insert into BOOKINGS(bookingId, carId, userEmail, driverLicenseNumber, cardNumber, cvv, expiryDate, nameOnCard) values(?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, bookingId);
			preparedStatement.setInt(2, carId);
			preparedStatement.setString(3, userEmail);
			preparedStatement.setString(4, driverLicenseNumber);
			preparedStatement.setString(5, cardNumber);
			preparedStatement.setString(6, cvv);
			preparedStatement.setString(7, expiryDate);
			preparedStatement.setString(8, nameOnCard);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	public int generateUniqueBookingId() {
		int bookingId = -1;
		try {
				String query = "SELECT MAX(BOOKINGID) + 1 FROM BOOKINGS";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
						bookingId = resultSet.getInt(1);
				} else {
						bookingId = 1; // If the table is empty, start with 1
				}

				resultSet.close();
				preparedStatement.close();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return bookingId;
	}

	public boolean isUserBookedCar(String userEmail)
	{
		try
		{
			String query = "select count(*) from BOOKINGS where userEmail = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userEmail);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				int count = resultSet.getInt(1);
				return count > 0;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public int getCarIdByName(String carName)
	{
		int carId = -1;
		try{
			String query = "select carId from CARS where carName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, carName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				carId = resultSet.getInt("carId");
			}
			resultSet.close();
			preparedStatement.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return carId;
	}

	public ArrayList<Integer> getDestinationLocations() 
	{
    ArrayList<Integer> destinationLocations = new ArrayList<>();
    try 
		{
			String query = "select locationId, locationName from DESTINATION_LOCATIONS";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				destinationLocations.add(resultSet.getInt("locationId"));
			}
			resultSet.close();
			preparedStatement.close();
    }catch (SQLException e) {
			e.printStackTrace();
    }
    return destinationLocations;
	}

	public ArrayList<String> getPickupDates()
	{
		ArrayList<String> pickupDates = new ArrayList<>();
		try
		{
			String query = "select dateId, TO_CHAR(pickupDate, 'YYYY-MM-DD') as pickupDate from PICKUP_DATES";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				pickupDates.add(resultSet.getString("pickupDate"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pickupDates;
	}

	public ArrayList<String> getPickupTimes()
	{
		ArrayList<String> pickupTimes = new ArrayList<>();
		try
		{
			String query = "select timeId, pickupTime from PICKUP_TIMES";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				pickupTimes.add(resultSet.getString("pickupTime"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pickupTimes;
	}

	public ArrayList<String> getReturnDates()
	{
		ArrayList<String> returnDates = new ArrayList<>();
		try
		{
			String query = "select dateId, TO_CHAR(returnDate, 'YYYY-MM-DD') as returnDate from RETURN_DATES";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				returnDates.add(resultSet.getString("returnDate"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return returnDates;
	}

	public ArrayList<String> getReturnTimes()
	{
		ArrayList<String> returnTimes = new ArrayList<>();
		try
		{
			String query = "select timeId, returnTime from RETURN_TIMES";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				returnTimes.add(resultSet.getString("returnTime"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return returnTimes;
	}

	public String getDestinationLocationName(int locationId)
	{
		String locationName = null;
		try
		{
			String query = "select locationName from DESTINATION_LOCATIONS where locationId =?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, locationId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				locationName = resultSet.getString("locationName");
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return locationName;
	}

  public ArrayList<Object> selectAll()
  {
	  ArrayList<Object> result = new ArrayList<Object>();
	  
	   try {
		  Statement statement = connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("select * from CARS");
		  
		  while (resultSet.next())
		  {
			  CarRental car = new CarRental(resultSet.getInt("carId"), resultSet.getString("carName"), 
                                    resultSet.getString("carModel"), resultSet.getInt("mpg"), 
                                    resultSet.getString("fuelType"), resultSet.getInt("numSeats"),
                                    resultSet.getString("carType"),resultSet.getInt("numDoors"),
                                    resultSet.getDouble("rentRatePerDay"), resultSet.getBoolean("isAvailable"),
                                    resultSet.getInt("destinationLocationId")
																	);

			  result.add(car);
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

	public ArrayList<CarBooking> getCarBookings(String userEmail) {
		ArrayList<CarBooking> carBookings = new ArrayList<>();
		try {
    //public CarRental(int carId, String carName, String carModel, int mpg, String fuelType, int numSeats, String carType,int numDoors, double rentRatePerDay, boolean isAvailable, int destinationLocation) 

				String query = "SELECT BOOKINGS.carID,CARS.carName, userEmail, driverLicenseNumber FROM BOOKINGS,CARS WHERE userEmail = ? AND BOOKINGS.carID=CARS.carID";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, userEmail);
				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
						CarBooking booking = new CarBooking();
            booking.setCarID(resultSet.getInt("carID"));
						booking.setCarName(resultSet.getString("carName"));
						booking.setEmail(resultSet.getString("userEmail"));
						booking.setDriverLicenseNumber(resultSet.getString("driverLicenseNumber"));
						carBookings.add(booking);
				}

				resultSet.close();
				preparedStatement.close();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return carBookings;
	}
}


