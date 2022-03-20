package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        boolean programIsOver = false;
        char[][] ourCinema = initializeCinemaScheme(howMuchRows(), howMuchSeats()); // Initialize the room

        while (!programIsOver) {
            programIsOver=menu(ourCinema);
        }
    }

    public static char[][] initializeCinemaScheme (int rows, int seats) {
        char[][] room = new char[rows + 1][seats + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                if (i == 0 && j == 0) {
                    room[i][j] = ' ';
                } else if (i == 0) {
                    room[i][j] = Character.forDigit(j, 10);
                } else if (j == 0) {
                    room[i][j] = Character.forDigit(i, 10);
                } else {
                    room[i][j] = 'S';
                }
            }
        }
        return room;
    }

    public static void printCinemaScheme ( char[][] room){
        System.out.println("Cinema:");
        for (char[] chars : room) {
            for (char aChar : chars) {
                System.out.printf("%c ", aChar);
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }

    public static int calculatePossibleProfit (char[][] ourCinema) {
            int rows = ourCinema.length - 1;
            int seats = ourCinema[0].length - 1;
            int totalNumberOfSits = rows * seats;
            if (totalNumberOfSits <= 60) {
                return totalNumberOfSits * 10;
            }
            return  rows / 2 * seats * 10 + (rows / 2 + rows % 2) * seats * 8;
        }

    public static int howMuchRows () {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of rows:");
            return scanner.nextInt();
        }

    public static int howMuchSeats () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of seats in each row:");
            return scanner.nextInt();
        }

    public static int rowForBuying () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        return scanner.nextInt();
        }

    public static int seatForBuying () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a seat number in that row:");
        return scanner.nextInt();
    }
    public static int calculateThePrice (char[][] ourCinema, int row) {
        int totalNumberOfSits = (ourCinema.length - 1) * (ourCinema[0].length - 1);
        if (totalNumberOfSits <= 60) {
            return 10;
        }
        if (row <= (ourCinema.length - 1) / 2) {
            return 10;
        }
            return 8;
    }

    public static boolean menu (char[][] ourCinema) {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        Scanner scanner = new Scanner(System.in);
        int pointOfMenu = scanner.nextInt();
        if (pointOfMenu == 1) {
            printCinemaScheme(ourCinema);
        }

        if (pointOfMenu == 2) {
            buyATicket(ourCinema);
        }
        if (pointOfMenu == 3) {
            displayStatistic(ourCinema);
        }
        return pointOfMenu == 0;
    }

    public static void buyATicket (char[][] ourCinema) {
        int row = rowForBuying();
        int seat = seatForBuying();
        boolean correctInput = false;
        while (!correctInput) {
            if (row > ourCinema.length - 1 || row <= 0 || seat > ourCinema[0].length - 1 || seat <= 0) {
                System.out.println("Wrong input!");
                 row = rowForBuying();
                 seat = seatForBuying();
            } else if (ourCinema[row][seat] == 'B') {
                System.out.println("That ticket has already been purchased!");
                row = rowForBuying();
                seat = seatForBuying();
            } else {
                    correctInput = true;
                }
            }

        System.out.printf("Ticket price: $%d\n\n",calculateThePrice(ourCinema, row));
        ourCinema[row][seat] = 'B';
    }

    public static void displayStatistic(char[][] ourCinema) {
        int boughtTickets = 0;
        int currentIncome = 0;
        for (int i = 0; i < ourCinema.length; i++) {
            for (int j = 0; j < ourCinema[0].length; j++) {
                if (ourCinema[i][j] == 'B') {
                    boughtTickets++;
                    currentIncome += calculateThePrice(ourCinema,i);
                }
            }
        }
        float percentageOfBoughtTickets = (float) boughtTickets / ((ourCinema.length - 1) * (ourCinema[0].length - 1)) * 100;
        int possibleTotalIncome = calculatePossibleProfit(ourCinema);
        System.out.printf("Number of purchased tickets: %d\n",boughtTickets);
        System.out.printf("Percentage:%.2f%% \n",percentageOfBoughtTickets);
        System.out.printf("Current income: $%d\n",currentIncome);
        System.out.printf("Total income: $%d\n",possibleTotalIncome);
    }
}


