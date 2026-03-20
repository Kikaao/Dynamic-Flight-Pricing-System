/**
 * Interface for Flight ADT
 * Represents a flight between two airports with capacity, pricing, and booking functionality.
 */
public interface IFlight {

    String getId();

    Airport getDeparture();

    Airport getArrival();

    int getCapacity();

    int getSeatsTaken();

    double getDistance();

    double getPrice();

    void setDistance(double distance);

    void setPrice(double price);

    int reserveSeat();

}
