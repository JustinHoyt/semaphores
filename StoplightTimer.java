import java.text.NumberFormat;
import java.util.Timer;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class StoplightTimer{
    public Enum.Color northOrSouthColor;
    public Enum.Color eastOrWestColor;
    Timer stoplightTimer;
    NumberFormat formatter;

    public StoplightTimer(){
        stoplightTimer = new Timer();
        northOrSouthColor = Enum.Color.GREEN;
        eastOrWestColor = Enum.Color.RED_OR_YELLOW;
        formatter = new DecimalFormat("#0.0");
        
        double startTime = System.currentTimeMillis();
    
        stoplightTimer.scheduleAtFixedRate(new TimerTask(){
            private int seconds;
            public void run(){
                //elapsed time
                double currentTime   = (System.currentTimeMillis() - startTime) / 1000.0;
                if(seconds < 10){
                    //System.out.println("GREEN: " + seconds);
                    northOrSouthColor = Enum.Color.GREEN;
                    eastOrWestColor = Enum.Color.RED_OR_YELLOW;
                }
                else if(seconds < 20){
                    //System.out.println("RED: " + seconds);
                    northOrSouthColor = Enum.Color.GREEN;
                    eastOrWestColor = Enum.Color.RED_OR_YELLOW;
                }
                
                System.out.println(formatter.format(currentTime));
                seconds = (seconds + 1) % 20;
            }
        }, 0, 1000);
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