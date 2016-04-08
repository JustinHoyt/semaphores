class Driver{
     public static Car[] carArray;
     public static Runnable[] runCarArray;
     public static Thread[] carThreadArray;
     public static Street[] street;
     public static final int NORTH = 0;
     public static final int EAST = 1;
     public static final int SOUTH = 2;
     public static final int WEST = 3;
     public static StoplightTimer stoplightTimer;

     public static void main(String[] args) {
          carArray = new Car[8];
          runCarArray = new Runnable[8];
          carThreadArray = new Thread[8];
          street = new Street[4];
          stoplightTimer = new StoplightTimer();

          street[NORTH] = new Street(Enum.Direction.NORTH);
          street[EAST] = new Street(Enum.Direction.EAST);
          street[SOUTH] = new Street(Enum.Direction.SOUTH);
          street[WEST] = new Street(Enum.Direction.WEST);

          carArray[0] = new Car(0, 1.1, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[1] = new Car(1, 2.0, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[2] = new Car(2, 3.3, Enum.Direction.NORTH, Enum.Direction.WEST);
          carArray[3] = new Car(3, 3.5, Enum.Direction.SOUTH, Enum.Direction.SOUTH);
          carArray[4] = new Car(4, 4.2, Enum.Direction.SOUTH, Enum.Direction.EAST);
          carArray[5] = new Car(5, 4.4, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[6] = new Car(6, 5.7, Enum.Direction.EAST, Enum.Direction.NORTH);
          carArray[7] = new Car(7, 5.9, Enum.Direction.WEST, Enum.Direction.NORTH);

        for(int i = 0; i < 8; i++) {
            for(int direction = 0; direction < 4; direction++){
                if (carArray[i].originalDirection.getNumber() == direction) {
                    street[direction].carQueue.add(carArray[i]);
                }
            }
        }

          while(!street[NORTH].carQueue.isEmpty() ||    // while queues aren't empty
                  !street[EAST].carQueue.isEmpty()  ||
                  !street[SOUTH].carQueue.isEmpty() ||
                  !street[WEST].carQueue.isEmpty()){
               for(int direction = 0; direction < 4; direction++){
                    if(!street[direction].carQueue.isEmpty() &&
                            stoplightTimer.getCurrentTime()  >= street[direction].carQueue.peek().timer){
                        Runnable runnableToStart = new CrossIntersection(street[direction], stoplightTimer);
                        Thread threadToStart = new Thread(runnableToStart);
                        threadToStart.start();
                    }
               }
          }
     }
}

class CrossIntersection implements Runnable {
    private Car car;
    private StoplightTimer stoplightTimer;
    private Street street;
     Intersection intersection = new Intersection();

     public CrossIntersection(Street street, StoplightTimer stoplightTimer){
          this.street = street;
          this.car = street.carQueue.poll();
          this.stoplightTimer = stoplightTimer;
     }

     @Override
     public void run() {
            try{
                car.timer = stoplightTimer.getCurrentTime();
                //spin until the light is green
                while(((car.originalDirection == Enum.Direction.NORTH || car.originalDirection == Enum.Direction.SOUTH) && stoplightTimer.northOrSouthColor == Enum.Color.RED_OR_YELLOW) ||
                    ((car.originalDirection == Enum.Direction.EAST || car.originalDirection == Enum.Direction.WEST)&& stoplightTimer.eastOrWestColor == Enum.Color.RED_OR_YELLOW)) {
                    Thread.sleep(50);
                }
                
                car.arriveIntersection(intersection);
                car.timer = stoplightTimer.getCurrentTime();
                car.crossIntersection();
                car.timer = stoplightTimer.getCurrentTime();
                car.exitIntersection(intersection);

            } catch (Exception ignored) {
               System.out.println("RUN FAILED");
        }
    }
}