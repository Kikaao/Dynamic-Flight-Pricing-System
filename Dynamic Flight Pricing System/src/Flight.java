import java.time.LocalDateTime;
public class Flight implements IFlight {

    private String id;
    private Airport departure;
    private Airport arrival;
    private int capacity;
    private int seatsTaken;
    private  double distance;
    private double price;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Flight(String id, Airport departure, Airport arrival, LocalDateTime dep, LocalDateTime arr){
        this.id=id;
        this.departure=departure;
        this.arrival=arrival;
        this.capacity = RandomSeats.RandomCapacity();
        this.seatsTaken = RandomSeats.RandomSeatsTaken(this.capacity);
        this.departureTime = dep;
        this.arrivalTime = arr;
    }


    public String getId(){
        return id;
    }

    public Airport getDeparture(){
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSeatsTaken() {
        return seatsTaken;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getDistance() {
        return distance;
    }

    public double getPrice(){
        return price;
    }

    public LocalDateTime getDepartureTime(){
        return departureTime;
    }

    public LocalDateTime getArrivalTime(){
        return arrivalTime;
    }

    public int reserveSeat(){
        if ((capacity-seatsTaken) > 0){
            seatsTaken++;
        }
        return seatsTaken;
    }

}
