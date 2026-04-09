package ca.bcit.comp2522.project.service;

import java.util.Scanner;

public class InputValidator
{
    public static final boolean validateInput(final String input)
    {
        return !(input == null || input.isBlank() || input.length() > 1);
    }

    public static final char userInput()
    {
        String input;
        final Scanner scan;

        scan = new Scanner(System.in);

        input = scan.nextLine().trim().toLowerCase();

        while (!validateInput(input))
        {
            System.out.println("Invalid input. Try again.");
            System.out.println("Press W to play the Word game.\n" +
                               "Press N to play the Number game.\n" +
                               "Press M to play the <your game's name> game.\n" +
                               "Press Q to quit.");
            input = scan.nextLine().trim().toLowerCase();
        }

        return input.charAt(0);
}
}


