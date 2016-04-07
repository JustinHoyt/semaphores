import java.io.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Driver{
     public static Car[] carArray;
     public static Runnable[] runCarArray;
     public static Thread[] carThreadArray;
     public static Street[] street;
     
     public static void main(String[] args) {
          carArray = new Car[8];
          runCarArray = new Runnable[8];
          carThreadArray = new Thread[8];
          street = new Street[4];

          northStreet = new Street(Enum.Direction.NORTH);
          eastStreet = new Street(Enum.Direction.EAST);
          southStreet = new Street(Enum.Direction.SOUTH);
          westStreet = new Street(Enum.Direction.WEST);

          carArray[0] = new Car(0, 1.1, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[1]= new Car(1, 2.0, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[2]= new Car(2, 3.3, Enum.Direction.NORTH, Enum.Direction.WEST);
          carArray[3]= new Car(3, 3.5, Enum.Direction.SOUTH, Enum.Direction.SOUTH);
          carArray[4]= new Car(4, 4.2, Enum.Direction.SOUTH, Enum.Direction.EAST);
          carArray[5]= new Car(5, 4.4, Enum.Direction.NORTH, Enum.Direction.NORTH);
          carArray[6]= new Car(6, 5.7, Enum.Direction.EAST, Enum.Direction.NORTH);
          carArray[7]= new Car(7, 5.9, Enum.Direction.WEST, Enum.Direction.NORTH);
         
          for(int i = 0; i < 8; i++){
               runCarArray[i] = new CrossIntersection(carArray[i]);
               carThreadArray[i] = new Thread(runCarArray[i]);
               
               if(carArray[i].originalDirection.getName().equalsIgnoreCase("NORTH")){
                    northStreet.carQueue.add(carThreadArray[i]);
               }
               else if(carArray[i].originalDirection.getName().equalsIgnoreCase("EAST")){
                    eastStreet.carQueue.add(carThreadArray[i]);
               }
               else if(carArray[i].originalDirection.getName().equalsIgnoreCase("SOUTH")){
                    southStreet.carQueue.add(carThreadArray[i]);
               }
               else if(carArray[i].originalDirection.getName().equalsIgnoreCase("WEST")){
                    westStreet.carQueue.add(carThreadArray[i]);
               }
          }
          while(!street[Enum.Compass.NORTH].carQueue.isEmpty() ||    // while queues aren't empty
                !street[Enum.Compass.EAST].carQueue.isEmpty()  ||
                !street[Enum.Compass.SOUTH].carQueue.isEmpty() ||
                !street[Enum.Compass.WEST].carQueue.isEmpty()){
               if(northStreet.stoplight.equalsIgnoreCase("GREEN")){
                    Thread northCarThread = street[Enum.Compass.NORTH].queue.remove();
                    Thread southCarThread = street[Enum.Compass.SOUTH].queue.remove();
                    northCarThread.start();
                    southCarThread.start();
               }
               else if(eastStreet.stoplight.equalsIgnoreCase("GREEN")){
                    Thread eastCarThread = street[Enum.Compass.EAST].queue.remove();
                    Thread westCarThread = street[Enum.Compass.WEST].queue.remove();
                    eastCarThread.start();
                    westCarThread.start();
               }
          }
     }
}

class CrossIntersection implements Runnable {
     private Car car;
     Intersection intersection = new Intersection();
     
     public CrossIntersection(Car car){
          /*Passes in a car from runnable*/
          this.car = new Car(car);
          System.out.println(this.car.carID);
     }
     
     @Override
     public void run() {
          try{
               //this is where running the thread happens
               
               car.arriveIntersection(intersection);
               car.crossIntersection();
               car.exitIntersection(intersection);
          } catch (Exception ignored) {
               System.out.println("RUN FAILED");
          }
     }
}