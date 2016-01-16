package montyhall;

import java.util.Random;
import java.util.Scanner;
import static montyhall.MontyHall.Door.CAR;
import static montyhall.MontyHall.Door.GOAT;

/**
 *
 * @author Jin Oh
 */
public class MontyHall {

    public enum Door {

        GOAT, CAR;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int gameMode = 0;
        boolean result;
        char playAgain;
        
        System.out.println("Welcome to Monty Hall Game!");
        do {
            System.out.println("Play game(1)");
            System.out.println("Monty Hall percentage generator(2)");
            System.out.print("Press game mode: ");
            gameMode = input.nextInt();
        } while (gameMode != 1 && gameMode != 2);
        switch (gameMode) {
            case 1:
                // Game mode 1
                // Doors set up

                int playerPick = -1;
                int montyHallPick = -1;
                char changePick;
                do {
                    Door[] doors = setDoor();
                    do {
                        // Player picks the door
                        System.out.println("[0] [1] [2] ");
                        System.out.print("Choose a door: ");
                        playerPick = input.nextInt();
                    } while (playerPick != 0 && playerPick != 1 && playerPick != 2);

                    // Monty Hall picks the door
                    montyHallPick = getMontyHallPick(doors, playerPick, true);
                    System.out.println("Monty Hall opens the door that has goat");
                    for (int i = 0; i < doors.length; i++) {
                        if (i == montyHallPick) {
                            System.out.print("[" + doors[i] + "] ");
                        }
                        else {
                            System.out.print("[] ");
                        }
                    }
                    System.out.print("\nDo you want to change your pick?(Y/N) ");
                    changePick = input.next().charAt(0);
                    // change index of player pick
                    if (changePick == 'Y' || changePick == 'y') {
                        playerPick = getNewDoorIndex(doors, playerPick, montyHallPick);
                    }

                    // Get the result
                    result = getResult(doors, playerPick);
                    for (Door door : doors) {
                        System.out.print("[" + door + "]");
                    }
                    if (result) {
                        System.out.println("\nCongratulation!\nYou got the car");
                    }
                    else {
                        System.out.println("\nSorry. You didn't get the car");
                    }
                    System.out.print("Play again? (Y/y) ");
                    playAgain = input.next().charAt(0);
                } while (playAgain == 'Y' || playAgain == 'y');
                break;

            case 2:
                do {
                    int simulation = 0;
                    int pickedCar = 0;
                    char doesMantyHallknows;
                    boolean montyHallKnows;

                    System.out.print("Enter number of simulations: ");
                    simulation = input.nextInt();
                    System.out.print("Let MontyHall knows where the CAR is? (Y/y) ");
                    doesMantyHallknows = input.next().charAt(0);
                    System.out.print("Change the first choice? (Y/y)");
                    changePick = input.next().charAt(0);

                    if (doesMantyHallknows == 'y' || doesMantyHallknows == 'Y') {
                        montyHallKnows = true;
                    }
                    else {
                        montyHallKnows = false;
                    }
                    for (int i = 0; i < simulation; i++) {
                        // set up doors
                        Door[] doors = setDoor();
                        // get door picked
                        int doorPicked = getRandomDoor();
                        // get monty picked
                        int montyPicked = getMontyHallPick(doors, doorPicked, montyHallKnows);
                        if (changePick == 'y' || changePick == 'Y') {
                            // change index of door if Y/y is selected
                            doorPicked = getNewDoorIndex(doors, doorPicked, montyPicked);
                        }
                        if (getResult(doors, doorPicked)) {
                            pickedCar++;
                        }
                    }
                    double percentage = ((double) pickedCar / (double) simulation) * 100.0;
                    System.out.println("Percentage of getting the car is: " + percentage + "%.");
                    System.out.println("Play again? (Y/y) ");
                    playAgain = input.next().charAt(0);
                } while (playAgain == 'y' || playAgain == 'Y');
                break;
        }

    }

    public static Door[] setDoor() {
        Door[] doors = new Door[3];
        Random random = new Random();
        int index = random.nextInt(3);
        doors[index] = CAR;
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] == null) {
                doors[i] = GOAT;
            }
        }
        return doors;
    }

    public static int getMontyHallPick(Door[] doors, int playerPick, boolean montyHallKnows) {
        int montyHallPick = -1;
        // Monty Hall knows where the car is
        if (montyHallKnows) {
            do {
                Random random = new Random();
                montyHallPick = random.nextInt(3);
            } while (montyHallPick == playerPick || doors[montyHallPick] == CAR);
        }
        else {
            // Monty Hall picks random door
            do {
                Random random = new Random();
                montyHallPick = random.nextInt(3);
            } while (montyHallPick == playerPick);
        }
        return montyHallPick;
    }

    public static int getRandomDoor() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public static int getNewDoorIndex(Door[] doors, int playerPick, int montyHallPick) {
        int newIndex = -1;
        for (int i = 0; i < doors.length; i++) {
            if (i != playerPick && i != montyHallPick) {
                newIndex = i;
            }
        }
        return newIndex;
    }

    public static boolean getResult(Door[] doors, int playerPick) {
        return doors[playerPick] == CAR;
    }
}
