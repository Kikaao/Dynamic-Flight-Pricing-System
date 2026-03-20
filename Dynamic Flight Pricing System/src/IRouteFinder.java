import java.util.List;


public interface IRouteFinder {


    List<Airport> BFS(Airport departure, Airport arrival);
    List<Airport> Dijkstra(Airport departure, Airport arrival);


    void printRoute(List<Airport> route);


    void printDetails(List<Airport> route, String legs);


    void printFromDeparture(List<Flight> directFlights, String legs);

}
