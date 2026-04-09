package ca.bcit.comp2522.project.hud;

import ca.bcit.comp2522.project.games.MyGame;
import ca.bcit.comp2522.project.games.NumberGame;
import ca.bcit.comp2522.project.games.WordGame;
import ca.bcit.comp2522.project.service.InputValidator;

public class TerminalGUI
{
    private static final char WORD_GAME_CHAR = 'w';
    private static final char NUMBER_GAME_CHAR = 'n';
    private static final char MY_GAME_CHAR = 'm';
    private static final char EXIT_CHAR = 'q';

    public TerminalGUI()
    {
        final char userInput;

        System.out.println("Press W to play the Word game.\n" +
                           "Press N to play the Number game.\n" +
                           "Press M to play the <your game's name> game.\n" +
                           "Press Q to quit.");

        userInput = ensureValidInput();

        switch (userInput)
        {
            case WORD_GAME_CHAR -> enterWord();
            case NUMBER_GAME_CHAR -> enterNumber();
            case MY_GAME_CHAR -> enterMyGame();
            case EXIT_CHAR -> exitGame();
        }
    }

    private final void enterWord()
    {
        final WordGame game;

        game = new WordGame();
    }

    private final void enterNumber()
    {
        final NumberGame game;

        game = new NumberGame();
    }

    private final void enterMyGame()
    {
        final MyGame game;

        game = new MyGame();
    }

    private final void exitGame()
    {
        return;
    }

    private final char ensureValidInput()
    {
        char testInput;

        testInput = InputValidator.userInput();

        while (!(testInput == WORD_GAME_CHAR ||
                 testInput == NUMBER_GAME_CHAR||
                 testInput == MY_GAME_CHAR ||
                 testInput == EXIT_CHAR))
        {
            System.out.println("Invalid input. Try again");
            System.out.println("Press W to play the Word game.\n" +
                               "Press N to play the Number game.\n" +
                               "Press M to play the <your game's name> game.\n" +
                               "Press Q to quit.");

            testInput = InputValidator.userInput();
        }

        return testInput;
    }

}

