public class StoplightTimer{
    public StoplightTimer(){
        Timer stoplightTimer = new Timer();
        Enum.Color northOrSouthColor = Enum.Color.GREEN;
        Enum.Color eastOrWestColor = Enum.Color.RED;
        NumberFormat formatter = new DecimalFormat("#0.0");
        
        double startTime = System.currentTimeMillis();
    
        stoplightTimer.scheduleAtFixedRate(new TimerTask(){
            private int seconds;
            public void run(){
                //elapsed time
                double currentTime   = (System.currentTimeMillis() - startTime) / 1000.0;
                if(seconds < 8){
                    //System.out.println("GREEN: " + seconds);
                    northOrSouthColor = Enum.Color.GREEN;
                    eastOrWestColor = Enum.Color.YELLOW;
                }
                if(seconds < 10){
                    //System.out.println("GREEN: " + seconds);
                    northOrSouthColor = Enum.Color.GREEN;
                    eastOrWestColor = Enum.Color.RED;
                }
                else if(seconds < 12){
                    //System.out.println("YELLOW: " + seconds);
                    northOrSouthColor = Enum.Color.YELLOW;
                    eastOrWestColor = Enum.Color.GREEN;
                }
                else if(seconds < 18){
                    //System.out.println("RED: " + seconds);
                    northOrSouthColor = Enum.Color.RED;
                    eastOrWestColor = Enum.Color.YELLOW;
                }
                else if(seconds < 20){
                    //System.out.println("RED: " + seconds);
                    northOrSouthColor = Enum.Color.RED;
                    eastOrWestColor = Enum.Color.RED;
                }
                
                System.out.println(formatter.format(currentTime));
                seconds = (seconds + 1) % 20;
            }
        }, 0, 2000);
    }
    
    public Enum.Color getStoplightColor(Enum.Direction direction){
        if(direction == Enum.Direction.NORTH ||
           direction == Enum.Direction.SOUTH){
            if(color == Enum.Color.GREEN){
                
            }
        }
        return color;
    }
    
    public double getCurrentTime(){
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }
}