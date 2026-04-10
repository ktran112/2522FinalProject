package ca.bcit.comp2522.project.games.word.objects;

import ca.bcit.comp2522.project.service.DirectoryToList;

import java.io.IOException;
import java.util.*;

public class World
{
    public static final int FACTS_AMOUNT = 3;

    private final static int COUNT_MIN = 0;

    private final Map<String, Country> countries;

    public World() throws IOException
    {
        countries = mapCountries();
    }

    public final String getRandomCountryKey()
    {
        final List<String> key;
        final Random rng;
        final String randomKey;

        key = new ArrayList<>(this.countries.keySet());
        rng = new Random();

        randomKey = key.get(rng.nextInt(key.size()));

        return randomKey;
    }

    public final Country getCountryByKey(final String key) {
        if (!this.countries.containsKey(key))
        {
            System.out.println("No key/country");
            return null;
        }

            return this.countries.get(key);

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
            facts = new String[FACTS_AMOUNT];
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
