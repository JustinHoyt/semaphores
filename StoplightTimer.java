import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class StoplightTimer{
    public Enum.Color northOrSouthColor;
    public Enum.Color eastOrWestColor;
    public Timer stoplightTimer;
    public double startTime;
    private boolean isNorthSouthGreen;

    public StoplightTimer(){
        stoplightTimer = new Timer();
        northOrSouthColor = Enum.Color.GREEN;
        eastOrWestColor = Enum.Color.RED_OR_YELLOW;
        startTime = System.currentTimeMillis();
        isNorthSouthGreen = true;

        stoplightTimer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                double currentTime = (System.currentTimeMillis() - startTime) / 1000.0;
                if(isNorthSouthGreen == true){
                    northOrSouthColor = Enum.Color.GREEN;
                    eastOrWestColor = Enum.Color.RED_OR_YELLOW;
                }
                else if(isNorthSouthGreen == false){
                    northOrSouthColor = Enum.Color.RED_OR_YELLOW;
                    eastOrWestColor = Enum.Color.GREEN;
                }
                isNorthSouthGreen = !isNorthSouthGreen;
            }
        }, 0, 20000);
    }
    
    public Enum.Color getStoplightColor(Enum.Direction direction){
        if(direction == Enum.Direction.NORTH ||
           direction == Enum.Direction.SOUTH){
            return northOrSouthColor;
        }
        else{
            return eastOrWestColor;
        }
    }
    
    public double getCurrentTime(){
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }
}