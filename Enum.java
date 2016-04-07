public class Enum{
    public enum Color {
        RED, YELLOW, GREEN
     }
    
    public enum Compass{
        NORTH(0),
        EAST(1),
        SOUTH(2),
        WEST(3)
    }
    
     public enum Direction {
        NORTH("North","N", 0), 
        EAST("East", "E", 1), 
        SOUTH("South", "S", 2), 
        WEST("West", "W", 3);
        
        private String name = "North";
        private String value = "N";
        private int number = 0;
        
        Direction(String name, String value, int number){
             this.name = name;
             this.value = value;
             this.number = number;
        }
        
        public static Direction getDirection(String value) {
            for(Direction direction : Direction.values()){
                if(value.equals(direction.value)){
                    return direction;
                }
            }
            return null;
        }
        
        public int getNumber(){
            return number;
        }
        
        public String getName(){
             return name;
        }
        public String getValue(){
             return value;
        }
     }
}