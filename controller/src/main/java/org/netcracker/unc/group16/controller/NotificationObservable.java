package org.netcracker.unc.group16.controller;

/**
 * Created by Ivan on 07.12.2015.
 */
public interface NotificationObservable {
    void registerObserver(NotificationObserver o);
    void removeObserver(NotificationObserver o);
    void notifyObservers();
}
