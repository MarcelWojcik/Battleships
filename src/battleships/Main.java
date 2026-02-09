package battleships;


import javax.xml.transform.Source;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private final static int height = 10;
    private final static int width = 10;
    private final static char[][] board = new char[height][width];



    public static void main(String[] args) {
        initBoard();
        printBoard();

        System.out.println((int)'J');

        System.out.println("Enter coordinates of the ship: ");
        Scanner scanner = new Scanner(System.in);
        String c1 = scanner.next();
        String c2 = scanner.next();
        int length = checkLength(c1, c2);
        String[] placements;
        if(length > 0){
            placements = shipPlacements(c1, c2, length);
            System.out.println("Length: " + length);
            System.out.print("Parts: ");
            for(int i = 0; i < length; i++) {
                System.out.print(placements[i] + " ");
            }
        }else {
            System.out.println("Error!");
        }

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

                int indexX = i - 1;
                int indexY = j - 1;

                System.out.print(board[indexX][indexY] + " ");

            }
            System.out.println();
        }

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


    public static boolean checkInput(char c) {
        int input = (int)c;
        return c >= 65 && c <= 74;
    }

    public static boolean checkInput(int c) {
        return c >= 1 && c <= 10;
    }
}

