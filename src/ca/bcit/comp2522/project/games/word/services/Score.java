package ca.bcit.comp2522.project.games.word.services;

import ca.bcit.comp2522.project.service.FileToList;
import ca.bcit.comp2522.project.service.FileWriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int FIELDS_TO_COVER = 5;

    private final LocalDateTime dateTime;
    private final int numGamesPlayed;
    private final int numCorrectFirstAttempt;
    private final int numCorrectSecondAttempt;
    private final int numIncorrectTwoAttempts;
    private final int points;

    public Score(final LocalDateTime dateTime,
                 final int numGamesPlayed,
                 final int numCorrectFirstAttempt,
                 final int numCorrectSecondAttempt,
                 final int numIncorrectTwoAttempts)
    {
        this.dateTime = dateTime;
        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
        this.points = (numCorrectFirstAttempt * POINTS_ON_FIRST_TRY) +
                      (numCorrectSecondAttempt * POINTS_ON_SECOND_TRY);
    }


    public final List<String> scoreToListWithSummary() throws IOException {
        final List<String> list;

        list = new ArrayList<>();

        list.add("Date and Time: " + this.dateTime.format(DATE_TIME_FORMATTER));
        list.add("Games Played: " + this.numGamesPlayed);
        list.add("Correct First Attempts: " + this.numCorrectFirstAttempt);
        list.add("Correct Second Attempts: " + this.numCorrectSecondAttempt);
        list.add("Incorrect Attempts: " + this.numIncorrectTwoAttempts);
        list.add("Points: " + this.points);
        list.add("\n");
        list.add(getGameSummarized());
        list.add("\n");
        list.add(getScoreMessage());
        list.add("\n");

        return list;
    }

    public final List<String> scoreToList() throws IOException {
        final List<String> list;

        list = new ArrayList<>();

        list.add("Date and Time: " + this.dateTime.format(DATE_TIME_FORMATTER));
        list.add("Games Played: " + this.numGamesPlayed);
        list.add("Correct First Attempts: " + this.numCorrectFirstAttempt);
        list.add("Correct Second Attempts: " + this.numCorrectSecondAttempt);
        list.add("Incorrect Attempts: " + this.numIncorrectTwoAttempts);
        list.add("Points: " + this.points);

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
                        " per game\n" + getCurrentHighScoreDate();
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

        return Math.round(avg * DECIMAL_TO_WHOLE_NUMBER) > Math.round(getCurrentHighScore() * DECIMAL_TO_WHOLE_NUMBER);
    }

    public final float getCurrentHighScore() throws IOException
    {
        final List<String> scores;
        final Optional<Float> highScore;

        scores = FileToList.read("scoreLogs/highscores/highscore.txt");

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

        scores = FileToList.read("scoreLogs/highscores/highscore.txt");

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

        scores = FileToList.read("scoreLogs/highscores/highscore.txt");

        if (getCurrentHighScoreIndex() == -1)
        {
            return null;
        }

        return scores.get(getCurrentHighScoreIndex()).split("\\|")[1];
    }

    public final int getScore()
    {
        return this.points;
    }


    public final int getNumGamesPlayed()
    {
        return this.numGamesPlayed;
    }

    public final int getNumCorrectFirstAttempt()
    {
        return this.numCorrectFirstAttempt;
    }

    public final int getNumCorrectSecondAttempt()
    {
        return this.numCorrectSecondAttempt;
    }

    public final int getNumIncorrectTwoAttempts()
    {
        return this.numIncorrectTwoAttempts;
    }

    @Override
    public String toString() {
        return "Date and Time: " + dateTime.format(DATE_TIME_FORMATTER) + "\n" +
                "Games Played: " + this.numGamesPlayed + "\n" +
                "Correct First Attempts: " + this.numCorrectFirstAttempt + "\n" +
                "Correct Second Attempts: " + this.numCorrectSecondAttempt + "\n" +
                "Incorrect Attempts: " + this.numIncorrectTwoAttempts + "\n" +
                "Score: " + this.points + " points" +
                "\n";
    }

    public static final void appendScoreToFile(final Score score,
                                               final String directory) throws IOException
    {
        FileWriterService.appendExistingFile(directory, score.scoreToList());
    }

    public static List<Score> readScoresFromFile(final String directory) throws IOException
    {
        final List<Score> scores;
        final List<String> lines;
        final Path path;

        LocalDateTime dateTime;
        int numGamesPlayed;
        int numCorrectFirstAttempt;
        int numCorrectSecondAttempt;
        int numIncorrectTwoAttempts;
        int count;

        path = Paths.get(directory);
        scores = new ArrayList<>();
        lines = Files.readAllLines(path);

        dateTime = null;
        numGamesPlayed = UNIVERSAL_MIN;
        numCorrectFirstAttempt = UNIVERSAL_MIN;
        numCorrectSecondAttempt = UNIVERSAL_MIN;
        numIncorrectTwoAttempts = UNIVERSAL_MIN;
        count = UNIVERSAL_MIN;


        for (final String line : lines)
        {
            if (line.startsWith("Date and Time: "))
            {
                dateTime = LocalDateTime.parse(line.substring("Date and Time: ".length()), DATE_TIME_FORMATTER);
                ++count;
            }

            else if (line.startsWith("Games Played: "))
            {
                numGamesPlayed = Integer.parseInt(line.substring("Games Played: ".length()).trim());
                ++count;
            }

            else if (line.startsWith("Correct First Attempts: "))
            {
                numCorrectFirstAttempt = Integer.parseInt(line.substring("Correct First Attempts: ".length()).trim());
                ++count;
            }

            else if (line.startsWith("Correct Second Attempts: "))
            {
                numCorrectSecondAttempt = Integer.parseInt(line.substring("Correct Second Attempts: ".length()).trim());
                ++count;
            }

            else if (line.startsWith("Incorrect Attempts: "))
            {
                numIncorrectTwoAttempts = Integer.parseInt(line.substring("Incorrect Attempts: ".length()).trim());
                ++count;
            }

            else if (line.isBlank() && dateTime != null)
            {
                scores.add(new Score(dateTime, numGamesPlayed, numCorrectFirstAttempt,
                                     numCorrectSecondAttempt, numIncorrectTwoAttempts));

                dateTime = null;
                numGamesPlayed = UNIVERSAL_MIN;
                numCorrectFirstAttempt = UNIVERSAL_MIN;
                numCorrectSecondAttempt = UNIVERSAL_MIN;
                numIncorrectTwoAttempts = UNIVERSAL_MIN;

            }

            if (count == FIELDS_TO_COVER)
            {
                count = UNIVERSAL_MIN;
            scores.add(new Score(dateTime, numGamesPlayed, numCorrectFirstAttempt,
                                 numCorrectSecondAttempt, numIncorrectTwoAttempts));
            }
        }

        return scores;
    }

    public static final void updateHighScoreFile(final Score score) throws IOException
    {
        final DecimalFormat df;
        final DateTimeFormatter formatter;
        final List<String> scores;
        final float avg;
        final boolean sameScore;

        df = new DecimalFormat("#.##");
        formatter = DateTimeFormatter.ofPattern("'on 'yyyy-MM-dd' at 'HH:mm:ss");
        avg = (float) score.getScore() / score.getNumGamesPlayed();
        scores = FileToList.read("scoreLogs/highscores/highscore.txt");

        sameScore = scores.stream()
                .map(s->s.split("\\|")[0])
                .map(Float::parseFloat)
                .anyMatch(f->Math.round(f * DECIMAL_TO_WHOLE_NUMBER) ==
                        Math.round(avg * DECIMAL_TO_WHOLE_NUMBER));

        if (score.getNumGamesPlayed() == UNIVERSAL_MIN)
        {
            return;
        }

        if (!sameScore && avg != 0.0F)
        {
            FileWriterService.appendExistingFile("scoreLogs/highscores/highscore.txt",
                                                  df.format(avg) + "|" + LocalDateTime.now().format(formatter));
        }
    }
}
