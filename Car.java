//package com.semaphore.Driver;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.concurrent.Semaphore;

public class Car {
    public Enum.Direction originalDirection;
    public Enum.Direction targetDirection;
    public int carID;
    public double timer;
    
    public Car(int carID, double arrivalTime, Enum.Direction originalDirection, Enum.Direction targetDirection){
        this.originalDirection = originalDirection;
        this.targetDirection = targetDirection;
        this.carID = carID;
        this.timer = arrivalTime;
    }
    
    public Car(Car car){
        this.originalDirection = car.originalDirection;
        this.targetDirection = car.targetDirection;
        this.carID = car.carID;
        this.timer = car.timer;
    }
    
    public void arriveIntersection(Intersection intersection){
        //if straight
        if(originalDirection.getNumber() == targetDirection.getNumber()){
            goStraight(intersection);
        }
        //if right
        else if((originalDirection.getNumber() + 1) % 4 == targetDirection.getNumber()){
            goRight(intersection);
        }
        //if left
        else if(originalDirection.getNumber() == (targetDirection.getNumber() + 1) % 4) {
            goLeft(intersection);
        }
        
        System.out.println(this.toString() + " arriving");
    }
    
    public void crossIntersection(){
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }   
        System.out.println(this.toString() + " crossing");
    }
    
    public synchronized void exitIntersection(Intersection intersection){
        //if straight
        if(originalDirection.getNumber() == targetDirection.getNumber()){
            String acquireOrRelease = "release";
            aquireOrReleaseGoStraightSemaphores(acquireOrRelease, intersection);  //STRAIGHT
        }
        
        //if right
        if((originalDirection.getNumber() + 1) % 4 == targetDirection.getNumber()){
            String acquireOrRelease = "release";
            aquireOrReleaseGoRightSemaphores(acquireOrRelease, intersection);     // RIGHT
        }
        
        //if left
        else if(originalDirection.getNumber() == (targetDirection.getNumber() + 1) % 4) {
            String acquireOrRelease = "release";
            aquireOrReleaseGoLeftSemaphores(acquireOrRelease, intersection);      // LEFT
        }
        
        System.out.println(this.toString() + " exiting");
    }

    public synchronized void goStraight(Intersection intersection) {
        String acquireOrRelease = "acquire";
        aquireOrReleaseGoStraightSemaphores(acquireOrRelease, intersection);
    }
    
    public synchronized void goRight(Intersection intersection) {
        String acquireOrRelease = "acquire";
        aquireOrReleaseGoRightSemaphores(acquireOrRelease, intersection);
    }
    
    public synchronized void goLeft(Intersection intersection) {
        String acquireOrRelease = "acquire";
        aquireOrReleaseGoLeftSemaphores(acquireOrRelease, intersection);
    }
    
    @Override
    public String toString(){
        DecimalFormat formatter = new DecimalFormat("#0.0");

        return "Time: " + formatter.format(timer) +
               ": Car " + carID + 
               " (->" + originalDirection.getName() +  
               " ->" + targetDirection.getName() + ")";
    }
    
    public void aquireOrReleaseGoStraightSemaphores(String acquireOrRelease, Intersection intersection){
        int startOuterSemaphoreToGet = (2*(this.originalDirection.getNumber()) + 4) % 8; // OUTER sem
        int middleInnerSemaphoreToGet = (this.originalDirection.getNumber() + 2) % 4;    // INNER sem
        int endOuterSemaphoreToGet = (2*(this.originalDirection.getNumber()) + 1) % 8;   // OUTER sem
    
        Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);  //OUTER
        Semaphore middleInnerSemaphore = intersection.innerSemaphore.get(middleInnerSemaphoreToGet); // INNER
        Semaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);      // OUTER
    
        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            try {
                startOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                middleInnerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                endOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            startOuterSemaphore.release();
            middleInnerSemaphore.release();
            endOuterSemaphore.release();
        }
        else{
            System.out.println("error in acquireOrReleaseGoStraightSemaphores");
        }
    }
    
    public void aquireOrReleaseGoRightSemaphores(String acquireOrRelease, Intersection intersection){
        int startOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 4) % 8;
        int endOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 3) % 8;
        
        Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);
        Semaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);
        
        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            try {
                startOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                endOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            startOuterSemaphore.release();
            endOuterSemaphore.release();
        }
        else{
            System.out.println("error in acquireOrReleaseGoRightSemaphores");
        }
    }
    
    public void aquireOrReleaseGoLeftSemaphores(String acquireOrRelease, Intersection intersection){
        int startOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 4) % 8;
        int middleInnerSemaphoreToGet = this.originalDirection.getNumber();
        int endOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 7) % 8;
        
        Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);
        Semaphore middleInnerSemaphore = intersection.innerSemaphore.get(middleInnerSemaphoreToGet);
        Semaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);

        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            try {
                startOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                middleInnerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                endOuterSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            startOuterSemaphore.release();
            middleInnerSemaphore.release();
            endOuterSemaphore.release();
        }
        else{
            System.out.println("error in acquireOrReleaseGoStraightSemaphores");
        }
    }
}