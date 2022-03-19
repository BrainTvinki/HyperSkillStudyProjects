package tictactoe;

import  java.util.Scanner;

public class Main {

    public static void printTheField (char[][] gameField) {
        System.out.print("---------\n");
        for (char[] chars : gameField) {
            System.out.printf("| %c %c %c |\n", chars[0], chars[1], chars[2]);
        }
        System.out.print("---------\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //initialize the game field
        char[][] gameField = new char [3][3];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = '_';
            }
        }
        printTheField(gameField);  // print the field
        // here should be the game
        boolean gameIsOver = false;
        boolean turnIsOver = false;
        char player = 'X';
        while (!gameIsOver) {
            while (!turnIsOver) {
                turnIsOver = inputTheCoordinates(gameField, player);
            }
            gameIsOver = analyzSituation(gameField);
            turnIsOver = false;

            if (player == 'X') {
                player = 'O';
            } else {
                player = 'X';
            }



        }


    }
    // check the draw by quantity of filled fields
    public static boolean isItDraw (char[][] situation) {
        int xCounter = 0;
        int oCounter = 0;
        for (char[] chars : situation) {
            for (char aChar : chars) {
                if (aChar == 'X') {
                    xCounter++;
                } else if (aChar == 'O') {
                    oCounter++;
                }
            }
        }
        return oCounter + xCounter == 9;
    }
    //checking is there one more turn by sum X and O. If it is less then 9, we have a turn
    public static boolean isThereOneMoreTurn (char[][] situation) {
        int xCounter = 0;
        int oCounter = 0;
        for (char[] chars : situation) {
            for (char aChar : chars) {
                if (aChar == 'X') {
                    xCounter++;
                } else if (aChar == 'O') {
                    oCounter++;
                }
            }
        }
        return oCounter + xCounter < 9;
    }

    // check, is there a situation when player win
    public static boolean winSituationForPlayer(char[][] situation, char player) {
        for (int i = 0; i < situation.length; i++) {
            if(signInARow(situation, i, player)) {
                return true;
            }
            if(signInAColumn(situation, i, player)) {
                return true;
            }
        }
        if(sign00(situation, player)) {
            return true;
        }
        if(sign22(situation, player)) {
            return true;
        }
        return false;
    }

    // check the row filling the same CHAR
    static boolean signInARow (char[][] situation, int rowNumber, char sign) {
        return situation[rowNumber][0] == situation[rowNumber][1] && situation[rowNumber][0] == situation[rowNumber][2] && situation[rowNumber][0] == sign;
    }
    // check the column filling the same CHAR
    static boolean signInAColumn (char[][] situation, int columnNumber, char sign) {
        return situation[0][columnNumber] == situation[1][columnNumber] && situation[0][columnNumber] == situation[2][columnNumber] && situation[0][columnNumber] == sign;
    }
    // check the \ filling the same CHAR
    static boolean sign00 (char[][] situation, char sign) {
        return situation[0][0] == situation[1][1] && situation[0][0] == situation[2][2] && situation[0][0] == sign;
    }
    // check the / filling the same CHAR
    static boolean sign22 (char[][] situation, char sign) {
        return situation[2][0] == situation[1][1] && situation[2][0] == situation[0][2] && situation[2][0] == sign;
    }
    // impossible because one of players has made too many turns
    static boolean toManySigns (char[][] situation) {
        int xCounter = 0;
        int oCounter = 0;
        for (char[] chars : situation) {
            for (char aChar : chars) {
                if (aChar == 'X') {
                    xCounter++;
                } else if (aChar == 'O') {
                    oCounter++;
                }
            }
        }
        return oCounter - xCounter >= 2 || oCounter - xCounter <= -2;
    }

    static boolean analyzSituation (char[][] gameField) {
        if(toManySigns(gameField) || winSituationForPlayer(gameField, 'X') && winSituationForPlayer(gameField, 'O')) {
            System.out.println("Impossible");
            return true;
        }
        if(winSituationForPlayer(gameField, 'X')) {
            System.out.println("X wins");
            return true;
        }
        if(winSituationForPlayer(gameField, 'O')) {
            System.out.println("O wins");
            return true;
        }
        if (isItDraw(gameField)) {
            System.out.println("Draw");
            return true;
        }
      /*  if (isThereOneMoreTurn(gameField)) {
            System.out.println("Game not finished");
        }
       */
        return false;
    }

    static boolean inputTheCoordinates (char[][] gameField, char player) {
        System.out.println("Enter the coordinates: ");
        Scanner scanner = new Scanner(System.in);
        String yCoordinateAsString = scanner.next();
        String xCoordinateAsString = scanner.next();
        int yCoordinate;
        int xCoordinate;
        try {
            yCoordinate = Integer.parseInt(yCoordinateAsString);
            xCoordinate = Integer.parseInt(xCoordinateAsString);
        } catch (Exception NumberFormatException) {
            System.out.println("You should enter numbers!");
            return false;
        }
        if (yCoordinate > 3 || yCoordinate < 1 || xCoordinate > 3 || xCoordinate < 1) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        if (gameField[yCoordinate-1][xCoordinate-1] == '_') {
            gameField[yCoordinate-1][xCoordinate-1] = player;
            printTheField(gameField);
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
        }
        return false;
    }
}
