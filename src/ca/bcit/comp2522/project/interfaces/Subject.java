package ca.bcit.comp2522.project.interfaces;

/**
 * A subject that has many subscribers (observers) that react to this.subject state changes.
 *
 * @author Kiet Tran
 *
 * @version 1.0
 */
public interface Subject
{
    void addSubscriber(final Observer sub);

    void removeSubscriber(final Observer sub);

    void notifySubscribers();
}
