package ca.bcit.comp2522.project.service;

import java.util.Scanner;

public class InputValidator
{
    private static final boolean validateInputChar(final String input)
    {
        return !(input == null || input.isBlank() || input.length() > 1);
    }

    private static final boolean validateInputString(final String input)
    {
        return !(input == null || input.isBlank());
    }

    public static final String userInput()
    {
        String input;
        final Scanner scan;

        scan = new Scanner(System.in);

        input = scan.nextLine().trim();

        while (!validateInputString(input))
        {
            System.out.println("Invalid input. Try again.");

            input = scan.nextLine().trim();
        }

        return input;
    }

    public static final String userInputIgnoreCase()
    {
        String input;
        final Scanner scan;

        scan = new Scanner(System.in);

        input = scan.nextLine().trim().toLowerCase();

        while (!validateInputString(input))
        {
            System.out.println("Invalid input. Try again.");

            input = scan.nextLine().trim().toLowerCase();
        }

        return input;
    }

    public static final char userChar()
    {
        String input;
        final Scanner scan;

        scan = new Scanner(System.in);

        input = scan.nextLine().trim();

        while (!validateInputChar(input))
        {
            System.out.println("Invalid input. Try again.");

            input = scan.nextLine().trim();
        }

        return input.charAt(0);
}

    public static final char userCharIgnoreCase()
    {
        String input;
        final Scanner scan;

        scan = new Scanner(System.in);

        input = scan.nextLine().trim().toLowerCase();

        while (!validateInputChar(input))
        {
            System.out.println("Invalid input. Try again.");

            input = scan.nextLine().trim().toLowerCase();
        }

        return input.charAt(0);
    }


    public static final char ensureValidChar(final char... validInputs)
    {
        char testInput;

        testInput = InputValidator.userChar();

        while (true)
        {
            for (final char c : validInputs)
            {
                if (testInput == c)
                {
                    return testInput;
                }
            }
            System.out.println("Invalid input. Try again");

            testInput = InputValidator.userChar();
        }

    }

    public static final char ensureValidCharIgnoreCase(final char... validInputs)
    {
        char testInput;

        testInput = InputValidator.userCharIgnoreCase();

        while (true)
        {
            for (final char c : validInputs)
            {
                if (testInput == c)
                {
                    return testInput;
                }
            }
            System.out.println("Invalid input. Try again");

            testInput = InputValidator.userCharIgnoreCase();
        }

    }


}


