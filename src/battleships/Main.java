package battleships;

import java.util.Scanner;
import static battleships.Board.*;


public class Main {

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
        printFogBoard();
        System.out.println("Take a shot!");
        String target;

        do {
             target = scanner.next();

        }while(!shoot(target));


    }

}

