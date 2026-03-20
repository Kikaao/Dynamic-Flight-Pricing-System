public class Airport implements IAirport {

    private String cityCode;
    private String city;
    private double latitude;
    private double longitude;

    public Airport(String cityCode, String city, double latitude, double longitude){
        this.cityCode=cityCode;
        this.city=city;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getCityCode(){
        return cityCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    //https://www.geeksforgeeks.org/java/equals-hashcode-methods-java/
    public int hashCode(){
        return cityCode.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airport airport = (Airport) o;
        return cityCode.equals(airport.cityCode);
    }


}
