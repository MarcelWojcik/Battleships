package battleships;

public class Util {

    public static int[] getXY(String coord) {
        int x = Integer.parseInt(coord.substring(1)) - 1;
        int y = (int)coord.charAt(0) - 65;

        return new int[] {x, y};
    }

    public static boolean checkInput(char c) {
        int input = (int)c;
        return c >= 65 && c <= 74;
    }

    public static boolean checkInput(int c) {
        return c >= 1 && c <= 10;
    }
}
