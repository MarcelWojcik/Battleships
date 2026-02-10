package battleships;



import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private final static int height = 10;
    private final static int width = 10;
    private final static char[][] board = new char[height][width];



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        initBoard();
        printBoard();

        Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);

        Ship[] ships = new Ship[] {aircraftCarrier, battleship, submarine, cruiser, destroyer};


        for(Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.name, ship.length);
            boolean created;
            do {

                String c1 = scanner.next();
                String c2 = scanner.next();
                int length = checkLength(c1, c2);
                if(length == 0) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    created = false;
                } else if(length == ship.length) {
                    created = placeShip(c1, c2, ship);
                }else {
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.name);
                    created = false;
                }

            } while (!created);
            printBoard();

        }

        System.out.println("The game starts!");
        printBoard();
        System.out.println("Take a shot!");
        String target;

        do {
             target = scanner.next();
        }while(!shoot(target));


    }

    public static void printBoard(){

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

                System.out.print(board[indexX][indexY] + " ");

            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void initBoard() {
        for(char[] chars : board) {
            Arrays.fill(chars, '~');
        }
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

    public static boolean placeShip(String c1, String c2, Ship ship) {

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
            board[x][y] = 'O';
        }

        return true;
    }

    public static int[] getXY(String coord) {
        int x = Integer.parseInt(coord.substring(1)) - 1;
        int y = (int)coord.charAt(0) - 65;

        return new int[] {x, y};
    }

    public static boolean checkAround(String coord){
        int x = getXY(coord)[0];
        int y = getXY(coord)[1];

        try {
            if(board[x][y] != '~'){
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if(x > 0) {
            if(board[x - 1][y] != '~') {
                return false;
            }
            if(x < 9) {
                if(board[x + 1][y] != '~') {
                    return false;
                }
            }

        }

        if(y > 0) {
            if(board[x][y-1] != '~') {
                return false;
            }
            if(y < 9) {
                if(board[x][y + 1] != '~') {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean shoot(String c) {
        int x = getXY(c)[0];
        int y = getXY(c)[1];

        try {
            if(board[x][y] == 'O') {
                board[x][y] = 'X';
                printBoard();
                System.out.println("You hit a ship!");
            } else {
                board[x][y] = 'M';
                printBoard();
                System.out.println("You missed!");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        return true;
    }

    public static boolean checkInput(char c) {
        int input = (int)c;
        return c >= 65 && c <= 74;
    }

    public static boolean checkInput(int c) {
        return c >= 1 && c <= 10;
    }
}

