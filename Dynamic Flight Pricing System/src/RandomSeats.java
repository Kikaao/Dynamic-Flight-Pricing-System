import java.util.Random;

public class RandomSeats {

    static Random rand = new Random();


    public static int RandomCapacity(){
        return rand.nextInt(140) + 120;
    }

    public static int RandomSeatsTaken(int capacitu){
        return rand.nextInt(capacitu);
    }
}
