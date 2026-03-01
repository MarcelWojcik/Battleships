package battleships;

public class Player {

    private String name;
    private Board board;


    Player(String name) {
        this.name = name;
        this.board = new Board(this.name);
    }

    public String getName() {
        return name;
    }
}
