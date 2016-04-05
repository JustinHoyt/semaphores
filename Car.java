//package com.semaphore.Driver;

import java.util.concurrent.Semaphore;

public class Car {
    public Enum.Direction originalDirection;
    public Enum.Direction targetDirection;
    public int carID;
    public double arrivalTime;
    // boolean northEastLock = false;
    // boolean northWestLock = false;
    // boolean southEastLock = false;
    // boolean southWestLock = false;
    
    public Car(int carID, double arrivalTime, Enum.Direction originalDirection, Enum.Direction targetDirection){
        this.originalDirection = originalDirection;
        this.targetDirection = targetDirection;
        this.carID = carID;
        this.arrivalTime = arrivalTime;
    }
    
    public Car(Car car){
        this.originalDirection = car.originalDirection;
        this.targetDirection = car.targetDirection;
        this.carID = car.carID;
        this.arrivalTime = car.arrivalTime;
    }
    
    public void arriveIntersection(Intersection intersection){
    /*
        When a car arrives at the intersection, if it sees a car (with the same original
        direction) stopping in front, it should wait until the front car starts crossing (it doesn’t need to wait
        until the front car finishes crossing). This is called head-of-line blocking. Otherwise, it can either
        drive through, or turn left, or turn right (U-turn is not allowed). But it should first check the traffic
        light in front.
        • Green Light: If it attempts to drive through, make sure that no car from the opposite direction
        is turing left, or any car is turning right to the same lane; if it attempts to turn left, make sure
        that no car is driving through the intersection in the opposite direction, or any car is turning right
        to the same lane; if it attempts to turn right, make sure that no car is driving to the same lane.
        • Yellow Light: If it attempts to turn right, make sure that no car is driving to the same lane,
        otherwise it should stop in front of the intersection.
        • Red Light: If it attempts to drive through or turn left, it should wait for the Green light and then
        follows the rule for Green Light; if it attempts to turn right, make sure that no car is driving to
        the same lane.
    */
        //if straight
        if(originalDirection.getNumber() == targetDirection.getNumber()){
            goStraight(intersection);
        }
        
        //if right
        if((originalDirection.getNumber() + 1) % 4 == targetDirection.getNumber()){
            goRight(intersection);
        }
        
        //if left
        else if(originalDirection.getNumber() == (targetDirection.getNumber() + 1) % 4) {
            goLeft(intersection);
        }
        
        try {
            intersection.northEastLock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.toString() + " arriving");
    }
    @Override
    public String toString(){
        return "Time: " + arrivalTime + 
               ": Car " + carID + 
               " (->" + originalDirection.getName() +  
               " ->" + targetDirection.getName() + ")";
    }
    public void crossIntersection(){
        /*
            We assume that it takes a fixed time period TC to cross the intersection and print
            out a debug message. You could use the Spin function to simulate the crossing.
        */
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }   
        System.out.println(this.toString() + " crossing");
    }
    public void exitIntersection(Intersection intersection){
        /*
            It is called to indicate that the caller has finished crossing the intersection and
            should take steps to let additional cars cross the intersection.
        */
        
        //if straight
        if(originalDirection.getNumber() == targetDirection.getNumber()){
            int semaphoreToGet1 = (this.originalDirection.getNumber() + 1) % 4;
            int semaphoreToGet2 = (this.originalDirection.getNumber() + 2) % 4;
            Semaphore semaphore1 = intersection.semaphore.get(semaphoreToGet1);
            Semaphore semaphore2 = intersection.semaphore.get(semaphoreToGet2);
            semaphore1.release();
            semaphore2.release();
        }
        
        //if right
        if((originalDirection.getNumber() + 1) % 4 == targetDirection.getNumber()){
            int semaphoreToGet = (this.originalDirection.getNumber() + 2) % 4;
        
            //acquire calculated semaphore
            Semaphore semaphore = intersection.semaphore.get(semaphoreToGet);
            semaphore.release();
        }
        
        //if left
        else if(originalDirection.getNumber() == (targetDirection.getNumber() + 1) % 4) {
            int semaphoreToGet1 = (this.originalDirection.getNumber() + 2) % 4;
            int semaphoreToGet2 = this.originalDirection.getNumber();
            Semaphore semaphore1 = intersection.semaphore.get(semaphoreToGet1);
            Semaphore semaphore2 = intersection.semaphore.get(semaphoreToGet2);
            semaphore1.release();
            semaphore2.release();
        }
        
        System.out.println(this.toString() + " exiting");
        
        
    /*    
        if(car.northEastLock == true){
            intersection.northEastLock.release();
            car.northEastLock = false;
        }
        if(car.northWestLock == true){
            intersection.northWestLock.release();
            car.northWestLock = false;
        }
        if(car.southEastLock == true){
            intersection.southEastLock.release();
            car.southEastLock = false;
        }
        if(car.southWestLock == true){
            intersection.southWestLock.release();
            car.southWestLock = false;
        }
    */
    }

    
    public void goStraight(Intersection intersection) {
        int semaphoreToGet1 = (this.originalDirection.getNumber() + 1) % 4;
        int semaphoreToGet2 = (this.originalDirection.getNumber() + 2) % 4;
        Semaphore semaphore1 = intersection.semaphore.get(semaphoreToGet1);
        Semaphore semaphore2 = intersection.semaphore.get(semaphoreToGet2);
        try {
            semaphore1.acquire();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            semaphore2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void goRight(Intersection intersection) {
        //calculate semaphore
        int semaphoreToGet = (this.originalDirection.getNumber() + 2) % 4;
        
        //acquire calculated semaphore
        Semaphore semaphore = intersection.semaphore.get(semaphoreToGet);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void goLeft(Intersection intersection) {
        int semaphoreToGet1 = (this.originalDirection.getNumber() + 2) % 4;
        int semaphoreToGet2 = this.originalDirection.getNumber();
        Semaphore semaphore1 = intersection.semaphore.get(semaphoreToGet1);
        Semaphore semaphore2 = intersection.semaphore.get(semaphoreToGet2);
        try {
            semaphore1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            semaphore2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}