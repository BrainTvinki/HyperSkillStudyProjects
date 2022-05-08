package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String secretCodeAsString = generatingValidSecretCode();
        grader(secretCodeAsString);
    }

    private static void grader (String secretCode){
        int bulls = 0;
        int cows = 0;
        int turn = 1;
        boolean isGameFinished = false;

        //make a secretCode as ArrayList
        ArrayList<Character> secretCodeAsArrayList = new ArrayList<> ();
        char[] secretCodeAsCharArray = String.valueOf(secretCode).toCharArray();
        for (int i = 0; i < secretCode.length(); i++) {
            secretCodeAsArrayList.add(secretCodeAsCharArray[i]);
        }
        //starting the game
        Scanner scanner = new Scanner(System.in);
        while (!isGameFinished) {
            System.out.printf("Turn %d:\n",turn);
            String guessOfPlayer = scanner.nextLine();
            char[] guessOfPlayerAsCharArray = String.valueOf(guessOfPlayer).toCharArray();
            //searching of bulls and cows
            for (int i = 0; i < secretCodeAsArrayList.size(); i++) {
                if (guessOfPlayerAsCharArray[i] == secretCodeAsArrayList.get(i)) {
                    bulls++;
                    continue;
                }
                if (secretCodeAsArrayList.contains(guessOfPlayerAsCharArray[i])) {
                    cows++;
                }
            }
            //show the result of turn
            if (bulls == 0 && cows == 0) {
                System.out.print("Grade: None.\n");
            } else if (bulls != 0 && cows != 0) {
                System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
            } else if (bulls == 0) {
                System.out.printf("Grade: %d cow(s).\n", cows);
            } else {
                System.out.printf("Grade: %d bull(s).\n", bulls);
            }
            //win condition
            if(guessOfPlayer.equals(secretCode)) {
                isGameFinished = true;
            }
            //counter of next turn and reset of bulls and cows
            turn++;
            bulls = 0;
            cows = 0;
        }
        //if the game is over shows the right code
        StringBuilder secretCodeAsStringBuilder = new StringBuilder();
        for (Character character : secretCodeAsArrayList) {
            secretCodeAsStringBuilder.append(character);
        }
        System.out.printf("The secret secretCode is %s.", secretCodeAsStringBuilder);

    }
    private static String generatingValidSecretCode() {
        Scanner scanner = new Scanner(System.in);
        final String allPossiblySymbols = "0123456789abcdefghijklmnopqrstuvwxyz";

        System.out.println("Input the length of the secret code:");
        int sizeOfSecretCode = -1;
        // checking the length of code: Is it a number? Is it between 1 and 36?
        try {
            sizeOfSecretCode = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: your input isn't a number.");
            System.exit(0);
        }
        if (sizeOfSecretCode > 36 || sizeOfSecretCode < 1) {
            System.out.printf("Error: can't generate a secret number with a length of %d " +
                    "because there aren't enough unique digits.", sizeOfSecretCode);
            System.exit(0);
        }
        // checking how much different symbols we can use in the code. Is it a number? Is it between 1 and 36?
        // Is it enough for generating secret code with unique symbols?
        System.out.println("Input the number of possible symbols in the code:");
        int quantityOfSymbolsInSecretCode = -1;
        try {
            quantityOfSymbolsInSecretCode = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: your input isn't a number.");
            System.exit(0);
        }
        if (quantityOfSymbolsInSecretCode > 36 || quantityOfSymbolsInSecretCode < 1) {
            System.out.println("Error: allowed number of possible symbols in the code is between 1 and 36 inclusive");
            System.exit(0);
        }
        if (sizeOfSecretCode > quantityOfSymbolsInSecretCode) {
            System.out.printf("Error: can't generate a secret number with a length of %d " +
                    "because there aren't enough unique digits.", sizeOfSecretCode);
            System.exit(0);
        }
        //take symbols according the quantity of symbols
        String stringAllSymbolsForCode = allPossiblySymbols.substring(0,quantityOfSymbolsInSecretCode);
        // create a secret code as StringBuilder object
        StringBuilder secretCode = new StringBuilder();
        Random random = new Random ();
        for (int i = 0; i < sizeOfSecretCode; i++) {
            int newInt = random.nextInt(quantityOfSymbolsInSecretCode);
            char newChar = stringAllSymbolsForCode.charAt(newInt);
            while (secretCode.toString().contains(String.valueOf(newChar))) {
                newInt = random.nextInt(quantityOfSymbolsInSecretCode);
                newChar = stringAllSymbolsForCode.charAt(newInt);
            }
            secretCode.append(newChar);
        }
        //inform the player about his code: the length and possible symbols
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < sizeOfSecretCode; i++){
            System.out.print("*");
        }
        if (quantityOfSymbolsInSecretCode <= 10) {
            System.out.printf(" (0-%d)\n",quantityOfSymbolsInSecretCode - 1);
        } else if (quantityOfSymbolsInSecretCode == 11) {
            System.out.print(" (0-9, a)\n");
        } else {
            System.out.printf(" (0-9, a-%c)\n",allPossiblySymbols.charAt(quantityOfSymbolsInSecretCode - 1));
        }
        System.out.println("Okay, let's start a game!");
        return secretCode.toString();
    }



}
