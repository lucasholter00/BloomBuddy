package com.group18.BloomBuddy;

import java.util.Observer;

public interface myObservable {
    void addObserver(myObserver observer);
    void removeObserver(myObserver observer);
    void notifyObservers();
}
