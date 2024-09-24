//import java.sql.Date;
import java.sql.Timestamp;

public class Flight {
	public int flightNo;
	public String airline;
	public String departure;
	public String destination;
	public Timestamp departureTimestamp;
	public Timestamp arrivalTimestamp;
	public String duration;

	public Flight(int flightNo, String airline, String departure, Timestamp departureTime, String destination, Timestamp arrivalTime) 
	{
			this.flightNo = flightNo;
			this.airline = airline;
			this.departure = departure;
			this.destination = destination;
			this.departureTimestamp = departureTime;
			this.arrivalTimestamp = arrivalTime;
			this.duration = getDuration();
	}

    public int getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(int flightNo) {
        this.flightNo = flightNo;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getDepartureTime() {
        return departureTimestamp;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTimestamp = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTimestamp;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTimestamp = arrivalTime;
    }

    public String getDuration() {
        long durationMillis = arrivalTimestamp.getTime() - departureTimestamp.getTime();

        long hours = durationMillis / (1000 * 60 * 60);
        long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((durationMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String toString()
	{
		return ("Flight ID: " + flightNo + "\nAirline: " + airline + 
        "\nDepart from " + departure +
        " on " + departureTimestamp + 
        "\nArrive at " + destination +  
        " on " + arrivalTimestamp +
        "\nDuration: " + duration + "\n");
	}
}
