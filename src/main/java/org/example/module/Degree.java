package org.example.module;


public enum Degree {
    I(12), 
    II(14), 
    III(16), 
    IV(17), 
    V(19), 
    VI(21), 
    VII(23);

    private final int keyNumber;
    public int getKeyNumber() {
        return keyNumber;
    }
    Degree(int keyNumber) {
        this.keyNumber = keyNumber;
    }
}