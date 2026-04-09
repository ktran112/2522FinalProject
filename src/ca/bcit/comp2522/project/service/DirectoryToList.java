package ca.bcit.comp2522.project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryToList
{
    public static final List<String> readDirectoryToListBlock(final String directory) throws IOException
    {
        final List<String> list;

        list = new ArrayList<>();

        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.txt"))
        {
            stream.forEach(entry -> {

                try (final BufferedReader reader = Files.newBufferedReader(entry))
                {
                    String lines;
                    StringBuilder block;

                    block = new StringBuilder();

                    while ((lines = reader.readLine()) != null) {
                        if (lines.trim().isEmpty())
                        {
                            if (!block.isEmpty())
                            {
                                list.add(block.toString().trim());
                                block.setLength(0);
                            }
                        }

                        else
                        {
                            block.append(lines.trim()).append("\n");
                        }
                    }

                    if (!block.isEmpty())
                    {
                        list.add(block.toString().trim());
                    }
                }

                catch (IOException e)
                {
                    try {
                        throw new IOException("Failed to grab from: \")" + directory + "\"");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            return list.stream()
                       .filter(line->!line.isEmpty())
                       .toList();
        }
    }

    public static final List<String> readDirectoryToListLine(final String directory) throws IOException
    {
        final List<String> list;

        list = new ArrayList<>();

        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.txt"))
        {
            stream.forEach(entry -> {

                try (final BufferedReader reader = Files.newBufferedReader(entry))
                {
                    String line;

                    while ((line = reader.readLine()) != null)
                    {
                        list.add(line);
                    }
                }

                catch (IOException e)
                {
                    try
                    {
                        throw new IOException("Failed to grab from: \")" + directory + "\"");
                    }

                    catch (IOException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
            });

            return list.stream()
                       .filter(line->!line.isEmpty())
                       .toList();
        }
    }
}
