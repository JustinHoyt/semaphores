import java.util.concurrent.Semaphore;

public class DirectionalSemaphore{
    public Semaphore semaphore;
    public int originalDirection;
    public int targetDirection;
    
    public DirectionalSemaphore(){
        semaphore = new Semaphore(1, true);
        originalDirection = -1;
        targetDirection = -1;
    }
}