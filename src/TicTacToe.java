import java.util.Scanner;
import java.util.Arrays;

public class TicTacToe {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Game loop variables
        String field = "         ";
        String playerMove;
        int turnCounter = 0;
        String newField;
        printField(field);

        do {
            System.out.print("Enter the coordinates: ");

            // Alternates between players
            if (turnCounter % 2 == 0){
                playerMove = "X";
            } else {
                playerMove = "O";
            }
            turnCounter++;

            // Keep asking for a new move
            newField = getNewField(field, playerMove);
            field = newField;
            printField(field);

            // Print the game state
            System.out.println(getGameState(field));
        } while (getGameState(field) == "Game not finished");
    }

    // Method promts the user to provide valid coordinates
    private static String getNewField(String field, String PlayerMove){

        int index = getIndexOfCoordinates();

        while (isCellOccupied(field, index)){
            System.out.println("This cell is occupied: Have another crack...");
            System.out.println("Enter the coordinates: ");
            index = getIndexOfCoordinates();
        }

        return field.substring(0, index) + PlayerMove + field.substring(index + 1);
    }

    //Method converts the coordinates and returns the corresponding index
    private static int getIndexOfCoordinates() {
        int[] coordinates = getCoordinatesFromUser();
        String coordinatesString = String.valueOf(coordinates[0]) + coordinates[1];
        switch (coordinatesString){
            case "13":
                return 0;
            case "23":
                return 1;
            case "33":
                return 2;
            case "12":
                return 3;
            case "22":
                return 4;
            case "32":
                return 5;
            case "11":
                return 6;
            case "21":
                return 7;
            case "31":
                return 8;
        }
        return -1;
    }

    // Pretty self explanatory
    private static boolean isCellOccupied(String field, int index){
        return field.charAt(index) == 'X' || field.charAt(index) == 'O';
    }

    // Method validates the users the coordinates given by the user
    private static int[] getCoordinatesFromUser(){

        boolean areValid = false;
        boolean areNumber = false;

        int[] coordinatesInIntegers = new int[2];

        while (!(areNumber && areValid)){

            String coordinatesString = scanner.nextLine();
            String[] coordinates = coordinatesString.split(" ");

            //checks if the co ordinates are numbers using Regex
            if (coordinates[0].matches("\\d") && coordinates[1].matches("\\d")){
                areNumber = true;

                // Passes the valid string array into an int array using Array Streams
                coordinatesInIntegers = Arrays.stream(coordinates).mapToInt(Integer::parseInt).toArray();

                // Checks if the values are in the range of 1 to 3
                if (coordinatesInIntegers[0] > 0 && coordinatesInIntegers[0] < 4 && coordinatesInIntegers[1] > 0 && coordinatesInIntegers[1] < 4){
                    areValid = true;
                } else {
                    System.out.println("Coordinates should be from 1 to 3 bruh");
                    System.out.println("Enter the coordinates: ");
                }

            } else {
                System.out.println("You should enter numbers!");
                System.out.println("Enter the coordinates: ");
            }
        }
        return coordinatesInIntegers;
    }

    // Prints it on a make-shift tictactoe board
    private static void printField(String field){
        System.out.println("---------");
        System.out.print("| ");
        for (int i = 0; i < 3; i++){
            System.out.print(field.charAt(i) + " ");
        }
        System.out.println("|");
        System.out.print("| ");
        for (int i = 3; i < 6; i++){
            System.out.print(field.charAt(i) + " ");
        }
        System.out.println("|");
        System.out.print("| ");
        for (int i = 6; i < 9; i++){
            System.out.print(field.charAt(i) + " ");
        }
        System.out.println("|");
        System.out.println("---------");
    }

    // Checks if the round is still ongoing
    private static String getGameState(String field){

        boolean xWins = isPlayerWins(field, 'X');
        boolean oWins = isPlayerWins(field, 'O');

        if (xWins && !oWins){
            return "X Wins";
        } else if (oWins && !xWins){
            return "O Wins";
        }

        if (countDifferenceBetweenPlayers(field) > 1 || xWins) {
            return "IMPOSSIBLE";
        } else if (field.contains(" ")){
            return "Game not finished";
        } else {
            return "Draw";
        }
    }

    private static boolean isPlayerWins(String field, char player){

        //check for horizontal match
        for (int i = 0; i < 9; i += 3){
            if (player == field.charAt(i) && player == field.charAt(i + 1) && player == field.charAt(i + 2)){
                return true;
            }
        }

        //check for vertical match
        for (int i = 0; i < 3; i++){
            if (player == field.charAt(i) && player == field.charAt(i + 3) && player == field.charAt(i + 6)){
                return true;
            }
        }

        //check for diagnonal match
        return player == field.charAt(0)
                && player == field.charAt(4)
                && player == field.charAt(8)
                || player == field.charAt(2)
                && player == field.charAt(4)
                && player == field.charAt(6);
    }

    // Checks for a cheat
    private static int countDifferenceBetweenPlayers(String field) {
        char[] charArray = field.toCharArray();
        int countX = 0;
        int countO = 0;
        for (char element : charArray) {
            if (element == 'X') {
                countX++;
            } else if (element == 'O') {
                countO++;
            }
        }
        // I didn't know this but Absolute returns a value as a positive, more
        // specifically how far it is from  which will always be positive
        return Math.abs(countO - countX);
    }
}
