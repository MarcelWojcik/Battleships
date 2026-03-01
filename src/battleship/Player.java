package battleship;

public class Player {

    private String name;
    public final battleship.Board board;


    Player(String name) {
        this.name = name;
        this.board = new battleship.Board(this.name);
    }

    public String getName() {
        return name;
    }
}
