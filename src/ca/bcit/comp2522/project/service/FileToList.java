package ca.bcit.comp2522.project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileToList {

    public static final List<String> read(final String directory) throws IOException {
        final Path path;
        final List<String> myList;

        path = Paths.get(directory);
        myList = new ArrayList<>();

        try (final BufferedReader reader = Files.newBufferedReader(path))
        {
            String line;

            while ((line = reader.readLine()) != null) {
                myList.add(line);
            }

            return myList;
        }

        catch (IOException e)
        {
            throw new IOException("Failed to grab from: \")" + directory + "\"");
        }
    }

}
