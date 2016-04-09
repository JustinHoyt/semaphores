import java.util.LinkedList;
import java.util.Queue;

public class Street{
    Stoplight stoplight;
    Queue<Car> carQueue;
    
    Street(Enum.Direction direction){
        carQueue = new LinkedList<Car>();
        stoplight = new Stoplight(direction, Enum.Color.GREEN);
    }
}