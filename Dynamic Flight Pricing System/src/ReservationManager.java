import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationManager implements IReservationManager {

    private Map<String, Flight> flightMap;

    public ReservationManager(List<Flight> flights) {
        this.flightMap = new HashMap<>();
        for (Flight flight : flights) {
            flightMap.put(flight.getId(), flight);
        }
    }

    public void reserve(String flightId) {
        Flight flight = flightMap.get(flightId);
        if (flight != null) {
            flight.reserveSeat();
            double distance = flight.getDistance();
            double price = PriceCalculator.price(flight, distance);
            flight.setPrice(price);
        }
    }

}
