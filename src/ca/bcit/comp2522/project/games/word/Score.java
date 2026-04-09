package ca.bcit.comp2522.project.games.word;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score
{
    private static final int UNIVERSAL_MIN = 0;

    private String dateTimePlayed;
    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;

    public Score()
    {
        // TODO still needs to work on this class
        final DateTimeFormatter formatter;

        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        this.dateTimePlayed = LocalDateTime.now().format(formatter);
        this.numGamesPlayed = UNIVERSAL_MIN;
        this.numCorrectFirstAttempt = UNIVERSAL_MIN;
        this.numCorrectSecondAttempt = UNIVERSAL_MIN;
        this.numIncorrectTwoAttempts = UNIVERSAL_MIN;
    }

    public final String getDateTimePlayed() {
        return this.dateTimePlayed;
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
}
