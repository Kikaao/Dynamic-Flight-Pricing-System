public class PriceCalculator {

    public static double price(Flight x, double distanceKm){

        double basePrice = 20;
        double distancePrice = distanceKm/10;
        double loadFactor = 1 + ((double)x.getSeatsTaken() / x.getCapacity());


        return (basePrice + distancePrice) * loadFactor;
    }


}
