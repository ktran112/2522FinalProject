package ca.bcit.comp2522.project.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileWriterService
{
    private static final String FILE_EXTENSION = ".txt";

    public static final void logNewFile(final String directory,
                                        final String name,
                                        final List<String> logs) throws IOException
    {
        final DateTimeFormatter formatter;
        final Path path;
        final String logName;

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        logName = LocalDateTime.now().format(formatter) + name + FILE_EXTENSION;
        path = Paths.get(directory, logName);


        try (final BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND))
        {

            for (String line : logs)
            {
                writer.write(line);
                writer.newLine();
            }
        }

        catch (IOException e)
        {
            throw new IOException("Failed to write to: \"" + path + "\"", e);
        }
    }

    public static final void appendExistingFile(final String directory,
                                                final List<String> logs) throws IOException
    {
        final DateTimeFormatter formatter;
        final Path path;
        final String logName;

        path = Paths.get(directory);


        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE))
        {
            for (String line : logs)
            {
                writer.write(line);
                writer.newLine();
            }
        }

        catch (IOException e)
        {
            throw new IOException("Failed to write to: \"" + path + "\"", e);
        }
    }

    public static final void appendExistingFile(final String directory,
                                                final String append) throws IOException
    {
        final DateTimeFormatter formatter;
        final Path path;
        final String logName;

        path = Paths.get(directory);

        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE))
        {
                writer.write(append);
                writer.newLine();
        }

        catch (IOException e)
        {
            throw new IOException("Failed to write to: \"" + path + "\"", e);
        }
    }
}
