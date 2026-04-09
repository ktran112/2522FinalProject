package ca.bcit.comp2522.project.interfaces;

/**
 * An ca.bcit.comp2522.project.interfaces.Observer with a method to receive news of some kind.
 *
 * @author Thor Baker
 *
 * @version 1.0
 */
public interface Observer
{
    void update(final String news);
}