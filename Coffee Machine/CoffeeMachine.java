package machine;

import java.util.Objects;
import java.util.Scanner;

public class CoffeeMachine {
    static final int waterForOneCup = 200;
    static final int milkForOneCup = 50;
    static final int beansForOneCup = 15;

    static final int waterForOneCupOfEspresso = 250;
    static final int milkForOneCupOfEspresso = 0;
    static final int beansForOneCupOfEspresso = 16;
    static final int moneyForOneCupOfEspresso = 4;

    static final int waterForOneCupOfLatte = 350;
    static final int milkForOneCupOfLatte = 75;
    static final int beansForOneCupOfLatte = 20;
    static final int moneyForOneCupOfLatte = 7;

    static final int waterForOneCupOfCappuccino = 200;
    static final int milkForOneCupOfCappuccino = 100;
    static final int beansForOneCupOfCappuccino = 12;
    static final int moneyForOneCupOfCappuccino = 6;

    static int waterInMachine = 400;
    static int milkInMachine = 540;
    static int beansInMachine = 120;
    static int disposableCupsInMachine = 9;
    static int moneyInMachine = 550;

    static int cupsCanBeProduced = 0;

    static boolean finishProgramm = false;

    public static void main(String[] args) {


        while (!finishProgramm) {
        displayMenu();
        }
    }
   
    private static void theCoffeeMachineHas() {
        System.out.printf("The coffee machine has: \n" +
                        "%d ml of water\n" +
                        "%d ml of milk\n" +
                        "%d g of coffee beans\n" +
                        "%d disposable cups\n" +
                        "$%d of money", waterInMachine,milkInMachine,beansInMachine,
                        disposableCupsInMachine, moneyInMachine);
    }
    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String pointOfMenu = scanner.next();

        if (Objects.equals(pointOfMenu, "buy")) {
            buyACupOfCoffee();
        }
        if (Objects.equals(pointOfMenu, "fill")) {
            fillTheMachine();
        }
        if (Objects.equals(pointOfMenu, "take")) {
            takeMoneyFromMachine();
        }
        if (Objects.equals(pointOfMenu, "remaining")) {
            theCoffeeMachineHas();
        }
        if (Objects.equals(pointOfMenu, "exit")) {
           finishProgramm = true;
        }

    }



    private static void buyACupOfCoffee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, " +
                "3 - cappuccino, back - to main menu: ");
        String choice = scanner.next();

        if (Objects.equals(choice, "1")) {
            if (waterInMachine < waterForOneCupOfEspresso) {
                System.out.println("Sorry, not enough water!");
                return;
            }
            if (beansInMachine < beansForOneCupOfEspresso) {
                System.out.println("Sorry, not enough coffee beans!");
                return;
            }
            if (disposableCupsInMachine < 1) {
                System.out.println("Sorry, not enough disposable cups!");
                return;
            }
            waterInMachine -= waterForOneCupOfEspresso;
            milkInMachine -= milkForOneCupOfEspresso;
            beansInMachine -= beansForOneCupOfEspresso;
            disposableCupsInMachine -= 1;
            moneyInMachine += moneyForOneCupOfEspresso;
        } else if (Objects.equals(choice, "2")) {
            if (waterInMachine < waterForOneCupOfLatte) {
                System.out.println("Sorry, not enough water!");
                return;
            }
            if (milkInMachine < milkForOneCupOfLatte) {
                System.out.println("Sorry, not enough milk!");
                return;
            }
            if (beansInMachine < beansForOneCupOfLatte) {
                System.out.println("Sorry, not enough coffee beans!");
                return;
            }
            if (disposableCupsInMachine < 1) {
                System.out.println("Sorry, not enough disposable cups!");
                return;
            }
            waterInMachine -= waterForOneCupOfLatte;
            milkInMachine -= milkForOneCupOfLatte;
            beansInMachine -= beansForOneCupOfLatte;
            disposableCupsInMachine -= 1;
            moneyInMachine += moneyForOneCupOfLatte;
        } else if (Objects.equals(choice, "3")) {
            if (waterInMachine < waterForOneCupOfCappuccino) {
                System.out.println("Sorry, not enough water!");
                return;
            }
            if (milkInMachine < milkForOneCupOfCappuccino) {
                System.out.println("Sorry, not enough milk!");
                return;
            }
            if (beansInMachine < beansForOneCupOfCappuccino) {
                System.out.println("Sorry, not enough coffee beans!");
                return;
            }
            if (disposableCupsInMachine < 1) {
                System.out.println("Sorry, not enough disposable cups!");
                return;
            }
            waterInMachine -= waterForOneCupOfCappuccino;
            milkInMachine -= milkForOneCupOfCappuccino;
            beansInMachine -= beansForOneCupOfCappuccino;
            disposableCupsInMachine -= 1;
            moneyInMachine += moneyForOneCupOfCappuccino;
        } else if (Objects.equals(choice, "back")) {
                return;
        } else {
            System.out.println("Out of range!");
        }
    }

    private static void fillTheMachine () {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Write how many ml of water you want to add: ");
        waterInMachine += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        milkInMachine += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        beansInMachine += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        disposableCupsInMachine += scanner.nextInt();
    }

    private static void takeMoneyFromMachine() {
        System.out.printf("I gave you $%d", moneyInMachine);
        moneyInMachine = 0;
    }

}

