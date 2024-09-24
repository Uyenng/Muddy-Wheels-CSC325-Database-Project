import java.sql.*;
import java.util.*;

public class CarBooking 
{
  public int carId;
	public String carName;
	public String userEmail;
	public String driverLicenseNumber;

	public CarBooking() 
	{
	// Initialize fields with default values
		this.carId = -1;
    this.carName = "";
		this.userEmail = "";
		this.driverLicenseNumber ="";
	}

	public CarBooking(int carId, String carName, String userEmail, String driverLicenseNumber) 
	{
    this.carId = carId;
		this.carName = carName;
		this.userEmail = userEmail;
		this.driverLicenseNumber = driverLicenseNumber;
  }
	// getters and setters
	
	public String getCarName() {
			return carName;
	}

	public void setCarName(String carName) {
			this.carName = carName;
	}

	public String getEmail() {
			return userEmail;
	}

	public void setEmail(String userEmail) {
			this.userEmail = userEmail;
	}

	public String getDriverLicenseNumber() {
			return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
			this.driverLicenseNumber = driverLicenseNumber;
	}

  public int getCarID() {
			return carId;
	}

	public void setCarID(int carId) {
			this.carId = carId;
	}

	@Override
	public String toString()
	{
	return ("Car ID: " + carId + 
        "\nName: " + carName + 
			"\nPEmail: " + userEmail +
			"\nDriver's License: " + driverLicenseNumber+  
			"\n");
	}
}


