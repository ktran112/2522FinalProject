package ca.bcit.comp2522.project.service;

import ca.bcit.comp2522.project.interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventManager
{
    private final Map<String, List<Observer>> observers;

    public EventManager()
    {
        this.observers = new HashMap<>();
    }

    public final void addObserver(final String eventType,
                                  final Observer o)
    {
        this.observers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(o);
    }

    public final void removeObserver(final String eventType,
                                     final Observer o)
    {
        this.observers.get(eventType).remove(o);
    }

    private final void notifyObservers(final String eventType, final String data)
    {
        if (this.observers.containsKey(eventType))
        {
            this.observers.get(eventType).forEach(obs -> obs.update(data));
        }
    }
}
