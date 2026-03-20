import java.util.*;
import java.time.format.DateTimeFormatter;

public class FlightGraph implements IFlightGraph {

    private Map<Airport, List<Flight>> adjacencyList = new HashMap<>();


    public void addAirport(Airport airport){
        adjacencyList.putIfAbsent(airport, new ArrayList<>());
    }

    public void addFlight(Flight f){
        Airport departure = f.getDeparture();
        //chekari ama iparxi ke ama den inparxi to dimiourgi
        adjacencyList.putIfAbsent(departure, new ArrayList<>());

        double distance = DistanceCalculator.getDistance(f.getDeparture(),f.getArrival());
        double price = PriceCalculator.price(f,distance);

        f.setDistance(distance);
        f.setPrice(price);

        //pigeni stin lista ke tin ptisi f
        adjacencyList.get(departure).add(f);
    }

    public List<Flight> getFlights(Airport airport){
        //ama iparxi epistrefi "arraylinkedlist" me tis ptisis
        return adjacencyList.getOrDefault(airport, new ArrayList<>());
    }


    public void printGraph(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE dd MMM, HH:mm");
        for (Airport a : adjacencyList.keySet()){
            for (Flight f: adjacencyList.get(a)){
                System.out.println(
                        a.getCityCode() + " --->> " + f.getArrival().getCityCode() + " " +
                                 " |  Seats Taken "+ f.getSeatsTaken() + "/" +f.getCapacity() + " | Available capacity: " + (f.getCapacity() - f.getSeatsTaken()) +
                                String.format( " |  %.1f", DistanceCalculator.getDistance(a,f.getArrival())) + " KM"
                                + String.format(" |  %.2f" , f.getPrice()) + " Euros"
                                + String.format(" | Dep Time  %s" , f.getDepartureTime().format(dtf)));
            }

        }
    }
}



