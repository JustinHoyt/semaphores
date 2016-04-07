import java.util.HashMap;
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
    
    public Semaphore northEntrance;
    public Semaphore eastEntrance;
    public Semaphore southEntrance;
    public Semaphore westEntrance;
    
    public Semaphore northExit;
    public Semaphore eastExit;
    public Semaphore southExit;
    public Semaphore westExit;
    
    public HashMap<Integer, Semaphore> innerSemaphore;
    public HashMap<Integer, Semaphore> outerSemaphore;
    
    public Intersection(){
        north = new Stoplight(Enum.Direction.NORTH, Enum.Color.GREEN);
        south = new Stoplight(Enum.Direction.SOUTH, Enum.Color.GREEN);
        east = new Stoplight(Enum.Direction.EAST, Enum.Color.RED_OR_YELLOW);
        west = new Stoplight(Enum.Direction.WEST, Enum.Color.RED_OR_YELLOW);
        
        northEastLock = new Semaphore(1, true);
        northWestLock = new Semaphore(1, true);
        southEastLock = new Semaphore(1, true);
        southWestLock = new Semaphore(1, true);
        
        northEntrance = new Semaphore(1, true);
        eastEntrance = new Semaphore(1, true);
        southEntrance = new Semaphore(1, true);
        westEntrance = new Semaphore(1, true);
        
        northExit = new Semaphore(1, true);
        eastExit = new Semaphore(1, true);
        southExit = new Semaphore(1, true);
        westExit = new Semaphore(1, true);
        
        innerSemaphore = new HashMap<Integer, Semaphore>();
        innerSemaphore.put(0, northWestLock);
        innerSemaphore.put(1, northEastLock);
        innerSemaphore.put(2, southEastLock);
        innerSemaphore.put(3, southWestLock);
        
        outerSemaphore = new HashMap<Integer, Semaphore>();
        outerSemaphore.put(0, southEntrance);
        outerSemaphore.put(1, northExit);
        outerSemaphore.put(2, westEntrance);
        outerSemaphore.put(3, eastExit);
        outerSemaphore.put(4, northEntrance);
        outerSemaphore.put(5, southExit);
        outerSemaphore.put(6, eastEntrance);
        outerSemaphore.put(7, westExit);
    }
}