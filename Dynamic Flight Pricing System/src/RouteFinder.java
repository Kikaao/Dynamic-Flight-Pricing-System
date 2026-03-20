import java.util.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class RouteFinder implements IRouteFinder {

    private FlightGraph flight;

    public RouteFinder(FlightGraph graph) {
        this.flight = graph;
    }

    public List<Airport> Dijkstra(Airport departure, Airport arival){

        //Stores the cheapest cost of each Airport
        Map<Airport, Double> minCost = new HashMap<>();
        //Parent of each Airport
        Map<Airport, Airport> parent = new HashMap<>();
        //Priority Queue
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));


        minCost.put(departure,0.0);
        pq.add(new Node(departure,0.0, null));


        while (!pq.isEmpty()){
            Node current = pq.poll();
            Airport currentAir = current.airport;

            if (currentAir.equals(arival))
                break;

            if (current.cost > minCost.getOrDefault(currentAir, Double.MAX_VALUE))
                continue;

            for (Flight f : flight.getFlights(currentAir)) {
                Airport neighbor = f.getArrival();
                if (current.lastFlight!= null){
                    Duration gapTime = Duration.between(current.lastFlight.getArrivalTime(), f.getDepartureTime());
                    long minutes = gapTime.toMinutes();

                    if (minutes < 60 || minutes > 480)
                        continue;
                }


                double cost = minCost.get(currentAir) + f.getPrice();
                if (cost < minCost.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    minCost.put(neighbor, cost);
                    parent.put(neighbor, currentAir);
                    pq.add(new Node(neighbor, cost, f));
                }
            }
        }

        List<Airport> route = new ArrayList<>();

        if(!parent.containsKey(arival)){
            System.out.println("No route found between " + departure.getCityCode() + " and " + arival.getCityCode());
            return new ArrayList<>();
        }

        Airport curr = arival;
        while (curr != null){
            route.addFirst(curr);
            curr = parent.get(curr);
        }

        return route;
    }

    private static class Node {
        Airport airport;
        double cost;
        Flight lastFlight;
        Node(Airport airport, double cost, Flight lastFlight) {
            this.airport = airport;
            this.cost = cost;
            this.lastFlight = lastFlight;
        }
    }

    public List<Airport> BFS(Airport departure, Airport arival) {

        Set<Airport> VisNode = new HashSet<>();

        LinkedList<Airport> queue = new LinkedList<>();

        //gemini eixe tin idea gia map ithela list
        Map<Airport, Airport> path = new HashMap<>();

        //Prevents to from visiting the same
        VisNode.add(departure);
        //were we pull the data
        queue.add(departure);
        //a map that sayes from which airport we came from
        path.put(departure,null);


        while (!queue.isEmpty()){
            //poll is remove but without expetion at null it finds it normal /bazi to airport pou ine stin sira se object
            //Which airport are we in
            Airport current = queue.poll();

            if (current.equals(arival))
                break;


            //oles i ptisis apo to to airport
            for (Flight f : flight.getFlights(current)){
                //next is a close airport
                Airport next = f.getArrival();

                if(!VisNode.contains(next)){
                    VisNode.add(next); // visited
                    //put it in queue to search the new airport
                    queue.add(next); // add to the queue
                    path.put(next, current); // save the path
                }

            }

        }

        if(!path.containsKey(arival)){
            System.out.println("No route found between " + departure.getCityCode() + " and " + arival.getCityCode());
            return new ArrayList<>();
        }

        List<Airport> route = new ArrayList<>();

        while (arival != null) {
            route.addFirst(arival); // ath , bud, zrh
            arival = path.get(arival); // ath = (ath ,_ ) = bud
        }

        return route;
    }

    public void printRoute(List<Airport> route) {
        if (route.size()<=1) {
            System.out.println("No route found.");
            return;
        }
        System.out.println();
    }

    public void printDetails(List<Airport> route, String Legs){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE dd MMM, HH:mm");
        int temp = route.size() - 2;

        if (!Legs.equals("YES") && !Legs.equals("NO")) {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            return;
        }

        double legFee = Legs.equals("YES") ? 20 : 0;

        if (temp >= 0) {
            System.out.println(temp == 0 ? " ===== Flight Description ===== \n" : " ===== Flights Description ===== \n");

            double TotalPrice = 0;
            double TotalDistance = 0;

            for (int i = 0; i < route.size() - 1; i++) {
                Airport departure = route.get(i);
                Airport arival = route.get(i + 1);

                Flight nextStop = null;
                for (Flight f : flight.getFlights(departure)) {
                    if (f.getArrival().equals(arival))
                        nextStop = f;
                }

                if (nextStop == null) {
                    System.out.println("Error: No direct flight found between " +
                            departure.getCityCode() + " and " + arival.getCityCode());
                    return;
                }

                TotalPrice += nextStop.getPrice() + legFee;
                TotalDistance += DistanceCalculator.getDistance(departure, nextStop.getArrival());

                System.out.println(departure.getCityCode() + " --->> " + nextStop.getArrival().getCityCode()
                        + " | Seats Taken " + nextStop.getSeatsTaken() + "/" + nextStop.getCapacity()
                        + " | Available capacity: " + (nextStop.getCapacity() - nextStop.getSeatsTaken())
                        + String.format(" | %.1f", DistanceCalculator.getDistance(departure, nextStop.getArrival())) + " KM"
                        + String.format(" | %.2f", nextStop.getPrice()) + " Euros"
                        + String.format(" | Dep Time  %s", nextStop.getDepartureTime().format(dtf)));
            }

            System.out.println("Stops: " + temp);
            System.out.println(String.format("Final Price: %.2f", TotalPrice) + String.format("\nDistance Covered: %.1f", TotalDistance));
        }
    }

    public void printFromDeparture(List <Flight> directFlights, String Legs){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE dd MMM, HH:mm");

        int temp = directFlights.size() ;
        if (Legs.equals("YES")){
            if (temp >= 1) {
                for (int i = 0; i < directFlights.size(); i++) {
                    Flight direct = directFlights.get(i);

                    System.out.println(direct.getDeparture().getCityCode() + " --->> " + direct.getArrival().getCityCode()
                            + " | Seats Taken " + direct.getSeatsTaken() + "/" + direct.getCapacity()
                            + " | Available capacity: " + (direct.getCapacity() - direct.getSeatsTaken())
                            + String.format(" | %.1f", DistanceCalculator.getDistance(direct.getDeparture(), direct.getArrival())) + " KM"
                            + String.format(" | %.2f", direct.getPrice() + 20) + " Euros"
                            + String.format(" | Dep Time  %s" , direct.getDepartureTime().format(dtf)));
                }
            }
        } else if (Legs.equals("NO")) {
            if (temp >= 1) {
                for (int i = 0; i < directFlights.size(); i++) {
                    Flight direct = directFlights.get(i);

                    System.out.println(direct.getDeparture().getCityCode() + " --->> " + direct.getArrival().getCityCode()
                            + " | Seats Taken " + direct.getSeatsTaken() + "/" + direct.getCapacity()
                            + " | Available capacity: " + (direct.getCapacity() - direct.getSeatsTaken())
                            + String.format(" | %.1f", DistanceCalculator.getDistance(direct.getDeparture(), direct.getArrival())) + " KM"
                            + String.format(" | %.2f", direct.getPrice()) + " Euros"
                            + String.format(" | Dep Time  %s" , direct.getDepartureTime().format(dtf)));
                }
            }
        }else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }


}