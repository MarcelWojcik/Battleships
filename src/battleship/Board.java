package battleship;

import java.util.Arrays;
import java.util.HashMap;

import static battleship.Main.scanner;
import static battleship.Util.*;


public class Board {


    private final static int height = 10;
    private final static int width = 10;
    protected final char[][] gameBoard = new char[height][width];

    private HashMap<String, Character> points = new HashMap<>();



    Board(String name) {
        initBoard(name);
    }

    public void printBoard(boolean fogged) {
        for(int i = 0; i <= height; i++) {
            for(int j = 0; j<= width; j++) {
                if(i == 0 && j == 0) {
                    System.out.print("  ");
                    continue;
                }
                if(i == 0) {
                    System.out.print(j + " ");
                    continue;
                }
                if(j == 0){
                    int symbol = 64 + i;
                    System.out.print((char)symbol + " ");
                    continue;
                }

                int indexX = j - 1;

                int indexY = i - 1;


                if((gameBoard[indexX][indexY] == 'X' || gameBoard[indexX][indexY] == 'M')) {
                    System.out.print(gameBoard[indexX][indexY] + " ");
                }
                else if(fogged){
                    System.out.print('~' + " ");
                } else {
                    System.out.print(gameBoard[indexX][indexY] + " ");
                }

            }
            System.out.println("");
        }
    }

    public void printBoard(){
        printBoard(false);
    }

    public void initBoard(String name) {

        for(char[] row : gameBoard) {
            Arrays.fill(row, '~');
        }

        Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        Ship[] ships = new Ship[]{aircraftCarrier, battleship, submarine, cruiser, destroyer};


        System.out.println(name + ", place your ships on the game field\n");
        for (Ship ship : ships) {
            printBoard(false);
            System.out.println();
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.name, ship.length);
            boolean created;
            do {

                String c1 = scanner.next();
                String c2 = scanner.next();
                int length = checkLength(c1, c2);
                if (length == 0) {
                    System.out.print("Error! Wrong ship location! Try again:");
                    created = false;
                } else if (length == ship.length) {
                    created = placeShip(c1, c2, ship);
                } else {
                    System.out.printf("Error! Wrong length of the %s! Try again:", ship.name);
                    created = false;
                }
                System.out.print("\n");
            } while (!created);
        }
        printBoard();
        System.out.println();

    }

    public boolean placeShip(String c1, String c2, Ship ship) {

        String[] placements = shipPlacements(c1, c2, ship.length);

        //check around
        for(int i = 0; i < ship.length; i++) {

            if(!checkAround(placements[i])){
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
        }

        for(int i = 0; i < ship.length; i++) {

            int x = getXY(placements[i])[0];
            int y = getXY(placements[i])[1];

            this.gameBoard[x][y] = 'O';
            points.put(placements[i], '0');
        }

        return true;
    }

    public static int checkLength(String c1, String c2) {

        try {

            char row1 = c1.charAt(0);
            char row2 = c2.charAt(0);
            int column1 = Integer.parseInt(c1.substring(1));
            int column2 = Integer.parseInt(c2.substring(1));
            int length = 0;


            boolean checker = checkInput(row1) && checkInput(row2) && checkInput(column1) && checkInput(column2);
            if(!checker) return 0;
            if (row1 == row2) {
                length = column1 - column2 ;

            } else if (column1 == column2) {
                length = (int)row1 - (int)row2;

            } else {
                throw new IllegalArgumentException();
            }
            length =  length > 0 ? length : length * (-1);
            return ++length;
        }catch(IllegalArgumentException e){
            return 0;
        }
    }

    public static String[] shipPlacements(String c1, String c2, int length) {

        String[] placement = new String[length];
        char row1 = c1.charAt(0);
        char row2 = c2.charAt(0);
        int column1 = Integer.parseInt(c1.substring(1));
        int column2 = Integer.parseInt(c2.substring(1));

        if (row1 == row2) {
            if(column1 < column2) {
                for(int i = 0; i < length; i++) {
                    placement[i] = ""+row1+(i + column1);
                }
            } else {
                for(int i = length - 1; i >= 0; i--) {
                    placement[length - i - 1] = ""+row1+(i + column2);
                }
            }
        } else if (column1 == column2) {
            int charRow1 = (int)row1;
            int charRow2 = (int)row2;
            int nextRow;
            if(charRow1 < charRow2) {
                for(int i = 0; i < length; i++) {
                    nextRow = charRow1 + i;
                    placement[i] = ""+(char)nextRow + column1 +"";
                }
            } else {
                for(int i = length - 1; i >= 0; i--) {
                    nextRow = charRow2 + i;
                    placement[length - i - 1] = ""+(char)nextRow + column1 + "";
                }
            }

        }
        return placement;
    }


    public boolean checkAround(String coord){

        if(points.containsKey(coord)) return false;
        int y = Integer.parseInt(coord.substring(1));
        char prefix = coord.charAt(0);

        if(points.containsKey((prefix) + String.valueOf(y + 1)) || points.containsKey(prefix + String.valueOf(y - 1))) return false;
        int i = ((int) prefix) + 1;
        if(points.containsKey(String.valueOf((char) i + String.valueOf(y)))) return false;
        i = ((int) prefix) - 1;
        if(points.containsKey(String.valueOf((char) i + String.valueOf(y)))) return false;


        return true;
    }

    public boolean shoot(String target, Player enemy) {

        try {
            int x = getXY(target)[0];
            int y = getXY(target)[1];
            if(enemy.board.gameBoard[x][y] == 'O' || enemy.board.gameBoard[x][y] == 'X') {
                enemy.board.gameBoard[x][y] = 'X';
                enemy.board.points.remove(target);

                if(enemy.board.gameOver()){
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return true;
                }
                if(checkAround(target)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }

            } else {
                enemy.board.gameBoard[x][y] = 'M';
                System.out.println("You missed!");
            }


        } catch (Exception e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }



    public boolean gameOver() {
        return points.isEmpty();
    }

}
