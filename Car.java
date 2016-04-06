//package com.semaphore.Driver;

import java.util.concurrent.Semaphore;

public class Car {
    public Enum.Direction originalDirection;
    public Enum.Direction targetDirection;
    public int carID;
    public double arrivalTime;
    
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
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }   
        System.out.println(this.toString() + " crossing");
    }
    public void exitIntersection(Intersection intersection){
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
    }

    
    public synchronized void goStraight(Intersection intersection) {
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
    public synchronized void goRight(Intersection intersection) {
        //calculate semaphore
        int semaphoreToGet = (this.originalDirection.getNumber() + 2) % 4;
        
        //acquire calculated semaphore
        Semaphore innerSemaphore = intersection.innerSemaphore.get(semaphoreToGet);
        try {
            innerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void goLeft(Intersection intersection) {
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