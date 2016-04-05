import java.util.concurrent.Semaphore;

public class Intersection {
    public Stoplight north;
    public Stoplight south;
    public Stoplight east;
    public Stoplight west;
    public Semaphore northEastLock;
    public Semaphore northWestLock;
    public Semaphore southEastLock;
    public Semaphore southWestLock;
    public HashMap<Integer, Semaphore> semaphore;
    
    public Intersection(){
        north = new Stoplight(Enum.Direction.NORTH, Enum.Color.GREEN);
        south = new Stoplight(Enum.Direction.SOUTH, Enum.Color.GREEN);
        east = new Stoplight(Enum.Direction.EAST, Enum.Color.RED);
        west = new Stoplight(Enum.Direction.WEST, Enum.Color.RED);
        northEastLock = new Semaphore(1, true);
        northWestLock = new Semaphore(1, true);
        southEastLock = new Semaphore(1, true);
        southWestLock = new Semaphore(1, true);
        
        semaphore = new HashMap<Integer, Semaphore>();
        semaphore.put(0, northWestLock);
        semaphore.put(1, northEastLock);
        semaphore.put(2, southEastLock);
        semaphore.put(3, southWestLock);
        
    }
    
    public Semaphore getSemaphore(int semaphoreNumberToGet){
        return this.semaphore.get(semaphoreNumberToGet);
    }
}