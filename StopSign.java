public class StopSign {
    public Enum.Direction direction;
    boolean carWaiting;
    public StopSign(Enum.Direction direction){
        this.direction = direction;
        this.carWaiting = false;
    }
}