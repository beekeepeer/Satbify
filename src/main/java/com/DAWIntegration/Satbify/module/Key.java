package com.DAWIntegration.Satbify.module;

public enum Key{
    C(0),
    C_Sharp(1),
    D(2),
    D_Sharp(3),
    E(4),
    F(5),
    F_Sharp(6),
    G(7),
    G_Sharp(8),
    A(9),
    A_Sharp(10),
    B(11);

    private final int keyNumber;

    public int getKeyNumber() {
        return keyNumber;
    }

    Key(int keyNumber) {
        this.keyNumber = keyNumber;
    }
}
