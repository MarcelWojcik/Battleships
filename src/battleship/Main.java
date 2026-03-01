package battleship;

import java.util.Scanner;
import static battleship.Board.*;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Player player1 = new Player("Player 1");
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        scanner.nextLine();
        System.out.println();
        Player player2 = new Player("Player 2");
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        scanner.nextLine();

        Player turn = player1;
        Player enemy = player2;

        while (!enemy.board.gameOver()) {
            enemy.board.printBoard(true);
            System.out.println("---------------------");
            turn.board.printBoard();
            System.out.println();
            System.out.println(turn.getName() + ", it's your turn:");
            String target;

            target = scanner.nextLine();
            turn.board.shoot(target, enemy);

            if(enemy.board.gameOver()) {
                System.out.println(turn.getName() + " wins!");
                break;
            }

            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();


            Player temp = turn;
            turn = enemy;
            enemy = temp;

        }
    }



}



