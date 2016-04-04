import java.util.concurrent.Semaphore;

public class Stoplight {
    public enum Color {
        RED, YELLOW, GREEN
    }
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
    public Stoplight(Direction direction, Color color){
        this.direction = direction;
        this.color = color;
    }
    public Semaphore stoplightLock = new Semaphore(1, true);
    public Direction direction;
    public Color color = Color.RED;
}