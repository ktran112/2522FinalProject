package ca.bcit.comp2522.project.games.word.services;

import ca.bcit.comp2522.project.service.FileToList;
import ca.bcit.comp2522.project.service.FileWriterService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Score
{
    private static final int UNIVERSAL_MIN = 0;
    private static final int SINGULAR = 1;
    private static final int POINTS_ON_FIRST_TRY = 2;
    private static final int POINTS_ON_SECOND_TRY = 1;
    private static final int DECIMAL_TO_WHOLE_NUMBER = 100;

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

    public final List<String> scoreToList() throws IOException {
        final List<String> list;

        list = new ArrayList<>();

        list.add("Date and Time: " + LocalDateTime.now().format(formatter));
        list.add("Games Played: " + this.numGamesPlayed);
        list.add("Correct First Attempts: " + this.numCorrectFirstAttempt);
        list.add("Correct Second Attempts: " + this.numCorrectSecondAttempt);
        list.add("Incorrect Attempts: " + this.numIncorrectTwoAttempts);
        list.add("Total points: " + this.points);
        list.add("\n");
        list.add(getGameSummarized());
        list.add("\n");
        list.add(getScoreMessage());
        list.add("\n");

        return list;
    }

    public final String getGameSummarized()
    {
        final DecimalFormat df;
        final String avg;
        final String gameAmount;

        df = new DecimalFormat("#.##");

        if (this.points == UNIVERSAL_MIN)
        {
            avg = "" + UNIVERSAL_MIN;
        }

        else
        {
            avg =  df.format((float) this.points / this.numGamesPlayed);
        }

        gameAmount = (this.numGamesPlayed > SINGULAR) ? "games" : "game";


        return "Total is " + this.points + " points in " + this.numGamesPlayed +
               " " + gameAmount + ", for an average score of " + avg +
               " points per game.";
    }

    public final String getScoreMessage() throws IOException
    {
        final DecimalFormat df;
        final DateTimeFormatter formatter;
        final String avg;
        final String pointAmount;
        final String highScorePointAmount;

        df = new DecimalFormat("#.##");
        formatter = DateTimeFormatter.ofPattern("'on 'yyyy-MM-dd' at 'HH:mm:ss");
        avg =  df.format( (float) this.points / this.numGamesPlayed);
        pointAmount = (this.points == SINGULAR) ? "points" : "point";
        highScorePointAmount = (getCurrentHighScore() == SINGULAR) ? "points" : "point";

        if (isThisScoreHighest())
        {
            if (getCurrentHighScoreDate() == null)
            {
                return "CONGRATULATIONS! You have a new high score, with an average of\n" +
                        avg + " " + pointAmount + " per game.";
            }

            else
            {
                return "CONGRATULATIONS! You have a new high score, with an average of\n" +
                        avg + " " + pointAmount + " per game; the previous record was " +
                        df.format(getCurrentHighScore()) + " " + highScorePointAmount +
                        " per game\n" + LocalDateTime.now().format(formatter);
            }
        }

        else if (getCurrentHighScoreIndex() != -1)
        {
            return "You did not beat the high score of " +
                                 df.format(getCurrentHighScore()) + " " + highScorePointAmount +
                                 " per game\n" + getCurrentHighScoreDate();
        }

        else
        {
            return "";
        }
    }

    public final boolean isThisScoreHighest() throws IOException
    {
        final float avg;

        avg = (float) this.points / this.numGamesPlayed;

        System.out.println(Math.round(avg * DECIMAL_TO_WHOLE_NUMBER) > Math.round(getCurrentHighScore() * DECIMAL_TO_WHOLE_NUMBER));
        return Math.round(avg * DECIMAL_TO_WHOLE_NUMBER) > Math.round(getCurrentHighScore() * DECIMAL_TO_WHOLE_NUMBER);
    }

    public final float getCurrentHighScore() throws IOException
    {
        final List<String> scores;
        final Optional<Float> highScore;

        scores = FileToList.read("scoreLogs/highscores/highscores.txt");

        highScore = scores.stream()
                          .map(s->s.split("\\|")[0])
                          .map(Float::parseFloat)
                          .max(Float::compareTo);

        return highScore.orElse(0.0f);
    }

    public final int getCurrentHighScoreIndex() throws IOException
    {
        final List<String> scores;
        final List<Float> list;

        scores = FileToList.read("scoreLogs/highscores/highscores.txt");

        if (scores.isEmpty())
        {
            return -1;
        }

        list = scores.stream()
                .map(s->s.split("\\|")[0])
                .map(Float::parseFloat)
                .toList();

        return list.indexOf(getCurrentHighScore());
    }

    public final String getCurrentHighScoreDate() throws IOException
    {
        final List<String> scores;

        scores = FileToList.read("scoreLogs/highscores/highscores.txt");

        if (getCurrentHighScoreIndex() == -1)
        {
            return null;
        }

        return scores.get(getCurrentHighScoreIndex()).split("\\|")[1];
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
        if (this.numGamesPlayed == 0)
        {
            return;
        }

        FileWriterService.appendExistingFile("scoreLogs", "score", scoreToList());
    }

    public final void updateHighScoreFile() throws IOException
    {
        final DecimalFormat df;
        final DateTimeFormatter formatter;
        final List<String> scores;
        final float avg;
        final boolean sameScore;

        df = new DecimalFormat("#.##");
        formatter = DateTimeFormatter.ofPattern("'on 'yyyy-MM-dd' at 'HH:mm:ss");
        avg = (float) this.points / this.numGamesPlayed;
        scores = FileToList.read("scoreLogs/highscores/highscores.txt");

        sameScore = scores.stream()
                .map(s->s.split("\\|")[0])
                .map(Float::parseFloat)
                .anyMatch(f->Math.round(f * DECIMAL_TO_WHOLE_NUMBER) ==
                                   Math.round(avg * DECIMAL_TO_WHOLE_NUMBER));

        if (this.numGamesPlayed == UNIVERSAL_MIN)
        {
            return;
        }

        if (!sameScore && avg != 0.0F)
        {
            FileWriterService.appendExistingFile("scoreLogs/highscores",
                                                    "highscores",
                                                   df.format(avg) + "|" + LocalDateTime.now().format(formatter));
        }
    }
}
