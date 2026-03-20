public class DistanceCalculator {
//https://www.geeksforgeeks.org/dsa/haversine-formula-to-findM,-distance-between-two-points-on-a-sphere/
//formula
    public static double getDistance(Airport departure, Airport arrival){
        double rad = 6371;

        double latDeparture = Math.toRadians(departure.getLatitude());
        double latArrival = Math.toRadians(arrival.getLatitude());
        double lonDeparture = Math.toRadians(departure.getLongitude());
        double lonArrival= Math.toRadians(arrival.getLongitude());

        double dLat = latArrival - latDeparture;
        double dLon = lonArrival - lonDeparture;

        double sinLat = Math.sin(dLat/2);
        double sinLon = Math.sin(dLon/2);

        double a = sinLat * sinLat + Math.cos(latDeparture) * Math.cos(latArrival) * sinLon * sinLon;

        double radiansDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        return rad * radiansDistance;
    }


}
