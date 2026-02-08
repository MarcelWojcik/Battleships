package battleships;


import java.util.Arrays;

public class Main {

    public final static int height = 10;
    public final static int width = 10;
    public final static char[][] board = new char[height][width];



    public static void main(String[] args) {
        initBoard();
        printBoard();
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
}

