package ca.bcit.comp2522.project.games.word.objects;

public class Country
{
    private static final int ARRAY_SIZE = 3;

    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    public Country(final String name,
                   final String capitalCityName,
                   final String[] facts)
    {
        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    public final String getName()
    {
        return this.name;
    }

    public final String getCapitalCityName()
    {
        return this.capitalCityName;
    }

    public final String[] getFacts()
    {
        return this.facts;
    }
}
