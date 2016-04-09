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
    
    public synchronized void arriveIntersection(Intersection intersection){
        //if straight
        System.out.println(this.toString() + " arriving");
        int entranceSemaphoreToGet = (2*(this.originalDirection.getNumber()) + 4) % 8;
        DirectionalSemaphore entranceSemaphore = intersection.outerSemaphore.get(entranceSemaphoreToGet);
        try{
            entranceSemaphore.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        
        entranceSemaphore.semaphore.release();
    }
    
    public void crossIntersection(){
        System.out.println(this.toString() + " crossing");
        try {
            Thread.sleep(2000);                 //2000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }   
    }
    
    public void exitIntersection(Intersection intersection){
        System.out.println(this.toString() + " exiting");
        
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
    
    public synchronized void aquireOrReleaseGoStraightSemaphores(String acquireOrRelease, Intersection intersection){
        //int startOuterSemaphoreToGet = (2*(this.originalDirection.getNumber()) + 4) % 8; // OUTER sem
        int middleInnerSemaphoreToGet = (this.originalDirection.getNumber() + 2) % 4;    // INNER sem
        int endOuterSemaphoreToGet = (2*(this.originalDirection.getNumber()) + 1) % 8;   // OUTER sem
    
        //Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);  //OUTER
        DirectionalSemaphore middleInnerSemaphore = intersection.innerSemaphore.get(middleInnerSemaphoreToGet); // INNER
        DirectionalSemaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);      // OUTER
    
        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            // try {
            //     startOuterSemaphore.acquire();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            if((middleInnerSemaphore.originalDirection == this.originalDirection.getNumber() &&
                    middleInnerSemaphore.targetDirection == this.targetDirection.getNumber()) == false){
                try {
                    middleInnerSemaphore.semaphore.acquire();
                    middleInnerSemaphore.originalDirection = originalDirection.getNumber();
                    middleInnerSemaphore.targetDirection = targetDirection.getNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    endOuterSemaphore.semaphore.acquire();
                    endOuterSemaphore.originalDirection = originalDirection.getNumber();
                    endOuterSemaphore.targetDirection = targetDirection.getNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            //startOuterSemaphore.release();
            middleInnerSemaphore.semaphore.release();
            middleInnerSemaphore.originalDirection = -1;
            middleInnerSemaphore.targetDirection = -1;
            endOuterSemaphore.semaphore.release();
            endOuterSemaphore.originalDirection = -1;
            endOuterSemaphore.targetDirection = -1;
            
        }
        else{
            System.out.println("error in acquireOrReleaseGoStraightSemaphores");
        }
    }
    
    public synchronized void aquireOrReleaseGoRightSemaphores(String acquireOrRelease, Intersection intersection){
        // int startOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 4) % 8;
        int endOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 3) % 8;
        
        // Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);
        DirectionalSemaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);
        
        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            // try {
            //     startOuterSemaphore.acquire();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            if((endOuterSemaphore.originalDirection == this.originalDirection.getNumber() &&
                    endOuterSemaphore.targetDirection == this.targetDirection.getNumber()) == false){
                try {
                    endOuterSemaphore.semaphore.acquire();
                    endOuterSemaphore.originalDirection = originalDirection.getNumber();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            // startOuterSemaphore.release();
            endOuterSemaphore.semaphore.release();
            endOuterSemaphore.originalDirection = -1;
        }
        else{
            System.out.println("error in acquireOrReleaseGoRightSemaphores");
        }
    }
    
    public synchronized void aquireOrReleaseGoLeftSemaphores(String acquireOrRelease, Intersection intersection){
        //int startOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 4) % 8;
        int middleInnerSemaphoreToGet = this.originalDirection.getNumber();
        int endOuterSemaphoreToGet = (2*this.originalDirection.getNumber() + 7) % 8;
        
        //Semaphore startOuterSemaphore = intersection.outerSemaphore.get(startOuterSemaphoreToGet);
        DirectionalSemaphore middleInnerSemaphore = intersection.innerSemaphore.get(middleInnerSemaphoreToGet);
        DirectionalSemaphore endOuterSemaphore = intersection.outerSemaphore.get(endOuterSemaphoreToGet);

        if(acquireOrRelease.equalsIgnoreCase("acquire")){
            // try {
            //     startOuterSemaphore.acquire();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            if((middleInnerSemaphore.originalDirection == this.originalDirection.getNumber() &&
                    middleInnerSemaphore.targetDirection == this.targetDirection.getNumber()) == false){
                try {
                       middleInnerSemaphore.semaphore.acquire();
                       middleInnerSemaphore.originalDirection = originalDirection.getNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    endOuterSemaphore.semaphore.acquire();
                    endOuterSemaphore.originalDirection = originalDirection.getNumber();
                    endOuterSemaphore.targetDirection = targetDirection.getNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(acquireOrRelease.equalsIgnoreCase("release")){
            //startOuterSemaphore.release();
            middleInnerSemaphore.semaphore.release();
            middleInnerSemaphore.originalDirection = -1;
            endOuterSemaphore.semaphore.release();
            endOuterSemaphore.targetDirection = -1;
        }
        else{
            System.out.println("error in acquireOrReleaseGoStraightSemaphores");
        }
    }
}