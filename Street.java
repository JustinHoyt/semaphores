import java.util.LinkedList;
import java.util.Queue;

public class Street{
    Stoplight stoplight;
    Queue<Thread> carQueue;
    
    Street(Enum.Direction direction){
        carQueue = new LinkedList<Thread>();
        stoplight = new Stoplight(direction, Enum.Color.GREEN);
    }
}