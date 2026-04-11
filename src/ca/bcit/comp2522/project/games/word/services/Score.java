package ca.bcit.comp2522.project.games.word.services;

import ca.bcit.comp2522.project.service.FileWriterService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score
{
    private static final int UNIVERSAL_MIN = 0;
    private static final int SINGULAR = 1;
    private static final int POINTS_ON_FIRST_TRY = 2;
    private static final int POINTS_ON_SECOND_TRY = 1;

    private final DateTimeFormatter formatter;

    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;
    private int points;

    public Score()
    {

        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");;
        this.numGamesPlayed = UNIVERSAL_MIN;
        this.numCorrectFirstAttempt = UNIVERSAL_MIN;
        this.numCorrectSecondAttempt = UNIVERSAL_MIN;
        this.numIncorrectTwoAttempts = UNIVERSAL_MIN;
    }

    public final void addNumGamesPlayed()
    {
        this.numGamesPlayed++;
    }

    public final void addNumCorrectFirstAttempt() {
        this.numCorrectFirstAttempt++;
    }

    public final void addNumCorrectSecondAttempt() {
        this.numCorrectSecondAttempt++;
    }

    public final void addNumIncorrectTwoAttempts() {
        this.numIncorrectTwoAttempts++;
    }

    public final void addPointsOnFirstTry()
    {
        this.points += POINTS_ON_FIRST_TRY;
    }

    public final void addPointsOnSecondTry()
    {
        this.points += POINTS_ON_SECOND_TRY;
    }

    public final List<String> scoreToList()
    {
        final List<String> list;

        list = new ArrayList<>();

        list.add("Date and Time: " + LocalDateTime.now().format(formatter));
        list.add("Games Played: " + this.numGamesPlayed);
        list.add("Correct First Attempts: " + this.numCorrectFirstAttempt);
        list.add("Correct Second Attempts: " + this.numCorrectSecondAttempt);
        list.add("Incorrect Attempts: " + this.numIncorrectTwoAttempts);
        list.add("Total points: " + this.points);
        list.add("\n");

        return list;
    }

    public final int getNumGamesPlayed() {
        return this.numGamesPlayed;
    }

    public final int getNumCorrectFirstAttempt() {
        return this.numCorrectFirstAttempt;
    }

    public final int getNumCorrectSecondAttempt() {
        return this.numCorrectSecondAttempt;
    }

    public final int getNumIncorrectTwoAttempts() {
        return this.numIncorrectTwoAttempts;
    }

    public final void logScore() throws IOException
    {
        FileWriterService.logExistingFile("scoreLogs", "score", scoreToList());
    }
}
