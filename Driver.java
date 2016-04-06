import java.io.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Driver{
     
     public static void main(String[] args) {
          
          Car car0 = new Car(0, 1.1, Enum.Direction.NORTH, Enum.Direction.NORTH);
          Car car1 = new Car(1, 2.0, Enum.Direction.NORTH, Enum.Direction.NORTH);
          Car car2 = new Car(2, 3.3, Enum.Direction.NORTH, Enum.Direction.WEST);
          Car car3 = new Car(3, 3.5, Enum.Direction.SOUTH, Enum.Direction.SOUTH);
          Car car4 = new Car(4, 4.2, Enum.Direction.SOUTH, Enum.Direction.EAST);
          Car car5 = new Car(5, 4.4, Enum.Direction.NORTH, Enum.Direction.NORTH);
          Car car6 = new Car(6, 5.7, Enum.Direction.EAST, Enum.Direction.NORTH);
          Car car7 = new Car(7, 5.9, Enum.Direction.WEST, Enum.Direction.NORTH);
         
          Runnable runCar0 = new CrossIntersection(car0);
          Runnable runCar1 = new CrossIntersection(car1);
          Runnable runCar2 = new CrossIntersection(car2);
          Runnable runCar3 = new CrossIntersection(car3);
          Runnable runCar4 = new CrossIntersection(car4);
          Runnable runCar5 = new CrossIntersection(car5);
          Runnable runCar6 = new CrossIntersection(car6);
          Runnable runCar7 = new CrossIntersection(car7);
         
          Thread CarThread0 = new Thread(runCar0);
          Thread CarThread1 = new Thread(runCar1);
          Thread CarThread2 = new Thread(runCar2);
          Thread CarThread3 = new Thread(runCar3);
          Thread CarThread4 = new Thread(runCar4);
          Thread CarThread5 = new Thread(runCar5);
          Thread CarThread6 = new Thread(runCar6);
          Thread CarThread7 = new Thread(runCar7);
         
          CarThread0.start();
          CarThread1.start();
          CarThread2.start();
          CarThread3.start();
          CarThread4.start();
          CarThread5.start();
          CarThread6.start();
          CarThread7.start();
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