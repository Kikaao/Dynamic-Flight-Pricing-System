import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Menu {



    public static void menu(Map <String, Airport> airports, RouteFinder finder, FlightGraph graph){
        Scanner read = new Scanner(System.in);


        boolean loop = true;
        while(loop) {

            int exit;
            while (true) {
                System.out.println("\n========Menu========\n");
                System.out.println("1. Specific departure and arrival destinations.");
                System.out.println("2. Print all available flights.");
                System.out.println("0. Exit.");
                System.out.print("Your option: ");
                try {
                    exit = read.nextInt();
                    read.nextLine();
                    if (exit >= 0 && exit <= 2)
                        break;
                    else
                        System.out.println();
                        System.out.println("Choose only a number between 0-2!");

                    System.out.println();
                } catch (Exception e) {
                    System.out.println();
                    System.out.println("Choose only a number between 0-2!");
                    read.nextLine();
                }

            }

            switch (exit){
                case 1:
                    int exit1;
                    while (true) {
                        System.out.println("1. Would you Like the Fastest Route.");
                        System.out.println("2. Would you Like the Cheapest Route.");
                        try {
                            exit1 = read.nextInt();
                            read.nextLine();
                            if (exit1 >= 1 && exit1 <= 2)
                                break;
                            else
                                System.out.println();
                            System.out.println("Choose only a number between 1-2!");

                            System.out.println();
                        } catch (Exception e) {
                            System.out.println();
                            System.out.println("Choose only a number between 1-2!");
                            read.nextLine();
                        }
                    }
                    switch (exit1) {
                        case 1:
                            System.out.print("What is your departure destination?: ");
                            String DepartureCode = read.nextLine().toUpperCase();

                            if (!airports.containsKey(DepartureCode)) {
                                System.out.println("The airport is not supported as a departure destination.");
                                continue;
                            }

                            System.out.print("\nWhat is your arrival destination?: ");
                            String ArrivalCode = read.nextLine().toUpperCase();

                            if (!airports.containsKey(ArrivalCode)) {
                                System.out.println("The airport is not supported as an arrival destination.");
                                continue;
                            }

                            if (ArrivalCode.equals(DepartureCode)){
                                System.out.println("You have same the Arrival and Departure destinations");
                                break;
                            }

                            System.out.println("Would you like seats with more leg room for an extra 20€ in each flight?");
                            System.out.println("yes / no");
                            String legspace = read.nextLine().toUpperCase();

                            Airport departure = airports.get(DepartureCode);
                            Airport arrival = airports.get(ArrivalCode);

                            List<Airport> route = finder.BFS(departure, arrival);

                            finder.printRoute(route);
                            finder.printDetails(route,legspace);

                            break;

                        case 2:
                            System.out.print("What is your departure destination?: ");
                            String DepartureCodeC = read.nextLine().toUpperCase();

                            if (!airports.containsKey(DepartureCodeC)) {
                                System.out.println("The airport is not supported as a departure destination.");
                                continue;
                            }

                            System.out.print("\nWhat is your arrival destination?: ");
                            String ArrivalCodeC = read.nextLine().toUpperCase();

                            if (!airports.containsKey(ArrivalCodeC)) {
                                System.out.println("The airport is not supported as an arrival destination.");
                                continue;
                            }

                            if (ArrivalCodeC.equals(DepartureCodeC)){
                                System.out.println("You have same the Arrival and Departure destinations");
                                break;
                            }

                            System.out.println("Would you like seats with more leg room for an extra 20€ in each flight?");
                            System.out.println("yes / no");
                            String legspaceC = read.nextLine().toUpperCase();

                            Airport departureC = airports.get(DepartureCodeC);
                            Airport arrivalC = airports.get(ArrivalCodeC);

                            List<Airport> routeC = finder.Dijkstra(departureC, arrivalC);

                            finder.printRoute(routeC);
                            finder.printDetails(routeC,legspaceC);

                            break;

                    }
                    break;

                case 2:
                    graph.printGraph();
                    break;
                case 0:
                    loop = false;
                    break;
            }
        }
    }
}

