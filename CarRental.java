import java.sql.*;
import java.util.*;


public class CarRental 
{
	// properties of car
	public int carId;
	public String carName;
	public String carModel;
	public int mpg;
	public String fuelType;
	public int numSeats;
	public String carType;
	public int numDoors;
	public double rentRatePerDay;
	public boolean isAvailable;
	public int destinationLocation;

	public CarRental() 
	{
	// Initialize fields with default values
		this.carId = 0;
		this.carName = "";
		this.carModel = "";
		this.mpg = 0;
		this.fuelType = "";
		this.numSeats = 0;
		this.carType = "";
		this.numDoors = 0;
		this.rentRatePerDay = 0.0;
		this.isAvailable = true;
		this.destinationLocation = 0;
	}

	public CarRental(int carId, String carName, String carModel, int mpg, String fuelType, int numSeats, String carType,int numDoors, double rentRatePerDay, boolean isAvailable, int destinationLocation) 
	{
		this.carId = carId;
		this.carName = carName;
		this.carModel = carModel;
		this.mpg = mpg;
		this.fuelType = fuelType;
		this.numSeats = numSeats;
		this.carType = carType;
		this.numDoors = numDoors;
		this.rentRatePerDay = rentRatePerDay;
		this.isAvailable = isAvailable;
		this.destinationLocation = destinationLocation;
	}

	// getters and setters
	
	public int getCarId(){
		return carId;
	}

	public void setCarId(int carId){
		this.carId = carId;
	}
	
	public String getCarName() {
			return carName;
	}

	public void setCarName(String carName) {
			this.carName = carName;
	}

	public String getCarModel() {
			return carModel;
	}

	public void setCarModel(String carModel) {
			this.carModel=carModel;
	}

	public int getMPG()
	{
		return mpg;
	}

	public void setMPG(int mpg)
	{
		this.mpg = mpg;
	}

	public String getFuelType()
	{
		return fuelType;
	}

	public void setFuelType(String fuelType)
	{
		this.fuelType = fuelType;
	}

	public int getNumSeats()
	{
		return numSeats;
	}

	public void setNumSeats(int numSeats)
	{
		this.numSeats = numSeats;
	}

	public String getCarType()
	{
		return carType;
	}

	public void setCarType(String carType)
	{
		this.carType = carType;
	}

	public int getNumDoors()
	{
		return numDoors;
	}

	public void setNumDoors(int numDoors)
	{
		this.numDoors = numDoors;
	}

	public double getRentRatePerDay() 
	{
		return rentRatePerDay;
	}

	public void setRentRatePerDay(double rentRatePerDay) 
	{
		this.rentRatePerDay = rentRatePerDay;
	}

	public boolean getIsAvailable() 
	{
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) 
	{
		this.isAvailable = isAvailable;
	}

	public int getDestinationLocation()
	{
		return destinationLocation;
	}

	public void setDestinationLocation(int destinationLocation)
	{
		this.destinationLocation = destinationLocation;
	}


	@Override
	public String toString()
	{
	return ("Car Name: " + carName + 
			"\nCar Model: " + carModel +
			"\nMiles Per Gallon: " + mpg +  
			"\nFuel Type: " + fuelType + 
			"\nNumber of Seats: " + numSeats + 
			"\nCar Type: " + carType + 
			"\nNumber of Doors: " + numDoors + 
			"\nRent Rate Per Day: " + rentRatePerDay + "\n");
	}
}


