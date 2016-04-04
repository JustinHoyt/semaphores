public class Car {
    private char originalDirection;
    private char targetDirection;
    private int carID;
    private double arrivalTime;
    public Car(int carID, double arrivalTime, char originalDirection, char targetDirection){
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
    
    public void arriveIntersection(Car car){
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
    }
    public void crossIntersection(){
        //sleep for a second
        /*
            We assume that it takes a fixed time period TC to cross the intersection and print
            out a debug message. You could use the Spin function to simulate the crossing.
        */
    }
    public void exitIntersection(Car car){
        /*
            It is called to indicate that the caller has finished crossing the intersection and
            should take steps to let additional cars cross the intersection.
        */
    }
    @Override
    public String toString(){
        return "Time: " + this.arrivalTime + 
               ": Car " + this.carID + 
               " (->" + this.originalDirection +  
               " (->" + this.targetDirection + ")";
    }
    
    public char getOriginalDirection() {
        return originalDirection;
    }

    public void setOriginalDirection(char originalDirection) {
        this.originalDirection = originalDirection;
    }

    public char getTargetDirection() {
        return targetDirection;
    }

    public void setTargetDirection(char targetDirection) {
        this.targetDirection = targetDirection;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}