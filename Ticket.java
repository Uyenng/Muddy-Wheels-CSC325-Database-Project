import java.sql.Timestamp;

public class Ticket 
{
  public int flightId;
	public String airline;
	public String departure;
	public String destination;
	public Timestamp departureTimestamp;
	public Timestamp arrivalTimestamp;
  public int ticketID;
  public String classType;
  public double fare;
  
  // Constructor with no ticketId (to be used before insertion)
  public Ticket(int flightId, String classType, double fare) 
  {
    this.flightId = flightId;        
    this.classType = classType;
    this.fare = fare;
  }

  // Constructor with ticketId (to be used after insertion)
  public Ticket(int ticketId, int flightId, String classType, double fare) 
  {
    this.ticketID = ticketId;
    this.flightId = flightId;        
    this.classType = classType;
    this.fare = fare;
  }

  // Display reservation
  public Ticket(int ticketID, int flightId, String airline, String departure, Timestamp departureTimestamp, 
                String destination, Timestamp arrivalTimestamp, String classType, double fare) 
  {
    this.ticketID = ticketID;
    this.flightId = flightId;
    this.airline = airline;
    this.departure = departure;
    this.departureTimestamp = departureTimestamp;
    this.destination = destination;
    this.arrivalTimestamp = arrivalTimestamp;
    this.classType = classType;
    this.fare = fare;
  }

  public int getTicketId() 
  {
    return ticketID;
  }

  public void setTicketId(int ticketId) 
  {
    this.ticketID = ticketId;
  }

  public int getFlightId() 
  {
    return flightId;
  }

  public void setFlightId(int flightId) 
  {
    this.flightId = flightId;
  }

  public String getClassType() 
  {
    return classType;
  }

  public void setClassType(String classType) 
  {
    this.classType = classType;
  }

  public double getFare() 
  {
    return fare;
  }

  public void setFare(double fare) 
  {
    this.fare = fare;
  }

  public String toString()
	{
 		return ("Ticket ID: " + ticketID + 
            "\nFlight ID: " + flightId + 
            "\nClass: " + classType +  
 		        "\nFare: " + fare + "\n");
 	}

  public String toStringReservation()
	{
 		return ("Ticket ID: " + ticketID + 
            "\nFlight ID: " + flightId + 
            "\nAirline: " + airline + 
            "\nDepart from: " + departure + 
            "\nDeparture Time: " + departureTimestamp + 
            "\nArrive at: " + destination + 
            "\nArrival Time: " + arrivalTimestamp + 
            "\nClass: " + classType +  
 		        "\nFare: " + fare + "\n");
 	}
}
