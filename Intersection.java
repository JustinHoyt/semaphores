import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Intersection {
    public Stoplight north;
    public Stoplight south;
    public Stoplight east;
    public Stoplight west;
    
    public DirectionalSemaphore northEastLock;
    public DirectionalSemaphore northWestLock;
    public DirectionalSemaphore southEastLock;
    public DirectionalSemaphore southWestLock;
    
    public DirectionalSemaphore northEntrance;
    public DirectionalSemaphore eastEntrance;
    public DirectionalSemaphore southEntrance;
    public DirectionalSemaphore westEntrance;
    
    public DirectionalSemaphore northExit;
    public DirectionalSemaphore eastExit;
    public DirectionalSemaphore southExit;
    public DirectionalSemaphore westExit;
    
    public HashMap<Integer, DirectionalSemaphore> innerSemaphore;
    public HashMap<Integer, DirectionalSemaphore> outerSemaphore;
    
    public Intersection(){
        north = new Stoplight(Enum.Direction.NORTH, Enum.Color.GREEN);
        south = new Stoplight(Enum.Direction.SOUTH, Enum.Color.GREEN);
        east = new Stoplight(Enum.Direction.EAST, Enum.Color.RED_OR_YELLOW);
        west = new Stoplight(Enum.Direction.WEST, Enum.Color.RED_OR_YELLOW);
        
        northEastLock = new DirectionalSemaphore();
        northWestLock = new DirectionalSemaphore();
        southEastLock = new DirectionalSemaphore();
        southWestLock = new DirectionalSemaphore();
        
        northEntrance = new DirectionalSemaphore();
        eastEntrance  = new DirectionalSemaphore();
        southEntrance = new DirectionalSemaphore();
        westEntrance  = new DirectionalSemaphore();
        
        northExit = new DirectionalSemaphore();
        eastExit  = new DirectionalSemaphore();
        southExit = new DirectionalSemaphore();
        westExit  = new DirectionalSemaphore();
        
        innerSemaphore = new HashMap<Integer, DirectionalSemaphore>();
        innerSemaphore.put(0, northWestLock);
        innerSemaphore.put(1, northEastLock);
        innerSemaphore.put(2, southEastLock);
        innerSemaphore.put(3, southWestLock);
        
        outerSemaphore = new HashMap<Integer, DirectionalSemaphore>();
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