package ca.bcit.comp2522.project.games;

import ca.bcit.comp2522.project.games.word.objects.Country;
import ca.bcit.comp2522.project.games.word.objects.World;
import ca.bcit.comp2522.project.games.word.services.Score;
import ca.bcit.comp2522.project.service.InputValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGame
{
    private static final char YES_CHAR = 'y';
    private static final char NO_CHAR = 'n';
    private static final char BACK_CHAR = 'b';
    private static final int QUESTION_AMOUNT = 10;
    private static final int QUESTION_TYPE_AMOUNT = 3;
    private static final int CAPITAL_EXPECTS_COUNTRY = 0;
    private static final int COUNTRY_EXPECTS_CAPITAL = 1;
    private static final int FACT_EXPECTS_COUNTRY = 2;
    private static final int GUESSES_ALLOWED = 2;
    private static final int UNIVERSAL_MIN = 0;

    private final World world;
    private final Score score;
    private int questionCount;

    public WordGame() throws IOException
    {
        this.world = new World();
        this.score = new Score();

        startGame();
    }

    private final void startGame() throws IOException
    {
        boolean playing;

        playing = confirmPlay();

        while (playing)
        {
            this.score.addNumGamesPlayed();
            final List<Country> listOfCountries;

            listOfCountries = loadQuestions();

            listOfCountries.forEach(this::askQuestion);

            this.questionCount = UNIVERSAL_MIN;

            playing = promptPlayAgain();
        }

        this.score.logScore();
        this.score.updateHighScoreFile();
    }

    private final boolean confirmPlay()
    {
        final char userInput;

        System.out.println("WORD GAME | " + QUESTION_AMOUNT + " Questions");
        System.out.println("Begin?" +
                           "\nPress Y to continue." +
                           "\nPress N or B to go back.");

        userInput = InputValidator.ensureValidCharIgnoreCase(YES_CHAR, NO_CHAR, BACK_CHAR);

        return userInput == YES_CHAR;
    }


    private final List<Country> loadQuestions()
    {
        final List<Country> countries;

        countries = new ArrayList<>();

        for (int i = 0; i < QUESTION_AMOUNT; ++i)
        {
            final Country country;

            country = this.world.getCountryByKey(this.world.getRandomCountryKey());

            countries.add(country);
        }

        return countries;
    }

    private final void askQuestion(final Country country)
    {
        final Random rng;
        final int questionType;

        rng = new Random();

        questionType = rng.nextInt(QUESTION_TYPE_AMOUNT);

        switch (questionType)
        {
            case CAPITAL_EXPECTS_COUNTRY -> giveCapitalExpectCountry(country);
            case COUNTRY_EXPECTS_CAPITAL -> giveCountryExpectCapital(country);
            case FACT_EXPECTS_COUNTRY    -> giveFactExpectCountry(country);
        }
    }

    private final void giveCapitalExpectCountry(final Country country)
    {
        ++this.questionCount;
        System.out.println("Question " + this.questionCount + "/" + QUESTION_AMOUNT + ":");
        System.out.println("The capital of this country is: " + country.getCapitalCityName());
        System.out.println("What is the country? Provide your guess (1/" + GUESSES_ALLOWED + "): ");

        checkAnswerProcedure(country.getName());
    }

    private final void giveCountryExpectCapital(final Country country)
    {
        ++this.questionCount;
        System.out.println("Question " + this.questionCount + "/" + QUESTION_AMOUNT + ":");
        System.out.println("The country of this capital is: " + country.getName());
        System.out.println("What is the capital? Provide your guess (1/" + GUESSES_ALLOWED + "): ");

        checkAnswerProcedure(country.getCapitalCityName());
    }

    private final void giveFactExpectCountry(final Country country)
    {
        final Random rng;
        final int factIndex;

        rng = new Random();
        factIndex = rng.nextInt(World.FACTS_AMOUNT);

        ++this.questionCount;
        System.out.println("Question " + this.questionCount + "/" + QUESTION_AMOUNT + ":");
        System.out.println("A fact of this country is: " + country.getFacts()[factIndex]);
        System.out.println("What is this country? Provide your guess (1/" + GUESSES_ALLOWED + "): ");

        checkAnswerProcedure(country.getName());
    }

    private final void checkAnswerProcedure(final String answer)
    {
        String input;
        int count;

        count = UNIVERSAL_MIN;

        input = InputValidator.userInputIgnoreCase();

        ++count;

        if (input.equalsIgnoreCase(answer))
        {
            this.score.addNumCorrectFirstAttempt();
            this.score.addPointsOnFirstTry();
            System.out.println("\nCORRECT\n");
            return;
        }

        ++count;

        System.out.println("\nINCORRECT. Try again (" + count + "/" + GUESSES_ALLOWED +" guesses): ");

        input = InputValidator.userInputIgnoreCase();

        if (input.equalsIgnoreCase(answer))
        {
            this.score.addNumCorrectSecondAttempt();
            this.score.addPointsOnSecondTry();
            System.out.println("\nCORRECT\n");
            return;
        }

        this.score.addNumIncorrectTwoAttempts();

        System.out.println("\nINCORRECT. The correct answer was: " + answer);
    }

    private final boolean promptPlayAgain()
    {
        final char userInput;

        System.out.println("\nDo you want to play again?");
        System.out.println("\nPress Y to play again." +
                           "\nPress N or B to go back.");

        userInput = InputValidator.ensureValidCharIgnoreCase(YES_CHAR, NO_CHAR, BACK_CHAR);

        return userInput == YES_CHAR;
    }



}
