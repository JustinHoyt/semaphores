public class Enum{
    public enum Color {
        RED, YELLOW, GREEN
     }
    
     public enum Direction {
        NORTH("North","N"), 
        SOUTH("South", "S"), 
        EAST("East", "E"), 
        WEST("West", "W");
        
        private String name = "North";
        private String value = "N";
        
        Direction(String name, String value){
             this.name = name;
             this.value = value;
        }
        
        public static Direction getDirection(String value) {
            for(Direction direction : Direction.values()){
                if(value.equals(direction.value)){
                    return direction;
                }
            }
            return null;
        }
        
        public String getName(){
             return name;
        }
        public String getValue(){
             return value;
        }
     }
}