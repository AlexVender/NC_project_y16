package org.netcracker.unc.group16.model;

/**
 * Created by Ivan on 30.11.2015.
 */
public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
}
