package battleships;

public class Ship {
    private String name;
    private String c1;
    private String c2;
    public int length;
    private String[] placements;

    Ship(String name, int length){
        this.name = name;
        this.length = length;
    }

}
