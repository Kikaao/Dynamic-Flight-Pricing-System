import java.util.*;
import java.time.LocalDateTime;
import java.time.Month;

public class Data {

    public static Map<String, Airport> loadAirports(){

        Map<String, Airport> airports = new HashMap<>();

        // =========================
        // SOUTHERN EUROPE
        // =========================
        airports.put("ATH", new Airport("ATH", "Athens", 37.9838, 23.7275));
        airports.put("SKG", new Airport("SKG", "Thessaloniki", 40.5197, 22.9709));
        airports.put("MLA", new Airport("MLA", "Malta", 35.8575, 14.4775));
        airports.put("LCA", new Airport("LCA", "Larnaca", 34.8721, 33.6249));
        airports.put("SOF", new Airport("SOF", "Sofia", 42.6880, 23.4142));
        airports.put("FCO", new Airport("FCO", "Rome", 41.8003, 12.2389));
        airports.put("BCN", new Airport("BCN", "Barcelona", 41.2974, 2.0833));
        airports.put("MAD", new Airport("MAD", "Madrid", 40.4722, -3.5608));
        airports.put("LIS", new Airport("LIS", "Lisbon", 38.7742, -9.1342));
        airports.put("ZAG", new Airport("ZAG", "Zagreb", 45.7429, 16.0688));
        airports.put("IST", new Airport("IST", "Istanbul", 41.2753, 28.7519));



        // =========================
        // CENTRAL EUROPE
        // =========================
        airports.put("VIE", new Airport("VIE", "Vienna", 48.1103, 16.5697));
        airports.put("PRG", new Airport("PRG", "Prague", 50.1008, 14.2632));
        airports.put("BUD", new Airport("BUD", "Budapest", 47.4399, 19.2610));
        airports.put("ZRH", new Airport("ZRH", "Zurich", 47.4581, 8.5555));
        airports.put("BER", new Airport("BER", "Berlin", 52.3667, 13.5033));
        airports.put("MUC", new Airport("MUC", "Munich", 48.3538, 11.7861));


        // =========================
        // NORTHERN EUROPE
        // =========================
        airports.put("CPH", new Airport("CPH", "Copenhagen", 55.6181, 12.6560));
        airports.put("ARN", new Airport("ARN", "Stockholm", 59.6498, 17.9236));
        airports.put("OSL", new Airport("OSL", "Oslo", 60.1976, 11.1004));
        airports.put("HEL", new Airport("HEL", "Helsinki", 60.3172, 24.9633));
        airports.put("RIX", new Airport("RIX", "Riga", 56.9236, 23.9711));


        // =========================
        // WESTERN EUROPE
        // =========================
        airports.put("LHR", new Airport("LHR", "London", 51.47, -0.4543));
        airports.put("MAN", new Airport("MAN", "Manchester", 53.3537, -2.2749));
        airports.put("EDI", new Airport("EDI", "Edinburgh", 55.9500, -3.3725));
        airports.put("CDG", new Airport("CDG", "Paris", 49.0097, 2.5479));
        airports.put("BRU", new Airport("BRU", "Brussels", 50.9014, 4.4844));
        airports.put("AMS", new Airport("AMS", "Amsterdam", 52.3105, 4.7683));
        airports.put("DUB", new Airport("DUB", "Dublin", 53.4273, -6.2436));


        // =========================
        // EASTERN EUROPE
        // =========================
        airports.put("WAW", new Airport("WAW", "Warsaw", 52.2297, 21.0122));
        airports.put("KRK", new Airport("KRK", "Krakow", 50.0770, 19.7848));
        airports.put("KEF", new Airport("KEF", "Reykjavik", 63.9850, -22.6056));


        return airports;
    }

    public static List<Flight> genarateMonthlyFlights (Map <String, Airport> airports, int year, Month month, int count){
        List<Flight> monthlyFlights = new ArrayList<>();

        //Lets keep an airport without any flights
        List<Airport> activeAirports = new ArrayList<>();
        for (Airport a : airports.values()){
            if (!a.getCityCode().equals("KEF"))
                activeAirports.add(a);
        }
        Random rand = new Random();
        int flightsCreated = 0;

        while (flightsCreated < count){

            Airport dep = activeAirports.get(rand.nextInt(activeAirports.size()));
            Airport arr = activeAirports.get(rand.nextInt(activeAirports.size()));

            if (dep.equals(arr)) continue;

            int day = 0;
            int hour = 0;
            int minute = 0;
            if (year%4 == 0) {
                 day = rand.nextInt(27)+1;
                 hour = rand.nextInt(24);
                 minute = rand.nextInt(60);
            }else {
                 day = rand.nextInt(28)+1;
                 hour = rand.nextInt(24);
                 minute = rand.nextInt(60);
            }
            LocalDateTime departureTime = LocalDateTime.of(year,month,day,hour,minute);

            double distance = DistanceCalculator.getDistance(dep, arr);

            //750 km/h avaerage speed + 30 min for landing
            long durationMinutes = (long) ((distance/750) * 60) + 30;
            LocalDateTime arrivalTime = departureTime.plusMinutes(durationMinutes);

            String id = "FL-" + month.toString().substring(0,3) + "--" + String.format("%03d", flightsCreated);
            monthlyFlights.add(new Flight(id,dep,arr,departureTime,arrivalTime));

            flightsCreated++;
        }

        return monthlyFlights;
    }


    public static List<Flight> loadflights(Map <String, Airport> airports){

        List<Flight> hotFlights = new ArrayList<>();
        LocalDateTime day1 = LocalDateTime.of(2026, 2, 20, 8, 0);
        // --- ATHENS HUB (ATH) ---
        hotFlights.add(new Flight("F001", airports.get("ATH"), airports.get("SKG"), day1, day1.plusMinutes(50)));
        hotFlights.add(new Flight("F002", airports.get("ATH"), airports.get("IST"), day1.plusHours(1), day1.plusHours(2).plusMinutes(30)));
        hotFlights.add(new Flight("F003", airports.get("ATH"), airports.get("SOF"), day1.plusHours(2), day1.plusHours(3).plusMinutes(15)));
        hotFlights.add(new Flight("F004", airports.get("ATH"), airports.get("LCA"), day1.plusHours(3), day1.plusHours(4).plusMinutes(45)));
        hotFlights.add(new Flight("F005", airports.get("ATH"), airports.get("FCO"), day1.plusHours(4), day1.plusHours(6)));
        hotFlights.add(new Flight("F006", airports.get("ATH"), airports.get("BCN"), day1.plusHours(5), day1.plusHours(8)));
        hotFlights.add(new Flight("F007", airports.get("ATH"), airports.get("MAD"), day1.plusHours(6), day1.plusHours(9).plusMinutes(30)));
        hotFlights.add(new Flight("F008", airports.get("ATH"), airports.get("LIS"), day1.plusHours(7), day1.plusHours(11)));
        hotFlights.add(new Flight("F009", airports.get("ATH"), airports.get("BUD"), day1, day1.plusHours(2).plusMinutes(15)));
        hotFlights.add(new Flight("F010", airports.get("ATH"), airports.get("VIE"), day1.plusHours(1), day1.plusHours(3).plusMinutes(30)));
        hotFlights.add(new Flight("F011", airports.get("ATH"), airports.get("ZRH"), day1.plusHours(2), day1.plusHours(5)));
        hotFlights.add(new Flight("F012", airports.get("ATH"), airports.get("MUC"), day1.plusHours(8), day1.plusHours(10).plusMinutes(45)));
        hotFlights.add(new Flight("F013", airports.get("ATH"), airports.get("BER"), day1.plusHours(9), day1.plusHours(12)));
        hotFlights.add(new Flight("F014", airports.get("ATH"), airports.get("CDG"), day1.plusHours(10), day1.plusHours(13).plusMinutes(30)));
        hotFlights.add(new Flight("F015", airports.get("ATH"), airports.get("LHR"), day1.plusHours(11), day1.plusHours(14).plusMinutes(50)));
        hotFlights.add(new Flight("F016", airports.get("ATH"), airports.get("AMS"), day1.plusHours(12), day1.plusHours(15).plusMinutes(40)));
        hotFlights.add(new Flight("F017", airports.get("ATH"), airports.get("CPH"), day1.plusHours(13), day1.plusHours(16)));
        hotFlights.add(new Flight("F018", airports.get("ATH"), airports.get("OSL"), day1.plusHours(14), day1.plusHours(18)));
        hotFlights.add(new Flight("F019", airports.get("ATH"), airports.get("HEL"), day1.plusHours(15), day1.plusHours(19)));
        hotFlights.add(new Flight("F020", airports.get("ATH"), airports.get("WAW"), day1.plusHours(16), day1.plusHours(18).plusMinutes(30)));

        // --- LONDON HUB (LHR) ---
        hotFlights.add(new Flight("F021", airports.get("LHR"), airports.get("MAN"), day1, day1.plusMinutes(60)));
        hotFlights.add(new Flight("F022", airports.get("LHR"), airports.get("EDI"), day1.plusHours(1), day1.plusHours(2).plusMinutes(20)));
        hotFlights.add(new Flight("F023", airports.get("LHR"), airports.get("DUB"), day1.plusHours(2), day1.plusHours(3).plusMinutes(15)));
        hotFlights.add(new Flight("F024", airports.get("LHR"), airports.get("CDG"), day1.plusHours(3), day1.plusHours(4).plusMinutes(15)));
        hotFlights.add(new Flight("F025", airports.get("LHR"), airports.get("BRU"), day1.plusHours(4), day1.plusHours(5).plusMinutes(10)));
        hotFlights.add(new Flight("F026", airports.get("LHR"), airports.get("AMS"), day1.plusHours(5), day1.plusHours(6).plusMinutes(20)));
        hotFlights.add(new Flight("F027", airports.get("LHR"), airports.get("BER"), day1.plusHours(6), day1.plusHours(8)));
        hotFlights.add(new Flight("F028", airports.get("LHR"), airports.get("MUC"), day1.plusHours(7), day1.plusHours(9).plusMinutes(45)));
        hotFlights.add(new Flight("F029", airports.get("LHR"), airports.get("ZRH"), day1.plusHours(8), day1.plusHours(10)));
        hotFlights.add(new Flight("F030", airports.get("LHR"), airports.get("VIE"), day1.plusHours(9), day1.plusHours(11).plusMinutes(30)));
        hotFlights.add(new Flight("F031", airports.get("LHR"), airports.get("PRG"), day1.plusHours(10), day1.plusHours(12)));
        hotFlights.add(new Flight("F032", airports.get("LHR"), airports.get("BUD"), day1.plusHours(11), day1.plusHours(13).plusMinutes(45)));
        hotFlights.add(new Flight("F033", airports.get("LHR"), airports.get("FCO"), day1.plusHours(12), day1.plusHours(14).plusMinutes(30)));
        hotFlights.add(new Flight("F034", airports.get("LHR"), airports.get("BCN"), day1.plusHours(13), day1.plusHours(15).plusMinutes(20)));
        hotFlights.add(new Flight("F035", airports.get("LHR"), airports.get("MAD"), day1.plusHours(14), day1.plusHours(16).plusMinutes(40)));
        hotFlights.add(new Flight("F036", airports.get("LHR"), airports.get("LIS"), day1.plusHours(15), day1.plusHours(18)));
        hotFlights.add(new Flight("F037", airports.get("LHR"), airports.get("CPH"), day1.plusHours(16), day1.plusHours(18)));
        hotFlights.add(new Flight("F038", airports.get("LHR"), airports.get("ARN"), day1.plusHours(17), day1.plusHours(19).plusMinutes(30)));
        hotFlights.add(new Flight("F039", airports.get("LHR"), airports.get("OSL"), day1.plusHours(18), day1.plusHours(20).plusMinutes(15)));
        hotFlights.add(new Flight("F040", airports.get("LHR"), airports.get("HEL"), day1.plusHours(19), day1.plusHours(22)));

        // --- AMSTERDAM HUB (AMS) ---
        hotFlights.add(new Flight("F041", airports.get("AMS"), airports.get("BRU"), day1.plusMinutes(30), day1.plusHours(1)));
        hotFlights.add(new Flight("F042", airports.get("AMS"), airports.get("CDG"), day1.plusHours(1), day1.plusHours(2).plusMinutes(15)));
        hotFlights.add(new Flight("F043", airports.get("AMS"), airports.get("LHR"), day1.plusHours(2), day1.plusHours(3).plusMinutes(20)));
        hotFlights.add(new Flight("F044", airports.get("AMS"), airports.get("BER"), day1.plusHours(3), day1.plusHours(4).plusMinutes(30)));
        hotFlights.add(new Flight("F045", airports.get("AMS"), airports.get("MUC"), day1.plusHours(4), day1.plusHours(5).plusMinutes(45)));
        hotFlights.add(new Flight("F046", airports.get("AMS"), airports.get("ZRH"), day1.plusHours(5), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F047", airports.get("AMS"), airports.get("VIE"), day1.plusHours(6), day1.plusHours(8)));
        hotFlights.add(new Flight("F048", airports.get("AMS"), airports.get("PRG"), day1.plusHours(7), day1.plusHours(9)));
        hotFlights.add(new Flight("F049", airports.get("AMS"), airports.get("BUD"), day1.plusHours(8), day1.plusHours(10).plusMinutes(15)));
        hotFlights.add(new Flight("F050", airports.get("AMS"), airports.get("WAW"), day1.plusHours(9), day1.plusHours(11)));
        hotFlights.add(new Flight("F051", airports.get("AMS"), airports.get("KRK"), day1.plusHours(10), day1.plusHours(12)));
        hotFlights.add(new Flight("F052", airports.get("AMS"), airports.get("CPH"), day1.plusHours(11), day1.plusHours(12).plusMinutes(30)));
        hotFlights.add(new Flight("F053", airports.get("AMS"), airports.get("ARN"), day1.plusHours(12), day1.plusHours(14)));
        hotFlights.add(new Flight("F054", airports.get("AMS"), airports.get("OSL"), day1.plusHours(13), day1.plusHours(15)));
        hotFlights.add(new Flight("F055", airports.get("AMS"), airports.get("HEL"), day1.plusHours(14), day1.plusHours(16).plusMinutes(45)));
        hotFlights.add(new Flight("F056", airports.get("AMS"), airports.get("RIX"), day1.plusHours(15), day1.plusHours(17).plusMinutes(30)));
        hotFlights.add(new Flight("F057", airports.get("AMS"), airports.get("FCO"), day1.plusHours(16), day1.plusHours(18).plusMinutes(15)));
        hotFlights.add(new Flight("F058", airports.get("AMS"), airports.get("BCN"), day1.plusHours(17), day1.plusHours(19).plusMinutes(30)));
        hotFlights.add(new Flight("F059", airports.get("AMS"), airports.get("MAD"), day1.plusHours(18), day1.plusHours(21)));
        hotFlights.add(new Flight("F060", airports.get("AMS"), airports.get("LIS"), day1.plusHours(19), day1.plusHours(22).plusMinutes(15)));

        // --- BUDAPEST CHAIN (BUD) ---
        // For testing the ATH -> BUD -> OSL route
        hotFlights.add(new Flight("F061", airports.get("BUD"), airports.get("OSL"), day1.plusHours(4), day1.plusHours(6).plusMinutes(30))); // Valid gap from ATH F009
        hotFlights.add(new Flight("F062", airports.get("BUD"), airports.get("OSL"), day1.plusHours(2).plusMinutes(30), day1.plusHours(5))); // Invalid gap (too short)
        hotFlights.add(new Flight("F063", airports.get("BUD"), airports.get("OSL"), day1.plusHours(11), day1.plusHours(13))); // Invalid gap (too long)
        hotFlights.add(new Flight("F064", airports.get("BUD"), airports.get("ARN"), day1.plusHours(5), day1.plusHours(7)));
        hotFlights.add(new Flight("F065", airports.get("BUD"), airports.get("CPH"), day1.plusHours(6), day1.plusHours(8)));
        hotFlights.add(new Flight("F066", airports.get("BUD"), airports.get("HEL"), day1.plusHours(7), day1.plusHours(9).plusMinutes(30)));
        hotFlights.add(new Flight("F067", airports.get("BUD"), airports.get("VIE"), day1.plusHours(8), day1.plusHours(9)));
        hotFlights.add(new Flight("F068", airports.get("BUD"), airports.get("PRG"), day1.plusHours(9), day1.plusHours(10).plusMinutes(15)));
        hotFlights.add(new Flight("F069", airports.get("BUD"), airports.get("MUC"), day1.plusHours(10), day1.plusHours(11).plusMinutes(30)));
        hotFlights.add(new Flight("F070", airports.get("BUD"), airports.get("BER"), day1.plusHours(11), day1.plusHours(13)));

        // --- PARIS HUB (CDG) ---
        hotFlights.add(new Flight("F071", airports.get("CDG"), airports.get("ZRH"), day1, day1.plusHours(1).plusMinutes(15)));
        hotFlights.add(new Flight("F072", airports.get("CDG"), airports.get("BRU"), day1.plusHours(1), day1.plusHours(2)));
        hotFlights.add(new Flight("F073", airports.get("CDG"), airports.get("AMS"), day1.plusHours(2), day1.plusHours(3).plusMinutes(15)));
        hotFlights.add(new Flight("F074", airports.get("CDG"), airports.get("LHR"), day1.plusHours(3), day1.plusHours(4).plusMinutes(15)));
        hotFlights.add(new Flight("F075", airports.get("CDG"), airports.get("MAD"), day1.plusHours(4), day1.plusHours(6).plusMinutes(10)));
        hotFlights.add(new Flight("F076", airports.get("CDG"), airports.get("LIS"), day1.plusHours(5), day1.plusHours(7).plusMinutes(45)));
        hotFlights.add(new Flight("F077", airports.get("CDG"), airports.get("FCO"), day1.plusHours(6), day1.plusHours(8).plusMinutes(15)));
        hotFlights.add(new Flight("F078", airports.get("CDG"), airports.get("VIE"), day1.plusHours(7), day1.plusHours(9).plusMinutes(15)));
        hotFlights.add(new Flight("F079", airports.get("CDG"), airports.get("MUC"), day1.plusHours(8), day1.plusHours(9).plusMinutes(30)));
        hotFlights.add(new Flight("F080", airports.get("CDG"), airports.get("BER"), day1.plusHours(9), day1.plusHours(10).plusMinutes(45)));

        // --- SCANDINAVIAN ROUTES (OSL, ARN, CPH, HEL) ---
        hotFlights.add(new Flight("F081", airports.get("OSL"), airports.get("HEL"), day1, day1.plusHours(1).plusMinutes(30)));
        hotFlights.add(new Flight("F082", airports.get("OSL"), airports.get("ARN"), day1.plusHours(1), day1.plusHours(2)));
        hotFlights.add(new Flight("F083", airports.get("OSL"), airports.get("CPH"), day1.plusHours(2), day1.plusHours(3).plusMinutes(15)));
        hotFlights.add(new Flight("F084", airports.get("ARN"), airports.get("HEL"), day1.plusHours(3), day1.plusHours(4)));
        hotFlights.add(new Flight("F085", airports.get("ARN"), airports.get("CPH"), day1.plusHours(4), day1.plusHours(5).plusMinutes(10)));
        hotFlights.add(new Flight("F086", airports.get("CPH"), airports.get("HEL"), day1.plusHours(5), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F087", airports.get("HEL"), airports.get("RIX"), day1.plusHours(6), day1.plusHours(7)));
        hotFlights.add(new Flight("F088", airports.get("RIX"), airports.get("WAW"), day1.plusHours(7), day1.plusHours(8).plusMinutes(15)));
        hotFlights.add(new Flight("F089", airports.get("WAW"), airports.get("KRK"), day1.plusHours(8), day1.plusHours(9)));
        hotFlights.add(new Flight("F090", airports.get("KRK"), airports.get("PRG"), day1.plusHours(9), day1.plusHours(10).plusMinutes(30)));

        // --- SOUTHERN EUROPE CONNECTORS (BCN, MAD, LIS, FCO) ---
        hotFlights.add(new Flight("F091", airports.get("BCN"), airports.get("MAD"), day1, day1.plusHours(1).plusMinutes(15)));
        hotFlights.add(new Flight("F092", airports.get("MAD"), airports.get("LIS"), day1.plusHours(2), day1.plusHours(3).plusMinutes(10)));
        hotFlights.add(new Flight("F093", airports.get("LIS"), airports.get("MAD"), day1.plusHours(4), day1.plusHours(5).plusMinutes(15)));
        hotFlights.add(new Flight("F094", airports.get("MAD"), airports.get("BCN"), day1.plusHours(6), day1.plusHours(7).plusMinutes(15)));
        hotFlights.add(new Flight("F095", airports.get("BCN"), airports.get("FCO"), day1.plusHours(8), day1.plusHours(9).plusMinutes(45)));
        hotFlights.add(new Flight("F096", airports.get("FCO"), airports.get("ZAG"), day1.plusHours(10), day1.plusHours(11).plusMinutes(30)));
        hotFlights.add(new Flight("F097", airports.get("ZAG"), airports.get("VIE"), day1.plusHours(12), day1.plusHours(13).plusMinutes(15)));
        hotFlights.add(new Flight("F098", airports.get("LIS"), airports.get("BCN"), day1.plusHours(14), day1.plusHours(15).plusMinutes(45)));
        hotFlights.add(new Flight("F099", airports.get("BCN"), airports.get("ATH"), day1.plusHours(16), day1.plusHours(19)));
        hotFlights.add(new Flight("F100", airports.get("MAD"), airports.get("ATH"), day1.plusHours(17), day1.plusHours(20).plusMinutes(30)));

        // --- RETURNING FLIGHTS (TO ATH) ---
        hotFlights.add(new Flight("F101", airports.get("SKG"), airports.get("ATH"), day1.plusHours(18), day1.plusHours(18).plusMinutes(50)));
        hotFlights.add(new Flight("F102", airports.get("IST"), airports.get("ATH"), day1.plusHours(19), day1.plusHours(20).plusMinutes(30)));
        hotFlights.add(new Flight("F103", airports.get("SOF"), airports.get("ATH"), day1.plusHours(20), day1.plusHours(21).plusMinutes(15)));
        hotFlights.add(new Flight("F104", airports.get("LCA"), airports.get("ATH"), day1.plusHours(21), day1.plusHours(22).plusMinutes(45)));
        hotFlights.add(new Flight("F105", airports.get("FCO"), airports.get("ATH"), day1.plusHours(1), day1.plusHours(3)));
        hotFlights.add(new Flight("F106", airports.get("BCN"), airports.get("ATH"), day1.plusHours(2), day1.plusHours(5)));
        hotFlights.add(new Flight("F107", airports.get("MAD"), airports.get("ATH"), day1.plusHours(3), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F108", airports.get("LIS"), airports.get("ATH"), day1.plusHours(4), day1.plusHours(8)));
        hotFlights.add(new Flight("F109", airports.get("BUD"), airports.get("ATH"), day1.plusHours(5), day1.plusHours(7).plusMinutes(15)));
        hotFlights.add(new Flight("F110", airports.get("VIE"), airports.get("ATH"), day1.plusHours(6), day1.plusHours(8).plusMinutes(30)));

        // --- RETURNING FLIGHTS (TO LHR) ---
        hotFlights.add(new Flight("F111", airports.get("MAN"), airports.get("LHR"), day1.plusHours(7), day1.plusHours(8)));
        hotFlights.add(new Flight("F112", airports.get("EDI"), airports.get("LHR"), day1.plusHours(8), day1.plusHours(9).plusMinutes(20)));
        hotFlights.add(new Flight("F113", airports.get("DUB"), airports.get("LHR"), day1.plusHours(9), day1.plusHours(10).plusMinutes(15)));
        hotFlights.add(new Flight("F114", airports.get("CDG"), airports.get("LHR"), day1.plusHours(10), day1.plusHours(11).plusMinutes(15)));
        hotFlights.add(new Flight("F115", airports.get("BRU"), airports.get("LHR"), day1.plusHours(11), day1.plusHours(12).plusMinutes(10)));
        hotFlights.add(new Flight("F116", airports.get("AMS"), airports.get("LHR"), day1.plusHours(12), day1.plusHours(13).plusMinutes(20)));
        hotFlights.add(new Flight("F117", airports.get("BER"), airports.get("LHR"), day1.plusHours(13), day1.plusHours(15)));
        hotFlights.add(new Flight("F118", airports.get("MUC"), airports.get("LHR"), day1.plusHours(14), day1.plusHours(16).plusMinutes(45)));
        hotFlights.add(new Flight("F119", airports.get("ZRH"), airports.get("LHR"), day1.plusHours(15), day1.plusHours(17)));
        hotFlights.add(new Flight("F120", airports.get("VIE"), airports.get("LHR"), day1.plusHours(16), day1.plusHours(18).plusMinutes(30)));

        // --- RETURNING FLIGHTS (TO AMS) ---
        hotFlights.add(new Flight("F121", airports.get("BRU"), airports.get("AMS"), day1.plusHours(17), day1.plusHours(17).plusMinutes(30)));
        hotFlights.add(new Flight("F122", airports.get("CDG"), airports.get("AMS"), day1.plusHours(18), day1.plusHours(19).plusMinutes(15)));
        hotFlights.add(new Flight("F123", airports.get("LHR"), airports.get("AMS"), day1.plusHours(19), day1.plusHours(20).plusMinutes(20)));
        hotFlights.add(new Flight("F124", airports.get("BER"), airports.get("AMS"), day1.plusHours(20), day1.plusHours(21).plusMinutes(30)));
        hotFlights.add(new Flight("F125", airports.get("MUC"), airports.get("AMS"), day1.plusHours(21), day1.plusHours(22).plusMinutes(45)));
        hotFlights.add(new Flight("F126", airports.get("ZRH"), airports.get("AMS"), day1.plusHours(22), day1.plusHours(23).plusMinutes(30)));
        hotFlights.add(new Flight("F127", airports.get("VIE"), airports.get("AMS"), day1.plusHours(23), day1.plusHours(1).plusMinutes(15)));
        hotFlights.add(new Flight("F128", airports.get("PRG"), airports.get("AMS"), day1.plusHours(0), day1.plusHours(2)));
        hotFlights.add(new Flight("F129", airports.get("BUD"), airports.get("AMS"), day1.plusHours(1), day1.plusHours(3).plusMinutes(15)));
        hotFlights.add(new Flight("F130", airports.get("WAW"), airports.get("AMS"), day1.plusHours(2), day1.plusHours(4)));

        // --- ADDITIONAL CONNECTORS (131 - 200) ---
        hotFlights.add(new Flight("F131", airports.get("ZRH"), airports.get("BER"), day1.plusHours(3), day1.plusHours(4).plusMinutes(30)));
        hotFlights.add(new Flight("F132", airports.get("BER"), airports.get("MUC"), day1.plusHours(5), day1.plusHours(6)));
        hotFlights.add(new Flight("F133", airports.get("MUC"), airports.get("PRG"), day1.plusHours(7), day1.plusHours(8)));
        hotFlights.add(new Flight("F134", airports.get("PRG"), airports.get("VIE"), day1.plusHours(9), day1.plusHours(10)));
        hotFlights.add(new Flight("F135", airports.get("VIE"), airports.get("BUD"), day1.plusHours(11), day1.plusHours(12)));
        hotFlights.add(new Flight("F136", airports.get("MLA"), airports.get("FCO"), day1.plusHours(13), day1.plusHours(14).plusMinutes(30)));
        hotFlights.add(new Flight("F137", airports.get("FCO"), airports.get("MLA"), day1.plusHours(15), day1.plusHours(16).plusMinutes(30)));
        hotFlights.add(new Flight("F138", airports.get("IST"), airports.get("PRG"), day1.plusHours(17), day1.plusHours(19).plusMinutes(30)));
        hotFlights.add(new Flight("F139", airports.get("PRG"), airports.get("IST"), day1.plusHours(20), day1.plusHours(22).plusMinutes(30)));
        hotFlights.add(new Flight("F140", airports.get("ZAG"), airports.get("LIS"), day1.plusHours(23), day1.plusHours(3)));
        hotFlights.add(new Flight("F141", airports.get("LIS"), airports.get("ZAG"), day1.plusHours(4), day1.plusHours(8)));
        hotFlights.add(new Flight("F142", airports.get("DUB"), airports.get("EDI"), day1.plusHours(9), day1.plusHours(10)));
        hotFlights.add(new Flight("F143", airports.get("EDI"), airports.get("MAN"), day1.plusHours(11), day1.plusHours(12)));
        hotFlights.add(new Flight("F144", airports.get("MAN"), airports.get("DUB"), day1.plusHours(13), day1.plusHours(14)));
        hotFlights.add(new Flight("F145", airports.get("DUB"), airports.get("CDG"), day1.plusHours(15), day1.plusHours(17)));
        hotFlights.add(new Flight("F146", airports.get("CDG"), airports.get("DUB"), day1.plusHours(18), day1.plusHours(20)));
        hotFlights.add(new Flight("F147", airports.get("OSL"), airports.get("CPH"), day1.plusHours(21), day1.plusHours(22).plusMinutes(30)));
        hotFlights.add(new Flight("F148", airports.get("CPH"), airports.get("OSL"), day1.plusHours(23), day1.plusHours(0).plusMinutes(30)));
        hotFlights.add(new Flight("F149", airports.get("ARN"), airports.get("RIX"), day1.plusHours(1), day1.plusHours(2)));
        hotFlights.add(new Flight("F150", airports.get("RIX"), airports.get("ARN"), day1.plusHours(3), day1.plusHours(4)));
        hotFlights.add(new Flight("F151", airports.get("HEL"), airports.get("WAW"), day1.plusHours(5), day1.plusHours(6).plusMinutes(45)));
        hotFlights.add(new Flight("F152", airports.get("WAW"), airports.get("HEL"), day1.plusHours(7), day1.plusHours(8).plusMinutes(45)));
        hotFlights.add(new Flight("F153", airports.get("MUC"), airports.get("RIX"), day1.plusHours(9), day1.plusHours(11)));
        hotFlights.add(new Flight("F154", airports.get("RIX"), airports.get("MUC"), day1.plusHours(12), day1.plusHours(14)));
        hotFlights.add(new Flight("F155", airports.get("LCA"), airports.get("MAD"), day1.plusHours(15), day1.plusHours(19)));
        hotFlights.add(new Flight("F156", airports.get("MAD"), airports.get("LCA"), day1.plusHours(20), day1.plusHours(0)));
        hotFlights.add(new Flight("F157", airports.get("ZRH"), airports.get("OSL"), day1.plusHours(1), day1.plusHours(3).plusMinutes(30)));
        hotFlights.add(new Flight("F158", airports.get("OSL"), airports.get("ZRH"), day1.plusHours(4), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F159", airports.get("VIE"), airports.get("MUC"), day1.plusHours(7), day1.plusHours(8)));
        hotFlights.add(new Flight("F160", airports.get("MUC"), airports.get("VIE"), day1.plusHours(9), day1.plusHours(10)));
        hotFlights.add(new Flight("F161", airports.get("ATH"), airports.get("DUB"), day1.plusHours(11), day1.plusHours(15)));
        hotFlights.add(new Flight("F162", airports.get("DUB"), airports.get("ATH"), day1.plusHours(16), day1.plusHours(20)));
        hotFlights.add(new Flight("F163", airports.get("BCN"), airports.get("AMS"), day1.plusHours(21), day1.plusHours(23).plusMinutes(30)));
        hotFlights.add(new Flight("F164", airports.get("AMS"), airports.get("BCN"), day1.plusHours(0), day1.plusHours(2).plusMinutes(30)));
        hotFlights.add(new Flight("F165", airports.get("MAD"), airports.get("CDG"), day1.plusHours(3), day1.plusHours(5)));
        hotFlights.add(new Flight("F166", airports.get("CDG"), airports.get("MAD"), day1.plusHours(6), day1.plusHours(8)));
        hotFlights.add(new Flight("F167", airports.get("FCO"), airports.get("CPH"), day1.plusHours(9), day1.plusHours(12)));
        hotFlights.add(new Flight("F168", airports.get("CPH"), airports.get("FCO"), day1.plusHours(13), day1.plusHours(16)));
        hotFlights.add(new Flight("F169", airports.get("LHR"), airports.get("BER"), day1.plusHours(17), day1.plusHours(19)));
        hotFlights.add(new Flight("F170", airports.get("BER"), airports.get("LHR"), day1.plusHours(20), day1.plusHours(22)));
        hotFlights.add(new Flight("F171", airports.get("MAN"), airports.get("VIE"), day1.plusHours(23), day1.plusHours(2).plusMinutes(30)));
        hotFlights.add(new Flight("F172", airports.get("VIE"), airports.get("MAN"), day1.plusHours(3), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F173", airports.get("EDI"), airports.get("BRU"), day1.plusHours(7), day1.plusHours(8).plusMinutes(45)));
        hotFlights.add(new Flight("F174", airports.get("BRU"), airports.get("EDI"), day1.plusHours(9), day1.plusHours(10).plusMinutes(45)));
        hotFlights.add(new Flight("F175", airports.get("WAW"), airports.get("HEL"), day1.plusHours(11), day1.plusHours(12).plusMinutes(45)));
        hotFlights.add(new Flight("F176", airports.get("HEL"), airports.get("WAW"), day1.plusHours(13), day1.plusHours(14).plusMinutes(45)));
        hotFlights.add(new Flight("F177", airports.get("KRK"), airports.get("OSL"), day1.plusHours(15), day1.plusHours(17).plusMinutes(30)));
        hotFlights.add(new Flight("F178", airports.get("OSL"), airports.get("KRK"), day1.plusHours(18), day1.plusHours(20).plusMinutes(30)));
        hotFlights.add(new Flight("F179", airports.get("RIX"), airports.get("MUC"), day1.plusHours(21), day1.plusHours(23).plusMinutes(15)));
        hotFlights.add(new Flight("F180", airports.get("MUC"), airports.get("RIX"), day1.plusHours(0), day1.plusHours(2).plusMinutes(15)));
        hotFlights.add(new Flight("F181", airports.get("ZAG"), airports.get("LIS"), day1.plusHours(3), day1.plusHours(6).plusMinutes(30)));
        hotFlights.add(new Flight("F182", airports.get("LIS"), airports.get("ZAG"), day1.plusHours(7), day1.plusHours(10).plusMinutes(30)));
        hotFlights.add(new Flight("F183", airports.get("AMS"), airports.get("LIS"), day1.plusHours(11), day1.plusHours(14)));
        hotFlights.add(new Flight("F184", airports.get("LIS"), airports.get("AMS"), day1.plusHours(15), day1.plusHours(18)));
        hotFlights.add(new Flight("F185", airports.get("MAD"), airports.get("PRG"), day1.plusHours(19), day1.plusHours(22)));
        hotFlights.add(new Flight("F186", airports.get("PRG"), airports.get("MAD"), day1.plusHours(23), day1.plusHours(2).plusMinutes(15)));
        hotFlights.add(new Flight("F187", airports.get("ATH"), airports.get("ZRH"), day1.plusHours(3), day1.plusHours(6)));
        hotFlights.add(new Flight("F188", airports.get("ZRH"), airports.get("ATH"), day1.plusHours(7), day1.plusHours(10)));
        hotFlights.add(new Flight("F189", airports.get("LHR"), airports.get("MAD"), day1.plusHours(11), day1.plusHours(13).plusMinutes(30)));
        hotFlights.add(new Flight("F190", airports.get("MAD"), airports.get("LHR"), day1.plusHours(14), day1.plusHours(16).plusMinutes(30)));
        hotFlights.add(new Flight("F191", airports.get("CDG"), airports.get("CPH"), day1.plusHours(17), day1.plusHours(19)));
        hotFlights.add(new Flight("F192", airports.get("CPH"), airports.get("CDG"), day1.plusHours(20), day1.plusHours(22)));
        hotFlights.add(new Flight("F193", airports.get("AMS"), airports.get("VIE"), day1.plusHours(23), day1.plusHours(1).plusMinutes(45)));
        hotFlights.add(new Flight("F194", airports.get("VIE"), airports.get("AMS"), day1.plusHours(2), day1.plusHours(4).plusMinutes(45)));
        hotFlights.add(new Flight("F195", airports.get("IST"), airports.get("BER"), day1.plusHours(5), day1.plusHours(8)));
        hotFlights.add(new Flight("F196", airports.get("BER"), airports.get("IST"), day1.plusHours(9), day1.plusHours(12)));
        hotFlights.add(new Flight("F197", airports.get("ATH"), airports.get("MUC"), day1.plusHours(13), day1.plusHours(15).plusMinutes(45)));
        hotFlights.add(new Flight("F198", airports.get("MUC"), airports.get("ATH"), day1.plusHours(16), day1.plusHours(18).plusMinutes(45)));
        hotFlights.add(new Flight("F199", airports.get("LHR"), airports.get("BCN"), day1.plusHours(19), day1.plusHours(21).plusMinutes(30)));
        hotFlights.add(new Flight("F200", airports.get("BCN"), airports.get("LHR"), day1.plusHours(22), day1.plusHours(0).plusMinutes(30)));

        // --- DIJKSTRA TEST ROUTE: ATH -> MLA -> FCO -> ZRH (cheap) ---
        hotFlights.add(new Flight("F201", airports.get("ATH"), airports.get("MLA"), day1, day1.plusHours(1).plusMinutes(30)));
        hotFlights.add(new Flight("F202", airports.get("MLA"), airports.get("FCO"), day1.plusHours(3), day1.plusHours(4).plusMinutes(15)));
        hotFlights.add(new Flight("F203", airports.get("FCO"), airports.get("ZRH"), day1.plusHours(5).plusMinutes(30), day1.plusHours(7)));
        return hotFlights;

    }
}
