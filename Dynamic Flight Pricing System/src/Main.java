import java.util.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Load data
        Map<String, Airport> airports = Data.loadAirports();
        List<Flight> hotFlights = Data.loadflights(airports);

        // Build graph
        FlightGraph graph = new FlightGraph();
        for (Airport a : airports.values()) {
            graph.addAirport(a);
        }
        for (Flight f : hotFlights) {
            graph.addFlight(f);
        }

        for (Flight f : hotFlights) {
            if (f.getId().equals("F201") || f.getId().equals("F202") || f.getId().equals("F203")) {
                f.setPrice(1.0);
            }
        }

        // Initialize components
        RouteFinder finder = new RouteFinder(graph);
        ReservationManager reservationManager = new ReservationManager(hotFlights);

        // ========== SAMPLE SCENARIOS ==========
        System.out.println("=== DYNAMIC FLIGHT PRICING SYSTEM HARDCODED DEMO ===\n");

        // SAMPLE 1: Find route from Athens to Berlin
        System.out.println("--- SAMPLE 1: Route Finding (BFS) ---");
        System.out.println("Finding route: Athens (ATH) to Berlin (BER)");
        Airport departure = airports.get("ATH");
        Airport arrival = airports.get("BER");
        List<Airport> route = finder.BFS(departure, arrival);
        finder.printRoute(route);
        finder.printDetails(route, "NO");

        // SAMPLE 2: Find a longer route - Athens to Reykjavik
        System.out.println("\n--- SAMPLE 2: Long Route ---");
        System.out.println("Finding route: Athens (ATH) to Reykjavik (KEF)");
        Airport reykjavik = airports.get("KEF");
        List<Airport> longRoute = finder.BFS(departure, reykjavik);
        finder.printRoute(longRoute);
        finder.printDetails(longRoute, "NO");

        // SAMPLE 3: Dynamic pricing demonstration
        System.out.println("\n--- SAMPLE 3: Dynamic Pricing ---");
        System.out.println("Showing how prices change based on seat availability.\n");

        // Find a direct flight to demonstrate pricing
        Flight demoFlight = null;
        for (Flight f : hotFlights) {
            if (f.getId().equals("FL99")) {
                demoFlight = f;
                break;
            }
        }

        if (demoFlight != null) {
            // Set initial distance and price
            double distance = DistanceCalculator.getDistance(demoFlight.getDeparture(), demoFlight.getArrival());
            demoFlight.setDistance(distance);
            double initialPrice = PriceCalculator.price(demoFlight, distance);
            demoFlight.setPrice(initialPrice);

            System.out.println("Flight FL99: " + demoFlight.getDeparture().getCityCode() + " -> " + demoFlight.getArrival().getCityCode());
            System.out.println("Before reservations:");
            System.out.printf("  Seats: %d/%d | Price: %.2f Euros%n",
                demoFlight.getSeatsTaken(), demoFlight.getCapacity(), demoFlight.getPrice());

            // Make 10 reservations
            System.out.println("\n10 reservations being made on FL99");
            for (int i = 0; i < 10; i++) {
                reservationManager.reserve("FL99");
            }

            System.out.println("\nAfter the reservations:");
            System.out.printf("  Seats: %d/%d | Price: %.2f Euros%n",
                demoFlight.getSeatsTaken(), demoFlight.getCapacity(), demoFlight.getPrice());
            System.out.println("Price increased due to reduced seat availability!");
        }

        // SAMPLE 4: Show all direct flights from Athens
        System.out.println("\n--- SAMPLE 4: Direct Flights from Athens ---");
        List<Flight> athensFlights = graph.getFlights(airports.get("ATH"));
        finder.printFromDeparture(athensFlights, "NO");

        System.out.println("\n=== END OF SAMPLES ===");


        ////////////////////
        Scanner read = new Scanner(System.in);

        while (true) {
            System.out.println("Would you like to see an optional menu about flights? yes/no");
            String specific = read.nextLine().toLowerCase();
            if (specific.equals("yes")) {
                Menu.menu(airports, finder, graph);
                break;
            }
            if (specific.equals("no")) {
                break;
            } else
                System.out.println("Wrong input.");
        }
        System.out.println("Program Terminates.");


        ///////////////////

    }

}
