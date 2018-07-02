package main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for asking the user to select a scenario.
 *
 * @author Eddie Summers
 */
public class InputProvider {

    private Scanner in;

    public InputProvider() {
        in = new Scanner(System.in);
    }

    /**
     * Prompt the user to select a scenario from the provided list, and return
     * it.
     * @param scenarios
     * @return Scenario
     */
    public Scenario selectScenario(List<Scenario> scenarios) {

        System.out.println("Welcome to Orbit Simulator! Please select a " +
                "scenario from the list below using its number.\n");

        for (Scenario scenario : scenarios) {
            System.out.println((scenarios.indexOf(scenario) + 1) + ". " +
                    scenario.getName());
        }

        System.out.println();

        int input = 0;

        while (input < 1 || input > scenarios.size()) {
            input = readNextInteger();
        }

        return scenarios.get(input - 1);
    }

    /**
     * Read integer input from user, ignoring invalid entries.
     * @return int
     */
    private int readNextInteger() {

        boolean validEntry = false;
        int value = 0;

        // Catch a non-integer entry and ignore it
        while (!validEntry) {

            try {
                value = in.nextInt();
                validEntry = true;
            } catch (InputMismatchException e) {
                break;
            }
        }

        // Skip over any unconsumed input
        in.nextLine();

        return value;
    }

}
