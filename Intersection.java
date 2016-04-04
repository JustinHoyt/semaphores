import java.util.concurrent.Semaphore;

public class Intersection {
    public enum Color {
        RED, YELLOW, GREEN
    }
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
    
    public Stoplight north;
    public Stoplight south;
    public Stoplight east;
    public Stoplight west;
    public Semaphore northEastLock;
    public Semaphore northWestLock;
    public Semaphore southEastLock;
    public Semaphore southWestLock;
    
    public Intersection(){
        north = new Stoplight(Direction.NORTH, Color.GREEN);
        south = new Stoplight(Direction.SOUTH, Color.GREEN);
        east = new Stoplight(Direction.EAST, Color.RED);
        west = new Stoplight(Direction.WEST, Color.RED);
        northEastLock = new Semaphore(1, true);
        northWestLock = new Semaphore(1, true);
        southEastLock = new Semaphore(1, true);
        southWestLock = new Semaphore(1, true);
    }
}