package com.group18.BloomBuddy;

public abstract class SensorData<T> {
    private final T value;

    public SensorData(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}
