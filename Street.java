public class Street{
    Stoplight stoplight;
    Queue<Car> carQueue;
    
    Street(){
        carQueue = new LinkedList<Car>();
        stoplight = new Stoplight();
    }
}