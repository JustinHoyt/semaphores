import java.util.concurrent.Semaphore;

public class DirectionalSemaphore{
    public Semaphore semaphore;
    public int direction;
    
    public DirectionalSemaphore(){
        semaphore = new Semaphore(1, true);
        direction = -1;
    }
}