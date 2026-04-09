package ca.bcit.comp2522.project.games.word;

import ca.bcit.comp2522.project.service.DirectoryToList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class World
{
    private final static int ARRAY_SIZE = 3;
    private final static int COUNT_MIN = 0;

    private final Map<String, Country> countries;

    public World() throws IOException
    {
        countries = mapCountries();
    }

    public final Map<String, Country> getCountries()
    {
        return this.countries;
    }

    private final Map<String, Country> mapCountries() throws IOException
    {
        final List<String> list;
        final Map<String, Country> mappedCountries;

        list = DirectoryToList.readDirectoryToListBlock("textFiles/countries");
        mappedCountries = new HashMap<>();

        list.forEach(block ->
        {
            final Scanner scan;
            final Country country;
            final String name;
            final String capitalCity;
            final String[] facts;
            final String[] temp;

            int count;

            scan = new Scanner(block);
            facts = new String[ARRAY_SIZE];
            count = COUNT_MIN;

            temp = scan.nextLine().split(":");
            name = temp[0];
            capitalCity = temp[1];

            while (scan.hasNextLine())
            {
                facts[count] = scan.nextLine();
                ++count;
            }

            country = new Country(name, capitalCity, facts);

            mappedCountries.put(name, country);
        });

        return mappedCountries;
    }



}
