import java.util.List;


public interface IFlightGraph {


    void addAirport(Airport airport);

    void addFlight(Flight flight);

    List<Flight> getFlights(Airport airport);

    void printGraph();

}
