package ca.bcit.comp2522.project.games.word.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score
{
    private static final int UNIVERSAL_MIN = 0;
    private static final int SINGULAR = 1;

    private String dateTimePlayed;
    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;

    public Score()
    {
        final DateTimeFormatter formatter;

        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        this.dateTimePlayed = LocalDateTime.now().format(formatter);
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

    public final List<String> scoreToList()
    {
        final List<String> list;

        list = new ArrayList<>();

        if (this.numGamesPlayed > UNIVERSAL_MIN)
        {
            list.add((this.numGamesPlayed > SINGULAR) ? this.numGamesPlayed + "Word games played" :
                                                        this.numGamesPlayed + "Word game played");
        }

        if (this.numCorrectFirstAttempt > UNIVERSAL_MIN)
        {
            list.add((this.numCorrectFirstAttempt + "correct answers on the first attempt"));
        }

        if (this.numCorrectSecondAttempt > UNIVERSAL_MIN)
        {
            list.add((this.numCorrectSecondAttempt + "correct answers on the second attempt"));
        }

        if (this.numIncorrectTwoAttempts > UNIVERSAL_MIN)
        {
            list.add((this.numIncorrectTwoAttempts + "incorrect answers on two attempts each"));
        }

        return list;
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
