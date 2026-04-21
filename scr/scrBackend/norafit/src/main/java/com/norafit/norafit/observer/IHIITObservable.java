package com.norafit.norafit.observer;

public interface IHIITObservable {
    void addObserver(IHIITObserver observer);
    void removeObserver(IHIITObserver observer);
    void notifyObservers(HIITEventData data);
}
